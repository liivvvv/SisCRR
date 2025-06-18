package dao;

import model.*;
import util.Conexao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void salvar(Reserva reserva) {
        String sql;
        sql = "insert into reserva (idresponsavel, idtiporecurso, descricao, quantidade, dataLocacao, " +
                "horarioLocacao, dataDevolucao, horarioDevolucao, ativo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getResponsavel().getIdResponsavel());
            stmt.setInt(2, reserva.getTipoRecurso().getIdTipoRecurso());
            stmt.setString(3, reserva.getDescricao());
            stmt.setInt(4, reserva.getQuantidade());
            stmt.setDate(5, Date.valueOf(reserva.getDataLocacao()));
            stmt.setTime(6, Time.valueOf(reserva.getHorarioLocacao()));
            if (reserva.getDataDevolucao() != null) {
                stmt.setDate(7, Date.valueOf(reserva.getDataDevolucao()));
                stmt.setTime(8, Time.valueOf(reserva.getHorarioDevolucao()));
            } else {
                stmt.setNull(7, Types.DATE);
                stmt.setNull(8, Types.TIME);
            }
            stmt.setBoolean(9, reserva.isAtivo());

            stmt.executeUpdate();
            System.out.println("Salvo com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a reserva: " + e.getMessage(), e);
        }
    }

    public void atualizar(Reserva reserva) {
        String sql;
        sql = "update reserva set idresponsavel = ?, idtiporecurso = ?, descricao = ?, quantidade = ?, " +
                "dataLocacao = ?, horarioLocacao = ?, dataDevolucao = ?, horarioDevolucao = ?, ativo = ? " +
                "where idrecurso = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getResponsavel().getIdResponsavel());
            stmt.setInt(2, reserva.getTipoRecurso().getIdTipoRecurso());
            stmt.setString(3, reserva.getDescricao());
            stmt.setInt(4, reserva.getQuantidade());
            stmt.setDate(5, Date.valueOf(reserva.getDataLocacao()));
            stmt.setTime(6, Time.valueOf(reserva.getHorarioLocacao()));
            if (reserva.getDataDevolucao() != null) {
                stmt.setDate(7, Date.valueOf(reserva.getDataDevolucao()));
                stmt.setTime(8, Time.valueOf(reserva.getHorarioDevolucao()));
            } else {
                stmt.setNull(7, Types.DATE);
                stmt.setNull(8, Types.TIME);
            }
            stmt.setBoolean(9, reserva.isAtivo());
            stmt.setInt(10, reserva.getIdReserva());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a reserva: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql;
        sql = "delete from reserva where idrecurso = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir a reserva: " + e.getMessage(), e);
        }
    }

    public List<Reserva> listarTodas() {
        String sql;
        sql = "select r.*, " +
                "resp.nome, resp.cracha, resp.telefone, resp.ativo as resp_ativo," +
                "f.idfuncao, f.tipoFuncao, f.permissao, f.ativo as func_ativo," +
                "tr.tiporecurso, tr.descricao as tr_descricao, tr.ativo as tr_ativo" +
                "from reserva r" +
                "join responsavel resp on r.idresponsavel = resp.idresponsavel" +
                "join funcao f on resp.idfuncao = f.idfuncao" +
                "join tiporecurso tr on r.idtiporecurso = tr.idtiporecurso";

        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // 1 - cria o objeto Funcao
                Funcao funcao = new Funcao();
                funcao.setIdFuncao(rs.getInt("idfuncao"));
                funcao.setTipoFuncao(rs.getString("tipoFuncao"));
                funcao.setPermissao(rs.getString("permissao"));
                funcao.setAtivo(rs.getBoolean("func_ativo"));
                // 2 - cria o objeto Responsavel e associa a Funcao
                Responsavel responsavel = new Responsavel();
                responsavel.setIdResponsavel(rs.getInt("idresponsavel"));
                responsavel.setNome(rs.getString("nome"));
                responsavel.setCracha(rs.getString("cracha"));
                responsavel.setTelefone(rs.getString("telefone"));
                responsavel.setAtivo(rs.getBoolean("resp_ativo"));
                responsavel.setFuncao(funcao);
                // 3 - cria o objeto TipoRecurso
                TipoRecurso tipoRecurso = new TipoRecurso();
                tipoRecurso.setIdTipoRecurso(rs.getInt("idtiporecurso"));
                tipoRecurso.setTipoRecurso(rs.getString("tiporecurso"));
                tipoRecurso.setDescricao(rs.getString("tr_descricao"));
                tipoRecurso.setAtivo(rs.getBoolean("tr_ativo"));
                // 4 - cria o objeto Reserva e associa os outros
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("idrecurso"));
                reserva.setDescricao(rs.getString("descricao"));
                reserva.setQuantidade(rs.getInt("quantidade"));
                reserva.setDataLocacao(rs.getDate("dataLocacao").toLocalDate());

                reserva.setHorarioLocacao(rs.getTime("horarioLocacao").toLocalTime());
                Date dataDevolucaoSQL = rs.getDate("dataDevolucao");
                if (dataDevolucaoSQL != null) {
                    reserva.setDataDevolucao(dataDevolucaoSQL.toLocalDate());
                    reserva.setHorarioDevolucao(rs.getTime("horarioDevolucao").toLocalTime());
                }

                reserva.setAtivo(rs.getBoolean("ativo"));
                reserva.setResponsavel(responsavel);
                reserva.setTipoRecurso(tipoRecurso);

                reservas.add(reserva);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar as reservas: " + e.getMessage(), e);
        }
        return reservas;
    }

    // RF11 - Lógica de verificação de conflito
    public boolean existeConflito(String descricaoRecurso, LocalDateTime inicioNovaReserva, LocalDateTime fimNovaReserva, Integer idReservaSendoEditada) {
        String sql = "select count(*) from reserva where descricao = ? " +
                "and ativo = true " +
                "and (CAST(CONCAT(dataLocacao, ' ', horarioLocacao) as DATETIME) < ?) " +
                "and (CAST(CONCAT(dataDevolucao, ' ', horarioDevolucao) as DATETIME) > ?)";

        if (idReservaSendoEditada != null) {
            sql += " and idrecurso != ?";
        }

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descricaoRecurso);
            stmt.setTimestamp(2, Timestamp.valueOf(fimNovaReserva));
            stmt.setTimestamp(3, Timestamp.valueOf(inicioNovaReserva));

            if (idReservaSendoEditada != null) {
                stmt.setInt(4, idReservaSendoEditada);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao checar conflito de reservas: " + e.getMessage(), e);
        }
        return false;
    }
}