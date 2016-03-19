package sg.edu.nus.iss.ssa.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Category;

public class CategoryReport extends JFrame {
	private JTable table;
	private JPanel jp;
	private JScrollPane scrollPane;
	private JButton btnPrint;
	private JButton btnExit;
	private List<Category> list;

	public CategoryReport() throws FieldMismatchExcepion {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setTitle("Category Report");

		setLayout(new FlowLayout());
		String[] columnName = { "Category Id", "Category Name" };

		list = new ArrayList<Category>(
				(Collection<? extends Category>) FileDataWrapper.categoryMap
						.values());
		// System.out.println("cate"+FileDataWrapper.categoryMap.values());
		int count = FileDataWrapper.categoryMap.size();
		Object[][] data = new Object[count][];
		for (int i = 0; i < count; i++) {

			data[i] = list.get(i).getCategoryArray();

		}

		// DefaultTableCellRenderer middleRenderer = new
		// DefaultTableCellRenderer();
		// middleRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		// non-editable
		table = new JTable(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// center
		// table.getColumnModel().getColumn(0).setCellRenderer(middleRenderer);
		// table.getColumnModel().getColumn(1).setCellRenderer(middleRenderer);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		// table.setFont(new Font("Arial", Font.BOLD, 12));
		table.setPreferredScrollableViewportSize(new Dimension(570, 150));
		table.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(table);
		add(scrollPane);

		jp = new JPanel();
		btnPrint = new JButton("Print");
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jp.add(btnPrint);
		jp.add(btnExit);
		add(jp);
	}
}