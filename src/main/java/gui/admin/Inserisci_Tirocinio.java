package gui.admin;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import controller.Controller;
import gui.Docente.Int_Docente;
import gui.Home;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Inserisci_Tirocinio extends JFrame {
    private transient Controller controller;
    private JTextField GiornoTextField;
    private JTextField AnnoTextField;
    private JTextField MeseTextField;
    private JButton logoutButton;
    private JLabel AGGIUNGITIROCINIOLabel;
    private JRadioButton esternoRadioButton;
    private JRadioButton internoRadioButton;
    private JPanel FinestraTIr;
    private JTextField NomeTextField;
    private JTextField NcfuTextField;
    private JTextField DurataTextField;
    private JButton AGGIUNGIButton;
    private JPanel DipPanel;
    private JPanel LabPanel;
    private JPanel AziendaPanel;
    private JPanel RefAziendaPanel;
    private JButton ReturnButton;
    private JTextField AziendaTextField;
    private JTextField RefAziendaTextField;
    private JTextField DipTextField;
    private JTextField LabTextField;
    private JTextField ArgTextField;
    private JTextField RelatoreTextField;
    ButtonGroup gruppoScelte = new ButtonGroup();


    public Inserisci_Tirocinio(Controller controller) {
        // 1. Inizializzazione base
        this.controller = controller;// Chiamata unica al mostro
        this.setContentPane(FinestraTIr);
        this.setTitle("AGGIUNGI TIROCINIO");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // 2. Setup RadioButtons
        gruppoScelte.add(esternoRadioButton);
        gruppoScelte.add(internoRadioButton);

        // Nascondi i pannelli specifici all'avvio
        AziendaPanel.setVisible(false);
        RefAziendaPanel.setVisible(false);
        DipPanel.setVisible(false);
        LabPanel.setVisible(false);

        // 3. Logica dinamica per mostrare/nascondere i pannelli al click
        esternoRadioButton.addActionListener(e -> {
            AziendaPanel.setVisible(true);
            RefAziendaPanel.setVisible(true);
            DipPanel.setVisible(false);
            LabPanel.setVisible(false);
            FinestraTIr.revalidate(); // Aggiorna la grafica
        });

        internoRadioButton.addActionListener(e -> {
            AziendaPanel.setVisible(false);
            RefAziendaPanel.setVisible(false);
            DipPanel.setVisible(true);
            LabPanel.setVisible(true);
            FinestraTIr.revalidate(); // Aggiorna la grafica
        });

        // 4. Logica del bottone AGGIUNGI
        AGGIUNGIButton.addActionListener(e -> {
            // Controllo se un radio button è stato selezionato
            if (!esternoRadioButton.isSelected() && !internoRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Seleziona se il tirocinio è Interno o Esterno", "JRadioButton Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String giorno = GiornoTextField.getText().trim();
            String anno = AnnoTextField.getText().trim();
            String mese = MeseTextField.getText().trim();
            String nome = NomeTextField.getText().trim();
            String ncfu = NcfuTextField.getText().trim();
            String durata = DurataTextField.getText().trim();
            String argomento = ArgTextField.getText().trim();
            String userRel = RelatoreTextField.getText().trim();
            String azienda = null;
            String refAzienda = null;
            String lab = null;
            String dip = null;
            String tipo = "";

            if (userRel.isEmpty() || argomento.isEmpty() || giorno.isEmpty() || anno.isEmpty() || mese.isEmpty() || nome.isEmpty() || ncfu.isEmpty() || durata.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Riempire tutti i campi comuni", "Errore dati mancanti 1", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean datiSpecificiOk = false;
            if (esternoRadioButton.isSelected()) {
                azienda = AziendaTextField.getText().trim();
                refAzienda = RefAziendaTextField.getText().trim();
                datiSpecificiOk = !azienda.isEmpty() && !refAzienda.isEmpty();
                tipo = "Esterno";

            } else if (internoRadioButton.isSelected()) {
                lab = LabTextField.getText().trim();
                dip = DipTextField.getText().trim();
                datiSpecificiOk = !lab.isEmpty() && !dip.isEmpty();
                tipo = "Interno";
            }

            if (!datiSpecificiOk) {
                JOptionPane.showMessageDialog(this, "Riempire tutti i campi specifici del tirocinio", "Errore dati mancanti 2", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                LocalDate dataTirocinio = controller.assemblaData(giorno, mese, anno);

                // Virgola corretta rimossa qui. Assicurati che i parametri passati coincidano con il tuo metodo nel Controller
                controller.inserisciTirocinio(argomento, nome, Integer.parseInt(ncfu), Integer.parseInt(durata), dataTirocinio, tipo, azienda, refAzienda, dip, lab, userRel);
                JOptionPane.showMessageDialog(this, "Tirocinio creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                // Reset campi
                GiornoTextField.setText("");
                AnnoTextField.setText("");
                MeseTextField.setText("");
                NomeTextField.setText("");
                NcfuTextField.setText("");
                DurataTextField.setText("");
                DipTextField.setText("");
                LabTextField.setText("");
                AziendaTextField.setText("");
                RefAziendaTextField.setText("");
                gruppoScelte.clearSelection();
                AziendaPanel.setVisible(false);
                RefAziendaPanel.setVisible(false);
                DipPanel.setVisible(false);
                LabPanel.setVisible(false);

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore nei dati", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore generico: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 5. Bottoni di navigazione
        ReturnButton.addActionListener(e -> {
            Int_admin admin = new Int_admin(controller);
            admin.setVisible(true);
            this.dispose();
        });

        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        FinestraTIr = new JPanel();
        FinestraTIr.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-10637219));
        FinestraTIr.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-13672913));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        panel2.add(logoutButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AGGIUNGITIROCINIOLabel = new JLabel();
        AGGIUNGITIROCINIOLabel.setBackground(new Color(-12314343));
        AGGIUNGITIROCINIOLabel.setForeground(new Color(-525825));
        AGGIUNGITIROCINIOLabel.setText("AGGIUNGI TIROCINIO");
        panel2.add(AGGIUNGITIROCINIOLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 50), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-10637219));
        panel3.setForeground(new Color(-525825));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Data Inizio", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-10637219));
        panel4.setForeground(new Color(-525825));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(null, "Giorno", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        GiornoTextField = new JTextField();
        panel4.add(GiornoTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-10637219));
        panel5.setForeground(new Color(-525825));
        panel3.add(panel5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Anno", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        AnnoTextField = new JTextField();
        panel5.add(AnnoTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-10637219));
        panel6.setForeground(new Color(-525825));
        panel3.add(panel6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(null, "Mese", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        MeseTextField = new JTextField();
        panel6.add(MeseTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel7, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-10637219));
        panel8.setForeground(new Color(-525825));
        panel7.add(panel8, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(null, "Nome", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        NomeTextField = new JTextField();
        panel8.add(NomeTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-10637219));
        panel9.setForeground(new Color(-525825));
        panel7.add(panel9, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(null, "N_cfu", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        NcfuTextField = new JTextField();
        panel9.add(NcfuTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel10, new GridConstraints(2, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel11.setBackground(new Color(-10637219));
        panel11.setForeground(new Color(-525825));
        panel10.add(panel11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel11.setBorder(BorderFactory.createTitledBorder(null, "Durata", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        DurataTextField = new JTextField();
        panel11.add(DurataTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel12, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        AziendaPanel = new JPanel();
        AziendaPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        AziendaPanel.setBackground(new Color(-10637219));
        AziendaPanel.setForeground(new Color(-525825));
        panel12.add(AziendaPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        AziendaPanel.setBorder(BorderFactory.createTitledBorder(null, "Azienda", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        AziendaTextField = new JTextField();
        AziendaPanel.add(AziendaTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel13.setBackground(new Color(-10637219));
        panel13.setForeground(new Color(-525825));
        panel7.add(panel13, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel13.setBorder(BorderFactory.createTitledBorder(null, "Argomento", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        ArgTextField = new JTextField();
        panel13.add(ArgTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        RefAziendaPanel = new JPanel();
        RefAziendaPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        RefAziendaPanel.setBackground(new Color(-10637219));
        RefAziendaPanel.setForeground(new Color(-525825));
        panel7.add(RefAziendaPanel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        RefAziendaPanel.setBorder(BorderFactory.createTitledBorder(null, "Referente Aziendale", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        RefAziendaTextField = new JTextField();
        RefAziendaPanel.add(RefAziendaTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel14.setBackground(new Color(-10637219));
        panel14.setForeground(new Color(-525825));
        panel7.add(panel14, new GridConstraints(2, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel14.setBorder(BorderFactory.createTitledBorder(null, "Username Relatore", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        RelatoreTextField = new JTextField();
        panel14.add(RelatoreTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        DipPanel = new JPanel();
        DipPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        DipPanel.setBackground(new Color(-10637219));
        DipPanel.setForeground(new Color(-525825));
        panel7.add(DipPanel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        DipPanel.setBorder(BorderFactory.createTitledBorder(null, "Dipartimento", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        DipTextField = new JTextField();
        DipPanel.add(DipTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        LabPanel = new JPanel();
        LabPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        LabPanel.setBackground(new Color(-10637219));
        LabPanel.setForeground(new Color(-525825));
        panel7.add(LabPanel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        LabPanel.setBorder(BorderFactory.createTitledBorder(null, "Laboratorio", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-525825)));
        LabTextField = new JTextField();
        LabPanel.add(LabTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        internoRadioButton = new JRadioButton();
        internoRadioButton.setText("Interno");
        panel7.add(internoRadioButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        esternoRadioButton = new JRadioButton();
        esternoRadioButton.setText("Esterno");
        panel7.add(esternoRadioButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AGGIUNGIButton = new JButton();
        AGGIUNGIButton.setBackground(new Color(-3421344));
        AGGIUNGIButton.setForeground(new Color(-15197925));
        AGGIUNGIButton.setText("AGGIUNGI");
        panel1.add(AGGIUNGIButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ReturnButton = new JButton();
        ReturnButton.setBackground(new Color(-3421344));
        ReturnButton.setForeground(new Color(-15132132));
        ReturnButton.setText("TORNA ALLA HOME");
        panel1.add(ReturnButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return FinestraTIr;
    }

}
// =========================================================
// INCOLLA SOTTO QUESTA RIGA UNA SOLA COPIA DEL METODO $$$setupUI$$$
// E UNA SOLA COPIA DEL METODO $$$getRootComponent$$$
// =========================================================