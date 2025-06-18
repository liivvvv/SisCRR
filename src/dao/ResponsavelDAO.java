package dao;

import model.Funcao;
import model.Responsavel;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsavelDAO {

    public void salvar(Responsavel responsavel) {
        String sql;
        sql = "insert into responsavel (idfuncao, nome, cracha, telefone, ativo) values (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, responsavel.getFuncao().getIdFuncao());
            stmt.setString(2, responsavel.getNome());
            stmt.setString(3, responsavel.getCracha());
            stmt.setString(4, responsavel.getTelefone());
            stmt.setBoolean(5, responsavel.isAtivo());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    responsavel.setIdResponsavel(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar responsável: ", e);
        }
    }

    public void atualizar(Responsavel responsavel) {
        String sql;
        sql = "update responsavel set idfuncao = ?, nome = ?, cracha = ?, telefone = ?, ativo = ? where idresponsavel = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, responsavel.getFuncao().getIdFuncao());
            stmt.setString(2, responsavel.getNome());
            stmt.setString(3, responsavel.getCracha());
            stmt.setString(4, responsavel.getTelefone());
            stmt.setBoolean(5, responsavel.isAtivo());
            stmt.setInt(6, responsavel.getIdResponsavel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar responsável: ", e);
        }
    }

    public void excluir(int id) {
        String sql;
        sql = "update responsavel set ativo = false where idresponsavel = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir (inativar) responsável: ", e);
        }
    }

    public List<Responsavel> listarTodos() {
        String sql = "select r.*, f.tipoFuncao, f.permissao " +
                "from responsavel r join funcao f on r.idfuncao = f.idfuncao where r.ativo = true";
        List<Responsavel> responsaveis = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcao funcao = new Funcao();
                funcao.setIdFuncao(rs.getInt("idfuncao"));
                funcao.setTipoFuncao(rs.getString("tipoFuncao"));
                funcao.setPermissao(rs.getString("permissao"));

                Responsavel resp = new Responsavel();
                resp.setIdResponsavel(rs.getInt("idresponsavel"));
                resp.setNome(rs.getString("nome"));
                resp.setCracha(rs.getString("cracha"));
                resp.setTelefone(rs.getString("telefone"));
                resp.setAtivo(rs.getBoolean("ativo"));
                resp.setFuncao(funcao);

                responsaveis.add(resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar responsáveis: ", e);
        }
        return responsaveis;
    }
}