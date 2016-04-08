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
import sg.edu.nus.iss.ssa.model.Discount;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class ManageDiscount extends JPanel {
	private static final long serialVersionUID = -5197841819210766744L;
	private EntityListController controller;

	private JTextField txtSearchText;
	private JTable TbResult;
	private JComboBox<String> comboBoxSearchBy;
	private JScrollPane scrollPane;
	private JLabel lblNoResult;
	private JButton btnEditDiscount;
	private JButton btnAddDiscount;
	private JButton btnRemoveDiscount;

	private String[] comboBoxSearchByItem = new String[] { "Code", "Description" };

	private String searchBy;
	private String searchText;
	private PeriodDiscount selectedDiscount;
	private List<PeriodDiscount> discountList;
	private List<PeriodDiscount> discountListResult;
	int selectedRow;

	String[] columns = new String[] { "Discount Code", "Discount Description", "Star Date", "Period(Days)",
			"Percentage", "Applicable To" };
	String[][] data;

	private TableModel model;
	private JButton btnClear;

	public ManageDiscount() {
		this.setSize(800, 600);
		this.setOpaque(false);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Search by");
		lblNewLabel.setBounds(10, 34, 80, 14);
		this.add(lblNewLabel);

		comboBoxSearchBy = new JComboBox();
		comboBoxSearchBy.setBounds(100, 27, 106, 28);
		this.add(comboBoxSearchBy);
		comboBoxSearchBy.setModel(new DefaultComboBoxModel(comboBoxSearchByItem));

		txtSearchText = new JTextField();
		txtSearchText.setBounds(227, 27, 200, 28);
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
		lblNoResult.setBounds(75, 71, 150, 14);
		this.add(lblNoResult);

		btnEditDiscount = new JButton("Edit Discount");
		btnEditDiscount.setBounds(326, 525, 150, 60);
		btnEditDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editDiscount();

			}
		});
		this.add(btnEditDiscount);

		TbResult = new JTable(new MyTableModel());
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(TbResult);
		scrollPane.setBounds(10, 100, 780, 420);
		this.add(scrollPane);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearKeyWordTextBox();
				search();
			}
		});
		btnClear.setBounds(640, 27, 150, 28);
		this.add(btnClear);

		btnAddDiscount = new JButton("Add Discount");
		btnAddDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDiscount();
			}
		});
		btnAddDiscount.setBounds(98, 525, 150, 60);
		add(btnAddDiscount);

		btnRemoveDiscount = new JButton("Remove Discount");
		btnRemoveDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = -1;
				if (validateForm() && validateData()) {
					dialogResult = DisplayUtil.displayConfirmationMessage(scrollPane,
							StoreConstants.CONFIRM_TO_REMOVE_CATEROGY);
					if (dialogResult == 0) {
						removeDiscount();
					}
					search();
				}
			}
		});
		btnRemoveDiscount.setBounds(561, 525, 150, 60);
		add(btnRemoveDiscount);
		scrollPane.setVisible(true);
		TbResult.setVisible(false);

		search();
	}

	private boolean validateForm() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			DisplayUtil.displayValidationError(this, StoreConstants.SELECT_DISCOUNT);
			return false;
		}
		selectedDiscount = discountListResult.get(selectedRow);
		if (selectedDiscount == null) {
			DisplayUtil.displayValidationError(this, StoreConstants.SELECT_DISCOUNT);
			return false;
		}
		return true;
	}

	private boolean validateData() {
		String msg = FormValidator.editRemoveDiscountValidateData(selectedDiscount);
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return false;
		}
		return true;
	}

	private void removeDiscount() {
		if (controller == null) {
			controller = new EntityListController();
		}
		String msg = controller.saveDiscount(selectedDiscount, 2);
		if (msg != null) {
			DisplayUtil.displayValidationError(this, msg);
			return;
		}

		controller = null;

		DisplayUtil.displayAcknowledgeMessage(this, StoreConstants.DISCOUNT_REMOVED_SUCCESSFULLY);
	}

	private void clearKeyWordTextBox() {
		txtSearchText.setText("");
	}

	private void search() {
		discountList = (List<PeriodDiscount>) FileDataWrapper.discounts;
		discountListResult = new ArrayList<PeriodDiscount>();
		if (comboBoxSearchBy.getSelectedItem() != null) {
			searchBy = comboBoxSearchBy.getSelectedItem().toString();
			for (PeriodDiscount discount : discountList) {
				// Name
				if (searchBy == comboBoxSearchByItem[0]) {
					if (discount.getDiscountCode().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						discountListResult.add(discount);
					}
				}
				// Description
				else if (searchBy == comboBoxSearchByItem[1]) {
					if (discount.getDiscountDesc().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						discountListResult.add(discount);
					}
				}
			}

			if (discountListResult.size() == 0) {
				showNoresult();
				return;
			}

			prepareTableData();
			showResultTable();
		}

	}

	private void prepareTableData() {
		data = new String[discountListResult.size()][];
		for (int i = 0; i < discountListResult.size(); i++) {
			String[] values = new String[columns.length];
			PeriodDiscount discount = (PeriodDiscount) discountListResult.get(i);
			values[0] = discount.getDiscountCode();
			values[1] = discount.getDiscountDesc();
			values[2] = discount.getStarDate();
			values[3] = discount.getDiscountPeriod();
			values[4] = String.valueOf(discount.getDiscountPerc());
			values[5] = discount.getApplicableToName();
			data[i] = values;
		}
	}

	private void showNoresult() {
		// scrollPane.setVisible(false);
		btnEditDiscount.setEnabled(false);
		btnRemoveDiscount.setEnabled(false);
		btnEditDiscount.setEnabled(false);
		TbResult.setVisible(false);
		lblNoResult.setText("No result found");
	}

	private void showResultTable() {
		lblNoResult.setText("");
		btnEditDiscount.setEnabled(true);
		btnRemoveDiscount.setEnabled(true);
		btnEditDiscount.setEnabled(true);
		
		model = new MyTableModel();

		TbResult.setModel(model);
		TbResult.setVisible(true);

	}

	private void editDiscount() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			// JOptionPane.showMessageDialog(contentPane, "Please select a
			// product", "Warning",JOptionPane.WARNING_MESSAGE);
			DisplayUtil.displayValidationError(scrollPane, StoreConstants.SELECT_DISCOUNT);
			return;
		}
		selectedDiscount = discountListResult.get(selectedRow);
		showAddEditWindow();
	}

	private void addDiscount() {
		selectedDiscount = null;
		showAddEditWindow();
	}

	private void showAddEditWindow() {
		EditDiscount addEditDiscountWindow = new EditDiscount(selectedDiscount);
		addEditDiscountWindow.setLocation(this.getLocationOnScreen());
		addEditDiscountWindow.setModal(true);
		addEditDiscountWindow.setVisible(true);
		addEditDiscountWindow.addWindowListener(new WindowListener() {

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

		private static final long serialVersionUID = 8539059160246223660L;

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
