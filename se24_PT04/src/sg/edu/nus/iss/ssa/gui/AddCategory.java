package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.Option;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.*;
import sg.edu.nus.iss.ssa.util.IOService;

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
import java.util.*;
import java.util.Locale.Category;

public class AddCategory extends JFrame
{

	private JPanel contentPane;
	private JTextField txtCatogeryID;
	private JTextArea txtaCategoryName;

	private String categoryID;
	private String categoryName;

	/**
	 * Launch the application.
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { AddCategory frame = new
	 * AddCategory(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	/**
	 * Create the frame.
	 */
	public AddCategory()
	{
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

		JLabel lblCategoryName = new JLabel("Category Name: ");
		lblCategoryName.setBounds(100, 163, 139, 14);
		contentPane.add(lblCategoryName);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (validateForm())
				{
					if (validateData())
					{
						addCategory();
					}
				}
			}
		});
		btnAdd.setBounds(114, 319, 89, 23);
		contentPane.add(btnAdd);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnClose.setBounds(317, 319, 89, 23);
		contentPane.add(btnClose);

		txtCatogeryID = new JTextField();
		txtCatogeryID.setDocument(new PlainDocument()
		{
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
			{
				if (getLength() + str.length() <= StoreConstants.CATEGORY_ID_MAX_LENGTH)
					super.insertString(offs, str, a);
			}
		});
		txtCatogeryID.setBounds(274, 80, 177, 20);
		contentPane.add(txtCatogeryID);

		txtaCategoryName = new JTextArea();
		txtaCategoryName.setLineWrap(true);
		txtaCategoryName.setBounds(274, 158, 177, 109);
		contentPane.add(txtaCategoryName);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(274, 158, 177, 109);
		scrollPane.setViewportView(txtaCategoryName);
		contentPane.add(scrollPane);

	}

	private Boolean validateForm()
	{
		categoryID = txtCatogeryID.getText();
		categoryName = txtaCategoryName.getText();

		if (categoryID == null || categoryID.isEmpty())
		{
			JOptionPane.showMessageDialog(contentPane, "Please enter category ID", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (categoryName == null || categoryName.isEmpty())
		{
			JOptionPane.showMessageDialog(contentPane, "Please enter category name", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private Boolean validateData()
	{
		Set<String> tempKeySet = FileDataWrapper.categoryMap.keySet();
		if (tempKeySet != null && tempKeySet.size() > 0)
		{
			for (String key : tempKeySet)
			{
				key = key.toUpperCase();
			}
			if (tempKeySet.contains(categoryID.toUpperCase()))
			{
				JOptionPane.showMessageDialog(contentPane, "Category ID: " + categoryID + " already exists", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private void addCategory()
	{
		// TODO: update filedatawrapper and call IOService to update cada file
		sg.edu.nus.iss.ssa.model.Category category = new sg.edu.nus.iss.ssa.model.Category();
		category.setCategoryId(categoryID);
		category.setCategoryName(categoryName);
		try
		{
			((HashMap) FileDataWrapper.categoryMap).put(categoryID, category);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(contentPane, "Error occured during creating new category", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		try
		{
			IOService<?> ioManager = new IOService<Entity>();
			ioManager.writeToFile(FileDataWrapper.categoryMap, new sg.edu.nus.iss.ssa.model.Category());
		}
		catch (Exception ex)

		{
			JOptionPane.showMessageDialog(contentPane, "Error occured during saving new category", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
