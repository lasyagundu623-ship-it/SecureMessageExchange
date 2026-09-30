package gui;

import model.SecureMessage;
import service.MessageService;

import javax.swing.*;
import java.awt.*;

public class ComposeMessagePanel extends JPanel {

    private String sender;
    private Runnable refreshCallback;

    private JTextField receiverField;
    private JTextArea messageArea;

    public ComposeMessagePanel(
            String sender,
            Runnable refreshCallback) {

        this.sender = sender;
        this.refreshCallback =
                refreshCallback;

        buildUI();
    }

    private void buildUI() {

        setBackground(
                Theme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        25, 35, 25, 35
                )
        );

        setLayout(new BorderLayout(0, 15));

        JPanel topPanel =
                new JPanel();

        topPanel.setBackground(
                Theme.BACKGROUND
        );

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("Compose Secure Message");

        title.setFont(
                Theme.HEADING_FONT
        );

        title.setForeground(
                Theme.TEXT
        );

        JLabel information =
                new JLabel(
                        "Your message will be encrypted before storage."
                );

        information.setFont(
                Theme.NORMAL_FONT
        );

        information.setForeground(
                Theme.SECONDARY_TEXT
        );

        topPanel.add(title);
        topPanel.add(
                Box.createVerticalStrut(5)
        );
        topPanel.add(information);
        topPanel.add(
                Box.createVerticalStrut(20)
        );

        JPanel receiverPanel =
                new JPanel(new BorderLayout(10, 0));

        receiverPanel.setBackground(
                Theme.BACKGROUND
        );

        JLabel receiverLabel =
                new JLabel("Receiver:");

        receiverLabel.setForeground(
                Theme.TEXT
        );

        receiverLabel.setFont(
                Theme.NORMAL_FONT
        );

        receiverField =
                new JTextField();

        Theme.styleTextField(
                receiverField
        );

        receiverPanel.add(
                receiverLabel,
                BorderLayout.WEST
        );

        receiverPanel.add(
                receiverField,
                BorderLayout.CENTER
        );

        topPanel.add(receiverPanel);

        add(
                topPanel,
                BorderLayout.NORTH
        );

        messageArea =
                new JTextArea();

        messageArea.setFont(
                Theme.NORMAL_FONT
        );

        messageArea.setForeground(
                Theme.TEXT
        );

        messageArea.setBackground(
                Theme.CARD
        );

        messageArea.setCaretColor(
                Theme.TEXT
        );

        messageArea.setLineWrap(true);

        messageArea.setWrapStyleWord(true);

        messageArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(70, 85, 110)
                        ),
                        BorderFactory.createEmptyBorder(
                                12, 12, 12, 12
                        )
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(messageArea);

        scrollPane.setBorder(null);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setBackground(
                Theme.BACKGROUND
        );

        JButton clearButton =
                new JButton("Clear");

        Theme.styleButton(
                clearButton,
                Theme.DANGER
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        JButton sendButton =
                new JButton("Encrypt & Send");

        Theme.styleButton(
                sendButton,
                Theme.SUCCESS
        );

        sendButton.addActionListener(
                e -> sendMessage()
        );

        bottomPanel.add(clearButton);
        bottomPanel.add(sendButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }

    private void sendMessage() {

        String receiver =
                receiverField.getText().trim();

        String message =
                messageArea.getText();

        if (receiver.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a receiver.",
                    "Missing Receiver",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (message.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a message.",
                    "Missing Message",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            SecureMessage secureMessage =
                    MessageService.sendMessage(
                            sender,
                            receiver,
                            message
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Message encrypted and sent successfully!\n\n"
                            + "Message ID:\n"
                            + secureMessage.getMessageId(),
                    "Message Sent",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            if (refreshCallback != null) {
                refreshCallback.run();
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Message Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to send message:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {

        receiverField.setText("");

        messageArea.setText("");
    }
}