package sg.edu.nus.iss.ssa.gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.Transaction;
import sg.edu.nus.iss.ssa.validation.ReportValidator;

import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;

import java.awt.Dimension;

public class ReportSummary extends JPanel {

	// private JFrame frame;

	private DefaultTableModel modelCategory, modelProduct, modelMember,
			modelTransction;
	private String[] columnCategoryName = new String[] { "Category Id",
			"Category Name" };
	private String[] columnProductName = new String[] { "ProductId",
			"ProductName", "ProductDescription", "Quantity", "Price",
			"BarCode", "ThresholdQuantity", "OrderQuantity" };
	private String[] columnMemberName = new String[] { "Member Id",
			"Member Name", "Loyalty Points" };
	private String[] columnNameTransaction = new String[] { "TransId",
			"MemberId", "Quantity", "Date", "Product Id", "Product Name",
			"Product Description" };

	private List<Category> listCategory;
	private List<Product> listProduct;
	private List<Member> listMember;
	private Map<String, Product> productMap;
	private List<Product> proList;
	private List<Transaction> txList;
	List<String[]> reportEntryList;
	private ReportValidator rv = new ReportValidator();
	private JTable table;
	private JTable transTable;
	private JButton filterBtn;
	private JPanel panelFilter;
	private DateSelector dsFrom;
	private DateSelector dsTo;

	/**
	 * Create the application.
	 */
	public ReportSummary() {

		setSize(800, 600);
		setOpaque(false);
		prepareData();
		this.setBounds(6, 6, 854, 588);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 173, 809, 347);
		add(scrollPane);

		table = new JTable();
		transTable = new JTable();

		JButton btnCatetory = new JButton("Category Report");
		btnCatetory.setBounds(25, 74, 185, 45);
		btnCatetory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tableSetting(modelCategory);
				scrollPane.setViewportView(table);
				panelFilter.setVisible(false);
			}
		});
		btnCatetory.setFont(new Font("AppleGothic", Font.BOLD, 15));

		JButton btnMember = new JButton("Member Report");
		btnMember.setBounds(410, 73, 185, 45);
		btnMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableSetting(modelMember);
				scrollPane.setViewportView(table);
				panelFilter.setVisible(false);
			}
		});
		btnMember.setFont(new Font("AppleGothic", Font.BOLD, 16));

		JButton btnProduct = new JButton("Product Report");
		btnProduct.setBounds(221, 73, 185, 45);
		btnProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableSetting(modelProduct);
				scrollPane.setViewportView(table);
				panelFilter.setVisible(false);
			}
		});
		btnProduct.setFont(new Font("AppleGothic", Font.BOLD, 16));

		JButton btnTransaction = new JButton("Transaction Report");
		btnTransaction.setBounds(612, 73, 185, 45);
		btnTransaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				transTableSetting(modelTransction);
				scrollPane.setViewportView(transTable);
				panelFilter.setVisible(true);
			}
		});
		btnTransaction.setFont(new Font("AppleGothic", Font.BOLD, 16));
		setLayout(null);

		add(btnCatetory);
		add(btnMember);
		add(btnProduct);
		add(btnTransaction);

		JLabel lblReportSummary = new JLabel("Report Summary");
		lblReportSummary.setBounds(343, 6, 205, 56);
		lblReportSummary.setFont(new Font("AppleGothic", Font.PLAIN, 25));
		add(lblReportSummary);

		JButton btnNewButton = new JButton("Print Report");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (panelFilter.isShowing())
						transTable.print();
					table.print();

				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(343, 532, 160, 50);
		add(btnNewButton);

		panelFilter = new JPanel();
		panelFilter.setBounds(10, 131, 824, 32);
		add(panelFilter);
		panelFilter.setOpaque(false);
		panelFilter.setVisible(false);
		panelFilter.setLayout(null);

		JLabel fromLbl = new JLabel("From:");
		fromLbl.setFont(new Font("AppleGothic", Font.BOLD, 12));
		fromLbl.setBounds(0, 8, 36, 16);
		panelFilter.add(fromLbl);
		dsFrom=new DateSelector();
		dsTo=new DateSelector();
		dsTo.setBounds(400, 6, 330, 20);
		dsFrom.setSize(330, 20);
		dsFrom.setLocation(34, 6);

		panelFilter.add(dsFrom);

		JLabel toLbl = new JLabel("To:");
		toLbl.setFont(new Font("AppleGothic", Font.BOLD, 12));
		toLbl.setBounds(374, 8, 20, 16);
		panelFilter.add(toLbl);

		panelFilter.add(dsTo);

		JButton filterBtn = new JButton("Filter");
		filterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterRecordsByDate();
			}
		});
		filterBtn.setBounds(733, 0, 76, 29);
		panelFilter.add(filterBtn);
	}

	private void prepareData() {

		modelCategory = new DefaultTableModel(this.getCategoryData(),
				columnCategoryName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelProduct = new DefaultTableModel(this.getProductData(),
				columnProductName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelMember = new DefaultTableModel(this.getMemberData(),
				columnMemberName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelTransction = new DefaultTableModel(this.getTransactionData(),
				columnNameTransaction) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

	}

	private Object[][] getCategoryData() {
		Object[][] data;
		int count = FileDataWrapper.categoryMap.size();
		listCategory = new ArrayList<Category>(
				(Collection<? extends Category>) FileDataWrapper.categoryMap
						.values());
		data = new Object[count][];
		for (int i = 0; i < count; i++) {

			data[i] = listCategory.get(i).getCategoryArray();

		}
		return data;
	}

	private Object[][] getProductData() {
		Object[][] data;
		listProduct = new ArrayList<Product>(
				(Collection<? extends Product>) FileDataWrapper.productMap
						.values());
		int count = FileDataWrapper.productMap.size();
		data = new Object[count][];
		for (int i = 0; i < count; i++) {
			data[i] = listProduct.get(i).getProductArray();
		}
		return data;
	}

	private Object[][] getMemberData() {
		Object[][] data;
		listMember = new ArrayList<Member>(
				(Collection<? extends Member>) FileDataWrapper.memberMap
						.values());
		int count = FileDataWrapper.memberMap.size();
		data = new Object[count][];
		for (int i = 0; i < count; i++) {
			data[i] = listMember.get(i).getMemeberArray();
		}
		return data;
	}

	private Object[][] getTransactionData() {
		Object[][] data;

		productMap = new HashMap<>();
		proList = new ArrayList<Product>(
				(Collection<? extends Product>) FileDataWrapper.productMap
						.values());
		for (Product prod : proList) {
			productMap.put(prod.getProductId(), prod);
		}
		// transaction
		txList = new ArrayList<Transaction>(FileDataWrapper.transactionList);
		reportEntryList = new ArrayList<>();

		// toArray
		for (Transaction tx : txList) {
			String[] txEntry = new String[7];
			txEntry[0] = String.valueOf(tx.getTransactionId());
			txEntry[1] = tx.getMemberId();
			txEntry[2] = String.valueOf(tx.getQuantity());
			txEntry[3] = tx.getDate();
			txEntry[4] = tx.getProductId();
			Product p = productMap.get(tx.getProductId());
			if (p != null) {
				txEntry[5] = p.getProductName();
				txEntry[6] = p.getProductDesc();
			}
			reportEntryList.add(txEntry);
		}

		int count = FileDataWrapper.transactionList.size();
		data = new Object[count][];
		for (int i = 0; i < count; i++) {
			data[i] = reportEntryList.get(i);
		}
		return data;
	}

	private Date getDate(String s) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					StoreConstants.DATE_FORMAT);
			Date d = sdf.parse(s);
			return d;

		} catch (ParseException ex) {
			return null;
		}
	}

	private void filterRecordsByDate() {
		String startDateString = dsFrom.getDateString();		
		String endDateString = dsTo.getDateString();
		//System.out.println(startDateString);
		//System.out.println(endDateString);

		if (!(rv.isDateValid(startDateString) && rv.isDateValid(endDateString))) {
			JOptionPane.showMessageDialog(filterBtn,
					StoreConstants.INVALID_DATE, null,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		RowFilter<Object, Object> dateFilter = new RowFilter<Object, Object>() {

			@Override
			public boolean include(
					javax.swing.RowFilter.Entry<? extends Object, ? extends Object> entry) {
				if (startDateString == null || startDateString.isEmpty()
						|| endDateString == null || endDateString.isEmpty()) {
					return true;
				}
				Date startDate = getDate(startDateString);
				Date endDate = getDate(endDateString);
				// F42563743156
				String dateString = (String) entry.getValue(3);
				Date date = getDate(dateString);
				if (date.after(startDate)
						&& (date.before(endDate) || date.equals(endDate))) {
					return true;
				}
				return false;
			}

		};

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
				transTable.getModel());
		sorter.setRowFilter(dateFilter);

		transTable.setRowSorter(sorter);
		transTable.getRowSorter().toggleSortOrder(4);

	}

	private void tableSetting(DefaultTableModel model) {
		table.setModel(model);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

	}

	private void transTableSetting(DefaultTableModel model) {
		transTable.setModel(model);
		transTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		transTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

	}

}
