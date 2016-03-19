package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

public class MemberNumberScreen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField memberNumberField;
	Map<String,Member> memberMap = null;
	Order order = null;
	/**
	 * Create the dialog.
	 */
	public MemberNumberScreen(ProductSelectionWindow productWin ) {
		memberMap = FileDataWrapper.memberMap;
		order = FileDataWrapper.receipt;
		setTitle("Enter Member Number:");
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
						String memberNumberStr = memberNumberField.getText();
						if(memberNumberStr.equals("") ){
							DisplayUtil.displayValidationError(buttonPane, StoreConstants.BLANK_MEMBER_NUMBER);
						}else {
							//logic to save and update parent window
							Member selectedItem = FileDataWrapper.memberMap.get(memberNumberStr);
							if(selectedItem == null){
								DisplayUtil.displayValidationError(buttonPane, StoreConstants.INVALID_MEMBER_NUMBER);
							}else {
								order.setUser(selectedItem);
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
