package gui;
import controller.Controller;
import javax.swing.*;
import model.*;

public class Home extends JFrame {
	private Controller controller;
	private JButton LoginButton;
	private JPasswordField password;
	private JTextField user;
	private JPanel finestra;
	private JTextField HOMETextField;
	private JButton CREAACCOUNTButton;
	private JRadioButton studenteRadioButton;
	private JRadioButton docenteRadioButton;

	public Home() {
		this.controller = new Controller();
		this.setContentPane(finestra);
		this.setTitle("Portale Login");
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		LoginButton.addActionListener(e -> {
			String userInserito = this.user.getText();
			String PasswordInserita = String.valueOf(this.password.getPassword());
			try {
				if (studenteRadioButton.isSelected()) {
					Studente oggettoStudente = controller.effettuaLoginStudente(userInserito, PasswordInserita);
					Int_Studente interfacciaStud = new Int_Studente(this.controller, oggettoStudente);
				}
			} catch (IllegalArgumentException loginErrato)  {
				JOptionPane.showMessageDialog(this, loginErrato.getMessage(), "Accesso Negato", JOptionPane.ERROR_MESSAGE);
			}
		});

		LoginButton.addActionListener(e -> {
			String userInserito = this.user.getText();
			String PasswordInserita = String.valueOf(this.password.getPassword());
			try {
				if (docenteRadioButton.isSelected()) {
					Docente oggettoDocente = controller.effettuaLoginDocente(userInserito, PasswordInserita);
					Int_Docente interfacciaDocente = new Int_Docente(this.controller, oggettoDocente);
				}
			} catch (IllegalArgumentException loginErrato)  {
				JOptionPane.showMessageDialog(this, loginErrato.getMessage(), "Accesso Negato", JOptionPane.ERROR_MESSAGE);
			}
		});

	}

	public static void main(String[] args) {
		{
			Home interfaccia = new Home();
			interfaccia.setVisible(true);
		}
	};


}
