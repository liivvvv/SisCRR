package dao;

import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import util.Conexao;

import java.sql.*;

public class UsuarioDAO {

    public void salvar(Usuario u) {
        String sql;
        sql = "INSERT INTO usuario (login, senha_hash, papel) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getSenhaHash());
            stmt.setString(3, u.getPapel());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) u.setIdUsuario(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean autenticar(String login, String senha) {
        String sql;
        sql = "SELECT senha_hash, ativo FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getBoolean("ativo")) {
                String hash = rs.getString("senha_hash");
                return BCrypt.checkpw(senha, hash);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario buscarPorLogin(String login) {
        String sql = "SELECT * FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idusuario"));
                u.setLogin(rs.getString("login"));
                u.setSenhaHash(rs.getString("senha_hash"));
                u.setPapel(rs.getString("papel"));
                u.setAtivo(rs.getBoolean("ativo"));
                u.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existeLogin(String login) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar login", e);
        }
        return false;
    }
}