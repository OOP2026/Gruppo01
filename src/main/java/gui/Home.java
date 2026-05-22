package gui;
import controller.Controller;
import javax.swing.*;

public class Home extends JFrame {
	private Controller controller;
	private JPanel finestra1;
	private JTextField User;
	private JPasswordField Pwd;
	private JButton logButton;
	private JPanel panel1;
	private JTextField user;


	public Home() {
		this.controller = new Controller();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);


	}


	public static void main(String[] args) {
		Home interfaccia = new Home();
		interfaccia.setVisible(true);
	}


}
