package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.main.SouvenirStoreApp;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ManageInventory extends JPanel {

	private static final long serialVersionUID = 5485764052645713039L;
	// private JPanel contentPane;
	private JTextField txtSearchText;
	private JTable TbResult;
	private JComboBox<String> comboBoxSearchBy;
	private JScrollPane scrollPane;
	private JLabel lblNoResult;
	private JButton btnReplenish;
	private JButton btnShowBelowThreshold;

	private String[] comboBoxSearchByItem = new String[] { "Name", "Description" };
	private EntityListController controller = new EntityListController();

	private String searchBy;
	private String searchText;
	private Product selectedProduct;
	private Collection<Product> productList;
	private List<Product> productListResult;
	int selectedRow;
	// 1 for search, 2 for show all below threshold
	private int searchType = 0;

	private String[] columns = new String[] { "Product Name", "Product Description", "Bar Code", "Price",
			"Current Quantity", "Reorder Threshold", "Reorder Quantity" };
	private String[][] data;

	private TableModel model;
	private JButton btnClear;
	private JButton btnConfigureThreshold;

	public ManageInventory() {
		// setResizable(false);
		// setTitle("Manage Stock Page");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setOpaque(false);
		setLayout(null);
		// contentPane = new JPanel();

		// setContentPane(contentPane);
		// contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Search by");
		lblNewLabel.setBounds(20, 30, 80, 14);
		this.add(lblNewLabel);

		comboBoxSearchBy = new JComboBox();
		comboBoxSearchBy.setBounds(88, 27, 106, 20);
		this.add(comboBoxSearchBy);
		comboBoxSearchBy.setModel(new DefaultComboBoxModel(comboBoxSearchByItem));

		txtSearchText = new JTextField();
		txtSearchText.setBounds(204, 27, 168, 20);
		this.add(txtSearchText);
		txtSearchText.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(382, 26, 79, 23);
		this.add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});

		lblNoResult = new JLabel("");
		lblNoResult.setBounds(75, 71, 150, 14);
		this.add(lblNoResult);

		btnReplenish = new JButton("Replenish");
		btnReplenish.setBounds(326, 525, 150, 60);
		btnReplenish.setEnabled(false);
		btnReplenish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEditInventoryWindow(1);

			}
		});
		this.add(btnReplenish);

		// JButton btnClose = new JButton("Close");
		// btnClose.setBounds(510, 474, 100, 23);
		// contentPane.add(btnClose);
		// btnClose.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// dispose();
		// }
		// });

		btnShowBelowThreshold = new JButton("Show all below threshold");
		btnShowBelowThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAllBelowThreshold();
			}
		});
		btnShowBelowThreshold.setBounds(592, 26, 193, 23);
		this.add(btnShowBelowThreshold);

		TbResult = new JTable(new model());
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(TbResult);
		scrollPane.setBounds(10, 100, 780, 420);
		this.add(scrollPane);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearKeyWordTextBox();
			}
		});
		btnClear.setBounds(471, 26, 89, 23);
		this.add(btnClear);

		JButton btnGeneratePurchaseOrder = new JButton("Generate Order");
		btnGeneratePurchaseOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TbResult.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGeneratePurchaseOrder.setBounds(561, 525, 150, 60);
		add(btnGeneratePurchaseOrder);

		btnConfigureThreshold = new JButton("Configure Threshold");
		btnConfigureThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEditInventoryWindow(0);
			}
		});
		btnConfigureThreshold.setBounds(98, 525, 150, 60);
		add(btnConfigureThreshold);
		scrollPane.setVisible(true);
		TbResult.setVisible(false);

		showAllBelowThreshold();
	}

	private void clearKeyWordTextBox() {
		txtSearchText.setText("");
	}

	private void showAllBelowThreshold() {
		productList = (Collection<Product>) FileDataWrapper.productMap.values();
		productListResult = new ArrayList<Product>();

		for (Product product : productList) {
			if (product.getQuantity() < product.getThresholdQuantity()) {
				productListResult.add(product);
			}

		}

		if (productListResult.size() == 0) {
			showNoresult();
			return;
		}
		prepareTableData();
		/*
		 * data = new String[productListResult.size()][]; for (int i = 0; i <
		 * productListResult.size(); i++) { String[] values = new
		 * String[columns.length]; values[0] =
		 * productListResult.get(i).getProductName(); values[1] =
		 * productListResult.get(i).getProductDesc(); values[2] =
		 * String.valueOf(productListResult.get(i).getBarCode()); values[3] =
		 * String.valueOf(productListResult.get(i).getPrice()); values[4] =
		 * String.valueOf(productListResult.get(i).getQuantity()); values[5] =
		 * String.valueOf(productListResult.get(i).getThresholdQuantity());
		 * values[6] =
		 * String.valueOf(productListResult.get(i).getOrderQuantity()); data[i]
		 * = values; }
		 */
		txtSearchText.setText("");
		searchType = 2;
		showResultTable();
	}

	private void search() {
		productList = (Collection<Product>) FileDataWrapper.productMap.values();
		productListResult = new ArrayList<Product>();
		if (comboBoxSearchBy.getSelectedItem() != null) {
			searchBy = comboBoxSearchBy.getSelectedItem().toString();
			for (Product product : productList) {
				// Name
				if (searchBy == comboBoxSearchByItem[0]) {
					if (product.getProductName().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						productListResult.add(product);
					}
				}
				// Description
				else if (searchBy == comboBoxSearchByItem[1]) {
					if (product.getProductDesc().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						productListResult.add(product);
					}
				}

			}

			if (productListResult.size() == 0) {
				showNoresult();
				return;
			}

			prepareTableData();
			searchType = 1;
			showResultTable();
		}

	}

	private void prepareTableData() {
		data = new String[productListResult.size()][];
		for (int i = 0; i < productListResult.size(); i++) {
			String[] values = new String[columns.length];
			values[0] = productListResult.get(i).getProductName();
			values[1] = productListResult.get(i).getProductDesc();
			values[2] = String.valueOf(productListResult.get(i).getBarCode());
			values[3] = String.valueOf(productListResult.get(i).getPrice());
			values[4] = String.valueOf(productListResult.get(i).getQuantity());
			values[5] = String.valueOf(productListResult.get(i).getThresholdQuantity());
			values[6] = String.valueOf(productListResult.get(i).getOrderQuantity());
			data[i] = values;
		}

	}

	private void showNoresult() {
		// scrollPane.setVisible(false);
		btnReplenish.setEnabled(false);
		TbResult.setVisible(false);
		lblNoResult.setText("No result found");
	}

	private void showResultTable() {
		lblNoResult.setText("");
		btnReplenish.setEnabled(true);

		model = new model();

		TbResult.setModel(model);
		TbResult.setVisible(true);

	}

	public boolean hasBelowThreshold() {
		if (searchType == 2 && productListResult != null && productListResult.size() > 0) {
			return true;
		}
		return false;
	}

	public JTable getResulTable()
	{
		return TbResult;
	}
	private void showEditInventoryWindow(int mode) {

		selectedRow = TbResult.getSelectedRow();
		if (selectedRow == -1) {
			DisplayUtil.displayValidationError(scrollPane, StoreConstants.SELECT_PRODUCT);
			return;
		}
		selectedProduct = productListResult.get(selectedRow);

		EditInventory replenishStockWindow = new EditInventory(selectedProduct, mode);
		replenishStockWindow.setLocation(this.getLocationOnScreen());
		replenishStockWindow.setModal(true);
		replenishStockWindow.setVisible(true);
		replenishStockWindow.addWindowListener(new WindowListener() {

			@Override
			public void windowClosed(WindowEvent e) {
				// prepareTableData();
				// showResultTable();
				if (searchType == 1) {
					search();
				} else if (searchType == 2) {
					showAllBelowThreshold();
				}
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
