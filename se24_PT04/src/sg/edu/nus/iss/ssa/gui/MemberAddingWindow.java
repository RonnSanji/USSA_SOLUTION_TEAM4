package sg.edu.nus.iss.ssa.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class MemberAddingWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	private JTextField addmemberNameField;
	private JTextField addmemberNumberField;

	
	/**
	 * Create the application.
	 */
	public MemberAddingWindow(Map memberMap, MemberManagerWindow memberManagerWindow) {
		
		setTitle("Adding New Member");
		setResizable(false);
		setSize(400,200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JLabel lblMemberName = new JLabel("Member Name :");
		lblMemberName.setBounds(30, 47, 107, 16);
		contentPanel.add(lblMemberName);
		
		JLabel lblMemberID = new JLabel("Member Number :");
		lblMemberID.setBounds(30, 89, 107, 16);
		contentPanel.add(lblMemberID);
		
		addmemberNameField = new JTextField();
		addmemberNameField.setBounds(170, 40, 210, 30);
		contentPanel.add(addmemberNameField);
		addmemberNameField.setColumns(15);
		
		addmemberNumberField = new JTextField();
		addmemberNumberField.setBounds(170, 82, 210, 30);
		contentPanel.add(addmemberNumberField);
		addmemberNumberField.setColumns(15);
	
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			  String memberNameString , memberNumberString = new String();
			  
			  memberNameString = addmemberNameField.getText();
			  memberNumberString = addmemberNumberField.getText();
			  
			  if(memberNameString.equals("") ||memberNumberString.equals("")){
					JOptionPane.showMessageDialog(btnAddMember, "Please enter Member Name & Member Number", "Error", JOptionPane.ERROR_MESSAGE);
			  }
			  else if(!memberNameString.equals("") && !memberNumberString.equals("") ){
				  //Check the IC number 
				  FormValidator validator = new FormValidator();
				  String validateResult = validator.addMemberValidateForm(memberNameString, memberNumberString);
				  if (validateResult!=null)
				  {
					  JOptionPane.showMessageDialog(btnAddMember, validateResult, "Error", JOptionPane.ERROR_MESSAGE);
				  }
				  
				  else{
					  //create new member object
					  Member newMember = new Member(memberNameString,memberNumberString,-1);

					  
					  //add new member to memory 
					  try{
					  memberMap.put(memberNumberString,newMember);
					  }catch (Exception ex){
						  DisplayUtil.displayValidationError(contentPanel, StoreConstants.ERROR + " creating new member");
					  }
					  
					  //write new memeber from memory to .dat file
					  IOService<?> ioManager = new IOService<Entity>();
						try {
							ioManager.writeToFile(memberMap.values(), new sg.edu.nus.iss.ssa.model.Member());
							ioManager = null;
						} catch (Exception ex)

						{
							DisplayUtil.displayValidationError(contentPanel, StoreConstants.ERROR + " saving new member");
							ioManager = null;
						}
				      	
						//update the table data in MemberManagerWindow
						memberManagerWindow.refreshTable(newMember.getMemeberArray());
					dispose();
				  }
				
			  }
			  
			  
			}
		});
		
		buttonPanel.add(btnAddMember);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(btnCancel);
		
	
	}

	public MemberAddingWindow() {
		// TODO Auto-generated constructor stub
	}
	
}
