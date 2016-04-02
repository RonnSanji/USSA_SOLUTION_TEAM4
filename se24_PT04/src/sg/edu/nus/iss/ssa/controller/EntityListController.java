package sg.edu.nus.iss.ssa.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.IOService;

/**
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class EntityListController {

	private IOService<?> ioManager;

	// For add category
	public String addCategory(String categoryID, String categoryName) {
		sg.edu.nus.iss.ssa.model.Category category = new sg.edu.nus.iss.ssa.model.Category();
		category.setCategoryId(categoryID);
		category.setCategoryName(categoryName);
		try {
			FileDataWrapper.categoryMap.put(categoryID, category);
		} catch (Exception ex) {
			reloadCategoryData();
			ex.printStackTrace();
			return StoreConstants.ERROR + " creating new category";
		}
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.categoryMap.values(), new sg.edu.nus.iss.ssa.model.Category());
		} catch (Exception ex) {
			reloadCategoryData();
			ex.printStackTrace();
			return StoreConstants.ERROR + " saving new category";
		} finally {
			ioManager = null;
		}
		return null;

	}

	// For remove category
	public String RemoveCategory(String categoryID) {
		if (categoryID == null || categoryID.isEmpty()) {
			return StoreConstants.SELECT_CATEGORY;
		}

		try {
			FileDataWrapper.categoryMap.remove(categoryID);
		} catch (Exception ex) {
			reloadCategoryData();
			ex.printStackTrace();
			return StoreConstants.ERROR + " removing category " + categoryID;
		}
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.categoryMap.values(), new sg.edu.nus.iss.ssa.model.Category());
		} catch (Exception ex) {
			reloadCategoryData();
			ex.printStackTrace();
			return StoreConstants.ERROR + " removing category " + categoryID;
		} finally {
			ioManager = null;
		}
		return null;

	}

	// for manage category
	public void reloadCategoryData() {
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			//System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}
	}

	// For replenish stock
	public String addStock(Product selectedProduct, long stockAdd) {
		selectedProduct.setQuantity(selectedProduct.getQuantity() + stockAdd);

		for (Product p : FileDataWrapper.productMap.values()) {
			int barcode = p.getBarCode();
			if (barcode == selectedProduct.getBarCode()) {
				p = selectedProduct;
				break;
			}
		}
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.productMap.values(), new sg.edu.nus.iss.ssa.model.Product());
		} catch (Exception ex) {
			reloadProductData();
			ex.printStackTrace();
			return StoreConstants.ERROR + " adding stock";
		} finally {
			ioManager = null;
		}
		return null;
	}
	// For configure threshold
		public String updateThreshold(Product selectedProduct, long newThreshold) {
			selectedProduct.setThresholdQuantity(newThreshold);

			for (Product p : FileDataWrapper.productMap.values()) {
				int barcode = p.getBarCode();
				if (barcode == selectedProduct.getBarCode()) {
					p = selectedProduct;
					break;
				}
			}
			if (ioManager == null) {
				ioManager = new IOService<>();
			}
			try {
				ioManager.writeToFile(FileDataWrapper.productMap.values(), new sg.edu.nus.iss.ssa.model.Product());
			} catch (Exception ex) {
				reloadProductData();
				ex.printStackTrace();
				return StoreConstants.ERROR + " updating threshold";
			} finally {
				ioManager = null;
			}
			return null;
		}
	// for manage stock
	public void reloadProductData() {
		FileDataWrapper.productMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			//System.out.println("products : " + FileDataWrapper.productMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}
	}
	// For manage discount
		public String saveDiscount(PeriodDiscount discount) {
			for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
				PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
				if (tempDiscount.getDiscountCode().equalsIgnoreCase(discount.getDiscountCode())) {
					tempDiscount.setDiscountCode(discount.getDiscountCode());
					tempDiscount.setDiscountDesc(discount.getDiscountDesc());
					tempDiscount.setDiscountPerc(discount.getDiscountPerc());
					tempDiscount.setDiscountPeriod(discount.getDiscountPeriod());
					tempDiscount.setStarDate(discount.getStarDate());
					tempDiscount.setApplicableTo(discount.getApplicableTo());
				}
			}
			if (ioManager == null) {
				ioManager = new IOService<>();
			}
			try {
				ioManager.writeToFile(FileDataWrapper.discounts, new PeriodDiscount());
			} catch (Exception ex) {
				reloadDiscountData();
				ex.printStackTrace();
				return StoreConstants.ERROR + " saving Discount";
			} finally {
				ioManager = null;
			}
			return null;
		}
		
	// for manage discount
	public void reloadDiscountData() {
		FileDataWrapper.discounts.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(null, FileDataWrapper.discounts, new PeriodDiscount());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}
	}

	// For add product
	public String addProduct(Product product) {
		try {
			FileDataWrapper.productMap.put(product.getBarCode(), product);
		} catch (Exception ex) {
			return StoreConstants.ERROR + " creating new product";
		}
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.productMap.values(), new sg.edu.nus.iss.ssa.model.Product());
		} catch (Exception ex) {
			return StoreConstants.ERROR + " saving new Product Information";
		} finally {
			ioManager = null;
		}
		return null;
	}

	

}
