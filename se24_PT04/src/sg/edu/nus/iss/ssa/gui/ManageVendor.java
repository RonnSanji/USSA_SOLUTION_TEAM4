package sg.edu.nus.iss.ssa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Vendor;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class ManageVendor extends JDialog {

	private static final long serialVersionUID = -3601582073046388930L;

	EntityListController controller = new EntityListController();
	private JPanel contentPane;
	// private JTextField txtSearchText;
	// private JButton btnSearch;
	private JButton btnPrint;
	private JTable TbResult;
	// JComboBox<String> comboBoxSearchBy;
	JScrollPane scrollPane;
	JLabel lblNoResult;
	JButton btnAddVendor;
	JButton btnRemoveVendor;

	private String[] comboBoxSearchByItem = new String[] { "Code", "Name" };

	// private String searchBy;
	// private String searchText;
	// private Product selectedProduct;
	private List<Vendor> vendorList;
	// private List<Vendor> vendorListResult;
	private Vendor selectedVendor;
	private Category selectedCategory;

	String[] columns = new String[] { "Vendor Code", "Vendor Name" };
	String[][] data;

	TableModel model;

	private int selectedRow;
	private JButton btnEditVendor;
	private JButton btnClose;

	public static void main(String[] args) {
		try {
			EntityListController controller = new EntityListController();
			controller.reloadCategoryData();
			controller.loadAllVendorMap();
			
			JFrame frame = new JFrame();
			frame.setBounds(0, 0, 800, 600);

			ManageCategory manageCat = new ManageCategory();
			manageCat.setVisible(true);

			frame.add(manageCat);
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ManageVendor(Category category) {
		this.selectedCategory = category;
		setTitle("Manage Vendor for category: " + category.getCategoryId() + " / " + category.getCategoryName());

		setResizable(false);

		this.setSize(800, 600);

		this.addWindowListener(new MyWindowListener());

		getContentPane().setLayout(null);
		contentPane = new JPanel();
		contentPane.setBounds(10, 11, 800, 600);
		contentPane.setLayout(null);
		getContentPane().add(contentPane);

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

		// JButton btnSearch = new JButton("Search");
		// btnSearch.setBounds(451, 26, 79, 23);
		// contentPane.add(btnSearch);
		// btnSearch.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// search();
		// }
		// });

		lblNoResult = new JLabel("");
		lblNoResult.setBounds(20, 20, 150, 14);
		contentPane.add(lblNoResult);

		btnAddVendor = new JButton("Add Vendor");
		btnAddVendor.setBounds(10, 480, 150, 60);
		btnAddVendor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addVendor();
			}

		});
		contentPane.add(btnAddVendor);

		TbResult = new JTable(new model());
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setFillsViewportHeight(true);
		TbResult.setRowSelectionAllowed(true);

		scrollPane = new JScrollPane(TbResult);
		scrollPane.setBounds(10, 50, 700, 420);
		contentPane.add(scrollPane);

		// JButton btnClear = new JButton("Clear");
		// btnClear.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// clearKeyWordTextBox();
		// search();
		// }
		// });
		// btnClear.setBounds(540, 26, 89, 23);
		// contentPane.add(btnClear);

		btnRemoveVendor = new JButton("Remove Vendor");
		btnRemoveVendor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = -1;
				if (validateForm() && validateData()) {
					dialogResult = DisplayUtil.displayConfirmationMessage(scrollPane,
							StoreConstants.CONFIRM_TO_REMOVE_VENDOR);
					if (dialogResult == 0) {
						removeVendor();
					}
					search();
				}
			}
		});
		btnRemoveVendor.setBounds(420, 480, 150, 60);
		contentPane.add(btnRemoveVendor);

		btnEditVendor = new JButton("Edit Vendor");
		btnEditVendor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editVendor();
			}
		});
		btnEditVendor.setBounds(213, 480, 150, 60);
		contentPane.add(btnEditVendor);

		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				print();
			}
		});
		btnPrint.setBounds(620, 20, 89, 23);
		contentPane.add(btnPrint);

		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(620, 480, 150, 60);
		contentPane.add(btnClose);

		JButton btnUP = new JButton("Up");
		btnUP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveUp();
			}
		});
		btnUP.setBounds(710, 135, 70, 50);
		contentPane.add(btnUP);

		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});
		btnDown.setBounds(710, 224, 70, 50);
		contentPane.add(btnDown);

		scrollPane.setVisible(true);
		TbResult.setVisible(false);

		FileDataWrapper.vendorList.clear();
		if(controller.getVendorListByCategoryID(selectedCategory.getCategoryId()) !=null)
		{
			FileDataWrapper.vendorList.addAll(controller.getVendorListByCategoryID(selectedCategory.getCategoryId()));
		}
		

		search();
	}

	protected void moveUp() {
		if (TbResult.getRowCount() == 0) {
			return;
		}
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1 || selectedRow == 0) {
			return;
		}

		FileDataWrapper.vendorList.clear();
		FileDataWrapper.vendorList.addAll(DisplayUtil.adjustVendorListOrder(vendorList, selectedRow, 1));

		controller.adjustVendorListOrder(selectedCategory.getCategoryId());

		TbResult.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);

		search();

	}

	protected void moveDown() {

		if (TbResult.getRowCount() == 0) {
			return;
		}
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1 || selectedRow == TbResult.getRowCount() - 1) {
			return;
		}

		FileDataWrapper.vendorList.clear();
		FileDataWrapper.vendorList.addAll(DisplayUtil.adjustVendorListOrder(vendorList, selectedRow, 2));

		controller.adjustVendorListOrder(selectedCategory.getCategoryId());

		search();
		// System.out.println(vendorList);
	}

	protected boolean validateForm() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			DisplayUtil.displayValidationError(contentPane, StoreConstants.SELECT_VENDOR);
			return false;
		}
		selectedVendor = vendorList.get(selectedRow);
		if (selectedVendor == null) {
			DisplayUtil.displayValidationError(contentPane, StoreConstants.SELECT_VENDOR);
			return false;
		}
		String msg = FormValidator.removeVendorValidateForm(selectedVendor.getVendorId());
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPane, msg);
			return false;
		}

		return true;
	}

	private boolean validateData() {
		String msg = FormValidator.editRemoveVendorValidateData(selectedVendor);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPane, msg);
			return false;
		}

		return true;
	}

	protected void removeVendor() {

		if (controller == null) {
			controller = new EntityListController();
		}
		String msg = controller.saveVendor(selectedCategory.getCategoryId(), selectedVendor, 2);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPane, msg);
			return;
		}

		DisplayUtil.displayAcknowledgeMessage(contentPane, StoreConstants.VENDOR_REMOVED_SUCCESSFULLY);

	}

	// private void clearKeyWordTextBox() {
	// txtSearchText.setText("");
	// }

	private void print() {
		if (TbResult.getRowCount() == 0) {
			DisplayUtil.displayAcknowledgeMessage(contentPane, StoreConstants.VENDOR_LIST_EMPTY);
		} else {
			try {
				TbResult.print();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void search() {

		// Collection<Vendor> vendorCol =
		vendorList = new ArrayList<Vendor>();

		for (Vendor vendor : FileDataWrapper.vendorList) {
			vendorList.add(vendor);
		}
		// if (comboBoxSearchBy.getSelectedItem() != null) {
		// searchBy = comboBoxSearchBy.getSelectedItem().toString();
		// for (Vendor vendor : vendorList) {
		// Name
		// if (searchBy == comboBoxSearchByItem[0]) {
		// if
		// (vendor.getVendorId().toUpperCase().contains(txtSearchText.getText().toUpperCase()))
		// {
		// vendorList.add(vendor);
		// }
		// }
		// Description
		// else if (searchBy == comboBoxSearchByItem[1]) {
		// if
		// (vendor.getVendorName().toUpperCase().contains(txtSearchText.getText().toUpperCase()))
		// {
		// vendorListResult.add(vendor);
		// }
		// }
		// }
		// }

		if (vendorList.size() == 0) {
			showNoresult();
			return;
		}

		prepareTableData();

		showResultTable();

	}

	private void prepareTableData() {
		data = new String[vendorList.size()][];
		for (int i = 0; i < vendorList.size(); i++) {
			String[] values = new String[columns.length];
			values[0] = vendorList.get(i).getVendorId();
			values[1] = vendorList.get(i).getVendorName();
			data[i] = values;
		}

	}

	private void showNoresult() {
		TbResult.setVisible(false);
		btnRemoveVendor.setEnabled(false);
		btnEditVendor.setEnabled(false);
		btnPrint.setEnabled(false);
		lblNoResult.setText("No result found");
	}

	private void showResultTable() {
		lblNoResult.setText("");
		model = new model();

		TbResult.setModel(model);
		TbResult.setVisible(true);
		btnRemoveVendor.setEnabled(true);
		btnEditVendor.setEnabled(true);
		btnPrint.setEnabled(true);
	}

	private void editVendor() {
		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {

			DisplayUtil.displayValidationError(scrollPane, StoreConstants.SELECT_VENDOR);
			return;
		}
		selectedVendor = vendorList.get(selectedRow);
		showAddEditVendorWindow();
	}

	private void addVendor() {
		selectedVendor = null;
		showAddEditVendorWindow();
	}

	private void showAddEditVendorWindow() {
		EditVendor addEditVendorWindow = new EditVendor(selectedCategory.getCategoryId(), selectedVendor);
		addEditVendorWindow.setLocation(scrollPane.getLocationOnScreen());
		addEditVendorWindow.setModal(true);
		addEditVendorWindow.setVisible(true);
		addEditVendorWindow.addWindowListener(new WindowListener() {

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

	class model extends AbstractTableModel {

		private static final long serialVersionUID = 5557453753764317339L;

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
