package sg.edu.nus.iss.ssa.model;

import sg.edu.nus.iss.ssa.util.DisplayUtil;

/**
 * @author Amarjeet B Singh
 *
 */
public class LineItem {
	
	private Product product;

	private long buyQuantity ;

	private double totalProductPrice;
	
	
	public LineItem(){
		
	}
	
		
	public LineItem(final Product product, final long buyQuantity ) {
		super();
		this.product = product;
		this.buyQuantity = buyQuantity;
		this.totalProductPrice = DisplayUtil.roundOffTwoDecimalPlaces(product.getPrice() * buyQuantity);
	}

	/**
	 * returns one dimensional array to display in table
	 * @return
	 */
    public String[] getItemsArray() {
    	String[] items = new String[5];
		items[0] = String.valueOf(this.product.getProductId());
    	items[1] = this.product.getProductName();
    	items[2] = String.valueOf(this.product.getPrice());
    	items[3] = String.valueOf(buyQuantity);
    	items[4] = String.valueOf(totalProductPrice);
    	return items;
    	
    }


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(long buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public double getTotalProductPrice() {
		return totalProductPrice;
	}

	public void setTotalProductPrice(double totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null ){
			return false;
		}
		LineItem item = (LineItem)obj;
		if((this.product.getProductId().equalsIgnoreCase(item.getProduct().getProductId()))
				&& (this.buyQuantity == item.getBuyQuantity()) ){
			return true;
		}
		return false;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" Product Name : ").append(this.product.getProductName()).
				append(" product Quantity : ").append(buyQuantity).append(" price : ").append(this.product.getPrice()).append(" barCode : ").append(this.product.getBarCode()).append(" buy Quantity : ").
				append(buyQuantity).append(" total Price : " ).append(totalProductPrice);
		return sb.toString();
	}

}
