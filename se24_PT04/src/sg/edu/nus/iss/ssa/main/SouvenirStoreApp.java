package sg.edu.nus.iss.ssa.main;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.gui.AddCategory;
import sg.edu.nus.iss.ssa.gui.ProductSelectionWindow;
import sg.edu.nus.iss.ssa.gui.ReplenishStock;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Order;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Member;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.model.StoreKeeper;
import sg.edu.nus.iss.ssa.model.Transaction;
import sg.edu.nus.iss.ssa.util.IOService;

/**
 * Main Class to launch SouvenirStore Application.
 * It will Call IO Service to read and load various entity data to memory.
 * 
 * 
 * @author Amarjeet B Singh
 *
 */
public class SouvenirStoreApp {
	
	

	public static void main(String[] args) throws FieldMismatchExcepion {

		IOService<?> ioManager = new IOService<Entity>();
				
		try {
			//load data in memory
			ioManager.readFromFile( FileDataWrapper.productMap, new Product());
			ioManager.readFromFile( FileDataWrapper.categoryMap, new Category());
			ioManager.readFromFile( FileDataWrapper.memberMap, new Member());
			ioManager.readFromFile( FileDataWrapper.transactionMap, new Transaction());
			ioManager.readFromFile( FileDataWrapper.storeKeeperMap, new StoreKeeper());
			
			System.out.println("products :" + FileDataWrapper.productMap.keySet());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
			System.out.println("members : " + FileDataWrapper.memberMap.keySet());
			System.out.println("transactions : " + FileDataWrapper.transactionMap.keySet());
			System.out.println("SoreKeeper : " + FileDataWrapper.storeKeeperMap.keySet());
			
			
			//Launch Purchase Window 
			ProductSelectionWindow productWindow = new ProductSelectionWindow();
			productWindow.setVisible(true);
		} catch (FileNotFoundException e) {
			System.err
					.println("Error occurred while executing program, please resolve the error and continue again");
			e.printStackTrace();
		}

		// Launch Add Category window, for testing
		AddCategory addCategoryWindow = new AddCategory();
		addCategoryWindow.setVisible(true);
		
		// Launch Replenish Stock window, for testing
		ReplenishStock replenishStockWindow = new ReplenishStock();
		replenishStockWindow.setVisible(true);

	}

}
