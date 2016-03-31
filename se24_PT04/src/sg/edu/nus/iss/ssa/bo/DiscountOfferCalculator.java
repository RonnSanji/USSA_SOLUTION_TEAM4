package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Discount;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Transaction;
import sg.edu.nus.iss.ssa.util.DisplayUtil;

import java.util.List;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class DiscountOfferCalculator {

	List<Transaction> transactions;
	List<? extends Discount> discounts;

	public DiscountOfferCalculator(final List<Transaction>  transactions, final List<? extends Discount> discounts){
		this.transactions = transactions;
		this.discounts = discounts;
	}

	/**
	 * Calculates cash equivalent for points, adds it to rendered cash and returns the total.
	 * @param renderedCash
	 * @param redeemedPoints
	 * @return
	 */
	public double getTotalCashIncludingPoints(final double renderedCash,  long redeemedPoints) {
		if(redeemedPoints < 0){
			redeemedPoints = 0;
		}
		double dollarEqPoints = getCashValueForPoints(redeemedPoints);
		return (renderedCash + dollarEqPoints);
	}

	/**
	 * Calculates cash equivalent for given points.
	 * @param redeemedPoints
	 * @return
	 */
	public double  	getCashValueForPoints(long redeemedPoints) {
		if(redeemedPoints < 0){
			redeemedPoints = 0;
		}
		double cashEqPoints = DisplayUtil.roundOffTwoDecimalPlaces(redeemedPoints/StoreConstants.CASH_EQ_POINTS);
		return cashEqPoints;
	}

	/**
	 * Calculates points equivalent for Cash.
	 * @return
	 */
	public Long calculatePointsEqCash(Order order) {
		long cashEqPoints = (long) (order.getFinalPrice()/StoreConstants.POINTS_FOR_CASH);
		return  cashEqPoints;
	}

	public void applyDiscount(Order order){
		float discountPerc = getMaximumDiscountOnOffer(order);
		Double totalPrice = order.getTotalPrice();
		double discountAmount = DisplayUtil.roundOffTwoDecimalPlaces(totalPrice*discountPerc/100);
		double finalPrice = totalPrice - discountAmount;
		order.setApplicableDiscountPerc(discountPerc);
		order.setApplicableDiscountAmount(discountAmount);
		order.setFinalPrice(finalPrice);
	}

	private float getMaximumDiscountOnOffer(Order order){
		//logic for applicable discount
		float memberTxnDiscount = 0f;
		float generalDiscount = 0f;
		String memberId = order.getMemberIdOfUser();
		if(!StoreConstants.PUBLIC_USER.equalsIgnoreCase(memberId)){
			if(isFirstTransactionForMember(memberId)){
				//get member discount for first transaction
				memberTxnDiscount = getDiscountForFirstTransaction();
			}else{
				//get member discount for subsequent transaction
				memberTxnDiscount = getDiscountForSubSequentTransaction();
			}
		}
		//get maximum public discount on Offer
		generalDiscount = getMaxGeneralDiscount();
		//get maximum Discount
		return Math.max(generalDiscount, memberTxnDiscount);
	}

	private float getMaxGeneralDiscount() {
		float maxGeneralDiscount = 0;
		for(Discount discount : discounts){
			PeriodDiscount periodDiscount = (PeriodDiscount)discount;
			if(periodDiscount.getApplicableTo().equalsIgnoreCase("A") &&
				periodDiscount.checkIfDiscountAvailable()){
				if(periodDiscount.getDiscountPerc() > maxGeneralDiscount){
					maxGeneralDiscount = periodDiscount.getDiscountPerc();
				}
			}
		}
		return maxGeneralDiscount;
	}

	protected float getDiscountForFirstTransaction() {
		for(Discount discount : discounts){
			PeriodDiscount periodDiscount = (PeriodDiscount)discount;
			if(periodDiscount.getApplicableTo().equalsIgnoreCase("M") &&
					periodDiscount.getDiscountCode().equalsIgnoreCase("MEMBER_FIRST")
					&& periodDiscount.checkIfDiscountAvailable()){
				return periodDiscount.getDiscountPerc();
			}
		}
		return 0;
	}

	protected float getDiscountForSubSequentTransaction() {
		for(Discount discount : discounts){
			PeriodDiscount periodDiscount = (PeriodDiscount)discount;
			if(periodDiscount.getApplicableTo().equalsIgnoreCase("M") &&
					periodDiscount.getDiscountCode().equalsIgnoreCase("MEMBER_SUBSEQ")){
				return periodDiscount.getDiscountPerc();
			}
		}
		return 0;
	}

	protected boolean isFirstTransactionForMember(String memberId){
		int count = 0;
		for(Transaction txn: transactions){
			if(txn.getMemberId().equalsIgnoreCase(memberId)){
				return false;
			}
		}
		return true;
	}
}
