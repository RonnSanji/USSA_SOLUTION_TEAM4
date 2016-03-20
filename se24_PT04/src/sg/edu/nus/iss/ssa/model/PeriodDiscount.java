package sg.edu.nus.iss.ssa.model;

import java.util.*;

/**
 * Periodic Discount is Applicable for certain period only.
 *
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class PeriodDiscount extends GeneralDiscount {

	private String starDate ;
	private String discountPeriod ;

	/*public PeriodDiscount(Calendar startDate, int duration) {
		this.startDate = startDate;
		this.duration = duration;
	}

	public Calendar getDiscountEndDate() {
		if (startDate == null) {
			return null;
		}
		if (duration <= 0) {
			return null;
		}
		startDate.add(Calendar.DATE, duration - 1);
		return startDate;
	}*/

	public String getStarDate() {
		return starDate;
	}

	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}

	public String getDiscountPeriod() {
		return discountPeriod;
	}

	public void setDiscountPeriod(String discountPeriod) {
		this.discountPeriod = discountPeriod;
	}
}
