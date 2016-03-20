package sg.edu.nus.iss.ssa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Amarjeet B Singh
 *
 */
public class Order {
	
	private List<LineItem> items = new ArrayList<LineItem>();
	
	private Double totalPrice = new Double(0);
	
	private User user;
	
	private Long pointsRedeemed = new Long(0);

	private Long pointsEarned = new Long(0);

	private Float applicableDiscountPerc = new Float(1);
	
	private Double applicableDiscountAmount = new Double(0);
	
	private Double finalPrice = new Double(0);
	
	private Double amountTendered = new Double(0);
	
	private Double returnAmount = new Double(0);
	
	public Order() {

	}

	/**
	 * Utility method to add line item.
	 * @param item
	 */
	public void addLineItem(LineItem item){
		this.items.add(item);
		this.totalPrice += item.getTotalProductPrice();
	}
	/**
	 * Utility method to add line item.
	 * @param item
	 */
	public void removeLineItem(LineItem item){
		this.items.remove(item);
		this.totalPrice -= item.getTotalProductPrice();
	}
	/**
	 * Utility method to get member Object. It will return null if user is not a valid member.
	 */
	public Member getMemberInfo(){
		if(user != null && user.isMember()){
			return (Member)user;
		}
		return  null;
	}

	/**
	 * It returns memberId if user is valid member otherwise it returns "PUBLIC"
	 * @return
	 */
	public String getMemberIdOfUser(){
		return getMemberInfo() != null ? getMemberInfo().getMemberId() : "PUBLIC";
	}


	public Order(List<LineItem> items, double finalPrice,
			User user, float applicableDiscountPerc,
			double applicableDiscountAmount, double amountTendered,
			double returnAmount) {
		super();
		this.items = items;
		this.finalPrice = finalPrice;
		this.user = user;
		this.applicableDiscountPerc = applicableDiscountPerc;
		this.applicableDiscountAmount = applicableDiscountAmount;
		this.amountTendered = amountTendered;
		this.returnAmount = returnAmount;
	}

	public List<LineItem> getItems() {
		return items;
	}

	public void setItems(List<LineItem> items) {
		this.items = items;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPointsRedeemed() {
		return pointsRedeemed;
	}

	public void setPointsRedeemed(Long pointsRedeemed) {
		this.pointsRedeemed = pointsRedeemed;
	}

	public Long getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(Long pointsEarned) {
		this.pointsEarned = pointsEarned;
	}


	public Float getApplicableDiscountPerc() {
		return applicableDiscountPerc;
	}

	public void setApplicableDiscountPerc(Float applicableDiscountPerc) {
		this.applicableDiscountPerc = applicableDiscountPerc;
	}

	public Double getApplicableDiscountAmount() {
		return applicableDiscountAmount;
	}

	public void setApplicableDiscountAmount(Double applicableDiscountAmount) {
		this.applicableDiscountAmount = applicableDiscountAmount;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Double getAmountTendered() {
		return amountTendered;
	}

	public void setAmountTendered(Double amountTendered) {
		this.amountTendered = amountTendered;
	}

	public Double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}
}
