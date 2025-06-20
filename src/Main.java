import view.TelaLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Não foi possível carregar o Look and Feel Nimbus.");
        }

        SwingUtilities.invokeLater(() -> {
            TelaLogin telaDeLogin = new TelaLogin();
            telaDeLogin.setVisible(true);
        });
    }
}