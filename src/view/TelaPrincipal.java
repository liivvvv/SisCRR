package view;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuCadastros;
    private JMenu menuReservas;
    private JMenuItem itemResponsavel;
    private JMenuItem itemTipoRecurso;
    private JMenuItem itemFazerReserva;

    private JDesktopPane desktopPane;

    public TelaPrincipal() {
        setTitle("CRR - Controle de Reserva de Recursos");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

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
    }

    private void abrirTelaResponsavel() {
        TelaResponsavel telaResponsavel = new TelaResponsavel(); // cria uma instância da janela interna de Responsáveis
        desktopPane.add(telaResponsavel); // adiciona a janela à "área de trabalho"
        telaResponsavel.setVisible(true);
    }

    private void abrirTelaTipoRecurso() {
        // **criar a classe TelaCadastroTipoRecurso depois
        // TelaCadastroTipoRecurso telaTipoRecurso = new TelaCadastroTipoRecurso();
        // desktopPane.add(telaTipoRecurso);
        // telaTipoRecurso.setVisible(true);
        JOptionPane.showMessageDialog(this, "Tela de Tipos de Recurso a ser implementada!");
    }

    private void abrirTelaReservas() {
        // **criar a classe TelaReservas depois
        // TelaReservas telaReservas = new TelaReservas();
        // desktopPane.add(telaReservas);
        // telaReservas.setVisible(true);
        JOptionPane.showMessageDialog(this, "Tela de Reservas a ser implementada!");
    }
}
