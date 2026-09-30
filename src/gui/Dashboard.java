
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.CRCGenerator;
import model.Packet;
import simulation.SimulationController;

public class Dashboard extends JFrame {

    private AnimationPanel animationPanel;
    private InputPanel inputPanel;
    private StatisticsPanel statisticsPanel;
    private LogPanel logPanel;

    private Packet currentPacket;
    private SimulationController simulation;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Dashboard() {

        initializeFrame();
        initializeUI();

        /*
         * Create the simulation ONLY ONCE.
         *
         * This is important because SimulationController
         * contains:
         *
         * 1. PacketGenerator
         * 2. NetworkStatistics
         * 3. RetransmissionManager
         * 4. TransmissionChannel
         *
         * If we create a new SimulationController every
         * time Transmit is clicked, packet numbering and
         * statistics will restart from zero.
         */

        simulation =
                new SimulationController(
                        0,
                        3
                );

        registerEvents();
    }

    // ==========================================
    // FRAME SETUP
    // ==========================================

    private void initializeFrame() {

        setTitle(
                "NetGuard CRC - Intelligent Network Transmission Simulator"
        );

        setSize(
                1200,
                750
        );

        setLocationRelativeTo(
                null
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        getContentPane().setBackground(
                Theme.BACKGROUND
        );
    }

    // ==========================================
    // UI SETUP
    // ==========================================

    private void initializeUI() {

        // --------------------------------------
        // HEADER
        // --------------------------------------

        JPanel topPanel =
                new JPanel();

        topPanel.setBackground(
                Theme.PRIMARY
        );

        topPanel.setPreferredSize(
                new Dimension(
                        1200,
                        60
                )
        );

        JLabel title =
                new JLabel(
                        "NetGuard CRC - Intelligent Network Transmission Simulator"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                Theme.TITLE
        );

        topPanel.add(
                title
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // --------------------------------------
        // CENTER
        // --------------------------------------

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.setBackground(
                Theme.BACKGROUND
        );

        inputPanel =
                new InputPanel();

        animationPanel =
                new AnimationPanel();

        statisticsPanel =
                new StatisticsPanel();

        centerPanel.add(
                inputPanel,
                BorderLayout.WEST
        );

        centerPanel.add(
                animationPanel,
                BorderLayout.CENTER
        );

        centerPanel.add(
                statisticsPanel,
                BorderLayout.EAST
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );

        // --------------------------------------
        // LOG PANEL
        // --------------------------------------

        logPanel =
                new LogPanel();

        logPanel.setPreferredSize(
                new Dimension(
                        1200,
                        170
                )
        );

        add(
                logPanel,
                BorderLayout.SOUTH
        );
    }

    // ==========================================
    // BUTTON EVENTS
    // ==========================================

    private void registerEvents() {

        // ======================================
        // GENERATE CRC
        // ======================================

        inputPanel
                .getGenerateButton()
                .addActionListener(e -> {

            String data =
                    inputPanel
                            .getDataField()
                            .getText()
                            .trim();

            if (!data.matches("[01]+")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter binary data using only 0 and 1."
                );

                return;
            }

            String type =
                    inputPanel
                            .getPolynomialBox()
                            .getSelectedItem()
                            .toString();

            String polynomial =
                    CRCGenerator.getPolynomial(
                            type
                    );

            String codeword =
                    CRCGenerator.generateCRC(
                            data,
                            polynomial
                    );

            inputPanel
                    .getCodewordField()
                    .setText(
                            codeword
                    );

            inputPanel
                    .getReceivedField()
                    .setText(
                            codeword
                    );

            addLog(
                    "===================================="
            );

            addLog(
                    "CRC GENERATION"
            );

            addLog(
                    "Data       : " +
                    data
            );

            addLog(
                    "Polynomial : " +
                    type
            );

            addLog(
                    "Codeword   : " +
                    codeword
            );

            addLog(
                    "✓ CRC codeword generated."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Generated Codeword:\n\n" +
                    codeword
            );
        });

        // ======================================
        // VERIFY CRC
        // ======================================

        inputPanel
                .getVerifyButton()
                .addActionListener(e -> {

            String received =
                    inputPanel
                            .getReceivedField()
                            .getText()
                            .trim();

            if (!received.matches("[01]+")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid binary received codeword."
                );

                return;
            }

            String type =
                    inputPanel
                            .getPolynomialBox()
                            .getSelectedItem()
                            .toString();

            String polynomial =
                    CRCGenerator.getPolynomial(
                            type
                    );

            boolean valid =
                    CRCGenerator.verifyCRC(
                            received,
                            polynomial
                    );

            if (valid) {

                addLog(
                        "===================================="
                );

                addLog(
                        "CRC VERIFICATION"
                );

                addLog(
                        "✓ CRC Verification PASSED"
                );

                addLog(
                        "✓ No transmission error detected."
                );

                JOptionPane.showMessageDialog(
                        this,
                        "✓ CRC PASSED\n\n" +
                        "No transmission error detected."
                );

            } else {

                addLog(
                        "===================================="
                );

                addLog(
                        "CRC VERIFICATION"
                );

                addLog(
                        "✗ CRC ERROR DETECTED!"
                );

                addLog(
                        "✗ Received codeword is corrupted."
                );

                animationPanel
                        .startErrorTransmission();

                JOptionPane.showMessageDialog(
                        this,
                        "✗ CRC ERROR DETECTED\n\n" +
                        "The received data is corrupted."
                );
            }
        });

        // ======================================
        // TRANSMIT PACKET
        // ======================================

        inputPanel
                .getTransmitButton()
                .addActionListener(e -> {

            // ----------------------------------
            // GET DATA
            // ----------------------------------

            String data =
                    inputPanel
                            .getDataField()
                            .getText()
                            .trim();

            if (!data.matches("[01]+")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid binary data."
                );

                return;
            }

            // ----------------------------------
            // GET CRC TYPE
            // ----------------------------------

            String type =
                    inputPanel
                            .getPolynomialBox()
                            .getSelectedItem()
                            .toString();

            String polynomial =
                    CRCGenerator.getPolynomial(
                            type
                    );

            // ----------------------------------
            // GET ERROR RATE
            // ----------------------------------

            String error =
                    inputPanel
                            .getErrorRateBox()
                            .getSelectedItem()
                            .toString();

            double errorRate =
                    Double.parseDouble(
                            error.replace(
                                    "%",
                                    ""
                            )
                    );

            // ----------------------------------
            // UPDATE CHANNEL ERROR RATE
            // ----------------------------------

            /*
             * IMPORTANT:
             *
             * DO NOT create a new SimulationController
             * here.
             *
             * We reuse the same simulation object.
             *
             * Therefore statistics continue:
             *
             * Packet #1
             * Packet #2
             * Packet #3
             * Packet #4
             *
             * instead of resetting to #1 every time.
             */

            simulation.setErrorRate(
                    errorRate
            );

            // ----------------------------------
            // CREATE PACKET
            // ----------------------------------

            currentPacket =
                    simulation.createPacket(
                            data,
                            polynomial
                    );

            // ----------------------------------
            // LOG TRANSMISSION START
            // ----------------------------------

            addLog(
                    "===================================="
            );

            addLog(
                    "NEW PACKET TRANSMISSION"
            );

            addLog(
                    "Packet ID: #" +
                    currentPacket.getPacketId()
            );

            addLog(
                    "Source: Sender"
            );

            addLog(
                    "Route: Sender → Router → Receiver"
            );

            addLog(
                    "Destination: Receiver"
            );

            addLog(
                    "CRC Type: " +
                    type
            );

            addLog(
                    "Error Rate: " +
                    error
            );

            // ----------------------------------
            // TRANSMIT FIRST ATTEMPT
            // ----------------------------------

            addLog(
                    "→ Starting first transmission..."
            );

            simulation.transmit(
                    currentPacket
            );

            // ----------------------------------
            // DISPLAY CODEWORDS
            // ----------------------------------

            inputPanel
                    .getCodewordField()
                    .setText(
                            currentPacket
                                    .getCodeword()
                    );

            inputPanel
                    .getReceivedField()
                    .setText(
                            currentPacket
                                    .getReceivedCodeword()
                    );

            // ==================================
            // SUCCESSFUL FIRST TRANSMISSION
            // ==================================

            if (
                    currentPacket
                            .isSuccessfullyReceived()
            ) {

                animationPanel
                        .startTransmission();

                addLog(
                        "✓ PACKET ACCEPTED"
                );

                addLog(
                        "✓ CRC Verification PASSED"
                );

                addLog(
                        "✓ Data delivered successfully."
                );
            }

            // ==================================
            // FAILED FIRST TRANSMISSION
            // ==================================

            else {

                animationPanel
                        .startErrorTransmission();

                addLog(
                        "✗ PACKET REJECTED"
                );

                addLog(
                        "✗ CRC Verification FAILED"
                );

                addLog(
                        "⚠ Transmission error detected."
                );

                // --------------------------------
                // CHECK RETRANSMISSION
                // --------------------------------

                if (
                        simulation
                                .getRetransmissionManager()
                                .canRetransmit(
                                        currentPacket
                                )
                ) {

                    addLog(
                            "↻ Retransmission required."
                    );

                    // --------------------------------
                    // RETRANSMISSION ANIMATION
                    // --------------------------------

                    animationPanel
                            .startRetransmission();

                    addLog(
                            "↻ Preparing packet for retransmission..

