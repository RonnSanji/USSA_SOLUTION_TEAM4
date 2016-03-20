package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.*;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.validation.FormValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.text.*;
import javax.swing.JScrollPane;
import java.io.IOException;
import java.util.*;

public class AddCategory extends JDialog {

	private static final long serialVersionUID = -1420940689801074313L;
	private JPanel contentPane;
	private JTextField txtCatogeryID;
	private JTextArea txtaCategoryName;

	private String categoryID;
	private String categoryName;
	private FormValidator formValidator = new FormValidator();
	private EntityListController controller = new EntityListController();

	public AddCategory() {
		setResizable(false);
		setTitle("Add Category Page");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCategoryID = new JLabel("Category ID: ");
		lblCategoryID.setBounds(68, 38, 80, 14);
		contentPane.add(lblCategoryID);

		JLabel lblCategoryName = new JLabel("Category Name: ");
		lblCategoryName.setBounds(68, 94, 139, 14);
		contentPane.add(lblCategoryName);

		JButton btnAdd = new JButton("OK");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dialogResult = -1;
				if (validateForm()) {
					String msg = validateData();
					if (msg == null) {
						if (addCategory()) {
							// dialogResult =
							// JOptionPane.showConfirmDialog(contentPane,"Category
							// has been added successfully. Would like to add
							// another one ?", "Message",
							// JOptionPane.YES_NO_OPTION,
							// JOptionPane.INFORMATION_MESSAGE);
							dialogResult = DisplayUtil.displayConfirmationMessage(contentPane,
									StoreConstants.CATEGORY_ADDED_SUCCESSFULLY);
							if (dialogResult == 0) {
								clearFields();
							} else if (dialogResult == 1) {
								dispose();
							}
						}

						reloadData();
					} else {
						// dialogResult =
						// JOptionPane.showConfirmDialog(contentPane, "Category
						// ID: " + categoryID + " already exists. Would you like
						// to add another one ?", "Warning",
						// JOptionPane.YES_NO_OPTION,
						// JOptionPane.WARNING_MESSAGE);
						dialogResult = DisplayUtil.displayConfirmationMessage(contentPane, msg);
						if (dialogResult == 0) {
							clearFields();
						} else if (dialogResult == 1) {
							dispose();
						}
					}
				}

			}
		});
		btnAdd.setBounds(98, 240, 89, 23);
		contentPane.add(btnAdd);

		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(249, 240, 89, 23);
		contentPane.add(btnClose);

		txtCatogeryID = new JTextField();
		txtCatogeryID.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				// System.out.println(getLength() + str.length());
				if (getLength() + str.length() <= StoreConstants.CATEGORY_ID_MAX_LENGTH && str.matches("[a-zA-Z]"))
					super.insertString(offs, str.toUpperCase(), a);
			}
		});
		txtCatogeryID.setBounds(206, 35, 177, 20);
		contentPane.add(txtCatogeryID);

		txtaCategoryName = new JTextArea();
		txtaCategoryName.setLineWrap(true);
		txtaCategoryName.setBounds(274, 158, 177, 109);
		contentPane.add(txtaCategoryName);

		JScrollPane scrollPane = new JScrollPane(txtaCategoryName);
		scrollPane.setBounds(206, 94, 177, 109);
		contentPane.add(scrollPane);

	}

	private Boolean validateForm() {
		categoryID = txtCatogeryID.getText();
		categoryName = txtaCategoryName.getText();

		String validatorMessage = formValidator.addCategoryValidateForm(categoryID, categoryName);
		if (validatorMessage != null) {
			DisplayUtil.displayValidationError(contentPane, validatorMessage);
			return false;
		}
		return true;
	}

	private String validateData() {
		String validatorMessage = formValidator.addCategoryValidateData(categoryID);
		return validatorMessage;
	}

	private boolean addCategory() {
		String msg = controller.addCategory(categoryID, categoryName);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPane, msg);
			return false;
		}
		return true;
	}

	private void reloadData() {
		IOService<?> ioManager = new IOService<Entity>();
		FileDataWrapper.categoryMap.clear();
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			ioManager = null;
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			ioManager = null;
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearFields() {
		txtCatogeryID.setText("");
		txtaCategoryName.setText("");
	}

}
