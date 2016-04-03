/**
 * 
 */
package sg.edu.nus.iss.ssa.controller;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import sg.edu.nus.iss.ssa.model.Category;
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
	public void testAddCategory() {
		Random ran = new Random();
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

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

		String msg = controller.saveCategory(cat, 0);

		if (msg == null) {

			FileDataWrapper.categoryMap.clear();
			if (ioManager == null) {
				ioManager = new IOService<>();
			}
			try {
				ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
				System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FieldMismatchExcepion fieldMismatchExcepion) {
				fieldMismatchExcepion.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				ioManager = null;
				controller = null;
			}

			assertEquals(categoryID, FileDataWrapper.categoryMap.get(categoryID).getCategoryId());
			assertEquals(categoryID, FileDataWrapper.categoryMap.get(categoryID).getCategoryName());
		}
	}

	@Test
	public void testRemoveCategory() {
		Random ran = new Random();
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				tempKeyList.add(key.toUpperCase());
			}
		}

		String categoryID = tempKeyList.get(ran.nextInt(keySet.size()));
		Category cat = new Category();
		cat.setCategoryId(categoryID);
		cat.setCategoryName(categoryID);
		System.out.println(categoryID);

		EntityListController controller = new EntityListController();

		String msg = controller.saveCategory(cat, 2);

		if (msg == null) {

			FileDataWrapper.categoryMap.clear();
			if (ioManager == null) {
				ioManager = new IOService<>();
			}
			try {
				ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
				System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FieldMismatchExcepion fieldMismatchExcepion) {
				fieldMismatchExcepion.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				ioManager = null;
				controller = null;
			}

			assertTrue(FileDataWrapper.categoryMap.get(categoryID) == null);

		}
	}

	@Test
	public void testReloadCategoryData() {
		Random ran = new Random();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.categoryMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.categoryMap, null, new sg.edu.nus.iss.ssa.model.Category());
			System.out.println("categories : " + FileDataWrapper.categoryMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				tempKeyList.add(key.toUpperCase());
			}
		}

		String categoryID = tempKeyList.get(ran.nextInt(tempKeyList.size()));
		String originalCategoryName = FileDataWrapper.categoryMap.get(categoryID).getCategoryName();

		FileDataWrapper.categoryMap.get(categoryID).setCategoryName("A new category name");

		System.out.println("Original Category Name: " + originalCategoryName);

		System.out.println("New Category Name: " + FileDataWrapper.categoryMap.get(categoryID).getCategoryName());

		EntityListController controller = new EntityListController();
		try {
			controller.reloadCategoryData();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			controller = null;
		}

		assertEquals(originalCategoryName, FileDataWrapper.categoryMap.get(categoryID).getCategoryName());

	}

	@Test
	public void testaddStock() {
		Random ran = new Random();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.productMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			System.out.println("products : " + FileDataWrapper.productMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

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
			FileDataWrapper.productMap.clear();
			if (ioManager == null) {
				ioManager = new IOService<>();
			}
			try {
				ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
				System.out.println("products : " + FileDataWrapper.productMap.keySet());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FieldMismatchExcepion fieldMismatchExcepion) {
				fieldMismatchExcepion.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				ioManager = null;
				controller = null;
			}
			for (Product product : FileDataWrapper.productMap.values()) {
				if (product.getBarCode() == selectedProduct.getBarCode()) {
					assertEquals(oldStock + stockAdd, product.getQuantity());
					break;
				}
			}

		}

	}

	@Test
	public void testReloadProductData() {
		Random ran = new Random();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.productMap.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(FileDataWrapper.productMap, null, new sg.edu.nus.iss.ssa.model.Product());
			System.out.println("products : " + FileDataWrapper.productMap.keySet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		ArrayList<Product> tempProductList = new ArrayList<Product>();

		for (Product p : FileDataWrapper.productMap.values()) {
			tempProductList.add(p);
		}

		Product p = tempProductList.get(ran.nextInt(tempProductList.size()));
		Integer barCode = p.getBarCode();
		String originalProductName = p.getProductName();

		for (Product product : FileDataWrapper.productMap.values()) {
			if (p.getBarCode() == barCode) {
				product.setProductName("A new product name");
				break;
			}
		}

		System.out.println("Original Product Name: " + originalProductName);

		System.out.println("New Product Name: A new product name");

		EntityListController controller = new EntityListController();
		try {
			controller.reloadProductData();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			controller = null;
		}

		for (Product product : FileDataWrapper.productMap.values()) {
			if (product.getBarCode() == barCode) {
				assertEquals(originalProductName, p.getProductName());
				break;
			}
		}
	}

}
