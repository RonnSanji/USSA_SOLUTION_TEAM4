package sg.edu.nus.iss.ssa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

import javax.swing.JLabel;

public class ManageCategory extends JPanel {

	private static final long serialVersionUID = -8767406220537538426L;
	EntityListController controller = new EntityListController();
	// private JPanel contentPane;
	// private JTextField txtSearchText;
	// privateJButton btnSearch
	private JTable TbResult;
	// JComboBox<String> comboBoxSearchBy;
	JScrollPane scrollPane;
	JLabel lblNoResult;
	JButton btnAddCategory;
	JButton btnRemoveCategory;

	private String[] comboBoxSearchByItem = new String[] { "Code", "Name" };

	private String searchBy;
	private String searchText;
	private Product selectedProduct;
	private Collection<Category> categoryList;
	private List<Category> categoryListResult;
	private Category selectedCategory;

	// 1 for search, 2 for show all below threshold
	int searchType = 0;

	String[] columns = new String[] { "Category Code", "Category Name" };
	String[][] data;

	TableModel model;

	private int selectedRow;

	public ManageCategory() {
		// setResizable(false);
		this.setSize(800, 600);
		this.setOpaque(false);
		// setTitle("Manage Category");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setBounds(100, 100, 800, 550);
		setLayout(null);
		// contentPane = new JPanel();

		// this.add(contentPane);
		// contentPane.setBounds(120, 205, 1, 1);
		// contentPane.setLayout(null);

		// JLabel lblNewLabel = new JLabel("Search by");
		// lblNewLabel.setBounds(50, 30, 80, 14);
		// contentPane.add(lblNewLabel);

		// comboBoxSearchBy = new JComboBox<String>();
		// comboBoxSearchBy.setBounds(129, 27, 106, 20);
		// contentPane.add(comboBoxSearchBy);
		// comboBoxSearchBy.setModel(new
		// DefaultComboBoxModel<String>(comboBoxSearchByItem));

		// txtSearchText = new JTextField();
		// txtSearchText.setBounds(257, 27, 184, 20);
		// contentPane.add(txtSearchText);
		// txtSearchText.setColumns(10);

		// privateJButton = new JButton("Search");
		// btnSearch.setBounds(451, 26, 79, 23);
		// contentPane.add(btnSearch);
		// btnSearch.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// searchAll();
		// }
		// });

		lblNoResult = new JLabel("");
		lblNoResult.setBounds(75, 60, 150, 14);
		this.add(lblNoResult);

		btnAddCategory = new JButton("Add Category");
		btnAddCategory.setBounds(192, 520, 150, 60);
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCategory();
			}

		});
		this.add(btnAddCategory);

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(447, 550, 120, 23);
		// this.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// dispose();
			}
		});

		TbResult = new JTable(new model());
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(TbResult);
		scrollPane.setBounds(10, 80, 780, 420);
		this.add(scrollPane);

		// JButton btnClear = new JButton("Clear");
		// btnClear.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// clearKeyWordTextBox();
		// }
		// });
		// btnClear.setBounds(540, 26, 89, 23);
		// contentPane.add(btnClear);

		JLabel lblNewLabel = new JLabel("Category List");
		lblNewLabel.setBounds(20, 20, 188, 20);
		this.add(lblNewLabel);

		btnRemoveCategory = new JButton("Remove Category");
		btnRemoveCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = -1;
				if (validateForm()) {
					dialogResult = DisplayUtil.displayConfirmationMessage(scrollPane,
							StoreConstants.CONFIRM_TO_REMOVE_CATEROGY);
					if (dialogResult == 0) {
						removeCategory();
					}
					bindData();
				}
			}
		});
		btnRemoveCategory.setBounds(460, 520, 150, 60);
		add(btnRemoveCategory);
		scrollPane.setVisible(true);
		TbResult.setVisible(false);

		bindData();
	}

	protected boolean validateForm() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			// JOptionPane.showMessageDialog(contentPane, "Please select a
			// product", "Warning",JOptionPane.WARNING_MESSAGE);
			DisplayUtil.displayValidationError(this, StoreConstants.SELECT_CATEGORY);
			return false;
		}
		selectedCategory = categoryListResult.get(selectedRow);
		if (selectedCategory == null) {
			DisplayUtil.displayValidationError(this, StoreConstants.SELECT_CATEGORY);
			return false;
		}
		return true;
	}

	protected void removeCategory() {
		String msg = FormValidator.removeCategoryValidateForm(selectedCategory.getCategoryId());
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return;
		}
		msg = FormValidator.removeCategoryValidateData(selectedCategory.getCategoryId());
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return;
		}
		if (controller == null) {
			controller = new EntityListController();
		}
		msg = controller.RemoveCategory(selectedCategory.getCategoryId());
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			controller = null;
			return;
		}
		reloadData();
		controller = null;

		DisplayUtil.displayAcknowledgeMessage(this, StoreConstants.CATEGORY_REMOVED_SUCCESSFULLY);

		////
	}

	public void reloadData() {
		if (controller == null) {
			controller = new EntityListController();
		}
		controller.reloadCategoryData();
		controller = null;
	}

	private void clearKeyWordTextBox() {
		// txtSearchText.setText("");
	}

	public void bindData() {
		categoryList = (Collection<Category>) FileDataWrapper.categoryMap.values();
		categoryListResult = new ArrayList<Category>();
		// if (comboBoxSearchBy.getSelectedItem() != null) {
		// searchBy = comboBoxSearchBy.getSelectedItem().toString();
		for (Category category : categoryList) {
			// Name
			// if (searchBy == comboBoxSearchByItem[0]) {
			// if
			// (category.getCategoryId().toUpperCase().contains(txtSearchText.getText().toUpperCase()))
			// {
			categoryListResult.add(category);
			// }
			// }
			// Description
			// else if (searchBy == comboBoxSearchByItem[1]) {
			// if
			// (category.getCategoryName().toUpperCase().contains(txtSearchText.getText().toUpperCase()))
			// {
			// categoryListResult.add(category);
			// }
			// }

		}

		if (categoryListResult.size() == 0) {
			showNoresult();
			return;
		}

		prepareTableData();

		showResultTable();

		// searchType = 1;
		// }
	}

	private void prepareTableData() {
		data = new String[categoryListResult.size()][];
		for (int i = 0; i < categoryListResult.size(); i++) {
			String[] values = new String[columns.length];
			values[0] = categoryListResult.get(i).getCategoryId();
			values[1] = categoryListResult.get(i).getCategoryName();
			data[i] = values;
		}

	}

	private void showNoresult() {
		TbResult.setVisible(false);
		btnRemoveCategory.setEnabled(false);
		lblNoResult.setText("No result found");
	}

	private void showResultTable() {
		lblNoResult.setText("");
		model = new model();

		TbResult.setModel(model);
		TbResult.setVisible(true);
		btnRemoveCategory.setEnabled(true);
	}

	private void addCategory() {
		showAddCategoryWindow();
	}

	private void showAddCategoryWindow() {
		AddCategory addCategoryWindow = new AddCategory();

		addCategoryWindow.setLocation(scrollPane.getLocationOnScreen());

		addCategoryWindow.setModal(true);
		addCategoryWindow.setVisible(true);
		addCategoryWindow.addWindowListener(new WindowListener() {

			@Override
			public void windowClosed(WindowEvent e) {
				// if (searchType == 1) {
				bindData();
				// }
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

	class model extends AbstractTableModel {

		private static final long serialVersionUID = 1817809661748404816L;

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			if (data == null) {
				return 0;
			}
			return data.length;
		}

		@Override
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

		@Override
		public String getColumnName(int col) {
			return columns[col];
		}
	}
}
