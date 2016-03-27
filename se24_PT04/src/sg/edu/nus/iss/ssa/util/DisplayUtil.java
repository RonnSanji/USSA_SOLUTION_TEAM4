package sg.edu.nus.iss.ssa.util;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Amarjeet B Singh on 3/19/2016.
 */
public class DisplayUtil {

	public static void displayValidationError(JPanel panel, String message) {
		JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static int displayConfirmationMessage(JPanel panel, String message) {
		return JOptionPane.showConfirmDialog(panel, message, "Message", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayAcknowledgeMessage(JPanel panel, String message) {
		JOptionPane.showMessageDialog(panel, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
    /**
     * Returns String representation for points and equivalent cash
     * @return
     */
    public static String getDiscountText(Order order) {
        StringBuilder sb = new StringBuilder();
        double discountPer = order.getApplicableDiscountPerc();
        double discountAmnt = order.getApplicableDiscountAmount();
        sb.append(discountAmnt).append(" (" ).append(discountPer).append(" %)");
        return sb.toString();
    }

	public static double roundOffTwoDecimalPlaces(double number){
		return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
