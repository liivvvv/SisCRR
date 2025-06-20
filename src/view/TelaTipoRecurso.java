package view;

import dao.TipoRecursoDAO;
import model.TipoRecurso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaTipoRecurso extends JInternalFrame {
    private JPanel telaTipoRecurso;
    private JTextField TF_Recurso;
    private JLabel LB_Recurso;
    private JLabel LB_Descricao;
    private JTextArea TA_Descricao;
    private JCheckBox checkAtivo;
    private JTable tabelaTiposRecurso;
    private JButton novoButton;
    private JButton salvarButton;
    private JButton excluirButton;

    private TipoRecursoDAO tipoRecursoDAO;
    private DefaultTableModel tableModel;
    private TipoRecurso tipoRecursoSelecionado;

    public TelaTipoRecurso() {
        setContentPane(telaTipoRecurso);
        setTitle("Cadastro de Tipos de Recurso");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        this.tipoRecursoDAO = new TipoRecursoDAO();

        configurarTabela();
        atualizarTabela();
        configurarAcoes();
        adicionarIconesAosBotoes();

        pack();
    }

    private void adicionarIconesAosBotoes() {
        try {
            int tamanhoIcone = 20;

            Icon iconeNovo = criarIconeRedimensionado("/imagens/plus.png", tamanhoIcone, tamanhoIcone);
            Icon iconeSalvar = criarIconeRedimensionado("/imagens/diskette.png", tamanhoIcone, tamanhoIcone);
            Icon iconeExcluir = criarIconeRedimensionado("/imagens/trash-can.png", tamanhoIcone, tamanhoIcone);

            novoButton.setIcon(iconeNovo);
            salvarButton.setIcon(iconeSalvar);
            excluirButton.setIcon(iconeExcluir);

            novoButton.setText("");
            salvarButton.setText("");
            excluirButton.setText("");

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
        String[] colunas = {"ID", "Tipo", "Descrição", "Ativo"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaTiposRecurso.setModel(tableModel);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // limpa
        List<TipoRecurso> tipos = tipoRecursoDAO.listarTodos();
        for (TipoRecurso tipo : tipos) {
            tableModel.addRow(new Object[]{
                    tipo.getIdTipoRecurso(),
                    tipo.getTipoRecurso(),
                    tipo.getDescricao(),
                    tipo.isAtivo() ? "Sim" : "Não"
            });
        }
    }

    private void limparCampos() {
        tipoRecursoSelecionado = null;
        TF_Recurso.setText("");
        TA_Descricao.setText("");
        checkAtivo.setSelected(true);
        tabelaTiposRecurso.clearSelection();
        TF_Recurso.requestFocus();
    }

    private void configurarAcoes() {
        novoButton.addActionListener(e -> limparCampos());
        salvarButton.addActionListener(e -> salvar());
        excluirButton.addActionListener(e -> excluir());

        tabelaTiposRecurso.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && tabelaTiposRecurso.getSelectedRow() != -1) {
                    carregarDadosDoFormulario(tabelaTiposRecurso.getSelectedRow());
                }
            }
        });
    }

    private void carregarDadosDoFormulario(int linha) {
        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        tipoRecursoSelecionado = tipoRecursoDAO.listarTodos().stream()
                .filter(tr -> tr.getIdTipoRecurso() == id)
                .findFirst().orElse(null);

        if (tipoRecursoSelecionado != null) {
            TF_Recurso.setText(tipoRecursoSelecionado.getTipoRecurso());
            TA_Descricao.setText(tipoRecursoSelecionado.getDescricao());
            checkAtivo.setSelected(tipoRecursoSelecionado.isAtivo());
        }
    }

    private void salvar() {
        if (TF_Recurso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo 'Tipo de Recurso' é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoRecurso tipo = (tipoRecursoSelecionado == null) ? new TipoRecurso() : tipoRecursoSelecionado;
        tipo.setTipoRecurso(TF_Recurso.getText().trim());
        tipo.setDescricao(TA_Descricao.getText().trim());
        tipo.setAtivo(checkAtivo.isSelected());

        try {
            if (tipo.getIdTipoRecurso() == 0) {
                tipoRecursoDAO.salvar(tipo);
                JOptionPane.showMessageDialog(this, "Tipo de Recurso salvo com sucesso!");
            } else {
                tipoRecursoDAO.atualizar(tipo);
                JOptionPane.showMessageDialog(this, "Tipo de Recurso atualizado com sucesso!");
            }
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (tipoRecursoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                tipoRecursoDAO.excluir(tipoRecursoSelecionado.getIdTipoRecurso());
                JOptionPane.showMessageDialog(this, "Item excluído (inativado) com sucesso!");
                limparCampos();
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
