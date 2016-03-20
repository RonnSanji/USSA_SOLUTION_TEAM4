package sg.edu.nus.iss.ssa.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;

import sg.edu.nus.iss.ssa.bo.DiscountOfferCalculator;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.OrderValidator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *Creates screen to capture payment details.
 * @author Amarjeet B Singh
 *
 */
public class PaymentWindow extends JFrame {

	private JPanel contentPane;
	private JTextField memberNumberField;
	private JTextField totalPrice;
	private JTextField discountAvailed;
	private JTextField loyaltyPoints;
	private JTextField finalPrice;
	private JTextField pointsRedeemed;
	private JTextField cashRendered;
	private JButton btnPay;
	private JButton btnCancel;

	private Order order;
	DiscountOfferCalculator offerCalculator = null;
	OrderValidator orderValidator;

	/**
	 * Create the frame.
	 */
	public PaymentWindow() {
		offerCalculator = new DiscountOfferCalculator();
		orderValidator = new OrderValidator(offerCalculator);
		order = FileDataWrapper.receipt;
		//Logic to create discount offer ..
		offerCalculator.applyDiscount(order);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Payment Summary");
		setSize(600,500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Total price:");
		lblNewLabel.setBounds(75, 133, 103, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblMemberNumber = new JLabel("Member Number");
		lblMemberNumber.setBounds(28, 16, 166, 20);
		contentPane.add(lblMemberNumber);
		
		memberNumberField = new JTextField();
		memberNumberField.setEditable(false);
		memberNumberField.setBounds(230, 13, 267, 26);
		contentPane.add(memberNumberField);
		memberNumberField.setColumns(10);
		memberNumberField.setText(order.getMemberInfo() != null ? order.getMemberInfo().getMemberId() : "");

		JLabel lblLoyaltyPoints = new JLabel("Loyalty Points");
		lblLoyaltyPoints.setBounds(28, 52, 166, 20);
		contentPane.add(lblLoyaltyPoints);

		loyaltyPoints = new JTextField();
		loyaltyPoints.setEditable(false);
		loyaltyPoints.setColumns(10);
		loyaltyPoints.setBounds(230, 55, 267, 26);
		loyaltyPoints.setText(String.valueOf(order.getMemberInfo() != null ? order.getMemberInfo().getLoyaltyPoints() : ""));
		contentPane.add(loyaltyPoints);


		JSeparator separator = new JSeparator();
		separator.setBounds(28, 96, 509, 2);
		contentPane.add(separator);
		
		totalPrice = new JTextField();
		totalPrice.setEditable(false);
		totalPrice.setBounds(230, 130, 166, 26);
		contentPane.add(totalPrice);
		totalPrice.setColumns(10);
		totalPrice.setText(String.valueOf(FileDataWrapper.receipt.getTotalPrice()));
		
		JLabel lblApplicableDiscount = new JLabel("Applicable Discount:");
		lblApplicableDiscount.setBounds(28, 175, 159, 20);
		contentPane.add(lblApplicableDiscount);
		
		discountAvailed = new JTextField();
		discountAvailed.setEditable(false);
		discountAvailed.setColumns(10);
		discountAvailed.setBounds(230, 172, 166, 26);
		discountAvailed.setText(DisplayUtil.getDiscountText(order));
		contentPane.add(discountAvailed);
		

		JLabel lblFinalPrice = new JLabel("Final Price:");
		lblFinalPrice.setBounds(35, 217, 159, 20);
		contentPane.add(lblFinalPrice);
		
		finalPrice = new JTextField();
		finalPrice.setEditable(false);
		finalPrice.setColumns(10);
		finalPrice.setBounds(230, 214, 166, 26);
		finalPrice.setText(String.valueOf(FileDataWrapper.receipt.getFinalPrice()));
		contentPane.add(finalPrice);
		
		JLabel lblRedeemPoints = new JLabel("Redeem Points:");
		lblRedeemPoints.setBounds(35, 262, 159, 20);
		contentPane.add(lblRedeemPoints);
		
		pointsRedeemed = new JTextField();
		pointsRedeemed.setColumns(10);
		pointsRedeemed.setBounds(230, 256, 166, 26);
		contentPane.add(pointsRedeemed);
		
		JLabel lblCashRendered = new JLabel("Cash Rendered:");
		lblCashRendered.setBounds(28, 298, 159, 20);
		contentPane.add(lblCashRendered);
		
		cashRendered = new JTextField();
		cashRendered.setColumns(10);
		cashRendered.setBounds(230, 295, 166, 26);
		contentPane.add(cashRendered);
		
		btnPay = new JButton("Pay");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check Amount Rendered
				String pointsRedeemedStr = pointsRedeemed.getText();
				String renderedCashStr = cashRendered.getText();
				Long redeemedPoints = new Long(0);
				Double renderedCash = new Double(0);
				if (pointsRedeemedStr.equals("") && renderedCashStr.equals("")) {
					DisplayUtil.displayValidationError(contentPane, StoreConstants.REQ_PAYMENT_FIELDS);
				} else {
					try {
						if (!pointsRedeemedStr.equals("")) {
							redeemedPoints = Long.parseLong(pointsRedeemedStr);
						}
						if (!renderedCashStr.equals("")) {
							renderedCash = Double.parseDouble(renderedCashStr);
						}

						//validate Points
						String message = orderValidator.validateRedeemedPoints(redeemedPoints, renderedCash, order);
						if (message != null) {
							DisplayUtil.displayValidationError(contentPane, message);
						} else {
							message = orderValidator.checkAmountToProcessPayment(redeemedPoints, renderedCash, order);
							if (message != null) {
								DisplayUtil.displayValidationError(contentPane, message);
							} else {
								order.setAmountTendered(renderedCash);
								order.setPointsRedeemed(redeemedPoints);
								ReceiptSummary newWindow = new ReceiptSummary();
								newWindow.setVisible(true);
								dispose();
							}

						}
					} catch (Exception ne) {
						ne.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "Quantity must be numeric value", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnPay.setBounds(422, 399, 115, 29);
		contentPane.add(btnPay);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(289, 399, 115, 29);
		contentPane.add(btnCancel);
	}
}
