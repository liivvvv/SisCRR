package dao;

import model.Reserva;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void salvar(Reserva reserva) {
        String sql;
        sql = "INSERT INTO reserva (idresponsavel, idtiporecurso, descricao, quantidade, dataLocacao, " +
                "horarioLocacao, dataDevolucao, horarioDevolucao, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);

            /*stmt.setInt(1, );
            stmt.setInt(2, );
            stmt.setString(3, );
            stmt.setInt(4, );
            stmt.setDate(5, );
            stmt.setTime(6, );
            stmt.setDate(7, );
            stmt.setTime(8, );
            stmt.setBoolean(9, );*/

            stmt.executeUpdate();
            System.out.println("Salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    public List<Reserva> listarTodas() {
        // Lógica para fazer um SELECT * FROM recurso com JOINs em responsavel e tiporecurso
        // para preencher os objetos completos.
        List<Reserva> reservas = new ArrayList<>();
        // ...código do SELECT...
        return reservas;
    }

    // Criar métodos para atualizar(Reserva reserva), excluir(int id), e buscarPorId(int id).

}
}
