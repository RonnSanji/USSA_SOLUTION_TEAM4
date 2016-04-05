package sg.edu.nus.iss.ssa.util;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.Vendor;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Amarjeet B Singh on 3/19/2016.
 */
public class DisplayUtil {

	public static void displayValidationError(JPanel panel, String message) {
		JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void displayValidationError(JComponent component, String message) {
		JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static int displayConfirmationMessage(JComponent component, String message) {
		return JOptionPane.showConfirmDialog(component, message, "Message", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayAcknowledgeMessage(JComponent component, String message) {
		JOptionPane.showMessageDialog(component, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Returns String representation for points and equivalent cash
	 * 
	 * @return
	 */
	public static String getDiscountText(Order order) {
		StringBuilder sb = new StringBuilder();
		double discountPer = order.getApplicableDiscountPerc();
		double discountAmnt = order.getApplicableDiscountAmount();
		sb.append(discountAmnt).append(" (").append(discountPer).append(" %)");
		return sb.toString();
	}

	public static double roundOffTwoDecimalPlaces(double number) {
		return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * by CH
	 * 
	 * @param comboBox
	 * @param str
	 * @return
	 */
	public static int findIndex(JComboBox comboBox, String str) {
		if (comboBox != null && comboBox.getItemCount() > 0) {
			for (int i = 0; i < comboBox.getItemCount(); i++) {
				if (comboBox.getItemAt(i).toString().equals(str)) {
					return i;
				}
			}
		}
		return 0;
	}

	/**
	 * by CH String parameter format yyyy-MM-DD. E.g. 1 Jan 2016, pass in
	 * "2016-1-1"
	 * 
	 * @return
	 */
	public static Date getDateByString(String str) {
		if (str == null | str.isEmpty()) {
			return null;
		}
		String[] s = str.split("-");
		if (s.length != 3) {
			return null;
		}
		int year = -1;
		try {
			year = Integer.parseInt(s[0]);
		} catch (Exception ex) {
			return null;
		}
		int month = -1;
		try {
			month = Integer.parseInt(s[1]) - 1;
		} catch (Exception ex) {
			return null;
		}
		int day = -1;
		try {
			day = Integer.parseInt(s[2]);
		} catch (Exception ex) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime();
	}

	/**
	 * by CH To adjust the order of an item in a arraylist
	 * 
	 * parameters: 1: arraylist. 2: selectedindex. 3: up or down. 1 for up, 2
	 * for down
	 * 
	 * One call only move once When there is no item before or after, no change
	 * is made
	 * 
	 * @return
	 */
	public static List<Vendor> adjustVendorListOrder(List<Vendor> vendorList, int selectedIndex, int upOrDown) {
		if (vendorList == null || vendorList.size() == 0) {
			return vendorList;
		}
		if (upOrDown != 1 && upOrDown != 2) {
			System.out.println("Unknow parameter: upOrDown. Accept only 1 or 2'");
			return vendorList;
		}
		// attmep to move the first item to previous position
		if (upOrDown == 1 && selectedIndex == 0) {
			return vendorList;
		}
		// attemp to move the last item to next position
		if (upOrDown == 2 && selectedIndex == (vendorList.size() - 1)) {
			return vendorList;
		}
		Vendor selectedVendor = vendorList.get(selectedIndex);
		// up
		if (upOrDown == 1) {
			Vendor previousVendor = vendorList.get(selectedIndex - 1);
			vendorList.set(selectedIndex - 1, selectedVendor);
			vendorList.set(selectedIndex, previousVendor);
		}
		// down
		else if (upOrDown == 2) {
			Vendor NextVendor = vendorList.get(selectedIndex + 1);
			vendorList.set(selectedIndex + 1, selectedVendor);
			vendorList.set(selectedIndex, NextVendor);
		}
		return vendorList;
	}
	
}