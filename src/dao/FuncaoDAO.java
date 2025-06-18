package dao;

import model.Funcao;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncaoDAO {

    public List<Funcao> listarTodas() {
        String sql = "select * from funcao where ativo = true";
        List<Funcao> funcoes = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcao funcao = new Funcao();
                funcao.setIdFuncao(rs.getInt("idfuncao"));
                funcao.setTipoFuncao(rs.getString("tipoFuncao"));
                funcao.setPermissao(rs.getString("permissao"));
                funcao.setAtivo(rs.getBoolean("ativo"));
                funcoes.add(funcao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funções: ", e);
        }
        return funcoes;
    }
}
