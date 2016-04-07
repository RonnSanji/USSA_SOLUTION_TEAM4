package sg.edu.nus.iss.ssa.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.model.LineItem;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Product;

public class Printer {
	public static void main(String[] args) {
		Printer printer = new Printer();
		String header = "Product ID";
		String value = "1234567890123456";
		System.out.println(header + " " + header.length() + " " + value + " " + value.length());
		String result = printer.fillString(header, value);
		System.out.println(result + " " + result.length());
	}

	private Order receiptOrder;
	private StringBuilder sb;
	private String linebreak = "\n";

	public void printReceipt(Order Order, Point location) {
		this.receiptOrder = Order;

		if (sb != null) {

			System.out.println(sb.toString());
			return;
		}
		if (receiptOrder != null) {

			sb = new StringBuilder();

			String header_product_code = "Product ID       ";
			String header_product_name = "Product Name               ";
			String header_price = "Price      ";
			String header_quantity = "Quantity    ";
			String header_total = "Total  ";

			sb.append(linebreak).append(linebreak).append(linebreak).append(linebreak).append(linebreak);

			sb.append("=======================  PT Team 4 Souvenir Store  =======================\n\n\n");

			// "Product Code Product Name Price Quantity Total \n"

			sb.append(header_product_code).append(header_product_name).append(header_price).append(header_quantity)
					.append(header_total).append(linebreak);
			for (LineItem item : receiptOrder.getItems()) {
				sb.append(fillString(header_product_code, item.getProduct().getProductId()));
				sb.append(fillString(header_product_name, item.getProduct().getProductName()));
				sb.append(fillString(header_price, "$" + String.valueOf(item.getProduct().getPrice())));
				sb.append(fillString(header_quantity, String.valueOf(item.getBuyQuantity())));
				sb.append(fillString(header_total, "$" + String.valueOf(item.getTotalProductPrice())));
				sb.append(linebreak);
			}

			sb.append(linebreak).append(linebreak).append(linebreak).append(linebreak).append(linebreak);

			sb.append("Total Price:                 ");
			sb.append("$").append(String.valueOf(receiptOrder.getTotalPrice()));
			sb.append(linebreak).append(linebreak);

			sb.append("Total Discount:              ");
			sb.append("$").append(String.valueOf(receiptOrder.getApplicableDiscountAmount()));
			sb.append(linebreak).append(linebreak);

			sb.append("Final Price:                 ");
			sb.append("$").append(String.valueOf(receiptOrder.getFinalPrice()));
			sb.append(linebreak).append(linebreak);

			sb.append("Points Redeemed:             ");
			sb.append(String.valueOf(receiptOrder.getPointsRedeemed()));
			sb.append(linebreak).append(linebreak);

			sb.append("Cash Tendered:               ");
			sb.append("$").append(String.valueOf(receiptOrder.getAmountTendered()));
			sb.append(linebreak).append(linebreak);

			sb.append("Amount To Return:            ");
			sb.append("$").append(String.valueOf(receiptOrder.getReturnAmount()));
			sb.append(linebreak).append(linebreak);

			sb.append("Remaining Loyaoty Points:    ");
			sb.append(String.valueOf(
					receiptOrder.getMemberInfo() != null ? receiptOrder.getMemberInfo().getLoyaltyPoints() : 0));
			sb.append(linebreak).append(linebreak);

			sb.append(linebreak).append(linebreak).append(linebreak).append(linebreak).append(linebreak);

			sb.append("Have a nice day !");
			sb.append(linebreak).append(linebreak).append(linebreak).append(linebreak).append(linebreak);

			System.out.println(sb.toString());
		}
	}

	public void printLabel(ArrayList<Product> productList, int copyCount) {
		if (productList == null) {
			return;
		}
		sb = new StringBuilder();
		sb.append(linebreak).append(linebreak).append(linebreak).append(linebreak).append(linebreak);
		sb.append("=======  PT Team 4 Souvenir Store  =======\n\n\n");
		//		  "Product Name                     Bar Code     \n"
		
		String header_product_name = "Product Name                     ";
		String header_bar_code ="Bar Code     ";
		
		sb.append(header_product_name);
		sb.append(header_bar_code);
		sb.append(linebreak);
		
		
		for (Product product : productList) {
			for (int i = 0; i < copyCount; i++) {
				sb.append(fillString(header_product_name, product.getProductName()));
				sb.append(fillString(header_bar_code, String.valueOf(product.getBarCode())));
				sb.append(linebreak);
			}
		}

		sb.append(linebreak).append(linebreak).append(linebreak).append(linebreak).append(linebreak);
		
		System.out.println(sb.toString());
		
	}

	private String fillString(String header, String value) {
		if (header == null || value == null) {
			return null;
		}
		if (header.length() == value.length()) {
			return value;
		}
		if (header.length() < value.length()) {
			value = value.substring(0, header.length());
		}
		if (header.length() > value.length()) {
			int velueAddLen = header.length() - value.length();
			for (int i = 0; i < velueAddLen; i++) {

				value += " ";
			}
		}
		return value;
	}

}
