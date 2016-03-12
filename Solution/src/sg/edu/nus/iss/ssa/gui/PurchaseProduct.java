package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSeparator;
import javax.swing.JTextField;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.QuadCurve2D;
import java.util.Map;

public class PurchaseProduct extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField barCodetext;
	private JTextField productQnty;

	/**
	 * Create the dialog.
	 * @param receipt 
	 * @param memberMap 
	 * @param productMap 
	 */
	public PurchaseProduct(ProductSelectionWindow payWin) {
		setTitle("Purchase Product");
		setSize(500,350);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				JLabel lblProductBarCode = new JLabel("Product Bar Code:");
				lblProductBarCode.setFont(new Font("Arial", Font.PLAIN, 17));
				lblProductBarCode.setBounds(25, 69, 141, 53);
				panel.add(lblProductBarCode);
			}
			
			JLabel lblEnterProductDetails = new JLabel("Enter product Details");
			lblEnterProductDetails.setFont(new Font("Arial", Font.BOLD, 18));
			lblEnterProductDetails.setBounds(15, 0, 197, 53);
			panel.add(lblEnterProductDetails);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 55, 450, 4);
			panel.add(separator);
			
			JLabel lblQuantity = new JLabel("Quantity:");
			lblQuantity.setFont(new Font("Arial", Font.PLAIN, 17));
			lblQuantity.setBounds(25, 128, 141, 53);
			panel.add(lblQuantity);
			
			barCodetext = new JTextField();
			barCodetext.setBounds(181, 82, 206, 26);
			panel.add(barCodetext);
			barCodetext.setColumns(10);
			
			productQnty = new JTextField();
			productQnty.setColumns(10);
			productQnty.setBounds(181, 141, 206, 26);
			panel.add(productQnty);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*Code to collect product bar code and Quantity*/
						String barCode = barCodetext.getText();
						String productQuantityStr = productQnty.getText();
						long productQuantity = 0l;
						if(barCode.equals("") || productQuantityStr.equals("")){
							JOptionPane.showMessageDialog(buttonPane, "Please enter BarCode and Quantity to purchase the product", "Error", JOptionPane.ERROR_MESSAGE);
						}else if(!barCode.equals("") && !productQuantityStr.equals("")){
							try{
								productQuantity = Long.parseLong(productQuantityStr);
								//get the selected product 
								Object selectedItem = FileDataWrapper.productMap.get(barCode);
								if(selectedItem == null){
									JOptionPane.showMessageDialog(buttonPane, "Please select a valid product", "Error", JOptionPane.ERROR_MESSAGE);
								}else {
									Product product = (Product)selectedItem;
									LineItem item = new LineItem(product.getProductId(),product.getProductName(), productQuantity,  product.getPrice(),
														product.getBarCode());
									FileDataWrapper.receipt.getItems().add(item);
									FileDataWrapper.receipt.setTotalPrice(FileDataWrapper.receipt.getTotalPrice()+item.getTotalproductPrice());
									payWin.dispose();
									ProductSelectionWindow newWindow = new ProductSelectionWindow();
									newWindow.setVisible(true);
								}
							}catch(Exception ne){
								JOptionPane.showMessageDialog(buttonPane, "Quantity must be numeric value", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
