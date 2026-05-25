package gui.Docente;
import controller.Controller;
import java.util.Date;
import javax.swing.*;

public class Int_Tirocinio_Esterno extends JFrame {
    private Controller controller;
    private JTextPane creaTirocinioEsternoTextPane;
    private JTextField nomeTextField;
    private JTextField DurataTextField;
    private JTextField Data_I_TextField;
    private JTextField N_PostiTextField;
    private JTextField N_CFUTextField;
    private JTextField AziendaTextField;
    private JButton ConfermaButton;
    private JTextField RefAziendaTextField;


    public Int_Tirocinio_Esterno(Controller controller) {
        this.controller = controller;
        this.setContentPane(creaTirocinioEsternoTextPane);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        String nome = nomeTextField.getText();
        String durata = DurataTextField.getText();
        String datai = Data_I_TextField.getText();
        Date data_inizio = ;
        String nposti =  N_PostiTextField.getText();
        int n_posti = Integer.parseInt(nposti);
        String ncfu = N_CFUTextField.getText();
        int n_cfu = Integer.parseInt(ncfu);
        //si utilizzerà un JTextField in più per confermare la Password scelta
        String azienda = AziendaTextField.getText();
        String referente = RefAziendaTextField.getText();

        ConfermaButton.addActionListener(e -> {
            if (nome.isEmpty() || data_inizio.isEmpty() || n_posti.isEmpty() || n_cfu.isEmpty() || azienda.isEmpty() || coreferentenfPwd.isEmpty() || durata.isEmpty()) {

                JOptionPane.showMessageDialog(this, "ERRORE! Tutti i campi sono obbligatori.", "Dati Mancanti", JOptionPane.WARNING_MESSAGE);
                return;
        });






    }

}
