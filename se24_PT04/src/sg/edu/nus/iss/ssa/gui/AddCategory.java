package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.Option;

import sg.edu.nus.iss.ssa.constants.StoreConstants;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.text.*;
import javax.swing.JScrollPane;


public class AddCategory extends JFrame {

	private JPanel contentPane;
	private JTextField txtCatogeryID;
	private JTextArea txtaCategoryDescription;

	private String categoryID;
	private String categoryDescription;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCategory frame = new AddCategory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddCategory() {
		setTitle("Add Category Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCategoryID = new JLabel("Category ID: ");
		lblCategoryID.setBounds(100, 83, 80, 14);
		contentPane.add(lblCategoryID);

		JLabel lblCategoryDescription = new JLabel("Category Description: ");
		lblCategoryDescription.setBounds(100, 163, 139, 14);
		contentPane.add(lblCategoryDescription);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (validateForm()) {
					addCategory();
				}
			}
		});
		btnAdd.setBounds(114, 319, 89, 23);
		contentPane.add(btnAdd);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(317, 319, 89, 23);
		contentPane.add(btnClose);

		txtCatogeryID = new JTextField();
		txtCatogeryID.setDocument(new PlainDocument(){
		    @Override
		    public void insertString(int offs, String str, AttributeSet a)
		            throws BadLocationException {
		        if(getLength() + str.length() <= StoreConstants.CATEGORY_ID_MAX_LENGTH)
		            super.insertString(offs, str, a);
		    }
		});
		txtCatogeryID.setBounds(274, 80, 177, 20);
		contentPane.add(txtCatogeryID);

		txtaCategoryDescription = new JTextArea();
		txtaCategoryDescription.setLineWrap(true);
		txtaCategoryDescription.setBounds(274, 158, 177, 109);
		contentPane.add(txtaCategoryDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(274, 158, 177, 109);
		scrollPane.setViewportView(txtaCategoryDescription);
		contentPane.add(scrollPane);
		
	}


	private Boolean validateForm() {
		categoryID = txtCatogeryID.getText();
		categoryDescription = txtaCategoryDescription.getText();

		if (categoryID == null || categoryID.isEmpty()) {
			JOptionPane.showMessageDialog(contentPane, "Please enter category ID", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (categoryDescription == null || categoryDescription.isEmpty()) {
			JOptionPane.showMessageDialog(contentPane, "Please enter category description", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void addCategory() {
		// TODO: update filedatawrapper and call IOService to update cada file

	}
}
