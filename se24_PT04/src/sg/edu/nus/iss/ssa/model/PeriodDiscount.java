package sg.edu.nus.iss.ssa.model;

import java.util.*;

public class PeriodDiscount extends Discount {
	private Calendar startDate;
	private int duration;

	public PeriodDiscount(Calendar startDate, int duration) {
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
	}
	
	public Calculate
}
