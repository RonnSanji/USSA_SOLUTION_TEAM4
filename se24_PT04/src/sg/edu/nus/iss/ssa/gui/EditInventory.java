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
	private final JPanel contentPanel = new JPanel();
	public Product selectedProduct;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblAddQuantity_NewThreshold;
	private JLabel lblProductDescription;
	private JLabel lblCurrentQuantity;
	private JTextField txtAddQuantity_NewThreshold;
	private JLabel lblProductName;
	private JPanel buttonPane;
	private JLabel lblNewLabel_4;
	private JLabel lblBarCode;
	private JLabel lblThresholdLable;
	private JLabel lblThreshold;

	private EntityListController controller = new EntityListController();

	// 0 for configure threshold
	// 1 for replenish stock
	private int mode = 0;

	public EditInventory(Product selectedProduct, int mode) {
		this.addWindowListener(new MyWindowListener());

		if (selectedProduct == null) {
			DisplayUtil.displayValidationError(contentPanel, StoreConstants.ERROR + " loading product");
			return;
		}
		this.selectedProduct = selectedProduct;
		this.mode = mode;
		setResizable(false);
		if (mode == 0) {
			setTitle("Configure Threshold");
			lblAddQuantity_NewThreshold = new JLabel("New Threshold:");
			lblThresholdLable = new JLabel("Current Threshold:");

		} else if (mode == 1) {
			setTitle("Replenish Stock");
			lblAddQuantity_NewThreshold = new JLabel("Add quantity:");
			lblThresholdLable = new JLabel("Threshold:");

		}
		setBounds(100, 100, 450, 348);
		contentPanel.setLayout(null);
		getContentPane().setLayout(null);

		buttonPane = new JPanel();
		buttonPane.setBounds(0, 256, 444, 50);
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

		lblThresholdLable.setBounds(75, 172, 130, 14);
		getContentPane().add(lblThresholdLable);

		lblThreshold = new JLabel("");
		lblThreshold.setBounds(223, 172, 46, 14);
		getContentPane().add(lblThreshold);

		lblAddQuantity_NewThreshold.setBounds(75, 214, 131, 14);
		getContentPane().add(lblAddQuantity_NewThreshold);

		lblProductName = new JLabel("");
		lblProductName.setBounds(223, 24, 211, 14);
		getContentPane().add(lblProductName);

		lblProductDescription = new JLabel("");
		lblProductDescription.setBounds(223, 60, 211, 14);
		getContentPane().add(lblProductDescription);

		lblCurrentQuantity = new JLabel("");
		lblCurrentQuantity.setBounds(223, 132, 211, 14);
		getContentPane().add(lblCurrentQuantity);

		txtAddQuantity_NewThreshold = new JTextField();
		txtAddQuantity_NewThreshold.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("[0-9]"))
					super.insertString(offs, str, a);
			}
		});
		txtAddQuantity_NewThreshold.setBounds(223, 211, 46, 20);
		getContentPane().add(txtAddQuantity_NewThreshold);

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
		lblThreshold.setText(String.valueOf(selectedProduct.getThresholdQuantity()));
	}

	private boolean validateForm() {
		String addQuantity_NewThreshold = txtAddQuantity_NewThreshold.getText();
		String msg = null;
		if (mode == 0) {
			msg = FormValidator.configureThresholdValidateForm(addQuantity_NewThreshold);
		} else if (mode == 1) {
			msg = FormValidator.replenishStockValidateForm(addQuantity_NewThreshold);
		}

		if (msg != null) {
			DisplayUtil.displayValidationError(buttonPane, msg);
			return false;
		}

		return true;
	}

	private void replenishStock() {
		String msg = FormValidator.replenishStockConfigureThresholdValidateData(selectedProduct.getBarCode());
		if (msg != null) {
			DisplayUtil.displayValidationError(buttonPane, msg);
			return;
		}
		long stockAdd_newThreshold = Long.parseLong(txtAddQuantity_NewThreshold.getText());
		if (mode == 0) {
			msg = controller.updateThreshold(selectedProduct, stockAdd_newThreshold);
		} else if (mode == 1) {
			msg = controller.addStock(selectedProduct, stockAdd_newThreshold);
		}

		if (msg != null) {
			DisplayUtil.displayValidationError(buttonPane, msg);
			return;
		}
		if (mode == 0) {
			DisplayUtil.displayAcknowledgeMessage(buttonPane, StoreConstants.THRESHOLD_UPDATED_SUCCESSFULLY);
		} else if (mode == 1) {
			DisplayUtil.displayAcknowledgeMessage(buttonPane, StoreConstants.STOCK_UPDATED_SUCCESSFULLY);
		}

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
