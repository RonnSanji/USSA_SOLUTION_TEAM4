package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Member;

public class MemberNumberScreen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField memberNumberField;


	/**
	 * Create the dialog.
	 * @param receipt 
	 * @param memberMap 
	 */
	public MemberNumberScreen(ProductSelectionWindow productWin ) {
		setTitle("Enter Member Number");
		setResizable(false);
		setSize(400,200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		memberNumberField = new JTextField();
		memberNumberField.setBounds(180, 42, 203, 26);
		contentPanel.add(memberNumberField);
		memberNumberField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Member Number:");
		lblNewLabel.setBounds(15, 45, 150, 20);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String memberNUmberStr = memberNumberField.getText();
						if(memberNUmberStr.equals("") ){
							JOptionPane.showMessageDialog(buttonPane, "Please enter Member Number", "Error", JOptionPane.ERROR_MESSAGE);
						}else if( !memberNUmberStr.equals("")){
							//logic to save and update parent window
							Object selectedItem = FileDataWrapper.memberMap.get(memberNUmberStr);
							if(selectedItem == null){
								JOptionPane.showMessageDialog(buttonPane, "Member Number not valid.", "Error", JOptionPane.ERROR_MESSAGE);
							}else {
								Member member = (Member)selectedItem;
								FileDataWrapper.receipt.setMemberId(member.getMemberId());
								FileDataWrapper.receipt.setAvlLoyaltyPoints(member.getLoyaltyPoints());
								productWin.dispose();
								ProductSelectionWindow newWindow = new ProductSelectionWindow();
								newWindow.setVisible(true);
								dispose();
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
