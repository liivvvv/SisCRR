package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JLabel Login;
    private JPanel telaLogin;
    private JLabel titulo;

    public TelaLogin() {
        setContentPane(telaLogin);
        setTitle("Login - Sistema CRR");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnEntrar.addActionListener(e -> {
            String login = txtLogin.getText();
            String senha = new String(txtSenha.getPassword());
            UsuarioDAO dao = new UsuarioDAO();
            boolean autenticado = dao.autenticar(login, senha);

            if (autenticado) {
                Usuario usuario = dao.buscarPorLogin(login);
                util.SessaoUsuario.setUsuarioLogado(usuario);
                new TelaPrincipal().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha inválidos!");
            }
        });
    }
}
