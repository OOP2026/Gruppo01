package gui;
import controller.Controller;
import javax.swing.*;

public class Home extends JFrame {
	private Controller controller;
	private JButton LoginButton;
	private JPasswordField password;
	private JTextField user;
	private JPanel finestra;
	private JButton RegisterButton;
	//i JRadioButton serviranno sia per la selezione del login che per quella della registrazione
	private JRadioButton studenteRadioButton;
	private JRadioButton docenteRadioButton;
	private JTextPane HOMETextPane;

	public Home() {
		this.controller = new Controller();
		this.setContentPane(finestra);
		this.setTitle("Portale Login");
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		//se viene premuto il JRadioButton Studente, viene creata l'interaccia studente, altrimenti crea l'interfaccia Docente
		LoginButton.addActionListener(e -> {
			String userInserito = this.user.getText();
			String PasswordInserita = String.valueOf(this.password.getPassword());
			try {
				if (studenteRadioButton.isSelected()) {
					//crea l'interfaccia Studente, gli passa sia controller che oggetto Studente desiderato
					Int_Studente interfacciaStud = new Int_Studente(this.controller);
					interfacciaStud.setVisible(true);
					this.dispose();
				} else if (docenteRadioButton.isSelected()) {
					//crea l'interfaccia Docente, gli passa sia controller che oggetto Docente desiderato
					Int_Docente interfacciaStud = new Int_Docente(this.controller);
					interfacciaStud.setVisible(true);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Seleziona un ruolo (Studente o Docente) prima di accedere.", "Attenzione", JOptionPane.WARNING_MESSAGE);				}
			} catch (IllegalArgumentException loginErrato)  {
				JOptionPane.showMessageDialog(this, loginErrato.getMessage(), "Accesso Negato", JOptionPane.ERROR_MESSAGE);
			}
		});

		RegisterButton.addActionListener(e -> {
			if (studenteRadioButton.isSelected()) {
				//se viene premuto il JRadioButton Studente, crea l'interfaccia registrazione Studente, e gli passa il controller
				Reg_Studente regStud = new Reg_Studente(this.controller);
				regStud.setVisible(true);
				this.dispose();
			} else if (docenteRadioButton.isSelected()) {
				//se viene premuto il JRadioButton Docente, crea l'interfaccia registrazione Studente, e gli passa il controller
				Reg_Studente regStud = new Reg_Studente(this.controller);
				regStud.setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Seleziona un ruolo (Studente o Docente) prima di provare a Registrarti.", "Attenzione", JOptionPane.WARNING_MESSAGE);				}
		});





	}



	public static void main(String[] args) {
		{
			Home interfaccia = new Home();
			interfaccia.setVisible(true);
		}
	};


}
