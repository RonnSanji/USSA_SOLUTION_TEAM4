package sg.edu.nus.iss.ssa.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

	private EntityListController controller = new EntityListController();

	public ReplenishStock(Product selectedProduct) {
		this.addWindowListener(new MyWindowListener());

		if (selectedProduct == null) {
			DisplayUtil.displayValidationError(contentPanel, StoreConstants.ERROR + " loading product");
			return;
		}
		this.selectedProduct = selectedProduct;
		setResizable(false);
		setTitle("Replenish Stock");
		setBounds(100, 100, 450, 337);
		contentPanel.setLayout(null);
		getContentPane().setLayout(null);

		buttonPane = new JPanel();
		buttonPane.setBounds(0, 231, 444, 50);
		getContentPane().add(buttonPane);
		buttonPane.setLayout(null);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(255, 16, 78, 23);
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		okButton = new JButton("OK");
		okButton.setBounds(111, 16, 78, 23);
		buttonPane.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validateForm()) {
					return;
				}
				replenishStock();
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
		String msg = FormValidator.replenishStockValidateForm(stockTxt);
		if (msg != null) {
			DisplayUtil.displayValidationError(buttonPane, msg);
			return false;
		}

		return true;
	}

	private void replenishStock() {
		String msg = FormValidator.replenishStockValidateData(selectedProduct.getBarCode());
		if (msg != null) {
			DisplayUtil.displayValidationError(buttonPane, msg);
			return;
		}
		long stockAdd = Long.parseLong(txtAddQuantity.getText());
		msg = controller.addStock(selectedProduct, stockAdd);
		if (msg != null) {
			DisplayUtil.displayValidationError(buttonPane, msg);
			return;
		}

		DisplayUtil.displayAcknowledgeMessage(buttonPane, StoreConstants.STOCK_UPDATED_SUCCESSFULLY);
	}

	private void reloadData() {
		controller.reloadProductData();
	}

	class MyWindowListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			controller = null;
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
