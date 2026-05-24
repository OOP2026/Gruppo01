package gui;
import controller.Controller;
import javax.swing.*;

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

		//se viene premuto il JRadioButton Studente, viene creata l'interaccia studente, altrimenti crea l'interfaccia Docente
		LoginButton.addActionListener(e -> {
			String userInserito = this.user.getText();
			String PasswordInserita = String.valueOf(this.password.getPassword());
			try {
				if (studenteRadioButton.isSelected() && controller.effettuaLoginStudente(userInserito, PasswordInserita)) {
					Int_Studente interfacciaStud = new Int_Studente(this.controller, controller.getMatricolaStudenteLoggato());
					interfacciaStud.setVisible(true);
					this.dispose();

				} else if (docenteRadioButton.isSelected()) {
					Int_Docente interfacciaStud = new Int_Docente(this.controller, controller.effettuaLoginDocente(userInserito, PasswordInserita));
					interfacciaStud.setVisible(true);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Seleziona un ruolo (Studente o Docente) prima di accedere.", "Attenzione", JOptionPane.WARNING_MESSAGE);				}
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
