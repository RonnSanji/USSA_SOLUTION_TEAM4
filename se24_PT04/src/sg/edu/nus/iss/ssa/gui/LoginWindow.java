package sg.edu.nus.iss.ssa.gui;

import java.awt.Color;
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
	
	private static final long serialVersionUID = 1L;
	private static final String LoginWindowName = "Store Keeper Login";
	private static final int LoginWindowFrameSizeWidth = 250;
	private static final int LoginWindowFrameSizeHeight = 200;
	private static final int LoginWindowTextFieldWidth = 200;
	private static final int LoginWindowTextFieldHeight = 20;
	private static final int LoginWindowElementLayoutGuideLeft = (LoginWindowFrameSizeWidth - LoginWindowTextFieldWidth)/2;
	private static final int LoginWindowElementLayoutGuideTop = 10;
	private static final int LoginWindowElementVerticalMargin = 5;
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel lblError;
	private JButton btnLogin;

	private AuthenticationManager authManager;
	
	public LoginWindow() {
		initializeUIElements();
		authManager = new AuthenticationManager();
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
		
		JLabel lblUsername = new JLabel("Name:");
		lblUsername.setBounds(LoginWindowElementLayoutGuideLeft, LoginWindowElementLayoutGuideTop, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(lblUsername.getBounds().x, lblUsername.getBounds().y + lblUsername.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(txtUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(lblUsername.getBounds().x, txtUsername.getBounds().y + txtUsername.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(lblUsername.getBounds().x, lblPassword.getBounds().y + lblPassword.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(txtPassword);
		
		lblError = new JLabel("", SwingConstants.CENTER);
		lblError.setFont(lblError.getFont().deriveFont(11.0f));
		lblError.setBounds(lblUsername.getBounds().x, txtPassword.getBounds().y + txtPassword.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight/2);
		contentPane.add(lblError);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(lblUsername.getBounds().x, lblError.getBounds().y + lblError.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight*2);
		contentPane.add(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblError.setText("");
				Boolean isValidStoreKeeper = authManager.authenticateUser(txtUsername.getText(), txtPassword.getText());
				if (!isValidStoreKeeper) {
					lblError.setForeground(Color.RED);
					lblError.setText(authManager.getErrorMessage());
				}else {
					lblError.setForeground(Color.GREEN);
					lblError.setText("Login Successfully!");
				}				
			}
		});

		
	}
}
