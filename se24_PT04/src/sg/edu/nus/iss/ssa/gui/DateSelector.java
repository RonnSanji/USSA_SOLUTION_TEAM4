package sg.edu.nus.iss.ssa.gui;

import javax.swing.JPanel;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class DateSelector extends JPanel {

	private Date date;
	private Calendar calendar;
	private int currentYear;
	private int startYear;

	private int[] yearList;
	private int[] monthList;
	private int[] dayList;

	private int selectedYear = -1;
	private int selectedMonth = -1;
	private int selectedDay = -1;

	private JComboBox<String> comboBoxYear;
	private JComboBox<String> comboBoxMonth;
	private JComboBox<String> comboBoxDay;

	// test
	/*
	 * public static void main(String[] args) {
	 * 
	 * DateSelector dateSelector = new DateSelector(); //
	 * dateSelector.setBounds(10, 10, 250, 50); JFrame frame = new JFrame();
	 * frame.getContentPane().setLayout(null); frame.setSize(800, 600);
	 * frame.getContentPane().add(dateSelector); frame.setVisible(true);
	 * 
	 * JButton btn1 = new JButton(); btn1.setText("go"); btn1.setBounds(10, 100,
	 * 50, 50); frame.getContentPane().add(btn1);
	 * 
	 * btn1.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 * System.out.println(dateSelector.getDate()); } });
	 * 
	 * JTextField txtYear = new JTextField(); txtYear.setBounds(10, 200, 50,
	 * 50); frame.getContentPane().add(txtYear);
	 * 
	 * JTextField txtMonth = new JTextField(); txtMonth.setBounds(10, 260, 100,
	 * 50); frame.getContentPane().add(txtMonth);
	 * 
	 * JTextField txtDay = new JTextField(); txtDay.setBounds(10, 320, 100, 50);
	 * frame.getContentPane().add(txtDay);
	 * 
	 * JButton btn2 = new JButton(); btn2.setText("bind date");
	 * btn2.setBounds(10, 380, 100, 50); frame.getContentPane().add(btn2);
	 * 
	 * btn2.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { Calendar cal =
	 * Calendar.getInstance(); try {
	 * cal.set(Integer.parseInt(txtYear.getText()),
	 * Integer.parseInt(txtMonth.getText()) - 1,
	 * Integer.parseInt(txtDay.getText())); dateSelector.setDate(cal.getTime());
	 * } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * } }); }
	 */
	public DateSelector() {
		setLayout(null);
		setSize(330, 20);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
			}
		});
		JLabel lblYear = new JLabel("Year: ");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblYear.setBounds(0, 0, 32, 20);
		add(lblYear);

		comboBoxYear = new JComboBox<String>();
		comboBoxYear.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBoxYear.setBounds(lblYear.getX() + lblYear.getWidth() + 2, 0, 70, 20);
		comboBoxYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSelectedYear();
				getSelectedMonth();
				initiateDay();
			}
		});

		add(comboBoxYear);

		JLabel lblMonth = new JLabel("Month: ");
		lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMonth.setBounds(comboBoxYear.getX() + comboBoxYear.getWidth() + 2, 0, 42, 20);
		add(lblMonth);

		comboBoxMonth = new JComboBox<String>();
		comboBoxMonth.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBoxMonth.setBounds(lblMonth.getX() + lblMonth.getWidth() + 2, 0, 70, 20);
		comboBoxMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getSelectedYear();
				getSelectedMonth();
				initiateDay();
			}
		});
		add(comboBoxMonth);

		JLabel lblDay = new JLabel("Day: ");
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDay.setBounds(comboBoxMonth.getX() + comboBoxMonth.getWidth() + 2, 3, 32, 14);
		add(lblDay);

		comboBoxDay = new JComboBox<String>();
		comboBoxDay.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBoxDay.setBounds(lblDay.getX() + lblDay.getWidth() + 2, 0, 70, 20);
		add(comboBoxDay);

		initiate();

	}

	private void initiate() {
		calendar = Calendar.getInstance();
		currentYear = calendar.get(calendar.YEAR);
		yearList = new int[StoreConstants.PERIOD_BACKWARD_YEAR + StoreConstants.PERIOD_FORWARD_YEAR];
		startYear = currentYear - StoreConstants.PERIOD_BACKWARD_YEAR;

		comboBoxYear.removeAllItems();
		comboBoxYear.addItem("-Select-");
		for (int i = 0; i < yearList.length; i++) {
			comboBoxYear.addItem(String.valueOf(startYear + i));
			yearList[0] = startYear + i;
		}

		comboBoxMonth.removeAllItems();
		comboBoxMonth.addItem("-Select-");
		monthList = new int[12];
		for (int i = 0; i < monthList.length; i++) {
			comboBoxMonth.addItem(String.valueOf(i + 1));
			monthList[0] = i + 1;
		}

		comboBoxDay.removeAllItems();
		comboBoxDay.addItem("-Select-");
	}

	private void getSelectedYear() {
		try {
			selectedYear = Integer.parseInt(comboBoxYear.getSelectedItem().toString());
		} catch (Exception ex) {
			selectedYear = -1;
		}

	}

	private void getSelectedMonth() {
		try {
			selectedMonth = Integer.parseInt(comboBoxMonth.getSelectedItem().toString());
		} catch (Exception ex) {
			selectedMonth = -1;
		}
	}

	private void getSelectedDay() {
		try {
			selectedDay = Integer.parseInt(comboBoxDay.getSelectedItem().toString());
		} catch (Exception ex) {
			selectedDay = -1;
		}
	}

	private void initiateDay() {

		if (selectedYear == -1 || selectedMonth == -1) {
			return;
		}

		switch (selectedMonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			dayList = new int[31];
			break;
		case 2:
			if (selectedYear % 4 == 0) {
				dayList = new int[29];
			} else {
				dayList = new int[28];
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			dayList = new int[30];
			break;
		}

		comboBoxDay.removeAllItems();
		comboBoxDay.addItem("-Select-");
		for (int i = 0; i < dayList.length; i++) {
			comboBoxDay.addItem(String.valueOf(i + 1));
			dayList[0] = i + 1;
		}

	}

	public void setDate(Date date) {
		this.date = date;
		bindDate();
	}

	public Date getDate() {
		getSelectedYear();
		getSelectedMonth();
		getSelectedDay();

		if (selectedYear == -1 || selectedMonth == -1 || selectedDay == -1) {
			date = null;

		} else {
			try {
				calendar.set(selectedYear, selectedMonth - 1, selectedDay);
				date = calendar.getTime();
			} catch (Exception ex) {
				date = null;
			}
		}
		return date;
	}

	public String getDateString() {
		String str = null;
		date = getDate();
		if (date != null) {
			return new SimpleDateFormat(StoreConstants.DATE_FORMAT).format(date);
		}
		return str;
	}

	private void bindDate() {
		if (date == null) {
			comboBoxYear.setSelectedIndex(0);
			comboBoxMonth.setSelectedIndex(0);
			comboBoxDay.setSelectedIndex(0);
			return;
		} else {
			calendar.setTime(date);
			selectedYear = calendar.get(Calendar.YEAR);
			comboBoxYear.setSelectedIndex(DisplayUtil.findIndex(comboBoxYear, String.valueOf(selectedYear)));

			selectedMonth = calendar.get(Calendar.MONTH) + 1;
			comboBoxMonth.setSelectedIndex(DisplayUtil.findIndex(comboBoxMonth, String.valueOf(selectedMonth)));

			selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

			initiateDay();

			comboBoxDay.setSelectedIndex(DisplayUtil.findIndex(comboBoxDay, String.valueOf(selectedDay)));

		}
	}

	@Override
	public void setEnabled(boolean bool) {
		comboBoxYear.setEnabled(bool);
		comboBoxMonth.setEnabled(bool);
		comboBoxDay.setEnabled(bool);
	}

}
