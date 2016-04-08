package sg.edu.nus.iss.ssa.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.Vendor;
import sg.edu.nus.iss.ssa.util.IOService;

/**
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class EntityListController {

	private IOService<?> ioManager;

	// For manage category
	public String saveCategory(Category selectedCategory, int mode) {
		// add and edit
		if (mode == 0 || mode == 1) {
			try {
				FileDataWrapper.categoryMap.put(selectedCategory.getCategoryId(), selectedCategory);
			} catch (Exception ex) {
				reloadCategoryData();
				ex.printStackTrace();
				return StoreConstants.ERROR + " creating new category";
			}
		}
		// remove
		else if (mode == 2) {
			try {
				FileDataWrapper.categoryMap.remove(selectedCategory.getCategoryId());
			} catch (Exception ex) {
				reloadCategoryData();
				ex.printStackTrace();
				return StoreConstants.ERROR + " removing category " + selectedCategory.getCategoryId();
			}
		}
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.categoryMap.values(), new Category());
		} catch (Exception ex) {
			reloadDiscountData();
			ex.printStackTrace();
			return StoreConstants.ERROR + " saving Category";
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
			// System.out.println("categories : " +
			// FileDataWrapper.categoryMap.keySet());
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

	// for save vendor
	public String saveVendor(String categoryID, Vendor selectedVendor, int mode) {
		// add
		if (mode == 0) {
			((List<Vendor>) FileDataWrapper.vendorList).add(selectedVendor);
		}
		// edit
		else if (mode == 1) {
			for (int i = 0; i < FileDataWrapper.vendorList.size(); i++) {
				Vendor tempVendor = (Vendor) FileDataWrapper.vendorList.get(i);
				if (tempVendor.getVendorId().equalsIgnoreCase(selectedVendor.getVendorId())) {
					tempVendor.setVendorId(selectedVendor.getVendorId());
					tempVendor.setVendorName(selectedVendor.getVendorName());
				}
			}
		}
		// remove
		else if (mode == 2) {
			((List<Vendor>) FileDataWrapper.vendorList).remove(selectedVendor);
		}
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.vendorList, new Vendor(categoryID));
			refreshVendorMap(categoryID);
		} catch (Exception ex) {
			reloadVendorDataByCategoryID(categoryID);
			ex.printStackTrace();
			return StoreConstants.ERROR + " saving Vendor";
		} finally {
			ioManager = null;
		}
		return null;
	}

	public String adjustVendorListOrder(String categoryID) {
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.writeToFile(FileDataWrapper.vendorList, new Vendor(categoryID));
			refreshVendorMap(categoryID);
		} catch (Exception ex) {
			reloadVendorDataByCategoryID(categoryID);
			ex.printStackTrace();
			return StoreConstants.ERROR + " saving Vendor";
		} finally {
			ioManager = null;
		}
		return null;
	}

	// for retrieve vendor by category
	public void reloadVendorDataByCategoryID(String categoryID) {
		FileDataWrapper.vendorList.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(null, FileDataWrapper.vendorList, new sg.edu.nus.iss.ssa.model.Vendor(categoryID));
			refreshVendorMap(categoryID);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

	}

	private void refreshVendorMap(String categoryID) {

		if (FileDataWrapper.vendorList != null) {
			ArrayList<Vendor> vendors = new ArrayList<Vendor>();
			for (Vendor vendor : FileDataWrapper.vendorList) {
				vendors.add(vendor);
			}

			FileDataWrapper.vendorMap.put(categoryID, vendors);
		}
	}

	public void loadAllVendorMap() {
		ArrayList<String> uniqueCategoryCodeList = new ArrayList<String>();
		for (Category category : FileDataWrapper.categoryMap.values()) {
			if (!uniqueCategoryCodeList.contains(category.getCategoryId())) {
				uniqueCategoryCodeList.add(category.getCategoryId());
			}
		}
		for (String categoryCode : uniqueCategoryCodeList) {
			reloadVendorDataByCategoryID(categoryCode);
		}
	}

	// for retrieve vendors
	public ArrayList<Vendor> getVendorListByCategoryID(String categoryID) {
		if(categoryID ==null || categoryID.isEmpty())
		{
			return null;
		}
		if (FileDataWrapper.vendorMap == null || FileDataWrapper.vendorMap.values().size() == 0) {
			reloadVendorDataByCategoryID(categoryID);
		}
		// ArrayList<Vendor> vendorList = new ArrayList<Vendor>();
		Set<String> keys = FileDataWrapper.vendorMap.keySet();
		for (String key : keys) {
			if (key.equals(categoryID)) {
				return (ArrayList<Vendor>) FileDataWrapper.vendorMap.get(key);
			}
		}

		return null;
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
	
	// For write off stock
	public String writeOffStock(Product selectedProduct, long stockMinus) {
		selectedProduct.setQuantity(selectedProduct.getQuantity() - stockMinus);

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
			return StoreConstants.ERROR + " writing off stock";
		} finally {
			ioManager = null;
		}
		return null;
	}
	// For configure threshold
	public String updateThreshold_ReorderQuantity(Product selectedProduct, long newThreshold, long newReorderQuantity) {
		selectedProduct.setThresholdQuantity(newThreshold);
		selectedProduct.setOrderQuantity(newReorderQuantity);

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
			// System.out.println("products : " +
			// FileDataWrapper.productMap.keySet());
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
	public String saveDiscount(PeriodDiscount discount, int mode) {
		// add
		if (mode == 0) {
			((List<PeriodDiscount>) FileDataWrapper.discounts).add(discount);
		}
		// edit
		else if (mode == 1) {
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
		}
		// remove
		else if (mode == 2) {
			((List<PeriodDiscount>) FileDataWrapper.discounts).remove(discount);
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
			FileDataWrapper.productMap.put(String.valueOf(product.getBarCode()), product);
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
