package sg.edu.nus.iss.ssa.bo;

import java.util.*;

import sg.edu.nus.iss.ssa.model.*;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class FileDataWrapper {

	public static final Map<String, Product> productMap = new TreeMap<String, Product>();
	public static final Map<String, Category> categoryMap = new HashMap<String, Category>();
	public static final Map<String, Member> memberMap = new HashMap<String, Member>();
	public static final List<Transaction> transactionList = new ArrayList<Transaction>();
	public static final Map<String, StoreKeeper> storeKeeperMap = new HashMap<String, StoreKeeper>();
	public static final List<? extends Discount> discounts = new ArrayList<PeriodDiscount>();
	public static Order receipt = new Order();
	public static final List<Vendor> vendorList = new ArrayList<Vendor>();
	public static final Map<String, List<Vendor>> vendorMap = new HashMap<String, List<Vendor>>();
}
