package sg.edu.nus.iss.ssa.model;

import sg.edu.nus.iss.ssa.constants.StoreConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Amarjeet B Singh
 *
 */
public class Transaction extends Entity{
	
	protected String fileName = "transactions.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.Transaction";
	protected String[] properties = {"transactionId","productId","memberId","quantity","date"};
	protected String mapKey = "transactionId";


	private long transactionId;
	private String productId;
	private String memberId;
	private long quantity;
	private String date;

	public Transaction(){

	}

	public Transaction(final long transactionId, final String productId, final String memberId,
					   final long quantity){
		this.transactionId = transactionId;
		this.productId = productId;
		this.memberId = memberId;
		this.quantity = quantity;
		//Create String Representation of Date
		Calendar date = GregorianCalendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(StoreConstants.DATE_FORMAT);
		this.date = dateFormat.format(date.getTime());
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String[] getProperties() {
		return properties;
	}

	@Override
	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	@Override
	public String getMapKey() {
		return mapKey;
	}

	@Override
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("::transactionID ").append(transactionId).append(" product Id: ").
				append(this.productId).append(" memberId : ").append(this.memberId).
				append(" Quantity : ").append(this.quantity).append(" Date : ").append(this.date);
		return sb.toString();
	}


}
