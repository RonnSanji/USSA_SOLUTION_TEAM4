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

public class EditInventory extends JDialog {
	private static final long serialVersionUID = 4069437661115880594L;
	// private final JPanel contentPanel = new JPanel();
	public Product selectedProduct;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblQuantityLabel;
	private JLabel lblAddQuantity_NewThreshold;
	private JLabel lblProductDescription;
	private JLabel lblCurrentQuantity;
	private JTextField txtAddQuantity_NewThreshold;
	private JLabel lblProductName;
	private JLabel lblBarCodeLabel;
	private JLabel lblBarCode;
	private JLabel lblThresholdLable;
	private JLabel lblThreshold;
	private JLabel lblRerorderQuantity;
	private JLabel lblReorderQuantityLable;
	private JLabel lblNewReorderQuantity;
	private JTextField txtReorderQuantity;

	private EntityListController controller = new EntityListController();

	// 0 for configure threshold
	// 1 for replenish stock
	// 2 for write off stock
	private int mode = 0;

	public EditInventory(Product selectedProduct, int mode) {
		this.addWindowListener(new MyWindowListener());

		if (selectedProduct == null) {
			DisplayUtil.displayValidationError(lblQuantityLabel, StoreConstants.ERROR + " loading product");
			return;
		}
		this.selectedProduct = selectedProduct;
		this.mode = mode;
		setResizable(false);

		setBounds(100, 100, 499, 422);
		// contentPanel.setLayout(null);
		getContentPane().setLayout(null);

		lblNewLabel = new JLabel("Product Name:");
		lblNewLabel.setBounds(75, 25, 131, 14);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Product Description:");
		lblNewLabel_1.setBounds(75, 60, 131, 14);
		getContentPane().add(lblNewLabel_1);

		lblQuantityLabel = new JLabel("Current quantity:");
		lblQuantityLabel.setBounds(75, 130, 131, 14);
		getContentPane().add(lblQuantityLabel);

		lblThresholdLable = new JLabel();
		lblThresholdLable.setBounds(75, 165, 165, 14);
		getContentPane().add(lblThresholdLable);

		lblThreshold = new JLabel("");
		lblThreshold.setBounds(255, 165, 180, 14);
		getContentPane().add(lblThreshold);

		lblAddQuantity_NewThreshold = new JLabel();
		lblAddQuantity_NewThreshold.setBounds(75, 235, 165, 14);
		getContentPane().add(lblAddQuantity_NewThreshold);

		lblProductName = new JLabel("");
		lblProductName.setBounds(255, 25, 180, 20);
		getContentPane().add(lblProductName);

		lblProductDescription = new JLabel("");
		lblProductDescription.setBounds(255, 60, 216, 14);
		getContentPane().add(lblProductDescription);

		lblCurrentQuantity = new JLabel("");
		lblCurrentQuantity.setBounds(255, 130, 180, 14);
		getContentPane().add(lblCurrentQuantity);

		txtAddQuantity_NewThreshold = new JTextField();
		txtAddQuantity_NewThreshold.setDocument(new PlainDocument() {

			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("[0-9]*"))
					super.insertString(offs, str, a);
			}
		});
		txtAddQuantity_NewThreshold.setBounds(255, 235, 83, 28);
		getContentPane().add(txtAddQuantity_NewThreshold);

		lblBarCodeLabel = new JLabel("Bar Code:");
		lblBarCodeLabel.setBounds(75, 95, 95, 14);
		getContentPane().add(lblBarCodeLabel);

		lblBarCode = new JLabel("");
		lblBarCode.setBounds(255, 95, 180, 14);
		getContentPane().add(lblBarCode);

		lblRerorderQuantity = new JLabel("New label");
		lblRerorderQuantity.setBounds(255, 200, 180, 14);
		getContentPane().add(lblRerorderQuantity);

		lblReorderQuantityLable = new JLabel("");
		lblReorderQuantityLable.setBounds(75, 200, 165, 14);
		getContentPane().add(lblReorderQuantityLable);

		lblNewReorderQuantity = new JLabel("New Reorder Quantity:");
		lblNewReorderQuantity.setBounds(75, 280, 165, 14);
		getContentPane().add(lblNewReorderQuantity);

		txtReorderQuantity = new JTextField();
		txtReorderQuantity.setBounds(255, 280, 83, 28);
		getContentPane().add(txtReorderQuantity);
		txtReorderQuantity.setDocument(new PlainDocument() {

			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("[0-9]*"))
					super.insertString(offs, str, a);
			}
		});

		okButton = new JButton("OK");
		okButton.setBounds(106, 333, 100, 50);
		getContentPane().add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validateForm()) {
					return;
				}
				saveInventory();

				dispose();
			}
		});
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(289, 333, 100, 50);
		getContentPane().add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		cancelButton.setActionCommand("Cancel");

		// 0 for configure threshold and reorder quantity
		if (mode == 0) {
			setTitle("Configure Threshold / Reorder Quantity");
			lblAddQuantity_NewThreshold.setText("New Threshold:");
			lblThresholdLable.setText("Current Threshold:");
			lblReorderQuantityLable.setText("Current Reorder Quantity:");
			lblNewReorderQuantity.setVisible(true);
			txtReorderQuantity.setVisible(true);
		}
		// 1 for replenish stock
		else if (mode == 1) {
			setTitle("Replenish Stock");
			lblAddQuantity_NewThreshold.setText("Add quantity:");
			lblThresholdLable.setText("Threshold:");
			lblReorderQuantityLable.setText("Reorder Quantity:");
			lblNewReorderQuantity.setVisible(false);
			txtReorderQuantity.setVisible(false);
		}
		// 2 for write off stock
		else if (mode == 2) {
			setTitle("Write Off Stock");
			lblAddQuantity_NewThreshold.setText("Minus quantity:");
			lblThresholdLable.setText("Threshold:");
			lblReorderQuantityLable.setText("Reorder Quantity:");
			lblNewReorderQuantity.setVisible(false);
			txtReorderQuantity.setVisible(false);
		}
		populateData();
	}

	private void populateData() {

		lblProductName.setText(selectedProduct.getProductName());
		lblProductDescription.setText(selectedProduct.getProductDesc());
		lblBarCode.setText(String.valueOf(selectedProduct.getBarCode()));
		lblCurrentQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
		lblThreshold.setText(String.valueOf(selectedProduct.getThresholdQuantity()));
		lblRerorderQuantity.setText(String.valueOf(selectedProduct.getOrderQuantity()));
		// 0 for configure threshold and reorder quantity
		if (mode == 0) {
			txtAddQuantity_NewThreshold.setText(String.valueOf(selectedProduct.getThresholdQuantity()));
			txtReorderQuantity.setText(String.valueOf(selectedProduct.getOrderQuantity()));
		}
	}

	private boolean validateForm() {
		String addQuantity_NewThreshold = txtAddQuantity_NewThreshold.getText().trim().replace("\n", " ");
		String newReorderQuantity = null;
		// 0 for configure threshold and reorder quantity
		if (mode == 0) {
			newReorderQuantity = txtReorderQuantity.getText().trim().replace("\n", " ");
		}
		// 1 for replenish stock
		else if (mode == 1) {
			newReorderQuantity = lblRerorderQuantity.getText().trim().replace("\n", " ");
		}
		// 2 for write off stock
		else if (mode == 2) {
			newReorderQuantity = lblRerorderQuantity.getText().trim().replace("\n", " ");
		}
		String msg = null;
		if (mode == 0) {
			msg = FormValidator.configureThresholdReorderQuantityValidateForm(addQuantity_NewThreshold,
					newReorderQuantity);
		} else if (mode == 1) {
			msg = FormValidator.replenishStockValidateForm(addQuantity_NewThreshold);
		} else if (mode == 2) {
			msg = FormValidator.writeOffStockValidateForm(selectedProduct.getQuantity(),addQuantity_NewThreshold);
		}

		if (msg != null) {
			DisplayUtil.displayValidationError(lblQuantityLabel, msg);
			return false;
		}

		return true;
	}

	private void saveInventory() {
		String msg = FormValidator.replenishStockConfigureThresholdValidateData(selectedProduct.getBarCode());
		if (msg != null) {
			DisplayUtil.displayValidationError(lblQuantityLabel, msg);
			return;
		}
		long stockAdd_newThreshold = Long.parseLong(txtAddQuantity_NewThreshold.getText().trim().replace("\n", " "));
		long newReorderQuantity = 0;
		// 0 for configure threshold and reorder quantity
		if (mode == 0) {
			newReorderQuantity = Long.parseLong(txtReorderQuantity.getText().trim().replace("\n", " "));
		}
		// 1 for replenish stock
		else if (mode == 1) {
			newReorderQuantity = Long.parseLong(lblRerorderQuantity.getText().trim().replace("\n", " "));
		}
		// 2 for write off stock
		else if (mode == 2) {
			newReorderQuantity = Long.parseLong(lblRerorderQuantity.getText().trim().replace("\n", " "));
		}

		if (mode == 0) {
			msg = controller.updateThreshold_ReorderQuantity(selectedProduct, stockAdd_newThreshold,
					newReorderQuantity);
		} else if (mode == 1) {
			msg = controller.addStock(selectedProduct, stockAdd_newThreshold);
		} else if (mode == 2) {
			msg = controller.writeOffStock(selectedProduct, stockAdd_newThreshold);
		}
		if (msg != null) {
			DisplayUtil.displayValidationError(lblQuantityLabel, msg);
			return;
		}
		if (mode == 0) {
			DisplayUtil.displayAcknowledgeMessage(lblQuantityLabel,
					StoreConstants.THRESHOLD_REORDER_QUANTITY_UPDATED_SUCCESSFULLY);
		} else if (mode == 1 || mode == 2) {
			DisplayUtil.displayAcknowledgeMessage(lblQuantityLabel, StoreConstants.STOCK_UPDATED_SUCCESSFULLY);
		}

	}

	class MyWindowListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {

		}

		@Override
		public void windowClosed(WindowEvent arg0) {

			controller = null;
		}

		@Override
		public void windowClosing(WindowEvent arg0) {

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {

		}

		@Override
		public void windowIconified(WindowEvent arg0) {

		}

		@Override
		public void windowOpened(WindowEvent arg0) {

		}

	}
}
