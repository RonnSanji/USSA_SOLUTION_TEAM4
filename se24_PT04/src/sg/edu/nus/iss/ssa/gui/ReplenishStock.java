package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Product;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReplenishStock extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2219926673801562401L;
	private JPanel contentPane;
	private JTextField txtSearchText;
	private JTable TbResult;
	JComboBox<String> comboBoxSearchBy;
	JScrollPane scrollPane;
	JLabel lblNoResult;
	JButton btnReplenish;
	JButton btnShowBelowThreshold;

	private String[] comboBoxSearchByItem = new String[]
	{ "Name", "Description" };

	private String searchBy;
	private String searchText;
	private Product selectedProduct;
	private Collection<Product> productList;
	private List<Product> productListResult;

	String[] columns = new String[]
	{ "Product Name", "Product Description", "Price", "Current Quantity", "Reorder Threshold", "Reorder Quantity" };
	String[][] data;

	TableModel model;

	/**
	 * Create the frame.
	 */
	public ReplenishStock()
	{
		setTitle("Replenish Stock Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 588);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Search by");
		lblNewLabel.setBounds(20, 30, 80, 14);
		contentPane.add(lblNewLabel);

		comboBoxSearchBy = new JComboBox();
		comboBoxSearchBy.setBounds(96, 27, 106, 20);
		contentPane.add(comboBoxSearchBy);
		comboBoxSearchBy.setModel(new DefaultComboBoxModel(comboBoxSearchByItem));

		txtSearchText = new JTextField();
		txtSearchText.setBounds(212, 27, 168, 20);
		contentPane.add(txtSearchText);
		txtSearchText.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(390, 26, 79, 23);
		contentPane.add(btnSearch);
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				search();
			}
		});

		lblNoResult = new JLabel("");
		lblNoResult.setBounds(75, 73, 150, 14);
		contentPane.add(lblNoResult);

		btnReplenish = new JButton("Replenish");
		btnReplenish.setBounds(170, 487, 100, 23);
		btnReplenish.setEnabled(false);
		btnReplenish.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				replenish();

			}
		});
		contentPane.add(btnReplenish);

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(440, 487, 100, 23);
		contentPane.add(btnClose);

		btnShowBelowThreshold = new JButton("Show all below threshold");
		btnShowBelowThreshold.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				showAllBelowThreshold();
			}
		});
		btnShowBelowThreshold.setBounds(481, 26, 193, 23);
		contentPane.add(btnShowBelowThreshold);
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		TbResult = new JTable(new model());
		TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TbResult.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(TbResult);
		scrollPane.setBounds(20, 100, 650, 350);
		contentPane.add(scrollPane);
		scrollPane.setVisible(true);
		TbResult.setVisible(false);
	}

	private void showAllBelowThreshold()
	{
		productList = (Collection<Product>) FileDataWrapper.productMap.values();
		productListResult = new ArrayList<Product>();

		for (Product product : productList)
		{
			if (product.getQuantity() < product.getThresholdQuantity())
			{
				productListResult.add(product);
			}

		}

		if (productListResult.size() == 0)
		{
			showNoresult();
			return;
		}

		data = new String[productListResult.size()][];
		for (int i = 0; i < productListResult.size(); i++)
		{
			String[] values = new String[columns.length];
			values[0] = productListResult.get(i).getProductName();
			values[1] = productListResult.get(i).getProductDesc();
			values[2] = String.valueOf(productListResult.get(i).getPrice());
			values[3] = String.valueOf(productListResult.get(i).getQuantity());
			values[4] = String.valueOf(productListResult.get(i).getThresholdQuantity());
			values[5] = String.valueOf(productListResult.get(i).getOrderQuantity());
			data[i] = values;
		}

		txtSearchText.setText("");
		showResultTable();
	}

	private void search()
	{
		productList = (Collection<Product>) FileDataWrapper.productMap.values();
		productListResult = new ArrayList<Product>();
		if (comboBoxSearchBy.getSelectedItem() != null)
		{
			searchBy = comboBoxSearchBy.getSelectedItem().toString();
			for (Product product : productList)
			{
				// Name
				if (searchBy == comboBoxSearchByItem[0])
				{
					if (product.getProductName().toUpperCase().contains(txtSearchText.getText().toUpperCase()))
					{
						productListResult.add(product);
					}
				}
				// Description
				else if (searchBy == comboBoxSearchByItem[1])
				{
					if (product.getProductDesc().toUpperCase().contains(txtSearchText.getText().toUpperCase()))
					{
						productListResult.add(product);
					}
				}

			}

			if (productListResult.size() == 0)
			{
				showNoresult();
				return;
			}

			data = new String[productListResult.size()][];
			for (int i = 0; i < productListResult.size(); i++)
			{
				String[] values = new String[columns.length];
				values[0] = productListResult.get(i).getProductName();
				values[1] = productListResult.get(i).getProductDesc();
				values[2] = String.valueOf(productListResult.get(i).getPrice());
				values[3] = String.valueOf(productListResult.get(i).getQuantity());
				values[4] = String.valueOf(productListResult.get(i).getThresholdQuantity());
				values[5] = String.valueOf(productListResult.get(i).getOrderQuantity());
				data[i] = values;
			}

			showResultTable();
		}

	}

	private void showResultTable()
	{
		lblNoResult.setText("");
		btnReplenish.setEnabled(true);

		model = new model();

		TbResult.setModel(model);
		TbResult.setVisible(true);
		/*
		 * TbResult = new JTable(data, columns) { private static final long
		 * serialVersionUID = 1L;
		 * 
		 * public boolean isCellEditable(int row, int column) { Object o =
		 * getValueAt(row, column); if (o != null) return false; return true; }
		 * };
		 * 
		 * TbResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * TbResult.setFillsViewportHeight(true);
		 * 
		 * scrollPane = new JScrollPane(TbResult); scrollPane.setBounds(20, 100,
		 * 560, 300); contentPane.add(scrollPane); scrollPane.setVisible(true);
		 * TbResult.setVisible(true);
		 */
	}

	private void showNoresult()
	{
		// scrollPane.setVisible(false);
		btnReplenish.setEnabled(false);
		TbResult.setVisible(false);
		lblNoResult.setText("No result found");
	}

	private void replenish()
	{
		int selectedRow = TbResult.getSelectedRow();
		System.out.println(selectedRow);
		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog(contentPane, "Please select a product", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		showReplenishWindow();
		
	}

	private void showReplenishWindow()
	{
		
		JDialog replenishdialog = new JDialog(this,  "Replenish Stock", true);
		
		JButton btnCloseReplenishWindow = new JButton("Close");
		replenishdialog.add(btnCloseReplenishWindow);
		
		btnCloseReplenishWindow.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				replenishdialog.dispose();
				
			}
		});
		replenishdialog.setVisible(true);
	}
	
	class model extends AbstractTableModel
	{

		@Override
		public int getColumnCount()
		{
			return columns.length;
		}

		@Override
		public int getRowCount()
		{
			// TODO Auto-generated method stub
			if (data == null)
			{
				return 0;
			}
			return data.length;
		}

		@Override
		public Object getValueAt(int arg0, int arg1)
		{
			// TODO Auto-generated method stub
			return data[arg0][arg1];
		}

		@Override
		public String getColumnName(int col)
		{
			return columns[col];
		}
	}
}
