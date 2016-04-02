package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.gui.AddCategory.MyWindowListener;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class LowStockAlert extends JFrame {

	private JPanel contentPane;
	ManageInventory manageInventory = new ManageInventory();
	private boolean hasBelowThreshold;
	private JTable tbResult;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntityListController controller = new EntityListController();
					controller.reloadProductData();
					LowStockAlert frame = new LowStockAlert();
					if (frame.hasBelowThreshold()) {
						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
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
		btnClose.setBounds(298, 435, 150, 60);
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
