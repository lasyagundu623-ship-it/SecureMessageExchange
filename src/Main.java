
import gui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import storage.StorageManager;

public class Main {

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to load system theme."
            );
        }

        // Create required data folders
        StorageManager.initialize();

        // Start the login window
        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}

