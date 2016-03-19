package sg.edu.nus.iss.ssa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculator;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.bo.TotalReceiptCalculator;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

public class ReceiptSummary extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField totalPrice;
	private JTextField applicableDiscount;
	private JTextField finalPrice;
	private JTextField pointsRedeemed;
	private JTextField cashRendered;
	private JLabel lblTotalPrice;
	private JLabel lblAmountToReturn;
	private JTextField returnedAmount;
	private JLabel lblRemainingPoints;
	private JTextField remainingPoints;
	private JButton btnOk;
	private JSeparator separator;

	Order order=  null;
	TotalReceiptCalculator reciptCalculator;


	/**
	 * Create the frame.
	 */
	public ReceiptSummary() {
		order = FileDataWrapper.receipt;
		reciptCalculator = new TotalReceiptCalculator(order);
		reciptCalculator.processPayment();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Receipt Summary");
		setSize(700,650);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProductsPurchased = new JLabel("Products Purchased:");
		lblProductsPurchased.setBounds(31, 16, 159, 20);
		contentPane.add(lblProductsPurchased);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 62, 596, 173);
		contentPane.add(scrollPane);
		
		// jTable Data Display
		String[] columns = new String[] { "Product Name", "Price", "Quantity",
				"Total Price" };

		List<LineItem> boughtProducts = FileDataWrapper.receipt.getItems();

		// actual data for the table in a 2d array
		Object[][] data = new Object[boughtProducts.size()][];

		for (int i = 0; i < boughtProducts.size(); i++) {
			data[i] = boughtProducts.get(i).getItemsArray();
		}
		
		table =   new JTable(data, columns);
		scrollPane.setViewportView(table);
			
		totalPrice = new JTextField();
		totalPrice.setEditable(false);
		totalPrice.setBounds(322, 251, 179, 26);
		contentPane.add(totalPrice);
		totalPrice.setColumns(10);
		totalPrice.setText(String.valueOf(FileDataWrapper.receipt.getTotalPrice()));
		
		JLabel lblApplicablePoints = new JLabel("Applicable Discount");
		lblApplicablePoints.setBounds(74, 289, 166, 20);
		contentPane.add(lblApplicablePoints);
		
		applicableDiscount = new JTextField();
		applicableDiscount.setEditable(false);
		applicableDiscount.setColumns(10);
		applicableDiscount.setBounds(322, 286, 179, 26);
		applicableDiscount.setText(DisplayUtil.getDiscountText(order));
		contentPane.add(applicableDiscount);
		
		JLabel lblFinalPrice = new JLabel("Final Price:");
		lblFinalPrice.setBounds(74, 331, 159, 20);
		contentPane.add(lblFinalPrice);
		
		finalPrice = new JTextField();
		finalPrice.setEditable(false);
		finalPrice.setColumns(10);
		finalPrice.setBounds(322, 328, 179, 26);
		finalPrice.setText(String.valueOf(order.getFinalPrice()));
		contentPane.add(finalPrice);
		
		JLabel lblRedeemPoints = new JLabel("Points Redeemed");
		lblRedeemPoints.setBounds(74, 373, 159, 20);
		contentPane.add(lblRedeemPoints);
		
		pointsRedeemed = new JTextField();
		pointsRedeemed.setEditable(false);
		pointsRedeemed.setColumns(10);
		pointsRedeemed.setBounds(322, 370, 179, 26);
		pointsRedeemed.setText(reciptCalculator.getCashEquivalentPointstext(order));
		contentPane.add(pointsRedeemed);
		
		JLabel lblCashRendered = new JLabel("Cash Rendered:");
		lblCashRendered.setBounds(74, 421, 159, 20);
		contentPane.add(lblCashRendered);
		
		cashRendered = new JTextField();
		cashRendered.setEditable(false);
		cashRendered.setColumns(10);
		cashRendered.setBounds(322, 418, 179, 26);
		cashRendered.setText(String.valueOf(order.getAmountTendered()));
		contentPane.add(cashRendered);
		
		lblTotalPrice = new JLabel("Total Price:");
		lblTotalPrice.setBounds(74, 254, 166, 20);
		contentPane.add(lblTotalPrice);
		
		lblAmountToReturn = new JLabel("Amount To Return:");
		lblAmountToReturn.setBounds(74, 463, 159, 20);
		contentPane.add(lblAmountToReturn);
		
		returnedAmount = new JTextField();
		returnedAmount.setEditable(false);
		returnedAmount.setColumns(10);
		returnedAmount.setBounds(322, 460, 179, 26);
		returnedAmount.setText(String.valueOf(order.getReturnAmount()));
		contentPane.add(returnedAmount);
		
		lblRemainingPoints = new JLabel("Remaining Loyalty Points:");
		lblRemainingPoints.setBounds(74, 499, 191, 20);
		contentPane.add(lblRemainingPoints);
		
		remainingPoints = new JTextField();
		remainingPoints.setEditable(false);
		remainingPoints.setColumns(10);
		remainingPoints.setBounds(322, 496, 179, 26);
		remainingPoints.setText(String.valueOf(order.getMemberInfo() != null ? order.getMemberInfo().getLoyaltyPoints() : 0));
		contentPane.add(remainingPoints);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//return to Dashboard
				dispose();
				
			}
		});
		btnOk.setBounds(522, 565, 115, 29);
		contentPane.add(btnOk);
		
		separator = new JSeparator();
		separator.setBounds(41, 548, 627, 2);
		contentPane.add(separator);
	
	}
}
