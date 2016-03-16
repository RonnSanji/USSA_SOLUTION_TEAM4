package sg.edu.nus.iss.ssa.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class ReplenishStock extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2219926673801562401L;
	private JPanel contentPane;
	private JTextField txtSearchText;
	private JTable TbResult;
	JComboBox comboBoxSearchBy;
	private String[] comboBoxSearchByItem = new String[] { "Name", "Description" };

	private String searchBy;
	private String searchText;
	private Product selectedProduct;
	private Collection<Product> productList;
	private Collection<Product> productListResult;

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
				// TODO call method
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

		TbResult = new JTable();
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setBounds(86, 367, 433, -220);
		contentPane.add(TbResult);

		JLabel lblNoResult = new JLabel("");
		lblNoResult.setBounds(83, 109, 46, 14);
		contentPane.add(lblNoResult);

		JButton btnReplenish = new JButton("Replenish");
		btnReplenish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO call method

			}
		});
		btnReplenish.setBounds(122, 456, 89, 23);
		contentPane.add(btnReplenish);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(404, 456, 89, 23);
		contentPane.add(btnClose);
	}

	private void search() {
		productList = (Collection<Product>) FileDataWrapper.productMap.values();

		if (comboBoxSearchBy.getSelectedItem() != null) {
			searchBy = comboBoxSearchBy.getSelectedItem().toString();
			for (Product product : productList) {
				// Name
				if (searchBy == comboBoxSearchByItem[0]) {
					if (product.getProductName().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						System.out.println(product.getProductName());
					}
				}
				// Description
				else if (searchBy == comboBoxSearchByItem[1]) {
					if (product.getProductDesc().toUpperCase().contains(txtSearchText.getText().toUpperCase())) {
						System.out.println(product.getProductDesc());
					}
				}

			}
		}

	}

	private void replenish() {

	}

}
