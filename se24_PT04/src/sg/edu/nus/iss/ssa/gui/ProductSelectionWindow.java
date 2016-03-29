package sg.edu.nus.iss.ssa.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.Order;

/**
 *Creates screen to capture product selection. This will constitute Order.
 * @author Amarjeet B Singh
 *
 */
public class ProductSelectionWindow extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private JTextField toalPrice;
	private JTextField memberNumber;
	private JTextField loyaltyPoints;
	private Order order;

	/**
	 * Create the frame.
	 */
	public ProductSelectionWindow() {
		order = FileDataWrapper.receipt;
		ProductSelectionWindow productWin = this;
		setSize(800,600);

		this.setOpaque(false);
		setLayout(null);
		
		JButton btnAddProduct = new JButton("Purchase Product");
		btnAddProduct.setBounds(589, 402, 177, 27);
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PurchaseProduct dialog = new PurchaseProduct(productWin);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});

		btnAddProduct.setFont(new Font("Arial", Font.BOLD, 16));
		btnAddProduct.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnAddProduct);
		
		JScrollPane tblScrollPane = new JScrollPane();
		tblScrollPane.setBounds(65, 101, 701, 288);
		this.add(tblScrollPane);
		
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
		model = new DefaultTableModel(data, columns)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};

		table =   new JTable(model);

		tblScrollPane.setViewportView(table);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(54, 86, 716, 2);
		this.add(separator);
		
		JLabel lblTotalPrice = new JLabel("Total Price: ");
		lblTotalPrice.setBounds(486, 475, 91, 20);
		this.add(lblTotalPrice);
		
		toalPrice = new JTextField();
		toalPrice.setEditable(false);
		toalPrice.setBounds(589, 472, 177, 26);
		this.add(toalPrice);
		toalPrice.setColumns(10);
		
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(65, 442, 709, 2);
		this.add(separator_1);
		
		JLabel lblEnterMemberNumber = new JLabel("Member Number:");
		lblEnterMemberNumber.setBounds(79, 40, 109, 20);
		this.add(lblEnterMemberNumber);
		
		memberNumber = new JTextField();
		memberNumber.setEditable(false);
		memberNumber.setBounds(200, 37, 185, 26);
		this.add(memberNumber);
		memberNumber.setColumns(10);
		

		JLabel lblLoyaltyPoints = new JLabel("Loyalty Points:");
		lblLoyaltyPoints.setBounds(486, 43, 91, 20);
		this.add(lblLoyaltyPoints);

		loyaltyPoints = new JTextField();
		loyaltyPoints.setEditable(false);
		loyaltyPoints.setColumns(10);
		loyaltyPoints.setBounds(589, 37, 177, 26);
		this.add(loyaltyPoints);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(65, 511, 709, 2);
		this.add(separator_2);
		
		JButton btnEnterMemberNumber = new JButton("Enter Member Number");
		btnEnterMemberNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MemberNumberScreen member = new MemberNumberScreen(productWin);
				member.setVisible(true);
			}
		});
		btnEnterMemberNumber.setHorizontalAlignment(SwingConstants.LEFT);
		btnEnterMemberNumber.setFont(new Font("Arial", Font.BOLD, 16));
		btnEnterMemberNumber.setBounds(393, 542, 213, 27);
		this.add(btnEnterMemberNumber);
		
		JButton btnRemoveProduct = new JButton("Remove Product");
		btnRemoveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String selectedRowKey = null;
				try
				{
				 selectedRowKey = table.getValueAt(selectedRow, 1).toString();
				}
				catch( Exception es)
				{
					JOptionPane.showMessageDialog(table, "Please select at least one row", "Error", JOptionPane.ERROR_MESSAGE);
				}
				// check if any row is selected
				if(selectedRow != -1){
					System.out.print(selectedRow);
					int option = JOptionPane.showConfirmDialog(null, "Confirm to remove this Product?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
					System.out.println(option);
					if(option==0){
						// Confirm Remove
					//	model.removeRow(selectedRow);
					//	FileDataWrapper.memberMap.remove(selectedRowKey);
					}
				}
			}
		});
		btnRemoveProduct.setHorizontalAlignment(SwingConstants.LEFT);
		btnRemoveProduct.setFont(new Font("Arial", Font.BOLD, 16));
		btnRemoveProduct.setBounds(393, 402, 171, 27);
		this.add(btnRemoveProduct);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checkout Item
				PaymentWindow payWindow = new PaymentWindow();
				payWindow.setVisible(true);

			}
		});
		btnCheckout.setHorizontalAlignment(SwingConstants.LEFT);
		btnCheckout.setFont(new Font("Arial", Font.BOLD, 16));
		btnCheckout.setBounds(627, 542, 139, 27);
		this.add(btnCheckout);
		
		updatedFieldValue(order);

	}
	
	public void refreshTable(String[] product){
		this.model.addRow(product);
	}
	
	public void updatedFieldValue(Order order){
		this.memberNumber.setText(order.getMemberInfo() != null ? order.getMemberInfo().getMemberId() : "");
		this.loyaltyPoints.setText(String.valueOf(order.getMemberInfo() != null ? order.getMemberInfo().getLoyaltyPoints() : ""));
		this.updatedTotal(order);
	}
	
	public void updatedTotal(Order order){
		this.toalPrice.setText(String.valueOf(order.getTotalPrice()));
	}

}
