package sg.edu.nus.iss.ssa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.table.DefaultTableModel;

import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculator;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.bo.TotalReceiptCalculator;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

/**
 * Creates screen to show Order Summary.
 *
 * @author Amarjeet B Singh
 *
 */
public class ReceiptSummary extends JPanel {

	private JTable table;
	private DefaultTableModel model;
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

	Order order = null;
	TotalReceiptCalculator reciptCalculator;

	/**
	 * Create the frame.
	 */
	public ReceiptSummary(DashBoard dashBoard) {
		order = FileDataWrapper.receipt;
		reciptCalculator = new TotalReceiptCalculator(order);
		reciptCalculator.processPayment();

		setSize(800, 600);
		this.setOpaque(false);
		setLayout(null);

		JLabel lblProductsPurchased = new JLabel("Products Purchased:");
		lblProductsPurchased.setBounds(31, 16, 159, 20);
		this.add(lblProductsPurchased);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 60, 596, 173);
		this.add(scrollPane);

		// jTable Data Display
		String[] columns = new String[] { "Product Id", "Product Name", "Price", "Quantity", "Total Price" };

		List<LineItem> boughtProducts = FileDataWrapper.receipt.getItems();

		// actual data for the table in a 2d array
		Object[][] data = new Object[boughtProducts.size()][];

		for (int i = 0; i < boughtProducts.size(); i++) {
			data[i] = boughtProducts.get(i).getItemsArray();
		}

		model = new DefaultTableModel(data, columns) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};

		table = new JTable(model);
		scrollPane.setViewportView(table);

		totalPrice = new JTextField();
		totalPrice.setEditable(false);
		totalPrice.setBounds(322, 244, 179, 26);
		this.add(totalPrice);
		totalPrice.setColumns(10);
		totalPrice.setText(String.valueOf(FileDataWrapper.receipt.getTotalPrice()));

		JLabel lblApplicablePoints = new JLabel("Applicable Discount");
		lblApplicablePoints.setBounds(72, 284, 166, 20);
		this.add(lblApplicablePoints);

		applicableDiscount = new JTextField();
		applicableDiscount.setEditable(false);
		applicableDiscount.setColumns(10);
		applicableDiscount.setBounds(322, 281, 179, 26);
		applicableDiscount.setText(DisplayUtil.getDiscountText(order));
		this.add(applicableDiscount);

		JLabel lblFinalPrice = new JLabel("Final Price:");
		lblFinalPrice.setBounds(72, 321, 174, 20);
		this.add(lblFinalPrice);

		finalPrice = new JTextField();
		finalPrice.setEditable(false);
		finalPrice.setColumns(10);
		finalPrice.setBounds(322, 318, 179, 26);
		finalPrice.setText(String.valueOf(order.getFinalPrice()));
		this.add(finalPrice);

		JLabel lblRedeemPoints = new JLabel("Points Redeemed");
		lblRedeemPoints.setBounds(72, 358, 159, 20);
		this.add(lblRedeemPoints);

		pointsRedeemed = new JTextField();
		pointsRedeemed.setEditable(false);
		pointsRedeemed.setColumns(10);
		pointsRedeemed.setBounds(322, 355, 179, 26);
		pointsRedeemed.setText(reciptCalculator.getCashEquivalentPointstext(order));
		this.add(pointsRedeemed);

		JLabel lblCashRendered = new JLabel("Cash Rendered:");
		lblCashRendered.setBounds(72, 395, 159, 20);
		this.add(lblCashRendered);

		cashRendered = new JTextField();
		cashRendered.setEditable(false);
		cashRendered.setColumns(10);
		cashRendered.setBounds(322, 392, 179, 26);
		cashRendered.setText(String.valueOf(order.getAmountTendered()));
		this.add(cashRendered);

		lblTotalPrice = new JLabel("Total Price:");
		lblTotalPrice.setBounds(72, 244, 166, 20);
		this.add(lblTotalPrice);

		lblAmountToReturn = new JLabel("Amount To Return:");
		lblAmountToReturn.setBounds(72, 432, 159, 20);
		this.add(lblAmountToReturn);

		returnedAmount = new JTextField();
		returnedAmount.setEditable(false);
		returnedAmount.setColumns(10);
		returnedAmount.setBounds(322, 429, 179, 26);
		returnedAmount.setText(String.valueOf(order.getReturnAmount()));
		this.add(returnedAmount);

		lblRemainingPoints = new JLabel("Remaining Loyalty Points:");
		lblRemainingPoints.setBounds(72, 469, 191, 20);
		this.add(lblRemainingPoints);

		remainingPoints = new JTextField();
		remainingPoints.setEditable(false);
		remainingPoints.setColumns(10);
		remainingPoints.setBounds(322, 466, 179, 26);
		remainingPoints
				.setText(String.valueOf(order.getMemberInfo() != null ? order.getMemberInfo().getLoyaltyPoints() : 0));
		this.add(remainingPoints);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDataWrapper.receipt = new Order();

				// ManageInventory manageStock = new ManageInventory();
				// dashBoard.activateMainPanel(manageStock);
				// show alert only when there is low stock
				LowStockAlert lowStockAlert = new LowStockAlert();
				if (lowStockAlert.hasBelowThreshold()) {
					lowStockAlert.setVisible(true);
					lowStockAlert.setLocation(getLocationOnScreen());
				}
			}
		});
		btnOk.setBounds(498, 512, 100, 50);
		this.add(btnOk);

		separator = new JSeparator();
		separator.setBounds(31, 503, 627, 2);
		this.add(separator);

	}
}
