package sg.edu.nus.iss.ssa.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Product;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

public class ReplenishStock extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2219926673801562401L;
	private JPanel contentPane;
	private JPanel panelResult;
	private JTextField txtSearchText;
	private JTable TbResult;
	JComboBox<String> comboBoxSearchBy;
	JScrollPane scrollPane;

	private String[] comboBoxSearchByItem = new String[] { "Name", "Description" };

	private String searchBy;
	private String searchText;
	private Product selectedProduct;
	private Collection<Product> productList;
	private List<Product> productListResult;

	/**
	 * Create the frame.
	 */
	public ReplenishStock() {
		setTitle("Replenish Stock Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 633, 546);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});
		btnSearch.setBounds(498, 48, 89, 23);
		contentPane.add(btnSearch);

		txtSearchText = new JTextField();
		txtSearchText.setBounds(236, 49, 229, 20);
		contentPane.add(txtSearchText);
		txtSearchText.setColumns(10);

		JLabel lblNewLabel = new JLabel("Search by");
		lblNewLabel.setBounds(40, 52, 89, 14);
		contentPane.add(lblNewLabel);

		comboBoxSearchBy = new JComboBox();
		comboBoxSearchBy.setModel(new DefaultComboBoxModel(comboBoxSearchByItem));
		comboBoxSearchBy.setBounds(122, 49, 104, 20);
		contentPane.add(comboBoxSearchBy);

		panelResult = new JPanel();
		panelResult.setBounds(41, 109, 534, 291);
		contentPane.add(panelResult);

		String[] columns = new String[] { "Product Name", "Product Description", "Price", "Current Quantity",
				"Reorder Threshold", "Reorder Quantity" };

		String[][] data = new String[2][];
		data[0] = new String[] { "Test name 1", "Test Description 2", "100", "20", "20", "40" };
		data[1] = new String[] { "Test name 2", "Test Description 2", "200", "22", "22", "20" };

		TbResult = new JTable(data, columns);

		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//TbResult.setBounds(83, 396, 447, -249);
		TbResult.setFillsViewportHeight(true);
		// panelResult.add(TbResult);

		JScrollPane scrollPane = new JScrollPane(TbResult);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setLayout(null);
		scrollPane.setBounds(83, 396, 447, -249);
		panelResult.add(scrollPane);

		scrollPane.setViewportView(TbResult);

		JLabel lblNoResult = new JLabel("");
		//lblNoResult.setBounds(83, 109, 46, 14);
		contentPane.add(lblNoResult);

		JButton btnReplenish = new JButton("Replenish");
		btnReplenish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO call method

			}
		});
		btnReplenish.setBounds(137, 456, 104, 23);
		contentPane.add(btnReplenish);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(377, 456, 95, 23);
		contentPane.add(btnClose);

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
			String[] columns = new String[] { "Product Name", "Product Description", "Price", "Current Quantity",
					"Reorder Threshold", "Reorder Quantity" };
			Object[][] data = new Object[productListResult.size()][];
			for (int i = 0; i < productListResult.size(); i++) {
				String[] values = new String[columns.length];
				values[0] = productListResult.get(i).getProductName();
				values[1] = productListResult.get(i).getProductDesc();
				values[2] = String.valueOf(productListResult.get(i).getPrice());
				values[3] = String.valueOf(productListResult.get(i).getQuantity());
				values[4] = String.valueOf(productListResult.get(i).getThresholdQuantity());
				values[5] = String.valueOf(productListResult.get(i).getOrderQuantity());
				data[i] = values;
			}

		}

	}

	private void replenish() {

	}
}
