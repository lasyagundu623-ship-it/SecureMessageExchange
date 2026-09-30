package gui;

import service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JFrame loginFrame;

    public RegisterFrame(JFrame loginFrame) {

        this.loginFrame = loginFrame;

        setTitle("Secure Message Exchange - Register");
        setSize(500, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                new JLabel("Create Account");

        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.TEXT);
        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Create your secure messaging account"
                );

        subtitle.setFont(Theme.NORMAL_FONT);
        subtitle.setForeground(
                Theme.SECONDARY_TEXT
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        JLabel usernameLabel =
                createLabel("Username");

        usernameField =
                new JTextField();

        Theme.styleTextField(usernameField);

        usernameField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        JLabel passwordLabel =
                createLabel("Password");

        passwordField =
                new JPasswordField();

        Theme.stylePasswordField(passwordField);

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        JLabel confirmLabel =
                createLabel("Confirm Password");

        confirmPasswordField =
                new JPasswordField();

        Theme.stylePasswordField(
                confirmPasswordField
        );

        confirmPasswordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(usernameField);

        card.add(Box.createVerticalStrut(15));

        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(passwordField);

        card.add(Box.createVerticalStrut(15));

        card.add(confirmLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(confirmPasswordField);

        card.add(Box.createVerticalStrut(25));

        JButton registerButton =
                new JButton("REGISTER");

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

        registerButton.addActionListener(
                e -> registerUser()
        );

        card.add(registerButton);

        card.add(Box.createVerticalStrut(12));

        JButton backButton =
                new JButton("Back to Login");

        Theme.styleButton(
                backButton,
                Theme.PRIMARY
        );

        backButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        backButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        backButton.addActionListener(e -> {

            dispose();

            if (loginFrame != null) {
                loginFrame.setVisible(true);
            }
        });

        card.add(backButton);

        mainPanel.add(
                card,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(Theme.NORMAL_FONT);
        label.setForeground(Theme.TEXT);
        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    private void registerUser() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );

        if (username.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Registration Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (username.contains("|")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username cannot contain the | character.",
                    "Invalid Username",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            boolean registered =
                    UserService.registerUser(
                            username,
                            password
                    );

            if (registered) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful!\n"
                                + "Your RSA keys have been generated.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

                if (loginFrame != null) {
                    loginFrame.setVisible(true);
                }

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration error:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}