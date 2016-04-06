package sg.edu.nus.iss.ssa.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.validation.FormValidator;

/**
 * 
 * @author Tang Han (Tom)
 *
 */

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String LoginWindowName = "Store Keeper Login";
	private static final String AppName = "Souvenir Store";
	private static final int LoginWindowFrameSizeWidth = 450;
	private static final int LoginWindowFrameSizeHeight = 320;
	private static final int LoginWindowTextFieldWidth = 400;
	private static final int LoginWindowLableHeight = 20;
	private static final int LoginWindowTextFieldHeight = 40;
	private static final int LoginWindowElementLayoutGuideLeft = (LoginWindowFrameSizeWidth - LoginWindowTextFieldWidth)/2;
	private static final int LoginWindowElementLayoutGuideTop = 20;
	private static final int LoginWindowElementVerticalMargin = 10;
	
	private JPanel contentPane;
	private JButton btnLogin;
	private JButton btnExit;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblError;
	
	public LoginWindow() {
		initializeUIElements();
	}
	
	private void initializeUIElements() {
		LoginWindow loginWin = this;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(LoginWindowName);
		setSize(LoginWindowFrameSizeWidth,LoginWindowFrameSizeHeight);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("PT Team 4 Souvenir Store", SwingConstants.CENTER);
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
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(lblUsername.getBounds().x, lblPassword.getBounds().y + lblPassword.getSize().height, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight);
		contentPane.add(txtPassword);
		
		lblError = new JLabel("", SwingConstants.CENTER);
		lblError.setFont(lblError.getFont().deriveFont(11.0f));
		lblError.setBounds(lblUsername.getBounds().x, txtPassword.getBounds().y + txtPassword.getSize().height + LoginWindowElementVerticalMargin, LoginWindowTextFieldWidth, LoginWindowTextFieldHeight/2);
		contentPane.add(lblError);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(253, 231, 100, 50);
		contentPane.add(btnExit);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(91, 231, 100, 50);
		contentPane.add(btnLogin);
		
		this.getRootPane().setDefaultButton(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName =  txtUsername.getText();
				String errorMsg = FormValidator.addStoreKeeperValidateForm(userName, txtPassword.getPassword());
				if (errorMsg != null) {
					lblError.setForeground(Color.RED);
					lblError.setText(errorMsg);
				}else {
					lblError.setForeground(Color.GREEN);
					lblError.setText("");
					try {
						DashBoard dashboardwindow = new DashBoard(userName);
						//dashboardwindow.setLocation(loginWin.getLocationOnScreen());
						dashboardwindow.setVisible(true);
						dispose();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}				
			}
		});	
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
