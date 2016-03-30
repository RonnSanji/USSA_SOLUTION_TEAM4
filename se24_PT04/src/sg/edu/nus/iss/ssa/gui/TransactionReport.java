package sg.edu.nus.iss.ssa.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.Transaction;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.validation.ReportValidator;

public class TransactionReport extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel fromLbl;
	private JLabel toLbl;
	private JTextField fromText;
	private JTextField toText;
	private JButton filterBtn;
	private JTable table;
	private JPanel panel;
	List<String[]> reportEntryList;
	TableModel model;
	private JLabel title;
	private ReportValidator rv = new ReportValidator();
	private Map<String, Product> productMap;
	private List<Product> proList;
	private List<Transaction> txList;
	private Object[][] data;

	public TransactionReport() throws FileNotFoundException,
			FieldMismatchExcepion {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(750, 400);
		setVisible(true);
		setTitle("Transaction Report");
		setLocationRelativeTo(null);

		filterBtn = new JButton("Filter");
		getContentPane().setLayout(new FlowLayout());
		// title=new JLabel("Transaction Report");
		// title.setFont(new Font("Arial", Font.BOLD, 15) );
		// add(title);

		String[] columnName = { "TransId", "MemberId", "Quantity", "Date",
				"Product Id", "Product Name", "Product Description" };

		prepareTableData();

		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		model = new DefaultTableModel(data, columnName) {
			public Class<?> getColumnClass(int column) {
				Object obj = getValueAt(0, column);
				if (obj != null)
					return obj.getClass();
				return String.class;
			}
		};

		// non-editable
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// auto-sorting
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(35);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		table.setPreferredScrollableViewportSize(new Dimension(700, 250));
		table.setFillsViewportHeight(true);

		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		// panel.add(title);
		fromLbl = new JLabel("From:");
		panel.add(fromLbl);
		fromText = new JTextField();
		panel.add(fromText);
		fromText.setPreferredSize(new Dimension(80, 20));
		toLbl = new JLabel("To:");
		panel.add(toLbl);
		toText = new JTextField();
		toText.setPreferredSize(new Dimension(80, 20));
		panel.add(toText);
		add(filterBtn);

		// filter function
		filterBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filterRecordsByDate();
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane);

		JPanel jp = new JPanel();
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					table.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		jp.add(btnPrint);
		jp.add(btnExit);
		getContentPane().add(jp);
	}

	private void filterRecordsByDate() {
		String startDateString = fromText.getText();
		String endDateString = toText.getText();
		System.out.println(startDateString);
		System.out.println(endDateString);
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
				model);
		sorter.setRowFilter(dateFilter);
		table.setRowSorter(sorter);
		table.getRowSorter().toggleSortOrder(4);

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

	public void prepareTableData() {
		// combine product and transaction
		// product
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

	}
}
