package sg.edu.nus.iss.ssa.gui;

import java.awt.EventQueue;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.LineItem;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.util.IOService;


import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class MemberManagerWindow extends JFrame {

	private  JScrollPane scrollPane;
	private  JPanel buttonPanel;
	private  JTable table;
	private JButton btnAddNewMember;
	private JButton btnRemoveMember;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberManagerWindow window = new MemberManagerWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MemberManagerWindow() {
		
		MemberManagerWindow memberManagerWindow = this;
		//show the current memory members 
		/*IOService<?> ioManager = new IOService<Entity>();
		try {
			ioManager.readFromFile( FileDataWrapper.memberMap, new Member());
		} catch (FileNotFoundException | FieldMismatchExcepion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		System.out.println("members : " + FileDataWrapper.memberMap.keySet());
		
		setResizable(false);
		setTitle("Member Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,670);
		setLocationRelativeTo(null);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnBackToMain = new JButton("Back To DashBoard");
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnAddNewMember = new JButton("Add New Memeber");
		btnAddNewMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberAddingWindow memberAddWindow = new MemberAddingWindow(FileDataWrapper.memberMap,memberManagerWindow);
				memberAddWindow.setVisible(true);
			}
		});
		buttonPanel.add(btnAddNewMember);
		
		btnRemoveMember = new JButton("Remove Member");
		btnRemoveMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int selectedRow = table.getSelectedRow();
				String selectedRowKey = table.getValueAt(selectedRow, 1).toString();
				
				// check if any row is selected
				if(selectedRow != -1){
					System.out.print(selectedRow);
					int option = JOptionPane.showConfirmDialog(null, "Confirm to remove this member?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
				    System.out.println(option);
				    if(option==0){
				    	// Confirm Remove
				    	model.removeRow(selectedRow);
				    	FileDataWrapper.memberMap.remove(selectedRowKey);
				    	//System.out.println("members : " + FileDataWrapper.memberMap.keySet());
				    }
					
				 }
			
			}
		});
		buttonPanel.add(btnRemoveMember);
		btnBackToMain.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(btnBackToMain);
		
		// jTable Data Display
		String[] columns = new String[] { "Member Name", "Member Number", "LoytyPoints" };
	    Object[] members = FileDataWrapper.memberMap.values().toArray();		
	    Object[][] data = new Object[members.length][];
		
		// actual data for the table in a 2d array		
		for (int i = 0; i < members.length; i++) {
			data[i] =   ((Member)members[i]).getMemeberArray();
		}
	    model = new DefaultTableModel(data, columns); 
		table =   new JTable(model);	
		scrollPane.setViewportView(table);

	}

}
