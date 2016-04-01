package sg.edu.nus.iss.ssa.model;

import sg.edu.nus.iss.ssa.constants.StoreConstants;

/**
 * General Discount is always Applicable Created by Amarjeet B Singh on
 * 3/20/2016.
 */
public abstract class GeneralDiscount extends Discount {

	private String discountCode;

	private String discountDesc;

	private float discountPerc;

	private String applicableTo;

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountDesc() {
		return discountDesc;
	}

	public void setDiscountDesc(String discountDesc) {
		this.discountDesc = discountDesc;
	}

	public float getDiscountPerc() {
		return discountPerc;
	}

	public void setDiscountPerc(float discountPerc) {
		this.discountPerc = discountPerc;
	}

	public String getApplicableTo() {
		return applicableTo;
	}

	public String getApplicableToName() {
		if (applicableTo == null || applicableTo.isEmpty()) {
			return "";
		}

		if (applicableTo.equalsIgnoreCase(StoreConstants.MEMBER_DICSOUNT_CODE)) {
			return StoreConstants.MEMBER_DICSOUNT_NAME;
		} else if (applicableTo.equalsIgnoreCase(StoreConstants.PUBLIC_DICSOUNT_CODE)) {
			return StoreConstants.PUBLIC_DICSOUNT_NAME;
		}

		return "";
	}

	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}
}
