package dao;

import model.TipoRecurso;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoRecursoDAO {

    public void salvar(TipoRecurso tipoRecurso) {
        String sql;
        sql = "insert into tiporecurso (tiporecurso, descricao, ativo) values (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tipoRecurso.getTipoRecurso());
            stmt.setString(2, tipoRecurso.getDescricao());
            stmt.setBoolean(3, tipoRecurso.isAtivo());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tipoRecurso.setIdTipoRecurso(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tipo de recurso: ", e);
        }
    }

    public void atualizar(TipoRecurso tipoRecurso) {
        String sql;
        sql = "update tiporecurso set tiporecurso = ?, descricao = ?, ativo = ? where idtiporecurso = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoRecurso.getTipoRecurso());
            stmt.setString(2, tipoRecurso.getDescricao());
            stmt.setBoolean(3, tipoRecurso.isAtivo());
            stmt.setInt(4, tipoRecurso.getIdTipoRecurso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo de recurso: ", e);
        }
    }

    public void excluir(int id) {
        String sql;
        sql = "update tiporecurso set ativo = false where idtiporecurso = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir (inativar) tipo de recurso: ", e);
        }
    }

    public List<TipoRecurso> listarTodos() {
        String sql = "select * from tiporecurso where ativo = true";
        List<TipoRecurso> tipos = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoRecurso tipo = new TipoRecurso();
                tipo.setIdTipoRecurso(rs.getInt("idtiporecurso"));
                tipo.setTipoRecurso(rs.getString("tiporecurso"));
                tipo.setDescricao(rs.getString("descricao"));
                tipo.setAtivo(rs.getBoolean("ativo"));
                tipos.add(tipo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de recurso: ", e);
        }
        return tipos;
    }
}
