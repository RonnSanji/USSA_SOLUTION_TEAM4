package sg.edu.nus.iss.ssa.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Order;

/**
 *Creates screen to capture product selection. This will constitute Order.
 * @author Amarjeet B Singh
 *
 */
public class ProductSelectionWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField toalPrice;
	private JTextField memberNumber;
	private JTextField textField;
	private Order order;

	/**
	 * Create the frame.
	 */
	public ProductSelectionWindow() {
		order = FileDataWrapper.receipt;
		ProductSelectionWindow productWin = this;
		setResizable(false);
		setTitle("Select Product Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,670);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(3, 3, 988, 754);
		panel.setForeground(Color.BLUE);
		contentPane.add(panel);
		
		JButton btnAddProduct = new JButton("Purchase Product");
		btnAddProduct.setBounds(740, 388, 177, 27);
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PurchaseProduct dialog = new PurchaseProduct(productWin);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});
		panel.setLayout(null);
		btnAddProduct.setFont(new Font("Arial", Font.BOLD, 16));
		btnAddProduct.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnAddProduct);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(119, 94, 798, 288);
		panel.add(scrollPane);
		
		// jTable Data Display
		String[] columns = new String[] { "Product Name", "Price", "Quantity",
				"Total Price" };

		List<LineItem> boughtProducts = order.getItems();
		// actual data for the table in a 2d array
		Object[][] data = new Object[boughtProducts.size()][];
		for (int i = 0; i < boughtProducts.size(); i++) {
			data[i] = boughtProducts.get(i).getItemsArray();
		}
		//Create Table Model to override cell editable
		TableModel model = new DefaultTableModel(data, columns)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};

		table =   new JTable(model);
		table.setRowSelectionAllowed(true);
		table.setCellSelectionEnabled(false);
		scrollPane.setViewportView(table);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(79, 76, 875, 2);
		panel.add(separator);
		
		JLabel lblTotalPrice = new JLabel("Total Price: ");
		lblTotalPrice.setBounds(577, 490, 91, 20);
		panel.add(lblTotalPrice);
		
		toalPrice = new JTextField();
		toalPrice.setEditable(false);
		toalPrice.setBounds(717, 487, 196, 26);
		panel.add(toalPrice);
		toalPrice.setColumns(10);
		toalPrice.setText(String.valueOf(FileDataWrapper.receipt.getTotalPrice()));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(119, 449, 825, 2);
		panel.add(separator_1);
		
		JLabel lblEnterMemberNumber = new JLabel("Member Number:");
		lblEnterMemberNumber.setBounds(132, 33, 204, 20);
		panel.add(lblEnterMemberNumber);
		
		memberNumber = new JTextField();
		memberNumber.setEditable(false);
		memberNumber.setBounds(293, 30, 204, 26);
		panel.add(memberNumber);
		memberNumber.setColumns(10);
		memberNumber.setText(order.getMemberInfo() != null ? order.getMemberInfo().getMemberId() : "");

		JLabel lblLoyaltyPoints = new JLabel("Loyalty Points:");
		lblLoyaltyPoints.setBounds(550, 33, 148, 20);
		panel.add(lblLoyaltyPoints);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(713, 30, 204, 26);
		textField.setText(String.valueOf(order.getMemberInfo() != null ? order.getMemberInfo().getLoyaltyPoints() : ""));
		panel.add(textField);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(120, 547, 834, 9);
		panel.add(separator_2);
		
		JButton btnEnterMemberNumber = new JButton("Enter Member Number");
		btnEnterMemberNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MemberNumberScreen member = new MemberNumberScreen(productWin);
				member.setVisible(true);
			}
		});
		btnEnterMemberNumber.setHorizontalAlignment(SwingConstants.LEFT);
		btnEnterMemberNumber.setFont(new Font("Arial", Font.BOLD, 16));
		btnEnterMemberNumber.setBounds(554, 572, 233, 27);
		panel.add(btnEnterMemberNumber);
		
		JButton btnRemoveProduct = new JButton("Remove Product");
		btnRemoveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					System.out.print(selectedRow);
					//	table.removeRowSelectionInterval(selectedRow-2, selectedRow);

				}

				// remove same from List based on index
			}
		});
		btnRemoveProduct.setHorizontalAlignment(SwingConstants.LEFT);
		btnRemoveProduct.setFont(new Font("Arial", Font.BOLD, 16));
		btnRemoveProduct.setBounds(554, 388, 171, 27);
		panel.add(btnRemoveProduct);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checkout Item
				PaymentWindow payWindow = new PaymentWindow();
				payWindow.setVisible(true);
				dispose();

			}
		});
		btnCheckout.setHorizontalAlignment(SwingConstants.LEFT);
		btnCheckout.setFont(new Font("Arial", Font.BOLD, 16));
		btnCheckout.setBounds(800, 571, 139, 27);
		panel.add(btnCheckout);

	}

}
