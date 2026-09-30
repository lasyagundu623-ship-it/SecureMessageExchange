package gui;

import model.SecureMessage;
import service.MessageService;

import javax.swing.*;
import java.awt.*;

public class MessageViewer extends JFrame {

    private SecureMessage secureMessage;

    public MessageViewer(
            SecureMessage secureMessage) {

        this.secureMessage =
                secureMessage;

        setTitle(
                "Secure Message - Decrypted View"
        );

        setSize(700, 550);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(0, 15));

        mainPanel.setBackground(
                Theme.BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        JPanel header =
                new JPanel();

        header.setBackground(
                Theme.BACKGROUND
        );

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Decrypted Secure Message"
                );

        title.setFont(
                Theme.HEADING_FONT
        );

        title.setForeground(
                Theme.TEXT
        );

        JLabel sender =
                new JLabel(
                        "From: "
                                + secureMessage.getSender()
                );

        sender.setFont(
                Theme.NORMAL_FONT
        );

        sender.setForeground(
                Theme.SECONDARY_TEXT
        );

        JLabel date =
                new JLabel(
                        "Time: "
                                + secureMessage.getTimestamp()
                );

        date.setFont(
                Theme.NORMAL_FONT
        );

        date.setForeground(
                Theme.SECONDARY_TEXT
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(8)
        );

        header.add(sender);

        header.add(
                Box.createVerticalStrut(4)
        );

        header.add(date);

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        JTextArea messageArea =
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

        messageArea.setEditable(false);

        messageArea.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(messageArea);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(70, 85, 110)
                )
        );

        mainPanel.add(
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

        JLabel securityStatus =
                new JLabel(
                        "Security verification: "
                );

        securityStatus.setForeground(
                Theme.SECONDARY_TEXT
        );

        JButton decryptButton =
                new JButton("Decrypt & Verify");

        Theme.styleButton(
                decryptButton,
                Theme.SUCCESS
        );

        decryptButton.addActionListener(e -> {

            try {

                String decryptedMessage =
                        MessageService.decryptMessage(
                                secureMessage
                        );

                messageArea.setText(
                        decryptedMessage
                );

                securityStatus.setText(
                        "Security verification: VALID"
                );

                securityStatus.setForeground(
                        Theme.SUCCESS
                );

                decryptButton.setEnabled(false);

            } catch (Exception ex) {

                securityStatus.setText(
                        "Security verification: FAILED"
                );

                securityStatus.setForeground(
                        Theme.DANGER
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Message verification failed:\n"
                                + ex.getMessage(),
                        "Security Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        bottomPanel.add(
                securityStatus
        );

        bottomPanel.add(
                decryptButton
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }
}