package sg.edu.nus.iss.ssa.model;

import sg.edu.nus.iss.ssa.constants.StoreConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Periodic Discount is Applicable for certain period only.
 *
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class PeriodDiscount extends GeneralDiscount {

	private String starDate ;
	private String discountPeriod ;


	public boolean checkIfDiscountAvailable(){
		if(this.discountPeriod.equalsIgnoreCase("ALWAYS")){
			return  true;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat(StoreConstants.DATE_FORMAT);
			try {
				int discountPeriodInt = Integer.parseInt(discountPeriod);
				Date startDt = sdf.parse(starDate);
				Calendar cal = GregorianCalendar.getInstance();
				Date  currentDate = cal.getTime();
				cal.setTime(startDt);
				cal.add(Calendar.DATE,discountPeriodInt-1);
				Date endDate = cal.getTime();
				return (startDt.compareTo(currentDate)<=0 && endDate.compareTo(currentDate) >=0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Discount Code : ").append(this.getDiscountCode()).append(" Description : ").
				append(this.getDiscountDesc()).append(" Start Date ").append(this.getStarDate()).
				append(" Period : ").append(this.getDiscountPeriod()).append(" Percentage : ").
				append(this.getDiscountPerc()).append(" ApplicableTo : ").append(this.getApplicableTo());
		return sb.toString();
	}
}
