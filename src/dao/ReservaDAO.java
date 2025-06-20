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
                "where idreserva = ?";

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
        // A query agora usa DELETE FROM para apagar o registro completamente.
        String sql = "DELETE FROM reserva WHERE idreserva = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            // Opcional: verificação para saber se algo foi realmente deletado.
            if (linhasAfetadas > 0) {
                System.out.println("Reserva com ID " + id + " foi deletada com sucesso.");
            } else {
                System.out.println("Nenhuma reserva encontrada com o ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir a reserva: " + e.getMessage(), e);
        }
    }
    public List<Reserva> listarTodas() {
        String sql;
        sql = "select r.*, " +
                "resp.nome, resp.cracha, resp.telefone, resp.ativo as resp_ativo, " +
                "f.idfuncao, f.tipoFuncao, f.permissao, f.ativo as func_ativo, " +
                "tr.tiporecurso, tr.descricao as tr_descricao, tr.ativo as tr_ativo " +
                "from reserva r " +
                "join responsavel resp on r.idresponsavel = resp.idresponsavel " +
                "join funcao f on resp.idfuncao = f.idfuncao " +
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
                reserva.setIdReserva(rs.getInt("idreserva"));
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
        // A lógica para detectar sobreposição de intervalos é:
        // (Inicio_A < Fim_B) AND (Fim_A > Inicio_B)
        // A = Reserva Existente no Banco
        // B = Nova Reserva que estamos tentando salvar

        // Concatenamos a data e a hora do banco para formar um DATETIME para comparação.
        String sql = "SELECT COUNT(*) FROM reserva " +
                "WHERE descricao = ? " +
                "AND ativo = true " +
                "AND (CAST(CONCAT(dataLocacao, ' ', horarioLocacao) AS DATETIME) < ?) " + // Início_A < Fim_B
                "AND (CAST(CONCAT(dataDevolucao, ' ', horarioDevolucao) AS DATETIME) > ?)"; // Fim_A > Inicio_B

        // Se estamos editando uma reserva, não podemos deixá-la conflitar com ela mesma.
        if (idReservaSendoEditada != null) {
            sql += " AND idreserva != ?"; // Corrigido para usar a coluna certa: idreserva
        }

        System.out.println("Executando checagem de conflito...");
        System.out.println("SQL: " + sql);
        System.out.println("Parâmetros: " + descricaoRecurso + ", " + fimNovaReserva + ", " + inicioNovaReserva);

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int parametroIndex = 1;
            ps.setString(parametroIndex++, descricaoRecurso);
            ps.setTimestamp(parametroIndex++, Timestamp.valueOf(fimNovaReserva));   // Fim_B
            ps.setTimestamp(parametroIndex++, Timestamp.valueOf(inicioNovaReserva)); // Inicio_B

            if (idReservaSendoEditada != null) {
                ps.setInt(parametroIndex++, idReservaSendoEditada);
                System.out.println("Ignorando reserva ID: " + idReservaSendoEditada);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Reservas conflitantes encontradas: " + count);
                    return count > 0; // Retorna true se encontrou 1 ou mais conflitos
                }
            }
        } catch (SQLException e) {
            // Lançar a exceção com uma mensagem mais clara ajuda na depuração
            throw new RuntimeException("Erro ao executar a checagem de conflito de reservas: " + e.getMessage(), e);
        }

        // Se algo der errado, é mais seguro assumir que não há conflito do que bloquear o usuário,
        // embora o ideal seja o catch tratar o erro.
        return false;
    }
}