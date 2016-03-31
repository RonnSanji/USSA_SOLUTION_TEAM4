package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.gui.AddCategory.MyWindowListener;
import sg.edu.nus.iss.ssa.model.Discount;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class EditDiscount extends JDialog
{
	private static final long serialVersionUID = -4958137028644403864L;
	private EntityListController controller = new EntityListController();

	private final JPanel contentPanel = new JPanel();
	public Discount selectedDiscount;
	private JTextField txtDiscountCode;
	private JTextField txtPeriod;
	private JTextField txtPercentage;
	private JButton okButton;
	private JButton cancelButton;
	private JScrollPane scrollPane;
	private JTextArea txtDiscountDescription;
	private JComboBox<String> comboApplicableTo;
	private DateSelector dateSelector; 

	// 0 for add, 1 for edit
	private int mode = 0;

	public EditDiscount(Discount selectedDiscount)
	{
		setResizable(false);
		this.selectedDiscount = selectedDiscount;
		this.addWindowListener(new MyWindowListener());

		setBounds(100, 100, 449, 439);

		contentPanel.setBounds(0, 0, 445, 339);

		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		okButton = new JButton("OK");
		okButton.setBounds(129, 367, 75, 23);
		contentPanel.add(okButton);
		okButton.setActionCommand("OK");

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(250, 367, 75, 23);
		contentPanel.add(cancelButton);
		cancelButton.setActionCommand("Cancel");

		JLabel lblDiscountCode = new JLabel("Discount Code:");
		lblDiscountCode.setBounds(49, 30, 127, 14);
		contentPanel.add(lblDiscountCode);

		JLabel lblDiscountDescription = new JLabel("Discount Description:");
		lblDiscountDescription.setBounds(49, 67, 125, 14);
		contentPanel.add(lblDiscountDescription);

		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setBounds(49, 170, 125, 14);
		contentPanel.add(lblStartDate);

		JLabel lblPeriod = new JLabel("Period:");
		lblPeriod.setBounds(49, 210, 127, 14);
		contentPanel.add(lblPeriod);

		JLabel lblPercentage = new JLabel("Percentage: ");
		lblPercentage.setBounds(49, 246, 125, 14);
		contentPanel.add(lblPercentage);

		JLabel lblApplicableTo = new JLabel("Applicable To: ");
		lblApplicableTo.setBounds(49, 282, 127, 14);
		contentPanel.add(lblApplicableTo);

		txtDiscountCode = new JTextField();
		txtDiscountCode.setBounds(186, 27, 185, 20);
		contentPanel.add(txtDiscountCode);
		txtDiscountCode.setDocument(new PlainDocument()
		{
			private static final long serialVersionUID = 5790057198546248513L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
			{
				super.insertString(offs, str.toUpperCase(), a);
			}
		});
		txtDiscountCode.setColumns(10);

		
		txtDiscountDescription = new JTextArea();
		txtDiscountDescription.setAutoscrolls(true);
		txtDiscountDescription.setLineWrap(true);
		contentPanel.add(txtDiscountDescription);

		scrollPane = new JScrollPane(txtDiscountDescription);
		scrollPane.setBounds(186, 67, 185, 90);
		txtDiscountDescription.setBounds(scrollPane.getBounds());
		contentPanel.add(scrollPane);

		dateSelector = new DateSelector();
		dateSelector.setBounds(186, 170, 217, 29);
		contentPanel.add(dateSelector);

		txtPeriod = new JTextField();
		txtPeriod.setBounds(186, 210, 100, 20);
		txtPeriod.setColumns(10);
		txtPeriod.setDocument(new PlainDocument()
		{
			private static final long serialVersionUID = 254862027426058887L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
			{
				if (str.matches("[0-9]"))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(txtPeriod);

		txtPercentage = new JTextField();
		txtPercentage.setBounds(186, 246, 100, 20);
		
		txtPercentage.setColumns(10);
		txtPercentage.setDocument(new PlainDocument()
		{
			private static final long serialVersionUID = 254862027426058887L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
			{
				if (str.matches("[0-9]+(\\.[0-9][0-9]?)?"))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(txtPercentage);

		comboApplicableTo = new JComboBox<String>();
		comboApplicableTo.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "-Select-", "Member", "All" }));
		comboApplicableTo.setBounds(186, 279, 100, 20);
		contentPanel.add(comboApplicableTo);

		bindData();
	}

	private void bindData()
	{
		// add
		if (selectedDiscount == null)
		{
			mode = 0;
			this.setTitle("Add Discount");
			clearFields();

		}
		// edit
		else
		{
			mode = 1;
			this.setTitle("Edit Discount");
		}
	}

	private Boolean validateForm()
	{

		return false;
	}

	private String validateData()
	{
		return null;
	}

	private boolean editDiscount()
	{
		return false;
	}
 
	private void clearFields()
	{
		txtDiscountCode.setText("");
		txtDiscountDescription.setText("");
		
		txtPeriod.setText("");
		txtPercentage.setText("");
		comboApplicableTo.setSelectedIndex(0);
	}

	class MyWindowListener implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0)
		{
			// TODO Auto-generated method stub
			controller = null;
		}

		@Override
		public void windowClosing(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

	}
}
