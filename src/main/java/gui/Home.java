package gui;
import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
	private Controller controller;

	public Home() {
		this.controller= new Controller();
	}
	private JPanel finestra1;
	private Window Finestra;

	public static void main(String[] args) {
		Home interfaccia = new Home();
		JFrame Frame = new JFrame("Home");
		Frame.setContentPane(new Home().finestra1);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.pack();
		Frame.setVisible(true);
	}
}

