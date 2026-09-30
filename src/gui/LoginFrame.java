package gui;

import service.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Secure Message Exchange - Login");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();
    }

    private void buildUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Theme.BACKGROUND);
        mainPanel.setLayout(new BorderLayout());

        JPanel card = new JPanel();
        card.setBackground(Theme.PANEL);
        card.setBorder(
                BorderFactory.createEmptyBorder(
                        35, 45, 35, 45
                )
        );

        card.setLayout(
                new BoxLayout(card, BoxLayout.Y_AXIS)
        );

        JLabel title =
                new JLabel("Secure Message Exchange");

        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle =
                new JLabel("Securely communicate using cryptography");

        subtitle.setFont(Theme.NORMAL_FONT);
        subtitle.setForeground(Theme.SECONDARY_TEXT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        JLabel usernameLabel =
                new JLabel("Username");

        usernameLabel.setFont(Theme.NORMAL_FONT);
        usernameLabel.setForeground(Theme.TEXT);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new JTextField();
        Theme.styleTextField(usernameField);
        usernameField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        JLabel passwordLabel =
                new JLabel("Password");

        passwordLabel.setFont(Theme.NORMAL_FONT);
        passwordLabel.setForeground(Theme.TEXT);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField =
                new JPasswordField();

        Theme.stylePasswordField(passwordField);

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(usernameField);

        card.add(Box.createVerticalStrut(18));

        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(passwordField);

        card.add(Box.createVerticalStrut(25));

        JButton loginButton =
                new JButton("LOGIN");

        Theme.styleButton(
                loginButton,
                Theme.PRIMARY
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        loginButton.addActionListener(
                e -> loginUser()
        );

        card.add(loginButton);

        card.add(Box.createVerticalStrut(12));

        JButton registerButton =
                new JButton("Create New Account");

        Theme.styleButton(
                registerButton,
                Theme.SUCCESS
        );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        registerButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        registerButton.addActionListener(e -> {

            RegisterFrame registerFrame =
                    new RegisterFrame(this);

            registerFrame.setVisible(true);

            setVisible(false);
        });

        card.add(registerButton);

        mainPanel.add(
                card,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    private void loginUser() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            boolean authenticated =
                    UserService.authenticate(
                            username,
                            password
                    );

            if (authenticated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                Dashboard dashboard =
                        new Dashboard(username);

                dashboard.setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login error:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}