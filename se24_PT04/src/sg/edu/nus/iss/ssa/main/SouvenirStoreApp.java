package sg.edu.nus.iss.ssa.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.ssa.bo.AuthenticationManager;
import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.gui.AddCategory;
import sg.edu.nus.iss.ssa.gui.CategoryReport;
import sg.edu.nus.iss.ssa.gui.MemberReport;
import sg.edu.nus.iss.ssa.gui.ProductReport;
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
import sg.edu.nus.iss.ssa.gui.LoginWindow;
import sg.edu.nus.iss.ssa.gui.ManageCategory;
import sg.edu.nus.iss.ssa.gui.ManageStock;
import sg.edu.nus.iss.ssa.gui.MemberManagerWindow;;

/**
 * Main Class to launch SouvenirStore Application.
 * It will Call IO Service to read and load various entity data to memory.
 * 
 * 
 * @author Amarjeet B Singh
 *
 */
public class SouvenirStoreApp {
	
	

	public static void main(String[] args) throws FieldMismatchExcepion, IOException {

		IOService<?> ioManager = new IOService<Entity>();
				
		try {
			//load data in memory
			ioManager.readFromFile( FileDataWrapper.productMap,null, new Product());
			ioManager.readFromFile( FileDataWrapper.categoryMap,null, new Category());
			ioManager.readFromFile( FileDataWrapper.memberMap,null, new Member());
			ioManager.readFromFile( null, FileDataWrapper.transactionList, new Transaction());
			ioManager.readFromFile( FileDataWrapper.storeKeeperMap,null, new StoreKeeper());
			
			System.out.println("products :" + FileDataWrapper.productMap.keySet());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
			System.out.println("members : " + FileDataWrapper.memberMap.keySet());
			System.out.println("transactions : " + FileDataWrapper.transactionList);
			System.out.println("SoreKeeper : " + FileDataWrapper.storeKeeperMap.keySet());
			
			//Launch Purchase Window 
			ProductSelectionWindow productWindow = new ProductSelectionWindow();
			productWindow.setVisible(true);
		} catch (FileNotFoundException e) {
			System.err
					.println("Error occurred while executing program, please resolve the error and continue again");
			e.printStackTrace();
		}

	/*	// Launch Manage Category window, for testing
		ManageCategory manageCategoryWindow = new ManageCategory();
		manageCategoryWindow.setVisible(true);
		
		// Launch Manage Stock window, for testing
		ManageStock manageStockWindow = new ManageStock();
		manageStockWindow.setVisible(true);
		
		// Launch Login window, for testing
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
		
		MemberManagerWindow memberManager = new MemberManagerWindow();
		memberManager.setVisible(true);
		
		
		//Launch Report for testing
		try {
			//CategoryReport cr=new CategoryReport();
			//MemberReport mr=new MemberReport();
			ProductReport pr=new ProductReport();
			//TransactionReport tr=new TransactionReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

}
