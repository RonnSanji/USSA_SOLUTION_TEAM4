package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.*;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;

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
import java.util.*;

public class AddCategory extends JDialog {

	private static final long serialVersionUID = -1420940689801074313L;
	private JPanel contentPane;
	private JTextField txtCatogeryID;
	private JTextArea txtaCategoryName;

	private String categoryID;
	private String categoryName;

	public AddCategory() {
		setTitle("Add Category Page");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 314);
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
					if (validateData()) {
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
						dialogResult = DisplayUtil.displayConfirmationMessage(contentPane,
								"Category ID: " + categoryID + StoreConstants.CATEGORY_EXISTS);
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
		btnClose.setBounds(239, 240, 89, 23);
		contentPane.add(btnClose);

		txtCatogeryID = new JTextField();
		txtCatogeryID.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				// System.out.println(getLength() + str.length());
				if (getLength() + str.length() <= StoreConstants.CATEGORY_ID_MAX_LENGTH && str.matches("[a-zA-Z]"))
					super.insertString(offs, str, a);
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

		if (categoryID == null || categoryID.isEmpty()) {
			// JOptionPane.showMessageDialog(contentPane, "Please enter category
			// ID", "Error", JOptionPane.ERROR_MESSAGE);
			DisplayUtil.displayValidationError(contentPane, StoreConstants.BLANK_CATEGORYID);
			return false;
		}
		categoryID = categoryID.trim().toUpperCase();
		txtCatogeryID.setText(categoryID);
		if (categoryID.length() != 3) {
			// JOptionPane.showMessageDialog(contentPane, "Category ID must be 3
			// characters", "Error",JOptionPane.ERROR_MESSAGE);
			DisplayUtil.displayValidationError(contentPane, StoreConstants.CATEGORY_3_LETTERS);
			return false;
		}
		categoryName = categoryName.trim();
		txtaCategoryName.setText(categoryName);
		if (categoryName == null || categoryName.isEmpty()) {
			// JOptionPane.showMessageDialog(contentPane, "Please enter category
			// name", "Error", JOptionPane.ERROR_MESSAGE);
			DisplayUtil.displayValidationError(contentPane, StoreConstants.BLANK_CATEGORYNAME);
			return false;
		}

		return true;
	}

	private Boolean validateData() {
		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				tempKeyList.add(key.toUpperCase());
			}
			if (tempKeyList.contains(categoryID.toUpperCase())) {
				return false;
			}
		}
		return true;
	}

	private Boolean addCategory() {
		sg.edu.nus.iss.ssa.model.Category category = new sg.edu.nus.iss.ssa.model.Category();
		category.setCategoryId(categoryID);
		category.setCategoryName(categoryName);
		try {
			FileDataWrapper.categoryMap.put(categoryID, category);
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(contentPane, "Error occured during
			// creating new category", "Error",JOptionPane.ERROR_MESSAGE);

			DisplayUtil.displayValidationError(contentPane, StoreConstants.ERROR + " creating new category");
			return false;
		}
		IOService<?> ioManager = new IOService<Entity>();
		try {
			ioManager.writeToFile(FileDataWrapper.categoryMap, new sg.edu.nus.iss.ssa.model.Category());
			ioManager = null;
		} catch (Exception ex)

		{
			// JOptionPane.showMessageDialog(contentPane, "Error occured during
			// saving new category", "Error", JOptionPane.ERROR_MESSAGE);
			DisplayUtil.displayValidationError(contentPane, StoreConstants.ERROR + " saving new category");
			ioManager = null;
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
		}
	}

	private void clearFields() {
		txtCatogeryID.setText("");
		txtaCategoryName.setText("");
	}

}
