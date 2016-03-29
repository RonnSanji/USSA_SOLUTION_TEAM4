package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import sg.edu.nus.iss.ssa.util.IOService;

import java.awt.Color;
import java.awt.Component;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DashBoard extends JFrame
{

	private ImageIcon imgStoreKeeper, imgBackGround, imgPurchasing, imgMember, imgCategory, imgInventory, imgClock,
			imgDiscount, imgReport, imgLogout;
	//private MemberManagerWindow memberManagerWindow;
	//private ManageCategory manageCaterogy;
	//private ManageStock manageStock;
	private JTextField textFieldSKName;
	private JTextField txtTime;
	private JPanel mainActivityPanel;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { DashBoard window = new
	 * DashBoard(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }/*
	 * 
	 * /** Create the application.
	 */
	public DashBoard(String loginuser)
	{

		initialize(loginuser);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void imageInitialize()
	{
		try
		{
			/*
			 * imgBackGround = new
			 * ImageIcon(this.getClass().getResource("/background1280.jpg"));
			 * imgStoreKeeper = new
			 * ImageIcon(this.getClass().getResource("/storeKeeper.png"));
			 * imgPurchasing = new
			 * ImageIcon(this.getClass().getResource("/purchasing.png"));
			 * imgMember = new
			 * ImageIcon(this.getClass().getResource("/member.png"));
			 * imgCategory = new
			 * ImageIcon(this.getClass().getResource("/category.png"));
			 * imgInventory = new
			 * ImageIcon(this.getClass().getResource("/inventory.png"));
			 * imgClock = new
			 * ImageIcon(this.getClass().getResource("/clock.png")); imgDiscount
			 * = new ImageIcon(this.getClass().getResource("/discount.png"));
			 * imgReport = new
			 * ImageIcon(this.getClass().getResource("/report.png")); imgLogout
			 * = new ImageIcon(this.getClass().getResource("/logout.png"));
			 */
			String imgFolderPath = IOService.getImageFileLocation();
			System.out.println(imgFolderPath);
			imgBackGround = new ImageIcon(imgFolderPath + "/background1280.jpg");
			imgStoreKeeper = new ImageIcon(imgFolderPath + "/storeKeeper.png");
			imgPurchasing = new ImageIcon(imgFolderPath + "/purchasing.png");
			imgMember = new ImageIcon(imgFolderPath + "/member.png");
			imgCategory = new ImageIcon(imgFolderPath + "/category.png");
			imgInventory = new ImageIcon(imgFolderPath + "/inventory.png");
			imgClock = new ImageIcon(imgFolderPath + "/clock.png");
			imgDiscount = new ImageIcon(imgFolderPath + "/discount.png");
			imgReport = new ImageIcon(imgFolderPath + "/report.png");
			imgLogout = new ImageIcon(imgFolderPath + "/logout.png");

		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
			System.out.println("File not found " + ex.getMessage());
		}
	}

	private void initialize(String loginuser)
	{
		imageInitialize();

		this.setResizable(false);
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(1280, 800);

		JPanel stroreKepperInfoPanel = new JPanel();
		stroreKepperInfoPanel.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		stroreKepperInfoPanel.setBounds(40, 0, 1200, 138);
		stroreKepperInfoPanel.setOpaque(false);
		this.getContentPane().add(stroreKepperInfoPanel);
		stroreKepperInfoPanel.setLayout(null);

		JLabel lblFantasyTeam = new JLabel("Fantasy Team 4 Souvenir Store");
		lblFantasyTeam.setFont(new Font("Zapfino", Font.BOLD, 24));
		lblFantasyTeam.setBounds(350, 6, 460, 64);
		stroreKepperInfoPanel.add(lblFantasyTeam);

		JLabel lblStoreKeeper = new JLabel("New label");
		lblStoreKeeper.setBounds(6, 51, 100, 96);
		lblStoreKeeper.setIcon(imgStoreKeeper);
		stroreKepperInfoPanel.add(lblStoreKeeper);

		JLabel lblSkName = new JLabel("Store Keeper Name:");
		lblSkName.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblSkName.setBounds(106, 83, 156, 30);
		stroreKepperInfoPanel.add(lblSkName);

		textFieldSKName = new JTextField();
		textFieldSKName.setText(loginuser);
		textFieldSKName.setEditable(false);
		textFieldSKName.setBounds(270, 82, 200, 35);
		stroreKepperInfoPanel.add(textFieldSKName);
		textFieldSKName.setColumns(10);

		JLabel lblClock = new JLabel("New label");
		lblClock.setBounds(599, 72, 55, 55);
		lblClock.setIcon(imgClock);
		stroreKepperInfoPanel.add(lblClock);

		txtTime = new JTextField();
		txtTime.setEditable(false);
		txtTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtTime.setText("Sun 20 Mar 23:07");
		txtTime.setBounds(676, 82, 200, 35);
		stroreKepperInfoPanel.add(txtTime);
		txtTime.setColumns(10);

		JButton btnLogout = new JButton("New button");
		btnLogout.setBounds(1040, 73, 49, 55);
		btnLogout.setIcon(imgLogout);
		stroreKepperInfoPanel.add(btnLogout);

		JLabel lblLogout = new JLabel("Logout");
		lblLogout.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblLogout.setBounds(1111, 81, 61, 35);
		stroreKepperInfoPanel.add(lblLogout);

		mainActivityPanel = new JPanel();
		mainActivityPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2, true),
				"MainActivities", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainActivityPanel.setBounds(380, 161, 860, 600);
		this.getContentPane().add(mainActivityPanel);
		mainActivityPanel.setOpaque(false);
		mainActivityPanel.setLayout(null);

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2, true), "MENU",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		menuPanel.setBounds(42, 161, 298, 600);
		this.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		menuPanel.setOpaque(false);

		JButton btnMemberManage = new JButton("Memeber Management");
		btnMemberManage.setFont(new Font("Zapfino", Font.BOLD, 13));
		btnMemberManage.setBounds(92, 120, 200, 50);
		menuPanel.add(btnMemberManage);

		JLabel lblPurchasing = new JLabel("");
		lblPurchasing.setBounds(18, 22, 60, 50);
		lblPurchasing.setIcon(imgPurchasing);
		menuPanel.add(lblPurchasing);

		JButton btnNewButton = new JButton("Purchase Management");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ProductSelectionWindow productSelectionWindow = new ProductSelectionWindow();
				activateMainPanel(mainActivityPanel,productSelectionWindow);
			}
		});
		btnNewButton.setFont(new Font("Zapfino", Font.PLAIN, 13));
		btnNewButton.setBounds(92, 22, 200, 50);
		menuPanel.add(btnNewButton);

		JButton btnCategoryManagement = new JButton("Category Management");
		btnCategoryManagement.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ManageCategory manageCaterogy = new ManageCategory();
				activateMainPanel(mainActivityPanel,manageCaterogy);
			}
		});
		btnCategoryManagement.setFont(new Font("Zapfino", Font.PLAIN, 13));
		btnCategoryManagement.setBounds(92, 212, 200, 50);
		menuPanel.add(btnCategoryManagement);

		JButton btnProductManagement = new JButton("Inventory Management");
		btnProductManagement.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ManageStock manageStock = new ManageStock();
				activateMainPanel(mainActivityPanel, manageStock);
			}
		});
		btnProductManagement.setFont(new Font("Zapfino", Font.PLAIN, 13));
		btnProductManagement.setBounds(92, 308, 200, 50);
		menuPanel.add(btnProductManagement);

		JButton btnNewButton_1 = new JButton("Discount Management");
		btnNewButton_1.setFont(new Font("Zapfino", Font.PLAIN, 13));
		btnNewButton_1.setBounds(92, 403, 200, 50);
		menuPanel.add(btnNewButton_1);

		JButton btnReport = new JButton("Report ");
		btnReport.setFont(new Font("Zapfino", Font.PLAIN, 13));
		btnReport.setBounds(92, 497, 200, 50);
		menuPanel.add(btnReport);

		JLabel lblMember = new JLabel("");
		lblMember.setBounds(18, 120, 60, 50);
		lblMember.setIcon(imgMember);
		menuPanel.add(lblMember);

		JLabel lblCategory = new JLabel("");
		lblCategory.setBounds(18, 212, 65, 50);
		lblCategory.setIcon(imgCategory);
		menuPanel.add(lblCategory);

		JLabel lblInventory = new JLabel("");
		lblInventory.setBounds(18, 308, 65, 50);
		lblInventory.setIcon(imgInventory);
		menuPanel.add(lblInventory);

		JLabel lblDiscount = new JLabel("");
		lblDiscount.setBounds(18, 403, 65, 67);
		lblDiscount.setIcon(imgDiscount);
		menuPanel.add(lblDiscount);

		JLabel lblReport = new JLabel("");
		lblReport.setBounds(15, 494, 65, 50);
		lblReport.setIcon(imgReport);
		;
		menuPanel.add(lblReport);

		btnMemberManage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MemberManagerWindow memberManagerWindow = new MemberManagerWindow();
				activateMainPanel(mainActivityPanel, memberManagerWindow);
			}
		});

		JLabel lblBackGround = new JLabel("");
		lblBackGround.setIcon(imgBackGround);
		lblBackGround.setBounds(0, 0, 1280, 800);
		this.getContentPane().add(lblBackGround);
	}

	/*private void showWindow(Component component)
	{
		Component[] components = mainActivityPanel.getComponents();
		if (components != null)
		{
			for (Component c : components)
			{
				c.setVisible(false);
			}
		}
		component.setVisible(true);
	}*/

	private void activateMainPanel(JPanel mainActivityPanel, JPanel memberManagerWindow){
		mainActivityPanel.removeAll();
		memberManagerWindow.setBounds(6, 6, 854, 588);
		memberManagerWindow.setVisible(false);
		mainActivityPanel.add(memberManagerWindow);
		memberManagerWindow.setVisible(true);
	}
}
