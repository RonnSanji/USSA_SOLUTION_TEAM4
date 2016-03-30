package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSeparator;
import javax.swing.JTextField;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.OrderValidator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * Create the dialog where user can enter product BarCode and Quantity.
 *
 * Created by Amarjeet B Singh on 3/19/2016.
 */
public class PurchaseProduct extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField barCodetext;
	private JTextField productQnty;

	Map<Integer,Product> productMap = null;
	Order order = null;

	private OrderValidator orderValidator = new OrderValidator();

	/**
	 * Create the dialog where user can enter product BarCode and Quantity.
	 */
	public PurchaseProduct(ProductSelectionWindow payWin) {
		productMap = FileDataWrapper.productMap;
		order = FileDataWrapper.receipt;
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
						String validationMsg = orderValidator.validateProductPurchaseInput(barCode,productQuantityStr);
						if(validationMsg != null){
							DisplayUtil.displayValidationError(buttonPane,validationMsg);
						}else if(!barCode.equals("") && !productQuantityStr.equals("")){
							try{
								productQuantity = Long.parseLong(productQuantityStr);
								//get the selected product
								validationMsg = orderValidator.validateSelectedProduct(barCode,productMap);
								if(validationMsg != null){
									DisplayUtil.displayValidationError(buttonPane,validationMsg);
								}else {
									Product product = productMap.get(barCode);
									validationMsg = orderValidator.validateProductOrder(product,productQuantity);
									if(validationMsg != null){
										DisplayUtil.displayValidationError(buttonPane,validationMsg);
									}else {
										LineItem item = new LineItem(product, productQuantity);
										order.addLineItem(item);
										payWin.refreshTable(item.getItemsArray());
										payWin.updatedTotal(order);
										dispose();
									}
								}
							}catch(Exception ne){
								DisplayUtil.displayValidationError(buttonPane, StoreConstants.PRODUCT_QUANTITY_NON_NUMERIC);
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
