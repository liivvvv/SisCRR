package view;

import dao.FuncaoDAO;
import dao.ResponsavelDAO;
import model.Funcao;
import model.Responsavel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaResponsavel extends JInternalFrame {

    private JPanel telaResponsável;
    private JPanel painelFormulario;
    private JTextField TF_nome;
    private JTextField TF_cracha;
    private JTextField TF_telefone;
    private JComboBox<Funcao> cb_Funcao;
    private JCheckBox checkAtivo;
    private JTable tabelaResponsaveis;
    private JScrollPane painelTabela;
    private JPanel painelBotoes;
    private JButton novoButton;
    private JButton salvarButton;
    private JButton excluirButton;
    private JLabel LB_Nome;
    private JLabel LB_Cracha;
    private JLabel LB_Telefone;
    private JLabel LB_Funcao;
    // variáveis de controle e de dados
    private ResponsavelDAO responsavelDAO;
    private FuncaoDAO funcaoDAO;
    private DefaultTableModel tableModel;
    private Responsavel responsavelSelecionado;

    public TelaResponsavel() {
        setContentPane(telaResponsável);
        setTitle("Cadastro de Responsáveis");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        //setSize(800, 600);
        setLayout(new BorderLayout());
        pack();

        this.responsavelDAO = new ResponsavelDAO();
        this.funcaoDAO = new FuncaoDAO();

        configurarTabela();
        popularComboBoxFuncoes();
        atualizarTabela();
        configurarAcoes();
    }

    // modelo tabela. dados vêm do banco
    private void configurarTabela() {
        String[] colunas = {"ID", "Nome", "Crachá", "Telefone", "Função", "Ativo"};
        tableModel = new DefaultTableModel(colunas, 0) {
            // células da tabela não-editáveis
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaResponsaveis.setModel(tableModel);
    }
    // busca as funções no banco de dados e as adiciona na caixa de seleção
    private void popularComboBoxFuncoes() {
        List<Funcao> funcoes = funcaoDAO.listarTodas();
        cb_Funcao.removeAllItems(); // limpa itens antigos
        for (Funcao funcao : funcoes) {
            cb_Funcao.addItem(funcao);
        }
    }
    // busca todos os responsáveis no banco e atualiza a tabela
    private void atualizarTabela() {
        tableModel.setRowCount(0); // limpa a tabela
        List<Responsavel> responsaveis = responsavelDAO.listarTodos();
        for (Responsavel resp : responsaveis) {
            tableModel.addRow(new Object[]{
                    resp.getIdResponsavel(),
                    resp.getNome(),
                    resp.getCracha(),
                    resp.getTelefone(),
                    resp.getFuncao().getTipoFuncao(), // pega o nome da função
                    resp.isAtivo() ? "Sim" : "Não"
            });
        }
    }
    // limpa os campos do formulário para uma nova inserção
    private void limparCampos() {
        responsavelSelecionado = null;
        TF_nome.setText("");
        TF_cracha.setText("");
        TF_telefone.setText("");
        cb_Funcao.setSelectedIndex(0);
        checkAtivo.setSelected(true);
        tabelaResponsaveis.clearSelection();
        TF_nome.requestFocus(); // coloca o cursor no campo nome
    }
    // centraliza a configuração de todos os listeners
    private void configurarAcoes() {
        novoButton.addActionListener(e -> limparCampos());
        salvarButton.addActionListener(e -> salvarResponsavel());
        excluirButton.addActionListener(e -> excluirResponsavel());
        // ação para quando o usuário clica em uma linha da tabela
        tabelaResponsaveis.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabelaResponsaveis.getSelectedRow();
                if (linhaSelecionada != -1) {
                    carregarDadosDoFormulario(linhaSelecionada);
                }
            }
        });
    }
    // pega os dados da linha selecionada na tabela e preenche o formulário
    private void carregarDadosDoFormulario(int linha) {
        // pega o ID da tabela
        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        // busca o objeto completo no banco
        // dá pra simplificar**
        responsavelSelecionado = responsavelDAO.listarTodos().stream()
                .filter(r -> r.getIdResponsavel() == id)
                .findFirst().orElse(null);

        if (responsavelSelecionado != null) {
            TF_nome.setText(responsavelSelecionado.getNome());
            TF_cracha.setText(responsavelSelecionado.getCracha());
            TF_telefone.setText(responsavelSelecionado.getTelefone());
            checkAtivo.setSelected(responsavelSelecionado.isAtivo());
            // seleciona a função correta no ComboBox
            for (int i = 0; i < cb_Funcao.getItemCount(); i++) {
                if (cb_Funcao.getItemAt(i).getIdFuncao() == responsavelSelecionado.getFuncao().getIdFuncao()) {
                    cb_Funcao.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    private void salvarResponsavel() {
        // coleta dados do formulário
        String nome = TF_nome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // se responsavelSelecionado for nulo, é um novo cadastro. se não, é uma edição.
        Responsavel responsavel = (responsavelSelecionado == null) ? new Responsavel() : responsavelSelecionado;

        responsavel.setNome(nome);
        responsavel.setCracha(TF_cracha.getText().trim());
        responsavel.setTelefone(TF_telefone.getText().trim());
        responsavel.setFuncao((Funcao) cb_Funcao.getSelectedItem());
        responsavel.setAtivo(checkAtivo.isSelected());

        try {
            // salva ou Atualiza no banco
            if (responsavel.getIdResponsavel() == 0) { // id 0 indica que é novo
                responsavelDAO.salvar(responsavel);
                JOptionPane.showMessageDialog(this, "Responsável salvo com sucesso!");
            } else {
                responsavelDAO.atualizar(responsavel);
                JOptionPane.showMessageDialog(this, "Responsável atualizado com sucesso!");
            }
            // atualiza a interface
            limparCampos();
            atualizarTabela();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar responsável: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void excluirResponsavel() {
        if (responsavelSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um responsável na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o responsável '" + responsavelSelecionado.getNome() + "'?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            try {
                responsavelDAO.excluir(responsavelSelecionado.getIdResponsavel());
                JOptionPane.showMessageDialog(this, "Responsável excluído (inativado) com sucesso!");
                limparCampos();
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir responsável: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
