package sg.edu.nus.iss.ssa.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.Vendor;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class ManageCategory extends JPanel {

	private static final long serialVersionUID = -8767406220537538426L;
	EntityListController controller = new EntityListController();
	// private JPanel contentPane;
	private JTextField txtSearchText;
	private JTable TbResult;
	private JComboBox<String> comboBoxSearchBy;
	private JScrollPane scrollPane;
	private JLabel lblNoResult;
	private JLabel lblSeachBy;
	private JButton btnAddCategory;
	private JButton btnRemoveCategory;

	private String[] comboBoxSearchByItem = new String[] { "Code", "Name" };

	private String searchBy;
	private String searchText;
	private Product selectedProduct;
	private Collection<Category> categoryList;
	private List<Category> categoryListResult;
	private Category selectedCategory;

	private String[] columns = new String[] { "Category Code", "Category Name", "Preferred Vendor" };
	private String[][] data;
	private TableModel model;

	private int selectedRow;
	private JButton btnEditCategory;

	public ManageCategory() {

		this.setSize(800, 600);
		this.setOpaque(false);

		setLayout(null);

		lblSeachBy = new JLabel("Search by");
		lblSeachBy.setBounds(10, 34, 80, 14);
		this.add(lblSeachBy);

		comboBoxSearchBy = new JComboBox<String>();
		comboBoxSearchBy.setBounds(88, 27, 120, 28);
		this.add(comboBoxSearchBy);
		comboBoxSearchBy.setModel(new DefaultComboBoxModel<String>(comboBoxSearchByItem));

		txtSearchText = new JTextField();
		txtSearchText.setBounds(245, 27, 200, 28);
		this.add(txtSearchText);
		txtSearchText.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(469, 27, 150, 28);
		this.add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});

		lblNoResult = new JLabel("");
		lblNoResult.setBounds(75, 60, 150, 14);
		this.add(lblNoResult);

		btnAddCategory = new JButton("Add Category");
		btnAddCategory.setBounds(10, 525, 150, 60);
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCategory();
			}

		});
		this.add(btnAddCategory);

		TbResult = new JTable(new MyTableModel());
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(TbResult);
		scrollPane.setBounds(10, 100, 780, 420);
		this.add(scrollPane);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearKeyWordTextBox();
				search();
			}
		});
		btnClear.setBounds(640, 27, 150, 28);
		this.add(btnClear);

		// JLabel lblNewLabel = new JLabel("Category List");
		// lblNewLabel.setBounds(20, 20, 188, 20);
		// this.add(lblNewLabel);

		btnRemoveCategory = new JButton("Remove Category");
		btnRemoveCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = -1;
				if (validateForm() && validateData()) {
					dialogResult = DisplayUtil.displayConfirmationMessage(scrollPane,
							StoreConstants.CONFIRM_TO_REMOVE_CATEROGY);
					if (dialogResult == 0) {
						removeCategory();
					}
					search();
				}
			}
		});
		btnRemoveCategory.setBounds(427, 525, 150, 60);
		add(btnRemoveCategory);

		btnEditCategory = new JButton("Edit Category");
		btnEditCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCategory();
			}
		});
		btnEditCategory.setBounds(214, 525, 150, 60);
		add(btnEditCategory);

		JButton btnManageVendor = new JButton("Manage Vendor");
		btnManageVendor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manageVendor();
			}
		});
		btnManageVendor.setBounds(640, 525, 150, 60);
		add(btnManageVendor);
		scrollPane.setVisible(true);
		TbResult.setVisible(false);

		search();
	}

	protected boolean validateForm() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			DisplayUtil.displayValidationError(this, StoreConstants.SELECT_CATEGORY);
			return false;
		}
		selectedCategory = categoryListResult.get(selectedRow);
		if (selectedCategory == null) {
			DisplayUtil.displayValidationError(this, StoreConstants.SELECT_CATEGORY);
			return false;
		}
		String msg = FormValidator.removeCategoryValidateForm(selectedCategory.getCategoryId());
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return false;
		}

		return true;
	}

	private boolean validateData() {
		String msg = FormValidator.editRemoveCategoryValidateData(selectedCategory);
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return false;
		}

		return true;
	}

	protected void removeCategory() {

		if (controller == null) {
			controller = new EntityListController();
		}
		String msg = controller.saveCategory(selectedCategory, 2);
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return;
		}

		controller = null;

		DisplayUtil.displayAcknowledgeMessage(this, StoreConstants.CATEGORY_REMOVED_SUCCESSFULLY);

	}

	private void clearKeyWordTextBox() {
		txtSearchText.setText("");
	}

	public void search() {
		categoryList = (Collection<Category>) FileDataWrapper.categoryMap.values();
		categoryListResult = new ArrayList<Category>();
		if (comboBoxSearchBy.getSelectedItem() != null) {
			searchBy = comboBoxSearchBy.getSelectedItem().toString();
			for (Category category : categoryList) {
				// Name
				if (searchBy == comboBoxSearchByItem[0]) {
					if (category.getCategoryId().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						categoryListResult.add(category);
					}
				}
				// Description
				else if (searchBy == comboBoxSearchByItem[1]) {
					if (category.getCategoryName().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						categoryListResult.add(category);
					}
				}
			}
		}

		if (categoryListResult.size() == 0) {
			showNoresult();
			return;
		}

		prepareTableData();

		showResultTable();

	}

	private void prepareTableData() {
		data = new String[categoryListResult.size()][];
		for (int i = 0; i < categoryListResult.size(); i++) {
			String[] values = new String[columns.length];
			values[0] = categoryListResult.get(i).getCategoryId();
			values[1] = categoryListResult.get(i).getCategoryName();
			ArrayList<Vendor> vendors = controller.getVendorListByCategoryID(values[0]);
			if (vendors != null && vendors.size() > 0) {
				values[2] = vendors.get(0).getVendorId();
			} else {
				values[2] = StoreConstants.NO_VENDOR_CONFIGURED;
			}
			data[i] = values;
		}

	}

	private void showNoresult() {
		TbResult.setVisible(false);
		btnRemoveCategory.setEnabled(false);
		btnEditCategory.setEnabled(false);
		lblNoResult.setText("No result found");
	}

	private void showResultTable() {
		lblNoResult.setText("");
		model = new MyTableModel();

		TbResult.setModel(model);
		TbResult.setVisible(true);
		btnRemoveCategory.setEnabled(true);
		btnEditCategory.setEnabled(true);
	}

	private void editCategory() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {

			DisplayUtil.displayValidationError(scrollPane, StoreConstants.SELECT_CATEGORY);
			return;
		}
		selectedCategory = categoryListResult.get(selectedRow);
		showAddEditCategoryWindow();
	}

	private void addCategory() {
		selectedCategory = null;
		showAddEditCategoryWindow();
	}

	private void manageVendor() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			if (selectedRow == -1) {
				DisplayUtil.displayValidationError(scrollPane, StoreConstants.SELECT_CATEGORY);
				return;
			}
		}
		selectedCategory = categoryListResult.get(selectedRow);
		showManageVendorWindow();
	}

	private void showManageVendorWindow() {
		ManageVendor manageVendor = new ManageVendor(selectedCategory);
		manageVendor.setLocation(this.getLocationOnScreen());
		manageVendor.setModal(true);
		manageVendor.setVisible(true);
		manageVendor.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				search();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void showAddEditCategoryWindow() {
		EditCategory addEditCategoryWindow = new EditCategory(selectedCategory);
		addEditCategoryWindow.setLocation(scrollPane.getLocationOnScreen());
		addEditCategoryWindow.setModal(true);
		addEditCategoryWindow.setVisible(true);
		addEditCategoryWindow.addWindowListener(new WindowListener() {

			@Override
			public void windowClosed(WindowEvent e) {
				search();
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1817809661748404816L;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		public int getColumnCount() {
			return columns.length;
		}

		public int getRowCount() {
			// TODO Auto-generated method stub
			if (data == null) {
				return 0;
			}
			return data.length;
		}

		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			if (data == null) {
				return null;
			}
			if (data[arg0] == null) {
				return null;
			}
			return data[arg0][arg1];

		}

		public String getColumnName(int col) {
			return columns[col];
		}

		public void setRowColour(int row, Color c) {
			rowColours.set(row, c);
			fireTableRowsUpdated(row, row);
		}

		public Color getRowColour(int row) {
			return rowColours.get(0);
		}

	}
}
