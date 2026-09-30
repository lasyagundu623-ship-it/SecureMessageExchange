package gui;

import model.SecureMessage;
import service.MessageService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InboxPanel extends JPanel {

    private String username;

    private DefaultListModel<SecureMessage> messageModel;

    private JList<SecureMessage> messageList;

    public InboxPanel(String username) {

        this.username = username;

        buildUI();

        refreshMessages();
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

        JLabel title =
                new JLabel("Inbox");

        title.setFont(
                Theme.HEADING_FONT
        );

        title.setForeground(
                Theme.TEXT
        );

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(
                Theme.BACKGROUND
        );

        topPanel.add(
                title,
                BorderLayout.WEST
        );

        JButton refreshButton =
                new JButton("Refresh");

        Theme.styleButton(
                refreshButton,
                Theme.PRIMARY
        );

        refreshButton.addActionListener(
                e -> refreshMessages()
        );

        topPanel.add(
                refreshButton,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        messageModel =
                new DefaultListModel<>();

        messageList =
                new JList<>(messageModel);

        messageList.setBackground(
                Theme.CARD
        );

        messageList.setForeground(
                Theme.TEXT
        );

        messageList.setFont(
                Theme.NORMAL_FONT
        );

        messageList.setSelectionBackground(
                Theme.PRIMARY
        );

        messageList.setSelectionForeground(
                Color.WHITE
        );

        messageList.setCellRenderer(
                new MessageCellRenderer()
        );

        messageList.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2) {

                            openSelectedMessage();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(messageList);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(70, 85, 110)
                )
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        JLabel hint =
                new JLabel(
                        "Double-click a message to decrypt and view it."
                );

        hint.setForeground(
                Theme.SECONDARY_TEXT
        );

        hint.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        add(
                hint,
                BorderLayout.SOUTH
        );
    }

    public void refreshMessages() {

        messageModel.clear();

        File directory =
                new File(
                        "data/messages"
                );

        if (!directory.exists()) {
            return;
        }

        File[] files =
                directory.listFiles();

        if (files == null) {
            return;
        }

        List<SecureMessage> messages =
                new ArrayList<>();

        for (File file : files) {

            if (!file.getName().endsWith(".txt")) {
                continue;
            }

            try {

                String messageId =
                        file.getName()
                                .replace(
                                        ".txt",
                                        ""
                                );

                SecureMessage message =
                        MessageService.getMessage(
                                messageId
                        );

                if (message != null &&
                        username.equals(
                                message.getReceiver()
                        )) {

                    messages.add(message);
                }

            } catch (Exception e) {

                System.out.println(
                        "Unable to read message: "
                                + file.getName()
                );
            }
        }

        for (SecureMessage message : messages) {
            messageModel.addElement(message);
        }
    }

    private void openSelectedMessage() {

        SecureMessage selected =
                messageList.getSelectedValue();

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a message.",
                    "No Message Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        MessageViewer viewer =
                new MessageViewer(selected);

        viewer.setVisible(true);
    }

    private static class MessageCellRenderer
            extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean selected,
                boolean focused) {

            super.getListCellRendererComponent(
                    list,
                    value,
                    index,
                    selected,
                    focused
            );

            if (value instanceof SecureMessage) {

                SecureMessage message =
                        (SecureMessage) value;

                setText(
                        "<html><b>From:</b> "
                                + message.getSender()
                                + "&nbsp;&nbsp;&nbsp;"
                                + "<b>Date:</b> "
                                + message.getTimestamp()
                                + "</html>"
                );

                setBorder(
                        BorderFactory.createEmptyBorder(
                                12, 12, 12, 12
                        )
                );
            }

            return this;
        }
    }
}