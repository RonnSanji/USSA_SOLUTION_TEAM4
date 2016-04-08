package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JButton;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class MemberEditWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textMemberName;
	private JTextField textMemberNo;
	private JTextField textLpoint;
	private JButton btnEdit;
	private JButton btnCencel;

/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Member m = new Member();
					m.setMemberId("test id");
					m.setMemberName("test name");
					m.setLoyaltyPoints(-1);
					MemberEditWindow window = new MemberEditWindow(m,null);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public MemberEditWindow(Member memberTOEdit, MemberManagerWindow memberManagerWindow) {

		getContentPane().setLayout(null);
		setTitle("Edit Member");
		setResizable(false);
		setSize(500, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMemberName = new JLabel("Member Name:");
		lblMemberName.setBounds(63, 50, 120, 16);
		contentPanel.add(lblMemberName);

		textMemberName = new JTextField();
		textMemberName.setBounds(206, 44, 213, 28);
		contentPanel.add(textMemberName);
		textMemberName.setText(memberTOEdit.getMemberName());
		textMemberName.setColumns(10);

		JLabel lblMemberNo = new JLabel("Member Number:");
		lblMemberNo.setBounds(63, 106, 120, 16);
		contentPanel.add(lblMemberNo);

		textMemberNo = new JTextField();
		textMemberNo.setBounds(206, 100, 213, 28);
		contentPanel.add(textMemberNo);
		textMemberNo.setDocument(new PlainDocument() {

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				//if ((getLength() == StoreConstants.MEMBER_NUMBER_LENGTH  || getLength() + str.length() <= StoreConstants.MEMBER_NUMBER_LENGTH) && str.matches("[a-zA-Z0-9]{1,9}"))
					if ( getLength() + str.length() <= StoreConstants.MEMBER_NUMBER_LENGTH && str.matches("[a-zA-Z0-9]{1,9}"))
				{
					super.insertString(offs, str, a);
				}
			}
		});
		textMemberNo.setText(memberTOEdit.getMemberId());
		textMemberNo.setColumns(10);
		

		JLabel lblLPoints = new JLabel("Loylty Points:");
		lblLPoints.setBounds(64, 164, 106, 16);
		contentPanel.add(lblLPoints);

		textLpoint = new JTextField();
		textLpoint.setBounds(206, 158, 213, 28);
		textLpoint.setDocument(new PlainDocument() {

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("\\-*[0-9]*"))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(textLpoint);
		textLpoint.setText(String.valueOf(memberTOEdit.getLoyaltyPoints()));
		textLpoint.setColumns(10);

		btnEdit = new JButton("OK");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String newMemeberName = textMemberName.getText().trim();
				String newMemeberNumber = textMemberNo.getText().trim();
				long newLoyltyPoint = -1;
				
				try {
					 newLoyltyPoint = Long.parseLong(textLpoint.getText().trim());			        
			      } catch (NumberFormatException nfe) {
			       //  System.out.println("NumberFormatException: " + nfe.getMessage());
			      }

				
				if (EditValidation(newMemeberName,newMemeberNumber,newLoyltyPoint)) {
					memberTOEdit.setMemberName(newMemeberName);
					memberTOEdit.setMemberId(newMemeberNumber);
					memberTOEdit.setLoyaltyPoints(newLoyltyPoint);
					memberManagerWindow.updateEditedMember();
					//System.out.println(memberTOEdit);
					DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.MEMBER_UPDATED_SUCCESSFULLY);
					dispose();
				}
				
			}
		});
		btnEdit.setBounds(109, 211, 100, 50);
		contentPanel.add(btnEdit);

		btnCencel = new JButton("Cencel");
		btnCencel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCencel.setBounds(274, 211, 100, 50);
		contentPanel.add(btnCencel);

	}

	// public MemberEditWindow(){}
	/**
	 * Initialize the contents of the frame.
	 */
	private boolean EditValidation(String newMemberName, String newMemeberNo, long newLoyltyPoint) {
		FormValidator validator = new FormValidator();
		String validateResult = validator.editMemeberValidateForm(newMemberName, newMemeberNo,newLoyltyPoint);
		if (validateResult != null) {
			JOptionPane.showMessageDialog(btnEdit, validateResult, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		else {
			return true;
		}

	}
}
