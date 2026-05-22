package gui;
import model.*;
import controller.Controller;
import javax.swing.*;

public class Home extends JFrame {
	private Controller controller;
	private JButton LoginButton;
	private JPasswordField password;
	private JTextField user;
	private JPanel finestra;

	public Home() {
		this.controller = new Controller();
		this.setContentPane(finestra);
		this.setTitle("Portale Login");
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}

	public static void main(String[] args) {
		{
			Home interfaccia = new Home();
			interfaccia.setVisible(true);
		}
	};


}
