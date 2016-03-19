package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Order;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class DiscountOfferCalculator {

	Order order = FileDataWrapper.receipt;

	public double getTotalCashIncludingPoints(double renderedCash, long redeemedPoints) {
		double dollarEqPoints = getCashValueForPoints(redeemedPoints);
		return (renderedCash + dollarEqPoints);
	}
	
	public double getCashValueForPoints(long redeemedPoints) {
		double cashEqPoints = redeemedPoints/StoreConstants.CASH_EQ_POINTS;
		return  cashEqPoints;
	}
	
	public  String getDollarEqOfPointsText() {
		StringBuilder sb = new StringBuilder();
		long pointsRedeemed = FileDataWrapper.receipt.getPointsRedeemed();
		sb.append(pointsRedeemed).append(" (" ).append(getCashValueForPoints(pointsRedeemed)).append(")");
		return sb.toString();
	}


	public static String getDiscountText() {
		StringBuilder sb = new StringBuilder();
		long discountPer = FileDataWrapper.receipt.getApplicableDiscountPerc();
		double discountAmnt = FileDataWrapper.receipt.getApplicableDiscountAmount();
		sb.append(discountAmnt).append(" (" ).append(discountPer).append(" %)");
		return sb.toString();
	}

	public static void applyDiscount(Order order){

	}


}
