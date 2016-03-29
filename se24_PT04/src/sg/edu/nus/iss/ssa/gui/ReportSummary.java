package sg.edu.nus.iss.ssa.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.FileNotFoundException;
import javax.swing.JLabel;

public class ReportSummary extends JPanel {

	// private JFrame frame;

	private DashBoard db;
	private CategoryReport cr;
	private ProductReport pr;
	private MemberReport mr;
	private TransactionReport tr;

	/**
	 * Create the application.
	 */
	public ReportSummary() {

		setSize(800, 600);
		setOpaque(false);
		setLayout(null);
		this.setBounds(6, 6, 854, 588);
		JButton btnCatetory = new JButton("Category Report");
		btnCatetory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cr = new CategoryReport();
				} catch (FieldMismatchExcepion e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCatetory.setFont(new Font("Zapfino", Font.PLAIN, 12));
		btnCatetory.setBounds(151, 189, 142, 42);

		JButton btnMember = new JButton("Member Report");
		btnMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mr = new MemberReport();
			}
		});
		btnMember.setFont(new Font("Zapfino", Font.PLAIN, 12));
		btnMember.setBounds(440, 189, 151, 42);

		JButton btnProduct = new JButton("Product Report");
		btnProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pr = new ProductReport();
				} catch (FieldMismatchExcepion e1) {
					e1.printStackTrace();
				}

			}
		});
		btnProduct.setFont(new Font("Zapfino", Font.PLAIN, 12));
		btnProduct.setBounds(151, 341, 142, 42);

		JButton btnTransaction = new JButton("Transaction Report");
		btnTransaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tr = new TransactionReport();
				} catch (FileNotFoundException | FieldMismatchExcepion e1) {
					e1.printStackTrace();
				}

			}
		});
		btnTransaction.setFont(new Font("Zapfino", Font.PLAIN, 12));
		btnTransaction.setBounds(440, 341, 151, 42);

		add(btnCatetory);
		add(btnMember);
		add(btnProduct);
		add(btnTransaction);
		
		JLabel lblReportSummary = new JLabel("Report Summary");
		lblReportSummary.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
		lblReportSummary.setBounds(283, 72, 205, 74);
		add(lblReportSummary);

	}
}
