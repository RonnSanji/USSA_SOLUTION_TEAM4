package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Order;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class DiscountOfferCalculator {

	/**
	 * Calculates cash equivalent for points, adds it to rendered cash and returns the total.
	 * @param renderedCash
	 * @param redeemedPoints
	 * @return
	 */
	public double getTotalCashIncludingPoints(double renderedCash, long redeemedPoints) {
		double dollarEqPoints = getCashValueForPoints(redeemedPoints);
		return (renderedCash + dollarEqPoints);
	}

	/**
	 * Calculates cash equivalent for given points.
	 * @param redeemedPoints
	 * @return
	 */
	public double getCashValueForPoints(long redeemedPoints) {
		double cashEqPoints = redeemedPoints/StoreConstants.CASH_EQ_POINTS;
		return  cashEqPoints;
	}

	/**
	 * Calculates points equivalent for Cash.
	 * @return
	 */
	public Long calculatePointsEqCash(Order order) {
		long cashEqPoints = (long) (order.getFinalPrice()/StoreConstants.CASH_EQ_POINTS);
		return  cashEqPoints;
	}

	public void applyDiscount(Order order){
		//logic for applicable discount
		float discountPerc =  10f;

		Double totalPrice = order.getTotalPrice();
		double discountAmount = totalPrice*discountPerc/100;
		double finalPrice = totalPrice - discountAmount;
		order.setApplicableDiscountPerc(discountPerc);
		order.setApplicableDiscountAmount(discountAmount);
		order.setFinalPrice(finalPrice);
	}

}
