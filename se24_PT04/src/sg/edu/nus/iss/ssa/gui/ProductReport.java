package sg.edu.nus.iss.ssa.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Product;

public class ProductReport extends JFrame {
	private JTable table;
	private List<Product> list;
	private JScrollPane scrollPane;
	private JPanel jp;
	private JButton btnPrint;
	private JButton btnExit;
	private JLabel title;

	public ProductReport() throws FieldMismatchExcepion {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		setTitle("Product Report");
		setVisible(true);
		setLocationRelativeTo(null);

		// List<Category> list = new
		// ArrayList<Category>(FileDataWrapper.categoryMap.values());

		setLayout(new FlowLayout());
		title=new JLabel("Product Report");
		title.setFont(new Font("Arial", Font.BOLD, 15) );
		
		String[] columnName = { "ProductId", "ProductName", "ProductDescription",
				"Quantity", "Price", "BarCode", "ThresholdQuantity",
				"OrderQuantity" };

		list = new ArrayList<Product>(
				(Collection<? extends Product>) FileDataWrapper.productMap
						.values());
		// System.out.println("Product" + FileDataWrapper.productMap.values());
		int count = FileDataWrapper.productMap.size();
		Object[][] data = new Object[count][];
		for (int i = 0; i < count; i++) {

			data[i] = list.get(i).getProductArray();

		}

		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		// non-editable
		table = new JTable(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// center
		table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setPreferredWidth(170);
		table.getColumnModel().getColumn(7).setPreferredWidth(140);
		// table.setFont(new Font("Arial", Font.BOLD, 12));
		table.setPreferredScrollableViewportSize(new Dimension(710, 250));
		table.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(table);
		add(title);
		add(scrollPane);

		jp = new JPanel();
		btnPrint = new JButton("Print");
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
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		jp.add(btnPrint);
		jp.add(btnExit);
		add(jp);
	}
}
