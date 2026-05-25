package gui.Docente;
import controller.Controller;

import javax.swing.*;

public class Int_Tirocinio_Interno extends JFrame {
    private Controller controller;
    private JTextPane creaTirocinioInternoTextPane;
    private JTextField nomeTextField;
    private JTextField DurataTextField;
    private JTextField Data_I_TextField;
    private JTextField N_PostiTextField;
    private JTextField N_CFUTextField;
    private JTextField DipartimentoTextField;
    private JButton ConfermaButton;
    private JTextField LaboratorioTextField;


    public Int_Tirocinio_Interno(Controller controller) {
        this.controller = controller;
        this.setContentPane(creaTirocinioInternoTextPane);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        String nome = nomeTextField.getText();
        String durata = DurataTextField.getText();
        String data_inizio = Data_I_TextField.getText();
        String nposti =  N_PostiTextField.getText();
        String ncfu = N_CFUTextField.getText();
        String dipartimento = DipartimentoTextField.getText();
        String laboratorio = LaboratorioTextField.getText();

    }
}
