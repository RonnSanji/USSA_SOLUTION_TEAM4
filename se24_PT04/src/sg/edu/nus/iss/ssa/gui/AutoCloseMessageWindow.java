package sg.edu.nus.iss.ssa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AutoCloseMessageWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Calendar startTime;
	private Calendar currentTime;
	private final Timer timer;

	public AutoCloseMessageWindow(String title, String message, int lifeSpanInSeconds) {
		setTitle(title);
		setBounds(100, 100, 284, 91);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblMessage = new JLabel(message);
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblMessage, BorderLayout.CENTER);

		startTime = Calendar.getInstance();
		//System.out.println(startTime.getTime());
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTime = Calendar.getInstance();
				//System.out.println(currentTime.getTime());
				if (currentTime.getTimeInMillis() - startTime.getTimeInMillis() > lifeSpanInSeconds * 1000) {
					timer.stop();
					dispose();
				}

			}
		});
		timer.start();
	}

}
