package view;

import dao.UsuarioDAO;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmar;
    private JComboBox<String> comboPapel;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private UsuarioDAO usuarioDAO;

    public TelaCadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        txtLogin = new JTextField();
        txtSenha = new JPasswordField();
        txtConfirmar = new JPasswordField();
        comboPapel = new JComboBox<>(new String[]{"RESPONSAVEL","ADMIN" });
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        add(new JLabel("Login:"));
        add(txtLogin);
        add(new JLabel("Senha:"));
        add(txtSenha);
        add(new JLabel("Confirmar Senha:"));
        add(txtConfirmar);
        add(new JLabel("Papel:"));
        add(comboPapel);
        add(btnCancelar);
        add(btnSalvar);

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> {
            String login = txtLogin.getText().trim();
            String senha = new String(txtSenha.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos");
                return;
            }
            if (!senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "Senhas não conferem");
                return;
            }
            String hash = BCrypt.hashpw(senha, BCrypt.gensalt(12));
            Usuario u = new Usuario(login, hash, comboPapel.getSelectedItem().toString());
            new UsuarioDAO().salvar(u);
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso");
            dispose();
        });

        this.usuarioDAO = new UsuarioDAO(); // Inicialize no construtor

        btnSalvar.addActionListener(e -> {
            String login = txtLogin.getText().trim();
            String senha = new String(txtSenha.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());

            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos");
                return;
            }

            if (usuarioDAO.existeLogin(login)) {
                JOptionPane.showMessageDialog(this, "Este login já está em uso. Escolha outro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "Senhas não conferem");
                return;
            }

            String hash = BCrypt.hashpw(senha, BCrypt.gensalt(12));
            Usuario u = new Usuario(login, hash, comboPapel.getSelectedItem().toString());
            usuarioDAO.salvar(u); // Reutiliza a instância
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso");
            dispose();
        });
    }
}
