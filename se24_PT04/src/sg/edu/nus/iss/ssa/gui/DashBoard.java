package sg.edu.nus.iss.ssa.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import sg.edu.nus.iss.ssa.util.IOService;

public class DashBoard extends JFrame
{

	private ImageIcon imgStoreKeeper, imgBackGround, imgPurchasing,imgProduct, imgMember, imgCategory, imgInventory, imgClock,
			imgDiscount, imgReport, imgLogout;

	private JTextField textFieldSKName;
	private JTextField txtTime;
	private JPanel mainActivityPanel;
	private final Timer updater = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtTime.setText(getSystemTime());
		}
	});

	public DashBoard(String loginuser)
	{
		setTitle("PT Team 4 Souvenir Store");

		initialize(loginuser);
		updater.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void imageInitialize()
	{
		try
		{

			String imgFolderPath = IOService.getImageFileLocation();
			//System.out.println(imgFolderPath);
			imgBackGround = new ImageIcon(imgFolderPath + "/background1280.png");
			imgStoreKeeper = new ImageIcon(imgFolderPath + "/storeKeeper.png");
			imgPurchasing = new ImageIcon(imgFolderPath + "/purchasing.png");
			imgProduct = new ImageIcon(imgFolderPath + "/product.png");
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
		DashBoard dashBoard = this;
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

		JLabel lblFantasyTeam = new JLabel("PT Team 4 Souvenir Store");
		lblFantasyTeam.setFont(new Font("AppleGothic", Font.BOLD, 32));
		lblFantasyTeam.setBounds(387, 6, 460, 64);
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
		textFieldSKName.setBounds(270, 82, 230, 35);
		stroreKepperInfoPanel.add(textFieldSKName);
		textFieldSKName.setColumns(10);

		JLabel lblClock = new JLabel("New label");
		lblClock.setBounds(599, 72, 55, 55);
		lblClock.setIcon(imgClock);
		stroreKepperInfoPanel.add(lblClock);

		txtTime = new JTextField();
		txtTime.setEditable(false);
		txtTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtTime.setText(this.getSystemTime());
		txtTime.setBounds(676, 82, 230, 35);
		stroreKepperInfoPanel.add(txtTime);
		txtTime.setColumns(10);

		JButton btnLogout = new JButton("New button");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updater.stop();
				dispose();
				System.exit(0);
			}
		});
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
		btnMemberManage.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnMemberManage.setBounds(70, 180, 220, 50);
		menuPanel.add(btnMemberManage);

		JLabel lblPurchasing = new JLabel("");
		lblPurchasing.setBounds(10, 20, 60, 50);
		lblPurchasing.setIcon(imgPurchasing);
		menuPanel.add(lblPurchasing);

		JButton btnNewButton = new JButton("Purchase Management");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showProductSelectionWindow(dashBoard);
			}
		});
		btnNewButton.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnNewButton.setBounds(70, 20, 220, 50);
		menuPanel.add(btnNewButton);

		JButton btnCategoryManagement = new JButton("Category Management");
		btnCategoryManagement.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ManageCategory manageCaterogy = new ManageCategory();
				activateMainPanel(manageCaterogy);
			}
		});
		btnCategoryManagement.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnCategoryManagement.setBounds(70, 260, 220, 50);
		menuPanel.add(btnCategoryManagement);

		JButton btnInvetoryManagement = new JButton("Inventory Management");
		btnInvetoryManagement.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ManageInventory manageInventory = new ManageInventory();
				activateMainPanel( manageInventory);
			}
		});
		btnInvetoryManagement.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnInvetoryManagement.setBounds(70, 340, 220, 50);
		menuPanel.add(btnInvetoryManagement);

		JButton btnDiscount = new JButton("Discount Management");
		btnDiscount.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnDiscount.setBounds(70, 420, 220, 50);
		menuPanel.add(btnDiscount);
		btnDiscount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManageDiscount discount = new ManageDiscount();
				activateMainPanel(discount);
			}
		});

		JButton btnReport = new JButton("Report");
		btnReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ReportSummary reportSummary=new ReportSummary();
				activateMainPanel(reportSummary);
			}
		});
		btnReport.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnReport.setBounds(70, 500, 220, 50);
		menuPanel.add(btnReport);

		JLabel lblProduct = new JLabel("");
		lblProduct.setBounds(10, 99, 60, 50);
		lblProduct.setIcon(imgProduct);
		menuPanel.add(lblProduct);

		JLabel lblMember = new JLabel("");
		lblMember.setBounds(10, 180, 60, 50);
		lblMember.setIcon(imgMember);
		menuPanel.add(lblMember);

		JLabel lblCategory = new JLabel("");
		lblCategory.setBounds(10, 260, 60, 50);
		lblCategory.setIcon(imgCategory);
		menuPanel.add(lblCategory);

		JLabel lblInventory = new JLabel("");
		lblInventory.setBounds(10, 340, 60, 50);
		lblInventory.setIcon(imgInventory);
		menuPanel.add(lblInventory);

		JLabel lblDiscount = new JLabel("");
		lblDiscount.setBounds(10, 420, 60, 50);
		lblDiscount.setIcon(imgDiscount);
		menuPanel.add(lblDiscount);

		JLabel lblReport = new JLabel("");
		lblReport.setBounds(10, 500, 60, 50);
		lblReport.setIcon(imgReport);
		;
		menuPanel.add(lblReport);
		btnDiscount.setFont(new Font("AppleGothic", Font.BOLD, 16));

		JButton buttonProductManagement = new JButton("Product Management");
		buttonProductManagement.setFont(new Font("AppleGothic", Font.BOLD, 16));
		btnDiscount.setFont(new Font("AppleGothic", Font.BOLD, 16));
		buttonProductManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
       ManageProductWindow productManagerWindow = new ManageProductWindow();
				activateMainPanel(productManagerWindow);
			}
		});
		buttonProductManagement.setBounds(70, 100, 220, 50);
		menuPanel.add(buttonProductManagement);



		btnMemberManage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MemberManagerWindow memberManagerWindow = new MemberManagerWindow();
				activateMainPanel( memberManagerWindow);
			}
		});

		JLabel lblBackGround = new JLabel("");
		lblBackGround.setIcon(imgBackGround);
		lblBackGround.setBounds(0, 0, 1280, 800);
		this.getContentPane().add(lblBackGround);

		showProductSelectionWindow(dashBoard);
	}


	public void activateMainPanel(JPanel panelToActivate){
		mainActivityPanel.removeAll();
		panelToActivate.setBounds(6, 6, 854, 588);
		panelToActivate.setVisible(false);
		mainActivityPanel.add(panelToActivate);
		panelToActivate.setVisible(true);
	}

	private String getSystemTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(date);
		return date.toString();

	}

	public void showProductSelectionWindow(DashBoard dashBoard) {
		ProductSelectionWindow productSelectionWindow = new ProductSelectionWindow(dashBoard);
		activateMainPanel(productSelectionWindow);
	}

	public JPanel getMainActivityPanel(){
		return mainActivityPanel;
	}
}
