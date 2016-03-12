package sg.edu.nus.iss.ssa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Amarjeet B Singh
 * @param <T>
 *
 */
public class Order {
	
	private List<LineItem> items = new ArrayList<LineItem>();
	
	private double totalPrice;
	
	private String memberId;
	
	private long poinitsRedeemed = 0;
	
	private long avlLoyaltyPoints;
	
	private long applicableDiscountPerc = 10l;
	
	private double applicableDiscountAmount;
	
	private double finalPrice;
	
	private double amountTendered;
	
	private double returnAmount;
	
	public Order() {

	}

	public Order(List<LineItem> items, double finalPrice,
			String memberNumber, long applicableDiscountPerc,
			double applicableDiscountAmount, double amountTendered,
			double returnAmount) {
		super();
		this.items = items;
		this.finalPrice = finalPrice;
		this.memberId = memberNumber;
		this.applicableDiscountPerc = applicableDiscountPerc;
		this.applicableDiscountAmount = applicableDiscountAmount;
		this.amountTendered = amountTendered;
		this.returnAmount = returnAmount;
	}

	public double getFinalPrice() {
		return this.totalPrice - this.getApplicableDiscountAmount();
	}
	
	public double getApplicableDiscountAmount() {
		if(this.getApplicableDiscountPerc()!= 0){
			return (this.totalPrice*this.getApplicableDiscountPerc()/100);
		}
		return 0d;
	}
	
	public long getAvlLoyaltyPoints() {
	//	return (this.getAvlLoyaltyPoints() - this.getPoinitsRedeemed());
		return 0l;
	}
	
	public double getReturnAmount() {
		return (this.getAmountTendered() - this.getFinalPrice() ) ;
	}
	
	public List<LineItem> getItems() {
		return items;
	}

	public void setItems(List<LineItem> items) {
		this.items = items;
	}

	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberNumber) {
		this.memberId = memberNumber;
	}
	
	

	public long getPoinitsRedeemed() {
		return poinitsRedeemed;
	}

	public void setPoinitsRedeemed(long poinitsRedeemed) {
		this.poinitsRedeemed = poinitsRedeemed;
	}

	public long getApplicableDiscountPerc() {
		return applicableDiscountPerc;
	}

	public void setApplicableDiscountPerc(long applicableDiscountPerc) {
		this.applicableDiscountPerc = applicableDiscountPerc;
	}

	

	public void setApplicableDiscountAmount(double applicableDiscountAmount) {
		this.applicableDiscountAmount = applicableDiscountAmount;
	}

	public double getAmountTendered() {
		return amountTendered;
	}

	public void setAmountTendered(double amountTendered) {
		this.amountTendered = amountTendered;
	}

	

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}


	public void setAvlLoyaltyPoints(long avlLoyaltyPoints) {
		this.avlLoyaltyPoints = avlLoyaltyPoints;
	}
	

}
