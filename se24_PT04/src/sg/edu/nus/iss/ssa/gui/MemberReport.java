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
import sg.edu.nus.iss.ssa.model.Member;

public class MemberReport extends JFrame {
	private JTable table;
	private List<Member> listMember;
	private JScrollPane scrollPane;
	private JPanel jp;
	private JButton btnPrint;
	private JButton btnExit;
	private JLabel title;

	public MemberReport() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setTitle("Member Report");
		setSize(750, 400);

		setLayout(new FlowLayout());
		title=new JLabel("Member Report");
		title.setFont(new Font("Arial", Font.BOLD, 15) );
		String[] columnName = { "Member Id", "Member Name", "Loyalty Points" };

		listMember = new ArrayList<Member>(
				(Collection<? extends Member>) FileDataWrapper.memberMap
						.values());

		int count = FileDataWrapper.memberMap.size();
		Object[][] data = new Object[count][];
		for (int i = 0; i < count; i++) {
			data[i] = listMember.get(i).getMemeberArray();
		}

		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		// non-editable
		table = new JTable(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.setPreferredScrollableViewportSize(new Dimension(700, 250));
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
