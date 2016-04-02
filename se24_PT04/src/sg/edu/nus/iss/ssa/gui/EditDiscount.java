package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.gui.AddCategory.MyWindowListener;
import sg.edu.nus.iss.ssa.model.Discount;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditDiscount extends JDialog {
	private static final long serialVersionUID = -4958137028644403864L;
	private EntityListController controller = new EntityListController();

	private final JPanel contentPanel = new JPanel();
	public PeriodDiscount selectedDiscount;
	private JTextField txtDiscountCode;
	private JTextField txtPeriod;
	private JTextField txtPercentage;
	private JButton okButton;
	private JButton cancelButton;
	private JScrollPane scrollPane;
	private JTextArea txtDiscountDescription;
	private JComboBox<String> comboApplicableTo;
	private JComboBox<String> comboPeriodType;
	private DateSelector dateSelector;

	// 0 for add, 1 for edit
	private int mode = 0;

	public EditDiscount(PeriodDiscount selectedDiscount) {
		setResizable(false);
		this.selectedDiscount = selectedDiscount;
		this.addWindowListener(new MyWindowListener());

		setBounds(100, 100, 652, 447);

		contentPanel.setBounds(0, 0, 445, 339);

		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (validateForm() && validateData()) {
					saveDiscount();
				}
			}
		});
		okButton.setBounds(197, 368, 75, 23);
		contentPanel.add(okButton);
		okButton.setActionCommand("OK");

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setBounds(371, 368, 75, 23);
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
		lblPeriod.setBounds(49, 213, 127, 14);
		contentPanel.add(lblPeriod);

		JLabel lblPercentage = new JLabel("Percentage: ");
		lblPercentage.setBounds(49, 253, 125, 14);
		contentPanel.add(lblPercentage);

		JLabel lblApplicableTo = new JLabel("Applicable To: ");
		lblApplicableTo.setBounds(49, 296, 127, 14);
		contentPanel.add(lblApplicableTo);

		txtDiscountCode = new JTextField();
		txtDiscountCode.setBounds(186, 27, 200, 20);
		contentPanel.add(txtDiscountCode);
		txtDiscountCode.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 5790057198546248513L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				super.insertString(offs, str.toUpperCase(), a);
			}
		});
		txtDiscountCode.setColumns(10);

		txtDiscountDescription = new JTextArea();
		txtDiscountDescription.setAutoscrolls(true);
		txtDiscountDescription.setLineWrap(true);
		contentPanel.add(txtDiscountDescription);

		scrollPane = new JScrollPane(txtDiscountDescription);
		scrollPane.setBounds(186, 67, 400, 90);
		txtDiscountDescription.setBounds(scrollPane.getBounds());
		contentPanel.add(scrollPane);

		dateSelector = new DateSelector();
		dateSelector.setLocation(260, 167);
		contentPanel.add(dateSelector);

		txtPeriod = new JTextField();
		txtPeriod.setBounds(186, 213, 100, 20);
		txtPeriod.setColumns(10);
		txtPeriod.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 254862027426058887L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("\\d*") || str.matches(StoreConstants.PERMANENT_DSCOUNT_START_PERIOD))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(txtPeriod);

		txtPercentage = new JTextField();
		txtPercentage.setBounds(186, 253, 100, 20);

		txtPercentage.setColumns(10);
		txtPercentage.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 254862027426058887L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("(?:\\d*\\.)?\\d+") || str.matches("\\."))
					super.insertString(offs, str, a);
			}
		});
		contentPanel.add(txtPercentage);

		comboApplicableTo = new JComboBox<String>();
		comboApplicableTo.setModel(new DefaultComboBoxModel<String>(
				new String[] { "-Select-", StoreConstants.MEMBER_DICSOUNT_NAME, StoreConstants.PUBLIC_DICSOUNT_NAME }));
		comboApplicableTo.setBounds(186, 296, 80, 20);
		contentPanel.add(comboApplicableTo);

		comboPeriodType = new JComboBox<String>();
		comboPeriodType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchPeriodType();
			}

		});
		comboPeriodType.setModel(new DefaultComboBoxModel<String>(new String[] { "-Select-", "Period", "Always" }));
		comboPeriodType.setBounds(186, 167, 70, 20);
		contentPanel.add(comboPeriodType);

		bindData();
	}

	private void switchPeriodType() {
		// period
		if (comboPeriodType.getSelectedIndex() == 1) {
			dateSelector.setDate(null);
			dateSelector.setEnabled(true);
			txtPeriod.setText("");
			txtPeriod.setEditable(true);
		}
		// always
		else if (comboPeriodType.getSelectedIndex() == 2) {
			dateSelector.setDate(null);
			txtPeriod.setText(StoreConstants.PERMANENT_DSCOUNT_START_PERIOD);
			txtPeriod.setEditable(false);
			dateSelector.setEnabled(false);

		}
	}

	private void bindData() {
		// add
		if (selectedDiscount == null) {
			mode = 0;
			this.setTitle("Add Discount");
			clearFields();

		}
		// edit
		else {
			mode = 1;
			this.setTitle("Edit Discount");
			txtDiscountCode.setText(selectedDiscount.getDiscountCode());
			txtDiscountCode.setEditable(false);
			txtDiscountDescription.setText(selectedDiscount.getDiscountDesc());

			if (selectedDiscount.getStarDate().equalsIgnoreCase(StoreConstants.PERMANENT_DSCOUNT_START_DATE)) {
				comboPeriodType.setSelectedIndex(2);
			} else {
				comboPeriodType.setSelectedIndex(1);
				Date date = DisplayUtil.getDateByString(selectedDiscount.getStarDate());
				dateSelector.setDate(date);
				txtPeriod.setText(selectedDiscount.getDiscountPeriod());
			}

			txtPercentage.setText(String.valueOf(selectedDiscount.getDiscountPerc()));
			comboApplicableTo
					.setSelectedIndex(DisplayUtil.findIndex(comboApplicableTo, selectedDiscount.getApplicableToName()));

			// fixed member first purchase discounts and subsequent discounts.
			// Period and type can't change
			// applicable to can't change
			if (selectedDiscount.getDiscountCode().equalsIgnoreCase(StoreConstants.MEMBER_FIRST_DISCOUNT_CODE)
					|| selectedDiscount.getDiscountCode().equalsIgnoreCase(StoreConstants.MEMBER_SUBSEQ_CODE)) {
				comboPeriodType.setSelectedIndex(2);
				comboPeriodType.setEnabled(false);
				comboApplicableTo.setSelectedIndex(1);
				comboApplicableTo.setEnabled(false);
			}

		}
	}

	private boolean validateForm() {
		String msg = FormValidator.addEditDiscountValidateForm(txtDiscountCode.getText().trim(),
				txtDiscountDescription.getText().trim(), comboPeriodType.getSelectedItem().toString().trim(),
				dateSelector.getDate(), txtPeriod.getText().trim(), txtPercentage.getText().trim(),
				comboApplicableTo.getSelectedItem().toString().trim());
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return false;
		}
		return true;
	}

	private boolean validateData() {
		
		if (mode == 0) {
			selectedDiscount = new PeriodDiscount();
		}

		selectedDiscount.setDiscountCode(txtDiscountCode.getText().trim());
		selectedDiscount.setDiscountDesc(txtDiscountDescription.getText().trim());
		selectedDiscount.setDiscountPerc(Float.parseFloat(txtPercentage.getText().trim()));
		// member
		if (comboApplicableTo.getSelectedItem().toString().equalsIgnoreCase(StoreConstants.MEMBER_DICSOUNT_NAME)) {
			selectedDiscount.setApplicableTo(StoreConstants.MEMBER_DICSOUNT_CODE);
		}
		// all
		else if (comboApplicableTo.getSelectedItem().toString().equalsIgnoreCase(StoreConstants.PUBLIC_DICSOUNT_NAME)) {
			selectedDiscount.setApplicableTo(StoreConstants.PUBLIC_DICSOUNT_CODE);
		}
		// period
		if (comboPeriodType.getSelectedIndex() == 1) {
			selectedDiscount.setStarDate(dateSelector.getDateString());
		}
		// always
		else if (comboPeriodType.getSelectedIndex() == 2) {
			selectedDiscount.setStarDate(StoreConstants.PERMANENT_DSCOUNT_START_PERIOD);
		}
		selectedDiscount.setDiscountPeriod(txtPeriod.getText().trim());
		
		String msg = null;
		// add
		if (mode == 0) {
			msg = FormValidator.addDiscountValidateData(selectedDiscount);
		}
		// edit
		else if (mode == 1) {
			msg = FormValidator.editRemoveDiscountValidateData(selectedDiscount);
		}

		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return false;
		}
		return true;
	}

	private void saveDiscount() {
		
		String msg = controller.saveDiscount(selectedDiscount, mode);
		if (msg != null) {
			DisplayUtil.displayValidationError(contentPanel, msg);
			return;
		}
		// add
		if (mode == 0) {
			DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.DISCOUNT_ADDED_SUCCESSFULLY);
		}
		// edit
		else if (mode == 1) {
			DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.DISCOUNT_UPDATED_SUCCESSFULLY);
		}
		dispose();
	}

	private void clearFields() {
		txtDiscountCode.setText("");
		txtDiscountDescription.setText("");

		txtPeriod.setText("");
		txtPercentage.setText("");
		comboApplicableTo.setSelectedIndex(0);
	}

	class MyWindowListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			controller = null;
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
