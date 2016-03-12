package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class DiscountOfferCalculator {
	

	
	public static double convertPointsToCash(long points) {
		if(points == 0) {
			return 0d;
		}
		return points/StoreConstants.CASH_EQ_POINTS;	
	}
	
	/**
	 * Points can be redeemed in multiple of 100.
	 * @param redeemedPoints
	 * @return
	 */
	public static String validateRedeemedPoints(long redeemedPoints, double renderedCash){
		long totalAvlPoints = FileDataWrapper.receipt.getAvlLoyaltyPoints();
		double finalCost = FileDataWrapper.receipt.getFinalPrice();
		if(redeemedPoints < totalAvlPoints ){
			return "Maximum points can be redeemed is : "+ totalAvlPoints;
		}
		if(redeemedPoints% 100 !=0){
			return "Points can be redeemed in multiple of 100.";
		}
		/*if(finalCost < getDollarEqOfPointsAndCash(renderedCash,redeemedPoints)){
			return "Not Enough Cash to make Payment";
		}*/
		
		return null;
	}
	
	public static double getDollarEqOfPointsAndCash(double renderedCash, long redeemedPoints) {
		double dollarEqPoints = getDollarEqOfPoints(redeemedPoints);
		return renderedCash + dollarEqPoints;
	}
	
	public static double getDollarEqOfPoints(long redeemedPoints) {
		long totalAvlPoints = FileDataWrapper.receipt.getAvlLoyaltyPoints();
		double dollarEqPoints = totalAvlPoints/redeemedPoints;
		return  dollarEqPoints;
	}
	
	public static String getDollarEqOfPointsText() {
		StringBuilder sb = new StringBuilder();
		long pointsRedeemed = FileDataWrapper.receipt.getPoinitsRedeemed();
		sb.append(pointsRedeemed).append(" (" ).append(getDollarEqOfPoints(pointsRedeemed)).append(")");
		return sb.toString();
	}
	
	public static String getDiscountText() {
		StringBuilder sb = new StringBuilder();
		long discountPer = FileDataWrapper.receipt.getApplicableDiscountPerc();
		double discountAmnt = FileDataWrapper.receipt.getApplicableDiscountAmount();
		sb.append(discountAmnt).append(" (" ).append(discountPer).append(" %)");
		return sb.toString();
	}

}
