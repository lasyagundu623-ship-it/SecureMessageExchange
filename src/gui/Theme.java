
package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Theme {

    public static final Color BACKGROUND =
            new Color(18, 24, 38);

    public static final Color PANEL =
            new Color(27, 36, 54);

    public static final Color CARD =
            new Color(34, 45, 67);

    public static final Color PRIMARY =
            new Color(65, 105, 225);

    public static final Color SUCCESS =
            new Color(46, 160, 67);

    public static final Color DANGER =
            new Color(220, 70, 70);

    public static final Color TEXT =
            new Color(240, 244, 250);

    public static final Color SECONDARY_TEXT =
            new Color(170, 180, 195);

    public static final Font TITLE_FONT =
            new Font("SansSerif", Font.BOLD, 28);

    public static final Font HEADING_FONT =
            new Font("SansSerif", Font.BOLD, 20);

    public static final Font NORMAL_FONT =
            new Font("SansSerif", Font.PLAIN, 14);

    public static final Font BUTTON_FONT =
            new Font("SansSerif", Font.BOLD, 14);

    private Theme() {
    }

    public static void styleTextField(
            JTextField field) {

        field.setFont(NORMAL_FONT);
        field.setForeground(TEXT);
        field.setBackground(CARD);
        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(70, 85, 110)
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 10, 8, 10
                        )
                )
        );
    }

    public static void stylePasswordField(
            JPasswordField field) {

        field.setFont(NORMAL_FONT);
        field.setForeground(TEXT);
        field.setBackground(CARD);
        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(70, 85, 110)
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 10, 8, 10
                        )
                )
        );
    }

    public static void styleButton(
            JButton button,
            Color background) {

        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}

