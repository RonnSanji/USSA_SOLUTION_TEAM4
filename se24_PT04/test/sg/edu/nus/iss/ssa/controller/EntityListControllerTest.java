/**
 * 
 */
package sg.edu.nus.iss.ssa.controller;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale.Category;
import java.util.Random;
import java.util.Set;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.IOService;

/**
 * @author LOL
 *
 */
public class EntityListControllerTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test() {

	}

	@Test
	public void testaddCategory() {
		Random ran = new Random();
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		IOService service = new IOService<>();
		try {
			service.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			service = null;
			e.printStackTrace();
		} catch (FieldMismatchExcepion e) {

			// TODO Auto-generated catch block
			service = null;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service = null;
		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				tempKeyList.add(key.toUpperCase());
			}
		}
		// for (int i = 0; i < 100; i++) {
		String categoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
				+ letters.charAt(ran.nextInt(26));

		while (true) {
			if (tempKeyList.contains(categoryID)) {
				System.out.println("Category: " + categoryID + " exists");
				categoryID = String.valueOf(letters.charAt(ran.nextInt(26))) + letters.charAt(ran.nextInt(26))
						+ letters.charAt(ran.nextInt(26));

			} else {
				break;
			}
		}

		System.out.println(categoryID);

		sg.edu.nus.iss.ssa.model.Category cat = new sg.edu.nus.iss.ssa.model.Category();
		cat.setCategoryId(categoryID);
		cat.setCategoryName(categoryID);

		EntityListController controller = new EntityListController();

		String msg = controller.addCategory(categoryID, categoryID);

		if (msg == null) {

			service = new IOService<>();
			try {
				service.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				service = null;
				e.printStackTrace();
			} catch (FieldMismatchExcepion e) {

				// TODO Auto-generated catch block
				service = null;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			service = null;

			assertEquals(categoryID, FileDataWrapper.categoryMap.get(categoryID).getCategoryId());
			assertEquals(categoryID, FileDataWrapper.categoryMap.get(categoryID).getCategoryName());
		}
	}

	@Test
	public void testaddStock() {
		Random ran = new Random();

		IOService service = new IOService<>();
		try {
			service.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			service = null;
			e.printStackTrace();
		} catch (FieldMismatchExcepion e) {

			// TODO Auto-generated catch block
			service = null;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service = null;

		// for (int i = 0; i < 100; i++) {
		int stockAdd = ran.nextInt(100);
		System.out.println("Stock to add: " + stockAdd);

		ArrayList<Product> products = new ArrayList<>();

		Collection<Product> col = FileDataWrapper.productMap.values();
		for (Product product : col) {
			products.add(product);
		}

		Product selectedProduct = products.get(ran.nextInt(products.size()));

		long oldStock = selectedProduct.getQuantity();
		System.out.println("Slected product: " + selectedProduct.getProductId() + " " + selectedProduct.getProductName()
				+ " " + oldStock);

		EntityListController controller = new EntityListController();

		String msg = controller.addStock(selectedProduct, stockAdd);

		if (msg == null) {
			service = new IOService<>();
			try {
				service.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				service = null;
				e.printStackTrace();
			} catch (FieldMismatchExcepion e) {

				// TODO Auto-generated catch block
				service = null;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			service = null;
			
			assertEquals(oldStock + stockAdd,
					FileDataWrapper.productMap.get(selectedProduct.getBarCode()).getQuantity());
			// assertEquals(categoryID,
			// FileDataWrapper.categoryMap.get(categoryID).getCategoryName());
		}

	}
}
