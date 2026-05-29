package gui;
import controller.Controller;
import gui.Docente.Int_Docente;
import gui.Registrazioni.Reg_Docente;
import gui.Registrazioni.Reg_Studente;
import gui.Studente.Int_Studente;

import javax.swing.*;

public class Home extends JFrame {
	private Controller controller;
	private JButton LoginButton;
	private JTextField user;
	private JPanel finestra;
	private JButton RegisterButton;
	//i JRadioButton serviranno sia per la selezione del login che per quella della registrazione
	private JRadioButton studenteRadioButton;
	private JRadioButton docenteRadioButton;
	private JPasswordField passwordField1;
	private JTextField HOMETextField;
	private JTextArea textArea2;
	ButtonGroup gruppoScelte = new ButtonGroup();



	public Home(Controller controller) {
		this.controller = controller; // Assegna quello passato dall'esterno
		this.setContentPane(finestra);
		this.setTitle("PORTALE LOGIN");
		this.setSize(300, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gruppoScelte.add(studenteRadioButton);
		gruppoScelte.add(docenteRadioButton);


		//se viene premuto il JRadioButton Studente, viene creata l'interaccia studente, altrimenti crea l'interfaccia Docente
		LoginButton.addActionListener(e -> {
			String userInserito = this.user.getText();
			String PasswordInserita = String.valueOf(this.passwordField1.getPassword());

			try {
				if (studenteRadioButton.isSelected()) {
					// Tenta il login come Studente, e nel caso apre l'interfaccia corrispondente
					controller.effettuaLoginStudente(userInserito, PasswordInserita);
					Int_Studente interfacciaStud = new Int_Studente(this.controller);
					interfacciaStud.setVisible(true);
					this.dispose();

				} else if (docenteRadioButton.isSelected()) {
					// Tenta il login come Docente, e nel caso apre l'interfaccia corrispondente
					controller.effettuaLoginDocente(userInserito, PasswordInserita);
					Int_Docente interfacciaDoc = new Int_Docente(this.controller);
					interfacciaDoc.setVisible(true);
					this.dispose();

				} else {
					// Nessun ruolo selezionato
					JOptionPane.showMessageDialog(this, "Seleziona un ruolo (Studente o Docente) prima di accedere.", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}

			} catch (IllegalArgumentException ex) {
				// Se uno dei due login lancia l'errore di login errato, viene mandato a schermo
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Accesso Negato", JOptionPane.ERROR_MESSAGE);
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
				Reg_Docente RegDoc = new Reg_Docente(this.controller);
				RegDoc.setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Seleziona un ruolo (Studente o Docente) prima di provare a Registrarti.", "Attenzione", JOptionPane.WARNING_MESSAGE);				}
		});

	}






}
