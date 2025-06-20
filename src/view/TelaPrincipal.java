package view;

import model.Usuario;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuCadastros;
    private JMenu menuReservas;
    private JMenuItem itemResponsavel;
    private JMenuItem itemTipoRecurso;
    private JMenuItem itemFazerReserva;
    private JMenuItem itemGerenciarUsuarios;

    private JDesktopPane desktopPane;

    public TelaPrincipal() {
        setTitle("CRR - Controle de Reserva de Recursos");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        add(desktopPane);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuCadastros = new JMenu("Cadastros");
        menuReservas = new JMenu("Reservas");
        menuBar.add(menuCadastros);
        menuBar.add(menuReservas);

        itemResponsavel = new JMenuItem("Responsáveis");
        itemTipoRecurso = new JMenuItem("Tipos de Recurso");
        itemFazerReserva = new JMenuItem("Fazer/Consultar Reserva");

        menuCadastros.add(itemResponsavel);
        menuCadastros.add(itemTipoRecurso);
        menuReservas.add(itemFazerReserva);


        itemResponsavel.addActionListener(e -> {
            abrirTelaResponsavel();
        });
        itemTipoRecurso.addActionListener(e -> {
            abrirTelaTipoRecurso();
        });
        itemFazerReserva.addActionListener(e -> {
            abrirTelaReservas();
        });



        itemGerenciarUsuarios = new JMenuItem("Gerenciar Usuários");
        menuCadastros.addSeparator();
        menuCadastros.add(itemGerenciarUsuarios);

        itemGerenciarUsuarios.addActionListener(e -> { // <-- 3. ADICIONAR A AÇÃO
            abrirTelaCadastroUsuario();
        });

        aplicarPermissoes();
    }

    private void abrirTelaResponsavel() {
        TelaResponsavel telaResponsavel = new TelaResponsavel(); // cria uma instância da janela interna de Responsáveis
        desktopPane.add(telaResponsavel); // adiciona a janela à "área de trabalho"
        telaResponsavel.setVisible(true);
    }

    private void abrirTelaTipoRecurso() {
        TelaTipoRecurso telaTipoRecurso = new TelaTipoRecurso();
        desktopPane.add(telaTipoRecurso);
        telaTipoRecurso.setVisible(true);
    }

    private void abrirTelaReservas() {
        TelaReservas telaReservas = new TelaReservas();
        desktopPane.add(telaReservas);
        telaReservas.setVisible(true);
    }

    private void abrirTelaCadastroUsuario() {
        TelaCadastroUsuario tela = new TelaCadastroUsuario();
        tela.setVisible(true);
    }

    private void aplicarPermissoes() {
        Usuario usuario = util.SessaoUsuario.getUsuarioLogado();
        if (usuario == null) {
            menuCadastros.setEnabled(false);
            menuReservas.setEnabled(false);
            return;
        }

        String permissao = usuario.getPapel();

        if (!permissao.equalsIgnoreCase("ADMIN")) {
            // se não for admin, desabilita os cadastros base
            itemResponsavel.setEnabled(false);
            itemTipoRecurso.setEnabled(false);
        }
        // todos podem ver as reservas
    }
}

