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
public class PaymentWindow extends JPanel {

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
	public PaymentWindow(DashBoard dashBoard ) {
		offerCalculator = new DiscountOfferCalculator(FileDataWrapper.transactionList,FileDataWrapper.discounts);
		orderValidator = new OrderValidator(offerCalculator);
		order = FileDataWrapper.receipt;
		//Logic to create discount offer ..
		offerCalculator.applyDiscount(order);
		PaymentWindow payWin = this;
		
		setSize(800,600);

		this.setOpaque(false);
		setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Total price:");
		lblNewLabel.setBounds(39, 165, 103, 20);
		this.add(lblNewLabel);
		
		JLabel lblMemberNumber = new JLabel("Member Number:");
		lblMemberNumber.setBounds(28, 53, 166, 20);
		this.add(lblMemberNumber);
		
		memberNumberField = new JTextField();
		memberNumberField.setEditable(false);
		memberNumberField.setBounds(230, 50, 267, 26);
		this.add(memberNumberField);
		memberNumberField.setColumns(10);
		memberNumberField.setText(order.getMemberInfo() != null ? order.getMemberInfo().getMemberId() : "");

		JLabel lblLoyaltyPoints = new JLabel("Loyalty Points:");
		lblLoyaltyPoints.setBounds(39, 91, 166, 20);
		this.add(lblLoyaltyPoints);

		loyaltyPoints = new JTextField();
		loyaltyPoints.setEditable(false);
		loyaltyPoints.setColumns(10);
		loyaltyPoints.setBounds(230, 88, 267, 26);
		loyaltyPoints.setText(String.valueOf(order.getMemberInfo() != null ? order.getMemberInfo().getLoyaltyPoints() : ""));
		this.add(loyaltyPoints);


		JSeparator separator = new JSeparator();
		separator.setBounds(28, 124, 509, 2);
		this.add(separator);
		
		totalPrice = new JTextField();
		totalPrice.setEditable(false);
		totalPrice.setBounds(230, 151, 166, 26);
		this.add(totalPrice);
		totalPrice.setColumns(10);
		totalPrice.setText(String.valueOf(FileDataWrapper.receipt.getTotalPrice()));
		
		JLabel lblApplicableDiscount = new JLabel("Applicable Discount:");
		lblApplicableDiscount.setBounds(38, 198, 169, 20);
		this.add(lblApplicableDiscount);
		
		discountAvailed = new JTextField();
		discountAvailed.setEditable(false);
		discountAvailed.setColumns(10);
		discountAvailed.setBounds(230, 195, 166, 26);
		discountAvailed.setText(DisplayUtil.getDiscountText(order));
		this.add(discountAvailed);
		

		JLabel lblFinalPrice = new JLabel("Final Price:");
		lblFinalPrice.setBounds(38, 241, 159, 20);
		this.add(lblFinalPrice);
		
		finalPrice = new JTextField();
		finalPrice.setEditable(false);
		finalPrice.setColumns(10);
		finalPrice.setBounds(230, 238, 166, 26);
		finalPrice.setText(String.valueOf(FileDataWrapper.receipt.getFinalPrice()));
		this.add(finalPrice);
		
		JLabel lblRedeemPoints = new JLabel("Redeem Points:");
		lblRedeemPoints.setBounds(38, 282, 159, 20);
		this.add(lblRedeemPoints);
		
		pointsRedeemed = new JTextField();
		pointsRedeemed.setColumns(10);
		pointsRedeemed.setBounds(230, 279, 166, 26);
		this.add(pointsRedeemed);
		
		JLabel lblCashRendered = new JLabel("Cash Rendered:");
		lblCashRendered.setBounds(38, 325, 159, 20);
		this.add(lblCashRendered);
		
		cashRendered = new JTextField();
		cashRendered.setColumns(10);
		cashRendered.setBounds(230, 322, 166, 26);
		this.add(cashRendered);
		
		btnPay = new JButton("Pay");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check Amount Rendered
				String pointsRedeemedStr = pointsRedeemed.getText();
				String renderedCashStr = cashRendered.getText();
				Long redeemedPoints = new Long(0);
				Double renderedCash = new Double(0);
				if (pointsRedeemedStr.equals("") && renderedCashStr.equals("")) {
					DisplayUtil.displayValidationError(payWin, StoreConstants.REQ_PAYMENT_FIELDS);
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
							DisplayUtil.displayValidationError(payWin, message);
						} else {
							message = orderValidator.checkAmountToProcessPayment(redeemedPoints, renderedCash, order);
							if (message != null) {
								DisplayUtil.displayValidationError(payWin, message);
							} else {
								order.setAmountTendered(renderedCash);
								order.setPointsRedeemed(redeemedPoints);
								ReceiptSummary newWindow = new ReceiptSummary(dashBoard);
								dashBoard.activateMainPanel(newWindow);
							}

						}
					} catch (Exception ne) {
						ne.printStackTrace();
						JOptionPane.showMessageDialog(payWin, "Quantity must be numeric value", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnPay.setBounds(422, 399, 115, 29);
		this.add(btnPay);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dispose();
			}
		});
		btnCancel.setBounds(259, 399, 115, 29);
		this.add(btnCancel);
	}
}
