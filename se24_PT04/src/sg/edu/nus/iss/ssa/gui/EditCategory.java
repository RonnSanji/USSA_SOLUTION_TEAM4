package sg.edu.nus.iss.ssa.gui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.text.*;
import javax.swing.JScrollPane;

public class EditCategory extends JDialog {

	private static final long serialVersionUID = -1420940689801074313L;
	public Category selectedCategory;
	private JPanel contentPanel;
	private JTextField txtCategoryCode;
	private JTextArea txtCategoryName;

	private String categoryID;
	private String categoryName;
	private EntityListController controller = new EntityListController();

	// 0 for add, 1 for edit
	private int mode = 0;

	public EditCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
		this.addWindowListener(new MyWindowListener());
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 330);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblCategoryID = new JLabel("Category Code: ");
		lblCategoryID.setBounds(68, 38, 128, 14);
		contentPanel.add(lblCategoryID);

		JLabel lblCategoryName = new JLabel("Category Name: ");
		lblCategoryName.setBounds(68, 94, 128, 14);
		contentPanel.add(lblCategoryName);

		JButton btnAdd = new JButton("OK");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (validateForm() && validateData()) {
					saveCategory();
				}
				/*
				 * int dialogResult = -1; if (validateForm()) { String msg =
				 * validateData(); if (msg == null) { if (addCategory()) {
				 * dialogResult =
				 * DisplayUtil.displayConfirmationMessage(contentPane,
				 * StoreConstants.CATEGORY_ADDED_SUCCESSFULLY); if (dialogResult
				 * == 0) { clearFields(); } else if (dialogResult == 1) {
				 * dispose(); } } } else { dialogResult =
				 * DisplayUtil.displayConfirmationMessage(contentPane, msg); if
				 * (dialogResult == 0) { clearFields(); } else if (dialogResult
				 * == 1) { dispose(); } } }
				 */

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

		txtCategoryCode = new JTextField();

		txtCategoryCode.setBounds(206, 35, 177, 28);
		contentPanel.add(txtCategoryCode);

		txtCategoryName = new JTextArea();
		txtCategoryName.setLineWrap(true);
		txtCategoryName.setBounds(274, 158, 177, 109);
		txtCategoryName.setDocument(new PlainDocument() {

			private static final long serialVersionUID = -4821895230137024666L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				if (!str.contains(","))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(txtCategoryName);

		JScrollPane scrollPane = new JScrollPane(txtCategoryName);
		scrollPane.setBounds(206, 94, 177, 109);
		contentPanel.add(scrollPane);

		bindData();
	}

	private void saveCategory() {

		String msg = controller.saveCategory(selectedCategory, mode);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return;
		}
		// add
		if (mode == 0) {
			int dialogResult = DisplayUtil.displayConfirmationMessage(contentPanel,
					StoreConstants.CATEGORY_ADDED_SUCCESSFULLY);
			if (dialogResult == 0) {
				selectedCategory = null;
				clearFields();
			} else if (dialogResult == 1) {
				dispose();
			}
		}
		// edit
		else if (mode == 1) {
			DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.CATEGORY_UPDATED_SUCCESSFULLY);
			dispose();
		}
	}

	private boolean validateForm() {
		categoryID = txtCategoryCode.getText().trim().replace("\n", " ");
		categoryName = txtCategoryName.getText().trim().replace("\n", " ");

		String msg = FormValidator.addEditCategoryValidateForm(categoryID, categoryName);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return false;
		}
		return true;
	}

	private boolean validateData() {
		if (mode == 0) {
			selectedCategory = new Category();
		}

		selectedCategory.setCategoryId(txtCategoryCode.getText().trim().replace("\n", " "));
		selectedCategory.setCategoryName(txtCategoryName.getText().trim().replace("\n", " "));

		String msg = null;
		// add
		if (mode == 0) {
			msg = FormValidator.addCategoryValidateData(selectedCategory);
		}
		// edit
		else if (mode == 1) {
			msg = FormValidator.editRemoveCategoryValidateData(selectedCategory);
		}

		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return false;
		}
		return true;
	}

	private void clearFields() {
		txtCategoryCode.setText("");
		txtCategoryName.setText("");
	}

	private void bindData() {
		// add
		if (selectedCategory == null) {
			mode = 0;
			this.setTitle("Add Category");
			txtCategoryCode.setDocument(new PlainDocument() {
				private static final long serialVersionUID = 5790057198546248513L;

				@Override
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					// System.out.println(getLength() + str.length());
					if (getLength() + str.length() <= StoreConstants.CATEGORY_ID_MAX_LENGTH && str.matches("[a-zA-Z]"))
						super.insertString(offs, str.toUpperCase(), a);
				}
			});
			clearFields();

		}
		// edit
		else {
			mode = 1;
			this.setTitle("Edit Category");
			txtCategoryCode.setText(selectedCategory.getCategoryId());
			txtCategoryCode.setEditable(false);
			txtCategoryName.setText(selectedCategory.getCategoryName());

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
