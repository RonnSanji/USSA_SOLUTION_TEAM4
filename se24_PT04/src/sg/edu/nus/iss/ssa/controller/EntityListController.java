package sg.edu.nus.iss.ssa.controller;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;

/**
 * Created by Amarjeet B Singh on 3/20/2016.
 */
public class EntityListController {

	// For add category
	public String addCategory(String categoryID, String categoryName) {
		sg.edu.nus.iss.ssa.model.Category category = new sg.edu.nus.iss.ssa.model.Category();
		category.setCategoryId(categoryID);
		category.setCategoryName(categoryName);
		try {
			FileDataWrapper.categoryMap.put(categoryID, category);
		} catch (Exception ex) {
			return StoreConstants.ERROR + " creating new category";
		}
		IOService<?> ioManager = new IOService<Entity>();
		try {
			ioManager.writeToFile(FileDataWrapper.categoryMap, new sg.edu.nus.iss.ssa.model.Category());
			ioManager = null;
		} catch (Exception ex) {
			ioManager = null;
			return StoreConstants.ERROR + " saving new category";

		}
		return null;

	}

	// For replenish stock
	public String addStock(Product selectedProduct, long stockAdd) {
		selectedProduct.setQuantity(selectedProduct.getQuantity() + stockAdd);

		for (Product p : FileDataWrapper.productMap.values()) {
			Long barcode = p.getBarCode();
			if (barcode == selectedProduct.getBarCode()) {
				p = selectedProduct;
				break;
			}
		}

		IOService<?> ioManager = new IOService<Entity>();
		try {
			ioManager.writeToFile(FileDataWrapper.productMap, new sg.edu.nus.iss.ssa.model.Product());
			ioManager = null;
		} catch (Exception ex)

		{
			ioManager = null;
			return StoreConstants.ERROR + " saving product";
		}

		return null;
	}
}
