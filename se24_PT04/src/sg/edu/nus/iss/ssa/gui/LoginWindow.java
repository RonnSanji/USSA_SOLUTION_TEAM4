package sg.edu.nus.iss.ssa.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.AuthenticationManager;

/**
 * 
 * @author Tang Han (Tom)
 *
 */

public class LoginWindow extends JFrame {
	
	public JButton btnLogin;
	public JTextField txtUsername;
	public JTextField txtPassword;
	public JLabel lblError;

	private static final long serialVersionUID = 1L;
	private static final String LoginWindowName = "Store Keeper Login";
	private static final String AppName = "Souvenir Store";
	private static final int LoginWindowFrameSizeWidth = 500;
	private static final int LoginWindowFrameSizeHeight = 340;
	private static final int LoginWindowTextFieldWidth = 400;
	private static final int LoginWindowLableHeight = 20;
	private static final int LoginWindowTextFieldHeight = 40;
	private static final int LoginWindowElementLayoutGuideLeft = (LoginWindowFrameSizeWidth - LoginWindowTextFieldWidth)/2;
	private static final int LoginWindowElementLayoutGuideTop = 20;
	private static final int LoginWindowElementVerticalMargin = 10;
	
	private JPanel contentPane;
	
	public LoginWindow() {
		initializeUIElements();
	}
	
	private void initializeUIElements() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(LoginWindowName);
		setSize(LoginWindowFrameSizeWidth,LoginWindowFrameSizeHeight);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel(AppName, SwingConstants.CENTER);
		lblLogo.setFont(new Font("Zapfino", Font.BOLD, 21));
		lblLogo.setBounds(LoginWindowElementLayoutGuideLeft, LoginWindowElementLayoutGuideTop, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(lblLogo);
		
		JLabel lblUsername = new JLabel("Name:");
		lblUsername.setBounds(lblLogo.getBounds().x, lblLogo.getBounds().y + lblLogo.getSize().height, LoginWindowTextFieldWidth, LoginWindowLableHeight);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(lblUsername.getBounds().x, lblUsername.getBounds().y + lblUsername.getSize().height, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(txtUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(lblUsername.getBounds().x, txtUsername.getBounds().y + txtUsername.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowLableHeight);
		contentPane.add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(lblUsername.getBounds().x, lblPassword.getBounds().y + lblPassword.getSize().height, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(txtPassword);
		
		lblError = new JLabel("", SwingConstants.CENTER);
		lblError.setFont(lblError.getFont().deriveFont(11.0f));
		lblError.setBounds(lblUsername.getBounds().x, txtPassword.getBounds().y + txtPassword.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight/2);
		contentPane.add(lblError);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(lblUsername.getBounds().x, lblError.getBounds().y + lblError.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(btnLogin);
		
	}
}
