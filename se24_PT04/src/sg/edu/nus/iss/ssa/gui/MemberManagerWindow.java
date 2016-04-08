package sg.edu.nus.iss.ssa.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
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
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Discount;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.LineItem;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;


import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class MemberManagerWindow extends JPanel {

	private  JScrollPane scrollPane;
	private  JPanel buttonPanel;
	private  JTable table;
	private JButton btnAddNewMember;
	private JButton btnRemoveMember;
	private DefaultTableModel model;
	private JTextField txtMemberSearch;
	private Collection<Member> memberList;
	private List<Member> memberListResult;
	JComboBox comboBoxMemberSearch;
	private String[] comboBoxSearchByItem = new String[] { "Member Name", "Member ID" };
	private String[] columns = new String[] { "Member Name", "Member Number", "Loylty Points" };
	private String searchBy;
	private Member membertoEdit;
	private int selectedRow;
	
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
		scrollPane.setBounds(12, 87, 782, 364);
		this.add(scrollPane);
		buttonPanel = new JPanel();
		buttonPanel.setBounds(12, 481, 782, 66);
		this.add(buttonPanel);
		buttonPanel.setOpaque(false);

		btnAddNewMember = new JButton("Add Memeber");
		btnAddNewMember.setBounds(20, 5, 160, 55);
		btnAddNewMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberAddingWindow memberAddWindow = new MemberAddingWindow(FileDataWrapper.memberMap,memberManagerWindow);
				memberAddWindow.setModal(true);
				memberAddWindow.setLocation(memberManagerWindow.getLocationOnScreen());
				memberAddWindow.setVisible(true);
				
			}
		});
		buttonPanel.setLayout(null);
		buttonPanel.add(btnAddNewMember);

		btnRemoveMember = new JButton("Remove Member");
		btnRemoveMember.setBounds(603, 5, 160, 55);
		btnRemoveMember.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
			    selectedRow = table.getSelectedRow();
				String selectedRowKey =null;
				try
				{
				 selectedRowKey = table.getValueAt(selectedRow, 1).toString();
				}
				catch( Exception es)
				{
					DisplayUtil.displayValidationError(table, StoreConstants.SELECT_MEMBER);
					//JOptionPane.showMessageDialog(table, "Please select at least one row", "Error", JOptionPane.ERROR_MESSAGE);
				}
				// check if any row is selected
				if(selectedRow != -1){
					//System.out.print(selectedRow);
					int option = DisplayUtil.displayConfirmationMessage(table, StoreConstants.CONFIRM_REMOVE_MEMBER);
							//JOptionPane.showConfirmDialog(null, "Confirm to remove this member?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
					//System.out.println(option);
					if(option==0){
						// Confirm Remove
						model.removeRow(selectedRow);
						FileDataWrapper.memberMap.remove(selectedRowKey);
						updateDatFile();
						DisplayUtil.displayAcknowledgeMessage(table, StoreConstants.MEMBER_REMOVED_SUCCESSFULLY);
					}

				}

			}
		});
		buttonPanel.add(btnRemoveMember);
		
		JButton btnEditMember = new JButton("Edit Member");
		btnEditMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    selectedRow = table.getSelectedRow();
				String selectedRowKey =null;
				try
				{
				 selectedRowKey = table.getValueAt(selectedRow, 1).toString();
				}
				catch( Exception es)
				{
					DisplayUtil.displayValidationError(table, StoreConstants.SELECT_MEMBER);
					//JOptionPane.showMessageDialog(table, StoreConstants.SELECT_MEMBER, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(selectedRow != -1){
			    membertoEdit = FileDataWrapper.memberMap.get(table.getValueAt(selectedRow, 1));
				MemberEditWindow memberEditWindow = new MemberEditWindow(membertoEdit,memberManagerWindow);				
				memberEditWindow.setModal(true);
				memberEditWindow.setLocation(memberManagerWindow.getLocationOnScreen());
				memberEditWindow.setVisible(true);
				}
			}
		});
		btnEditMember.setBounds(316, 5, 160, 55);
		buttonPanel.add(btnEditMember);

		// jTable Data Display
		
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
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JLabel lblSearchBy = new JLabel("Search by:");
		lblSearchBy.setBounds(12, 33, 73, 16);
		add(lblSearchBy);
		
	    comboBoxMemberSearch = new JComboBox();
		comboBoxMemberSearch.setModel(new DefaultComboBoxModel(comboBoxSearchByItem));
		comboBoxMemberSearch.setBounds(95, 28, 117, 27);
		add(comboBoxMemberSearch);
		
		txtMemberSearch = new JTextField();
		txtMemberSearch.setBounds(235, 27, 200, 28);
		add(txtMemberSearch);
		txtMemberSearch.setColumns(10);
		
		JButton btnMemberSearch = new JButton("Search");
		btnMemberSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberSearch();
			}
		});
		btnMemberSearch.setBounds(472, 27, 150, 28);
		add(btnMemberSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtMemberSearch.setText("");
				table.setModel(model);
			}
		});
		btnClear.setBounds(644, 27, 150, 28);
		add(btnClear);

	}
	public void refreshTable(String[] memberProperty){
		this.model.addRow(memberProperty);
	}
	
	public void updateEditedMember(){
		model.setValueAt(membertoEdit.getMemberName(), selectedRow, 0);
		model.setValueAt(membertoEdit.getMemberId(), selectedRow, 1);
		model.setValueAt(membertoEdit.getLoyaltyPoints(), selectedRow, 2);
		FileDataWrapper.memberMap.put(membertoEdit.getMemberId(), membertoEdit);
		table.setModel(model);
		updateDatFile();	
	}
	
	private void memberSearch(){
		memberList = (Collection<Member>) FileDataWrapper.memberMap.values();
		memberListResult = new ArrayList<Member>();
		if (comboBoxMemberSearch.getSelectedItem() != null) {
			searchBy = comboBoxMemberSearch.getSelectedItem().toString();
			for (Member member : memberList) {
				// member Name
				if (searchBy == comboBoxSearchByItem[0]) {
					if (member.getMemberName().toUpperCase().contains(txtMemberSearch.getText().toUpperCase())) {
						memberListResult.add(member);
					}
				}
				// member ID
				else if (searchBy == comboBoxSearchByItem[1]) {
					if (member.getMemberId().toUpperCase().contains(txtMemberSearch.getText().toUpperCase())) {
						memberListResult.add(member);
					}
				}
			}
		}
		//System.out.println(memberListResult.toArray());
		showSearchResult(memberListResult.toArray());
	}
	
	private void showSearchResult(Object[] members){
	
		
		Object[][] data = new Object[members.length][];
		for (int i = 0; i < members.length; i++) {
			data[i] =   ((Member)members[i]).getMemeberArray();
		}	
		DefaultTableModel modelResult = new DefaultTableModel(data, columns){
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		modelResult.isCellEditable(0, 0);
		 
		table.setModel(modelResult);
		
	}
	
	private void updateDatFile(){
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
