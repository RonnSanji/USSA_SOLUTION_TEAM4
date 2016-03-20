package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ReplenishStock extends JDialog {
	private static final long serialVersionUID = 4069437661115880594L;
	private final JPanel contentPanel = new JPanel();
	public Product selectedProduct;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblProductDescription;
	private JLabel lblCurrentQuantity;
	private JTextField txtAddQuantity;
	private JLabel lblProductName;
	private JPanel buttonPane;
	private JLabel lblNewLabel_4;
	private JLabel lblBarCode;

	public ReplenishStock(Product selectedProduct) {
		if (selectedProduct == null) {
			DisplayUtil.displayValidationError(contentPanel, StoreConstants.ERROR + " loading product");
			return;
		}
		this.selectedProduct = selectedProduct;
		setResizable(false);
		setTitle("Replenish Stock");
		setBounds(100, 100, 450, 300);
		contentPanel.setLayout(null);
		getContentPane().setLayout(null);

		buttonPane = new JPanel();
		buttonPane.setBounds(0, 231, 444, 41);
		getContentPane().add(buttonPane);
		buttonPane.setLayout(null);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(266, 5, 78, 23);
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		okButton = new JButton("OK");
		okButton.setBounds(104, 5, 78, 23);
		buttonPane.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validateForm()) {
					return;
				}
				addStock();
				reloadData();
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		lblNewLabel = new JLabel("Product Name:");
		lblNewLabel.setBounds(75, 24, 131, 14);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Product Description:");
		lblNewLabel_1.setBounds(75, 60, 131, 14);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Current quantity:");
		lblNewLabel_2.setBounds(75, 132, 131, 14);
		getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Add quantity:");
		lblNewLabel_3.setBounds(75, 171, 130, 14);
		getContentPane().add(lblNewLabel_3);

		lblProductName = new JLabel("");
		lblProductName.setBounds(223, 24, 211, 14);
		getContentPane().add(lblProductName);

		lblProductDescription = new JLabel("");
		lblProductDescription.setBounds(223, 60, 211, 14);
		getContentPane().add(lblProductDescription);

		lblCurrentQuantity = new JLabel("");
		lblCurrentQuantity.setBounds(223, 132, 211, 14);
		getContentPane().add(lblCurrentQuantity);

		txtAddQuantity = new JTextField();
		txtAddQuantity.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("[0-9]"))
					super.insertString(offs, str, a);
			}
		});
		txtAddQuantity.setBounds(223, 168, 46, 20);
		getContentPane().add(txtAddQuantity);

		lblNewLabel_4 = new JLabel("Bar Code:");
		lblNewLabel_4.setBounds(75, 96, 95, 14);
		getContentPane().add(lblNewLabel_4);

		lblBarCode = new JLabel("");
		lblBarCode.setBounds(223, 96, 46, 14);
		getContentPane().add(lblBarCode);

		populateData();
	}

	private void populateData() {

		lblProductName.setText(selectedProduct.getProductName());
		lblProductDescription.setText(selectedProduct.getProductDesc());
		lblBarCode.setText(String.valueOf(selectedProduct.getBarCode()));
		lblCurrentQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
	}

	private boolean validateForm() {
		String stockTxt = txtAddQuantity.getText();
		if (stockTxt == null || stockTxt.isEmpty()) {
			DisplayUtil.displayValidationError(buttonPane, StoreConstants.BLANK_REPLENISH_QUANTITY);
			return false;
		}
		return true;
	}

	private void addStock() {

		long stockAdd = Long.parseLong(txtAddQuantity.getText());
		this.selectedProduct.setQuantity(selectedProduct.getQuantity() + stockAdd);

		for (Product p : FileDataWrapper.productMap.values()) {
			Long barcode = p.getBarCode();
			if (barcode == selectedProduct.getBarCode()) {
				p = selectedProduct;
				break;
			}
		}
		// FileDataWrapper.productMap.(selectedProduct.getBarCode(),
		// selectedProduct);

		IOService<?> ioManager = new IOService<Entity>();
		try {
			ioManager.writeToFile(FileDataWrapper.productMap, new sg.edu.nus.iss.ssa.model.Product());
			ioManager = null;
		} catch (Exception ex)

		{
			// JOptionPane.showMessageDialog(contentPane, "Error occured during
			// saving new category", "Error", JOptionPane.ERROR_MESSAGE);
			DisplayUtil.displayValidationError(buttonPane, StoreConstants.ERROR + " saving product");
			ioManager = null;
			return;
		}

		DisplayUtil.displayAcknowledgeMessage(buttonPane, StoreConstants.STOCK_UPDATED_SUCCESSFULLY);
	}

	private void reloadData() {
		IOService<?> ioManager = new IOService<Entity>();
		FileDataWrapper.productMap.clear();
		try {
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			ioManager = null;
			System.out.println("products : " + FileDataWrapper.productMap.keySet());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			ioManager = null;
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		}
	}

}
