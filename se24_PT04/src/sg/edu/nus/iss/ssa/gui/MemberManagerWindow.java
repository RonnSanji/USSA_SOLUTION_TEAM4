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
import sg.edu.nus.iss.ssa.constants.StoreConstants;
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
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;


import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class MemberManagerWindow extends JPanel {

	private  JScrollPane scrollPane;
	private  JPanel buttonPanel;
	private  JTable table;
	private JButton btnAddNewMember;
	private JButton btnRemoveMember;
	private DefaultTableModel model;



	/**
	 * Create the application.
	 */
	public MemberManagerWindow() {

		MemberManagerWindow memberManagerWindow = this;
		//show the current memory members 

		setSize(800,600);

		this.setOpaque(false);
		setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 46, 782, 405);
		this.add(scrollPane);
		buttonPanel = new JPanel();
		buttonPanel.setBounds(154, 483, 492, 66);
		this.add(buttonPanel);
		buttonPanel.setOpaque(false);

		btnAddNewMember = new JButton("Add New Memeber");
		btnAddNewMember.setBounds(5, 5, 161, 55);
		btnAddNewMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberAddingWindow memberAddWindow = new MemberAddingWindow(FileDataWrapper.memberMap,memberManagerWindow);
				memberAddWindow.setVisible(true);
			}
		});
		buttonPanel.setLayout(null);
		buttonPanel.add(btnAddNewMember);

		btnRemoveMember = new JButton("Remove Member");
		btnRemoveMember.setBounds(328, 5, 147, 55);
		btnRemoveMember.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String selectedRowKey =null;
				try
				{
				 selectedRowKey = table.getValueAt(selectedRow, 1).toString();
				}
				catch( Exception es)
				{
					JOptionPane.showMessageDialog(table, "Please select at least one row", "Error", JOptionPane.ERROR_MESSAGE);
				}
				// check if any row is selected
				if(selectedRow != -1){
					System.out.print(selectedRow);
					int option = JOptionPane.showConfirmDialog(null, "Confirm to remove this member?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
					System.out.println(option);
					if(option==0){
						// Confirm Remove
						model.removeRow(selectedRow);
						FileDataWrapper.memberMap.remove(selectedRowKey);
						// update the .dat file
						IOService<?> ioManager = new IOService<Entity>();
						try {
							ioManager.writeToFile(FileDataWrapper.memberMap.values(), new Member());
							ioManager = null;
						} catch (Exception ex)

						{
							DisplayUtil.displayValidationError(buttonPanel, StoreConstants.ERROR + " saving new member");
							ioManager = null;
						}
					}

				}

			}
		});
		buttonPanel.add(btnRemoveMember);

		// jTable Data Display
		String[] columns = new String[] { "Member Name", "Member Number", "LoytyPoints" };
		Object[] members = FileDataWrapper.memberMap.values().toArray();
		Object[][] data = new Object[members.length][];

		// actual data for the table in a 2d array		
		for (int i = 0; i < members.length; i++) {
			data[i] =   ((Member)members[i]).getMemeberArray();
		}
		model = new DefaultTableModel(data, columns){
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		model.isCellEditable(0, 0);
		table =   new JTable(model);
		
		
		scrollPane.setViewportView(table);

	}
	public void refreshTable(String[] memberProperty){
		this.model.addRow(memberProperty);
	}

}
