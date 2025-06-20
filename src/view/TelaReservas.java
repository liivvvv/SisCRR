package view;
import com.toedter.calendar.JDateChooser;
import dao.ReservaDAO;
import dao.ResponsavelDAO;
import dao.TipoRecursoDAO;
import model.Reserva;
import model.Responsavel;
import model.TipoRecurso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
public class TelaReservas extends JInternalFrame {
    private JPanel telaReservas;
    private JComboBox CB_Responsavel;
    private JLabel LB_Responsavel;
    private JLabel LB_Recurso;
    private JComboBox CB_Recurso;
    private JLabel LB_Descricao;
    private JTextField TF_Descricao;
    private JLabel LB_Quantidade;
    private JSpinner spinnerQuantidade;
    private JLabel LB_DataLocacao;
    private JTextField TF_DataLocacao;
    private JLabel LB_DataDevolucao;
    private JTextField TF_DataDevolucao;
    private JLabel LB_HorarioLocacao;
    private JTextField TF_HorarioLocacao;
    private JTextField TF_HorarioDevolucao;
    private JLabel LB_HorarioDevolucao;
    private JCheckBox checkAtivo;
    private JTable tabelaReservas;
    private JButton novaReservaButton;
    private JButton salvarReservaButton;
    private JButton excluirReservaButton;
    private ReservaDAO reservaDAO;
    private ResponsavelDAO responsavelDAO;
    private TipoRecursoDAO tipoRecursoDAO;
    private DefaultTableModel tableModel;
    private Reserva reservaSelecionada;
    private final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaReservas() {
        setContentPane(telaReservas);
        setTitle("Gerenciamento de Reservas");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        this.reservaDAO = new ReservaDAO();
        this.responsavelDAO = new ResponsavelDAO();
        this.tipoRecursoDAO = new TipoRecursoDAO();

        configurarTabela();
        popularComboBoxes();
        atualizarTabela();
        configurarAcoes();
        adicionarIconesAosBotoes();

        spinnerQuantidade.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        pack();
    }

    private void adicionarIconesAosBotoes() {
        try {
            int tamanhoIcone = 20;

            Icon iconeNovo = criarIconeRedimensionado("/imagens/plus.png", tamanhoIcone, tamanhoIcone);
            Icon iconeSalvar = criarIconeRedimensionado("/imagens/diskette.png", tamanhoIcone, tamanhoIcone);
            Icon iconeExcluir = criarIconeRedimensionado("/imagens/trash-can.png", tamanhoIcone, tamanhoIcone);

            novaReservaButton.setIcon(iconeNovo);
            salvarReservaButton.setIcon(iconeSalvar);
            excluirReservaButton.setIcon(iconeExcluir);

            novaReservaButton.setText("");
            salvarReservaButton.setText("");
            excluirReservaButton.setText("");

        } catch (Exception ex) {
            System.err.println("Falha ao configurar ícones: " + ex.getMessage());
        }
    }

    private ImageIcon criarIconeRedimensionado(String caminho, int largura, int altura) {
        try {
            ImageIcon iconeOriginal = new ImageIcon(getClass().getResource(caminho));
            Image imagemOriginal = iconeOriginal.getImage();
            Image imagemRedimensionada = imagemOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(imagemRedimensionada);
        } catch (Exception ex) {
            System.err.println("Erro ao carregar ou redimensionar o ícone: " + caminho);
            return null;
        }
    }

    private void configurarTabela() {
        String[] colunas = {"ID", "Item", "Responsável", "Data Locação", "Data Devolução"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaReservas.setModel(tableModel);
    }

    private void popularComboBoxes() {
        List<Responsavel> responsaveis = responsavelDAO.listarTodos();
        CB_Responsavel.removeAllItems();
        responsaveis.forEach(CB_Responsavel::addItem);

        List<TipoRecurso> tipos = tipoRecursoDAO.listarTodos();
        CB_Recurso.removeAllItems();
        tipos.forEach(CB_Recurso::addItem);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Reserva> reservas = reservaDAO.listarTodas();
        for (Reserva r : reservas) {
            String dataHoraLocacao = r.getDataLocacao().format(DateTimeFormatter.ofPattern("dd/MM/yy")) + " " + r.getHorarioLocacao().format(HORA_FORMATTER);
            String dataHoraDevolucao = r.getDataDevolucao().format(DateTimeFormatter.ofPattern("dd/MM/yy")) + " " + r.getHorarioDevolucao().format(HORA_FORMATTER);

            tableModel.addRow(new Object[]{
                    r.getIdReserva(),
                    r.getDescricao(),
                    r.getResponsavel().getNome(),
                    dataHoraLocacao,
                    dataHoraDevolucao
            });
        }
    }

    private void limparCampos() {
        reservaSelecionada = null;
        CB_Responsavel.setSelectedIndex(-1);
        CB_Recurso.setSelectedIndex(-1);
        TF_Descricao.setText("");
        spinnerQuantidade.setValue(1);
        TF_DataLocacao.setText("");
        TF_HorarioLocacao.setText("");
        TF_DataDevolucao.setText("");
        TF_HorarioDevolucao.setText("");
        checkAtivo.setSelected(true);
        tabelaReservas.clearSelection();
    }

    private void configurarAcoes() {
        novaReservaButton.addActionListener(e -> limparCampos());
        salvarReservaButton.addActionListener(e -> salvar());

        excluirReservaButton.addActionListener(e -> excluirReserva());

        tabelaReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int linhaSelecionada = tabelaReservas.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    carregarDadosDoFormulario(linhaSelecionada);
                }
            }
        });
    }
    private void carregarDadosDoFormulario(int linha) {
        Integer id = (Integer) tableModel.getValueAt(linha, 0);

        reservaDAO.listarTodas().stream()
                .filter(r -> r.getIdReserva() == id)
                .findFirst()
                .ifPresent(reserva -> {
                    reservaSelecionada = reserva; // Guarda a reserva selecionada

                    TF_Descricao.setText(reserva.getDescricao());
                    spinnerQuantidade.setValue(reserva.getQuantidade());
                    checkAtivo.setSelected(reserva.isAtivo());
                });
    }


    private void excluirReserva() {
        // 1. Verifica se uma reserva foi selecionada na tabela
        if (reservaSelecionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione na tabela a reserva que deseja excluir.",
                    "Nenhuma Reserva Selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Pede confirmação ao usuário
        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir a reserva do item '" + reservaSelecionada.getDescricao() + "'?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // 3. Se o usuário confirmou, executa a exclusão
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                // Chama o mé todo do DAO para inativar a reserva
                reservaDAO.excluir(reservaSelecionada.getIdReserva());

                JOptionPane.showMessageDialog(this, "Reserva excluída com sucesso!");

                // 4. Atualiza a interface gráfica
                limparCampos();      // Limpa o formulário
                atualizarTabela();   // Recarrega a tabela (a reserva excluída vai sumir)

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Ocorreu um erro ao excluir a reserva: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void salvar() {
        // --- 1. Validações ---
        // Usando os nomes corretos dos seus JTextFields
        if (TF_DataLocacao.getText().trim().isEmpty() || TF_DataDevolucao.getText().trim().isEmpty() ||
                TF_HorarioLocacao.getText().trim().isEmpty() || TF_HorarioDevolucao.getText().trim().isEmpty() ||
                CB_Responsavel.getSelectedItem() == null || TF_Descricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // --- 2. Coleta e Conversão dos Dados (A PARTE CORRIGIDA) ---
            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Lê a DATA do JTextField e converte
            LocalDate dataL = LocalDate.parse(TF_DataLocacao.getText().trim(), dataFormatter);
            // Lê a HORA do JTextField e converte
            LocalTime horaL = LocalTime.parse(TF_HorarioLocacao.getText().trim(), horaFormatter);
            LocalDateTime inicioReserva = LocalDateTime.of(dataL, horaL);

            // Faz o mesmo para a devolução
            LocalDate dataD = LocalDate.parse(TF_DataDevolucao.getText().trim(), dataFormatter);
            LocalTime horaD = LocalTime.parse(TF_HorarioDevolucao.getText().trim(), horaFormatter);
            LocalDateTime fimReserva = LocalDateTime.of(dataD, horaD);

            // --- 3. Lógica de Negócio (O resto do código já estava bom) ---
            if (!fimReserva.isAfter(inicioReserva)) {
                // A condição !isAfter() pega tanto os casos em que o fim é ANTES
                // quanto os casos em que o fim é IGUAL ao início.
                JOptionPane.showMessageDialog(this, "A data/hora de devolução deve ser posterior à de locação.", "Erro de Lógica", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String itemDescricao = TF_Descricao.getText().trim();
            Integer idReservaEditada = (reservaSelecionada != null) ? reservaSelecionada.getIdReserva() : null;

            if (reservaDAO.existeConflito(itemDescricao, inicioReserva, fimReserva, idReservaEditada)) {
                JOptionPane.showMessageDialog(this, "CONFLITO! Este item já está reservado para este período.", "Erro de Reserva", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- 4. Preparar o objeto para salvar ---
            Reserva reserva = (reservaSelecionada == null) ? new Reserva() : reservaSelecionada;
            reserva.setResponsavel((Responsavel) CB_Responsavel.getSelectedItem());
            reserva.setTipoRecurso((TipoRecurso) CB_Recurso.getSelectedItem());
            reserva.setDescricao(itemDescricao);
            reserva.setQuantidade((Integer) spinnerQuantidade.getValue());
            reserva.setDataLocacao(dataL);
            reserva.setHorarioLocacao(horaL);
            reserva.setDataDevolucao(dataD);
            reserva.setHorarioDevolucao(horaD);
            reserva.setAtivo(checkAtivo.isSelected());

            // --- 5. Salvar e Atualizar a UI ---
            if (reserva.getIdReserva() == 0) {
                reservaDAO.salvar(reserva);
                JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
            } else {
                reservaDAO.atualizar(reserva);
                JOptionPane.showMessageDialog(this, "Reserva atualizada com sucesso!");
            }
            limparCampos();
            atualizarTabela();

        } catch (DateTimeParseException ex) {
            // Agora a mensagem de erro é mais precisa
            JOptionPane.showMessageDialog(this, "Formato de data (dd/MM/yyyy) ou hora (HH:mm) inválido. Verifique os campos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Ajuda a ver o erro no console
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao salvar: " + ex.getMessage(), "Erro Geral", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}