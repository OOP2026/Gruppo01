package gui.Docente;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.Controller;
import gui.Home;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Int_Docente extends JFrame {
    Controller controller;
    private JPanel FinestraDocente;
    private JLabel INTERFACCIADOCENTELabel;
    private JButton aggiungiArgomButton;
    private JButton visualizzaRichiesteTButton;
    private JButton logoutButton;
    private JButton VisualizzaTesiButton;
    private JButton CreaSedutaButton;
    private JPanel Coordinatore;
    private JButton GestisciCommissioniButton;
    private JPanel Tabella;
    private JTextField NomeCognomeDoc;
    private JButton VisualizzaTirociniButton;
    private JTable tabellaTirocini;
    private JButton terminaVisualizzazioneButton;


    public Int_Docente(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraDocente);
        this.setTitle("INTERFACCIA DOCENTE");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.Coordinatore.setVisible(false);
        this.Tabella.setVisible(true);
        this.tabellaTirocini.setVisible(true);
        this.terminaVisualizzazioneButton.setVisible(false);
        this.GestisciCommissioniButton.setVisible(false);

        if (controller.getdocLoggato().getisCoordinatore()) {
            this.Coordinatore.setVisible(true);
            this.GestisciCommissioniButton.setVisible(true);
        }

        NomeCognomeDoc.setText(controller.getdocLoggato().getNome() + " " + controller.getdocLoggato().getCognome());

        //Se coordinatore, imposta la Seduta
        CreaSedutaButton.addActionListener(e -> {
            CreaSeduta impostaSeduta = new CreaSeduta(controller);
            impostaSeduta.setVisible(true);
            this.dispose();
        });

        GestisciCommissioniButton.addActionListener(e -> {
            Gestisci_Commissioni gestisciCommissioni = new Gestisci_Commissioni(controller);
            gestisciCommissioni.setVisible(true);
            this.dispose();
        });


        //Apre l'interfaccia per aggiungere l'argomento
        aggiungiArgomButton.addActionListener(e -> {
            Aggiunta_Argomenti finestraAggArg = new Aggiunta_Argomenti(controller);
            finestraAggArg.setVisible(true);
            this.dispose();
        });

        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });


        // Visualizza le richieste di Tirocinio arrivate e le valuta
        visualizzaRichiesteTButton.addActionListener(e -> {
            Visualizza_R_Tir finestraRTir = new Visualizza_R_Tir(controller);
            finestraRTir.setVisible(true);
            this.dispose();
        });


        // Visualizza Tesi a lui associate e le valuta
        VisualizzaTesiButton.addActionListener(e -> {
            Valuta_Tesi valutaTesi = new Valuta_Tesi(controller);
            valutaTesi.setVisible(true);
            this.dispose();
        });


        // Visualizza Tirocini in corso
        VisualizzaTirociniButton.addActionListener(e -> {
            try {
                // Recupera dal controller
                List<String[]> datiTabella = controller.visualizzaTirocinioStudenti();


                //MINI DEBUG
                System.out.println("Righe recuperate dal DB: " + datiTabella.size());
                for (String[] stringozza: datiTabella) {
                    for(String stringa: stringozza) {
                        System.out.println(stringa);
                    }
                }



                // 2. Se la lista è vuota, avvisa l'utente e interrompi
                if (datiTabella == null || datiTabella.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Nessun tirocinio in corso associato a questo docente.",
                            "Informazione",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    String[] nomiColonne = {"ID Tirocinio", "Nome Studente", "Stato"};

                    // 2. Inizializza un NUOVO modello con le colonne corrette e 0 righe iniziali
                    DefaultTableModel model = new DefaultTableModel(nomiColonne, 0);

                    // 3. Inserisci i dati
                    for (String[] riga : datiTabella) {
                        model.addRow(riga);
                    }
                    tabellaTirocini.setModel(model);
                }

                terminaVisualizzazioneButton.setVisible(true);
                setSize(500, 800);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore durante il caricamento dei tirocini: " + ex.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        terminaVisualizzazioneButton.addActionListener(e -> {
            tabellaTirocini.setVisible(false);
            setSize(500, 500);
        });


    }
    // region GUI designer generated Code
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
        FinestraDocente = new JPanel();
        FinestraDocente.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        FinestraDocente.setBackground(new Color(-3245500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-3245500));
        FinestraDocente.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-3245500));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        aggiungiArgomButton = new JButton();
        aggiungiArgomButton.setText("Aggiungi Argomenti");
        panel2.add(aggiungiArgomButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-3245500));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        visualizzaRichiesteTButton = new JButton();
        visualizzaRichiesteTButton.setText("Visualizza Richieste di Tirocinio");
        panel3.add(visualizzaRichiesteTButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-3245500));
        panel1.add(panel4, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        VisualizzaTesiButton = new JButton();
        VisualizzaTesiButton.setText("Visualizza Tesi da Valutare");
        panel4.add(VisualizzaTesiButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-3245500));
        panel1.add(panel5, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        GestisciCommissioniButton = new JButton();
        GestisciCommissioniButton.setText("Gestisci Commissioni");
        panel5.add(GestisciCommissioniButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Coordinatore = new JPanel();
        Coordinatore.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        Coordinatore.setBackground(new Color(-3245500));
        panel1.add(Coordinatore, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CreaSedutaButton = new JButton();
        CreaSedutaButton.setHorizontalAlignment(0);
        CreaSedutaButton.setText("Crea Nuova Seduta");
        Coordinatore.add(CreaSedutaButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        Coordinatore.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Tabella = new JPanel();
        Tabella.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(Tabella, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabellaTirocini = new JTable();
        tabellaTirocini.setBackground(new Color(-3245500));
        tabellaTirocini.setForeground(new Color(-525825));
        Tabella.add(tabellaTirocini, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-9029114));
        panel1.add(panel6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(518, 61), null, 0, false));
        NomeCognomeDoc = new JTextField();
        NomeCognomeDoc.setBackground(new Color(-9029114));
        NomeCognomeDoc.setEditable(false);
        NomeCognomeDoc.setForeground(new Color(-525825));
        panel6.add(NomeCognomeDoc, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        INTERFACCIADOCENTELabel = new JLabel();
        INTERFACCIADOCENTELabel.setForeground(new Color(-525825));
        INTERFACCIADOCENTELabel.setText("INTERFACCIA DOCENTE");
        panel6.add(INTERFACCIADOCENTELabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(230, 50), null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        panel6.add(logoutButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-3245500));
        panel1.add(panel7, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        VisualizzaTirociniButton = new JButton();
        VisualizzaTirociniButton.setHorizontalAlignment(0);
        VisualizzaTirociniButton.setText("Visualizza Tirocini in Corso");
        panel7.add(VisualizzaTirociniButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel7.add(spacer6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        terminaVisualizzazioneButton = new JButton();
        terminaVisualizzazioneButton.setText("Termina Visualizzazione");
        panel7.add(terminaVisualizzazioneButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return FinestraDocente;
    }

}