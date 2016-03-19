package sg.edu.nus.iss.ssa.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.model.Transaction;

/**
 * 
 * @author Amarjeet B Singh
 *
 */
public class FileDataWrapper {

	public static final Map<Long, Product> productMap = new HashMap<Long, Product>();
	public static final Map<String, Category> categoryMap = new HashMap<String, Category>();
	public static final Map<String, Member> memberMap = new HashMap<String, Member>();
	public static final List<Transaction> transactionList = new ArrayList<Transaction>();
	public static final Map<String, ?> storeKeeperMap = new HashMap<String, StoreKeeper>();
	public static final Order receipt = new Order();

}
