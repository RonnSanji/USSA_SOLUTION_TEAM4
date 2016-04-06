package sg.edu.nus.iss.ssa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.Vendor;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class EditVendor extends JDialog {

	private static final long serialVersionUID = 1161515678958525932L;

	public Vendor selectedVendor;
	public String selectedCategoryID;
	private JPanel contentPanel;
	private JTextField txtVendorCode;
	private JTextArea txtVendorName;

	private String vendorID;
	private String vendorName;
	private EntityListController controller = new EntityListController();

	// 0 for add, 1 for edit
	private int mode = 0;

	public EditVendor(String categoryID, Vendor selectedVendor) {
		this.selectedVendor = selectedVendor;
		this.selectedCategoryID = categoryID;
		this.addWindowListener(new MyWindowListener());
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 330);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblVendorID = new JLabel("Vendor Code: ");
		lblVendorID.setBounds(68, 38, 128, 14);
		contentPanel.add(lblVendorID);

		JLabel lblVendorName = new JLabel("Vendor Name: ");
		lblVendorName.setBounds(68, 94, 128, 14);
		contentPanel.add(lblVendorName);

		JButton btnAdd = new JButton("OK");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (validateForm() && validateData()) {
					saveVendor();
				}
			}
		});
		btnAdd.setBounds(98, 240, 100, 50);
		contentPanel.add(btnAdd);

		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(249, 240, 100, 50);
		contentPanel.add(btnClose);

		txtVendorCode = new JTextField();

		txtVendorCode.setBounds(206, 35, 177, 28);
		contentPanel.add(txtVendorCode);

		txtVendorName = new JTextArea();
		txtVendorName.setLineWrap(true);
		txtVendorName.setBounds(274, 158, 177, 109);
		txtVendorName.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 8233477329983644784L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				if (!str.contains(","))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(txtVendorName);

		JScrollPane scrollPane = new JScrollPane(txtVendorName);
		scrollPane.setBounds(206, 94, 177, 109);
		contentPanel.add(scrollPane);

		bindData();
	}

	private void saveVendor() {

		String msg = controller.saveVendor(selectedCategoryID, selectedVendor, mode);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return;
		}
		// add
		if (mode == 0) {
			int dialogResult = DisplayUtil.displayConfirmationMessage(contentPanel,
					StoreConstants.VENDOR_ADDED_SUCCESSFULLY);
			if (dialogResult == 0) {
				selectedVendor = null;
				clearFields();
			} else if (dialogResult == 1) {
				dispose();
			}
		}
		// edit
		else if (mode == 1) {
			DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.VENDOR_UPDATED_SUCCESSFULLY);
			dispose();
		}
	}

	private boolean validateForm() {
		vendorID = txtVendorCode.getText().trim().replace("\n", " ");
		vendorName = txtVendorName.getText().trim().replace("\n", " ");

		String msg = FormValidator.addEditVendorValidateForm(vendorID, vendorName);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return false;
		}
		return true;
	}

	private boolean validateData() {
		if (mode == 0) {
			selectedVendor = new Vendor(selectedCategoryID);
		}

		selectedVendor.setVendorId(txtVendorCode.getText().trim().replace("\n", " "));
		selectedVendor.setVendorName(txtVendorName.getText().trim().replace("\n", " "));

		String msg = null;
		// add
		if (mode == 0) {
			msg = FormValidator.addVendorValidateData(selectedVendor);
		}
		// edit
		else if (mode == 1) {
			msg = FormValidator.editRemoveVendorValidateData(selectedVendor);
		}

		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return false;
		}
		return true;
	}

	private void clearFields() {
		txtVendorCode.setText("");
		txtVendorName.setText("");
	}

	private void bindData() {
		// add
		if (selectedVendor == null) {
			mode = 0;
			this.setTitle("Add Vendor");
			txtVendorCode.setDocument(new PlainDocument() {
				private static final long serialVersionUID = 5790057198546248513L;

				@Override
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

					if (!str.contains(","))
						super.insertString(offs, str, a);
				}
			});
			clearFields();

		}
		// edit
		else {
			mode = 1;
			this.setTitle("Edit Vendor");
			txtVendorCode.setText(selectedVendor.getVendorId());
			txtVendorCode.setEditable(false);
			txtVendorName.setText(selectedVendor.getVendorName());

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