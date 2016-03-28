package sg.edu.nus.iss.ssa.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
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
				
		try {
			//load data in memory
			ioManager.readFromFile( FileDataWrapper.productMap,null, new Product());
			ioManager.readFromFile( FileDataWrapper.categoryMap,null, new Category());
			ioManager.readFromFile( FileDataWrapper.memberMap,null, new Member());
			ioManager.readFromFile( null, FileDataWrapper.transactionList, new Transaction());
			ioManager.readFromFile( FileDataWrapper.storeKeeperMap,null, new StoreKeeper());
			ioManager.readFromFile( null, FileDataWrapper.discounts, new PeriodDiscount());
			
			System.out.println("products :" + FileDataWrapper.productMap.keySet());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
			System.out.println("members : " + FileDataWrapper.memberMap.keySet());
			System.out.println("transactions : " + FileDataWrapper.transactionList);
			System.out.println("SoreKeeper : " + FileDataWrapper.storeKeeperMap.keySet());
			System.out.println("discounts : " + FileDataWrapper.discounts);
			System.out.println(((PeriodDiscount)FileDataWrapper.discounts.get(4)).checkIfDiscountAvailable());
			
			//Launch Purchase Window 
			//ProductSelectionWindow productWindow = new ProductSelectionWindow();
			//productWindow.setVisible(true);
			
			LoginWindow login = new LoginWindow();
			login.btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String userName =  login.txtUsername.getText();
					String errorMsg = FormValidator.addStoreKeeperValidateForm(userName, login.txtPassword.getText());
					if (errorMsg != null) {
						login.lblError.setForeground(Color.RED);
						login.lblError.setText(errorMsg);
					}else {
						login.lblError.setForeground(Color.GREEN);
						login.lblError.setText("");
						login.dispose();
						
						try {
							DashBoard dashboardwindow = new DashBoard(userName);
							dashboardwindow.setVisible(true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}				
				}
			});		
			login.setVisible(true);

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
