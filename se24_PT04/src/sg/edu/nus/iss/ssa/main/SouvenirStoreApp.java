package sg.edu.nus.iss.ssa.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.*;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.validation.FormValidator;
import sg.edu.nus.iss.ssa.gui.LoginWindow;
import sg.edu.nus.iss.ssa.gui.DashBoard;

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
		EntityListController controller = new EntityListController();
				
		try {
			//load data in memory
			ioManager.readFromFile( FileDataWrapper.productMap,null, new Product());
			ioManager.readFromFile( FileDataWrapper.categoryMap,null, new Category());
			ioManager.readFromFile( FileDataWrapper.memberMap,null, new Member());
			ioManager.readFromFile( null, FileDataWrapper.transactionList, new Transaction());
			ioManager.readFromFile( FileDataWrapper.storeKeeperMap,null, new StoreKeeper());
			ioManager.readFromFile( null, FileDataWrapper.discounts, new PeriodDiscount());
			controller.loadAllVendorMap();
			
			//System.out.println("products :" + FileDataWrapper.productMap.keySet());
			//System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
			//System.out.println("members : " + FileDataWrapper.memberMap.keySet());
			//System.out.println("transactions : " + FileDataWrapper.transactionList);
			//System.out.println("SoreKeeper : " + FileDataWrapper.storeKeeperMap.keySet());
			//System.out.println("discounts : " + FileDataWrapper.discounts);
			//System.out.println(((PeriodDiscount)FileDataWrapper.discounts.get(4)).checkIfDiscountAvailable());

			LoginWindow login = new LoginWindow();	
			login.setVisible(true);

		} catch (FileNotFoundException e) {
			System.err
					.println("Error occurred while executing program, please resolve the error and continue again");
			e.printStackTrace();
		}
	}

}
