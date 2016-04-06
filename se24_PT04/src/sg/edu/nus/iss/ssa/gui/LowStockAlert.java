package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class LowStockAlert extends JFrame {

	private static final long serialVersionUID = 5964739831257277828L;
	private JPanel contentPane;
	ManageInventory manageInventory = new ManageInventory();
	private boolean hasBelowThreshold;
	private JTable tbResult;
	private JScrollPane scrollPane;


	public LowStockAlert() {
		setResizable(false);
		setTitle("Low Stock Alert !");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 544);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(320, 433, 150, 60);
		contentPane.add(btnClose);
	}

	public boolean hasBelowThreshold() {
		hasBelowThreshold = manageInventory.hasBelowThreshold();
		if (hasBelowThreshold == false) {
			if (manageInventory != null) {
				manageInventory = null;
			}
		}
		return hasBelowThreshold;
	}

	private void showBelowThreshold() {
		tbResult = manageInventory.getResulTable();
		scrollPane = new JScrollPane(tbResult);
		scrollPane.setBounds(10, 10, 780, 420);
		contentPane.add(scrollPane);
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b == true) {
			showBelowThreshold();
			if (manageInventory != null) {
				manageInventory = null;
			}
		}
	}
}
