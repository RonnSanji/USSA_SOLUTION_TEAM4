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

import javax.annotation.Generated;

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
import sg.edu.nus.iss.ssa.model.Discount;
import sg.edu.nus.iss.ssa.model.PeriodDiscount;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.util.TestUtil;
import sg.edu.nus.iss.ssa.util.BarCodeGenerator;

/**
 * @author LOL
 *
 */
public class EntityListControllerTest extends TestCase {

	EntityListController controller;
	IOService<?> ioManager;

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
		controller = new EntityListController();
		ioManager = new IOService<>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		controller = null;
		ioManager = null;
	}

	@Test
	public void testSaveCategory() {

		controller.reloadCategoryData();
		// add
		Set<String> keySet = FileDataWrapper.categoryMap.keySet();
		ArrayList<String> tempKeyList = new ArrayList<>();

		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				tempKeyList.add(key.toUpperCase());
			}
		}
		String categoryID = TestUtil.generateRandomString(3, tempKeyList, false);
		String newCategoryname = TestUtil.generateRandomString(50);

		Category cat = new Category();
		cat.setCategoryId(categoryID);
		cat.setCategoryName(newCategoryname);

		// System.out.println(categoryID);

		String msg = controller.saveCategory(cat, 0);

		assertTrue(msg == null);

		// System.out.println(msg);
		if (msg == null) {
			controller.reloadCategoryData();

			assertEquals(categoryID, FileDataWrapper.categoryMap.get(categoryID).getCategoryId());
			assertEquals(newCategoryname, FileDataWrapper.categoryMap.get(categoryID).getCategoryName());

			// System.out.println(FileDataWrapper.categoryMap.get(categoryID).getCategoryId());
		}

		// edit
		newCategoryname = TestUtil.generateRandomString(20);
		cat = ((Category) FileDataWrapper.categoryMap.values().toArray()[new Random()
				.nextInt(FileDataWrapper.categoryMap.size())]);

		cat.setCategoryName(newCategoryname);
		msg = controller.saveCategory(cat, 1);
		assertTrue(msg == null);

		if (msg == null) {
			controller.reloadCategoryData();
			assertEquals(newCategoryname, FileDataWrapper.categoryMap.get(cat.getCategoryId()).getCategoryName());
		}

		// remove
		cat = ((Category) FileDataWrapper.categoryMap.values().toArray()[new Random()
				.nextInt(FileDataWrapper.categoryMap.size())]);

		msg = controller.saveCategory(cat, 2);
		assertTrue(msg == null);

		if (msg == null) {
			controller.reloadCategoryData();
			assertEquals(FileDataWrapper.categoryMap.get(cat.getCategoryId()), null);
		}
	}

	@Test
	public void testReloadCategoryData() {
		Random ran = new Random();

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
	public void testUpdateThreshold_ReorderQuantity() {
		Random ran = new Random();
		EntityListController controller = new EntityListController();
		controller.reloadProductData();

		Product testProduct = (Product) FileDataWrapper.productMap.values().toArray()[ran
				.nextInt(FileDataWrapper.productMap.size())];
		long testThreshold = ran.nextInt(100);
		long testReorderQuantity = ran.nextInt(100);

		String msg = controller.updateThreshold_ReorderQuantity(testProduct, testThreshold, testReorderQuantity);

		assertTrue(msg == null);

		if (msg == null) {
			controller.reloadProductData();

			for (Product p : FileDataWrapper.productMap.values()) {
				if (p.getBarCode() == testProduct.getBarCode()) {
					assertEquals(testThreshold, p.getThresholdQuantity());

					assertEquals(testReorderQuantity, p.getOrderQuantity());
				}
			}
		}
		controller = null;
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

	@Test
	public void testSaveDiscount() {
		Random ran = new Random();

		EntityListController controller = new EntityListController();
		controller.reloadDiscountData();

		ArrayList<String> discountCodeList = new ArrayList<>();
		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			discountCodeList.add(tempDiscount.getDiscountCode());
		}

		// add
		PeriodDiscount discount = new PeriodDiscount();

		String newDiscountCode = TestUtil.generateRandomString(10, discountCodeList, false);

		discount.setDiscountCode(newDiscountCode);
		discount.setDiscountDesc(TestUtil.generateRandomString(50));
		discount.setDiscountPerc(ran.nextInt(99));
		discount.setDiscountPeriod(String.valueOf(ran.nextInt(100)));
		discount.setStarDate("2017-01-01");
		discount.setApplicableTo("A");

		String msg = controller.saveDiscount(discount, 0);

		assertTrue(msg == null);

		if (msg == null) {
			controller.reloadDiscountData();

			for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
				PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
				if (tempDiscount.getDiscountCode().equalsIgnoreCase(newDiscountCode)) {
					assertEquals(tempDiscount.getDiscountDesc(), discount.getDiscountDesc());
					assertEquals(tempDiscount.getDiscountPerc(), discount.getDiscountPerc());
					assertEquals(tempDiscount.getDiscountPeriod(), discount.getDiscountPeriod());
					assertEquals(tempDiscount.getStarDate(), discount.getStarDate());
					assertEquals(tempDiscount.getApplicableTo(), discount.getApplicableTo());
				}
			}
		}

		// edit

		discount = (PeriodDiscount) FileDataWrapper.discounts.get(ran.nextInt(FileDataWrapper.discounts.size()));
		discount.setDiscountDesc(TestUtil.generateRandomString(80));
		discount.setDiscountPerc(ran.nextInt(99));
		discount.setDiscountPeriod(String.valueOf(ran.nextInt(100)));
		discount.setStarDate("2018-01-01");
		discount.setApplicableTo("M");

		msg = controller.saveDiscount(discount, 1);

		assertTrue(msg == null);

		if (msg == null) {
			controller.reloadDiscountData();

			for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
				PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
				if (tempDiscount.getDiscountCode().equalsIgnoreCase(discount.getDiscountCode())) {
					assertEquals(tempDiscount.getDiscountDesc(), discount.getDiscountDesc());
					assertEquals(tempDiscount.getDiscountPerc(), discount.getDiscountPerc());
					assertEquals(tempDiscount.getDiscountPeriod(), discount.getDiscountPeriod());
					assertEquals(tempDiscount.getStarDate(), discount.getStarDate());
					assertEquals(tempDiscount.getApplicableTo(), discount.getApplicableTo());
				}
			}
		}

		// remove

		discount = (PeriodDiscount) FileDataWrapper.discounts.get(ran.nextInt(FileDataWrapper.discounts.size()));

		msg = controller.saveDiscount(discount, 2);

		assertTrue(msg == null);

		if (msg == null) {
			controller.reloadDiscountData();

			for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
				PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
				if (tempDiscount.getDiscountCode().equalsIgnoreCase(discount.getDiscountCode())) {
					assertFalse(true);
				}
			}

		}
		controller = null;
	}

	@Test
	public void testReloadDiscountData() {
		Random ran = new Random();

		IOService<?> ioManager = new IOService<>();
		FileDataWrapper.discounts.clear();
		if (ioManager == null) {
			ioManager = new IOService<>();
		}
		try {
			ioManager.readFromFile(null, FileDataWrapper.discounts, new sg.edu.nus.iss.ssa.model.PeriodDiscount());
			System.out.println("discounts : " + FileDataWrapper.discounts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FieldMismatchExcepion fieldMismatchExcepion) {
			fieldMismatchExcepion.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ioManager = null;
		}

		ArrayList<PeriodDiscount> tempDiscountList = new ArrayList<PeriodDiscount>();

		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			tempDiscountList.add(tempDiscount);
		}

		PeriodDiscount p = tempDiscountList.get(ran.nextInt(tempDiscountList.size()));
		String discountCode = p.getDiscountCode();
		String originalDiscountDesc = p.getDiscountDesc();

		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			if (tempDiscount.getDiscountCode().equalsIgnoreCase(discountCode)) {
				tempDiscount.setDiscountDesc("A new discount description");
				break;
			}
		}

		System.out.println("Original discount description: " + originalDiscountDesc);

		System.out.println("New discount description: A new discount description");

		EntityListController controller = new EntityListController();
		try {
			controller.reloadDiscountData();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			controller = null;
		}

		for (int i = 0; i < FileDataWrapper.discounts.size(); i++) {
			PeriodDiscount tempDiscount = (PeriodDiscount) FileDataWrapper.discounts.get(i);
			if (tempDiscount.getDiscountCode().equalsIgnoreCase(discountCode)) {
				assertEquals(originalDiscountDesc, tempDiscount.getDiscountDesc());
				break;
			}
		}
	}

	@Test
	public void testAddProduct(){
		EntityListController entityListController = new EntityListController();
		BarCodeGenerator barCodeGenerator = new BarCodeGenerator();
		String productId = "DRK/3";
		String productName = "Mazaa";
		String productDescription = "Mazaa 1 L";
	  Long Quantity = 10L;
		Double price = 1.2;
		int barCode = barCodeGenerator.generateBarCode();
		long thresholdQuantity = 10L;
		long orderQuantity = 100L;
		Product product = new Product(productId,productName,productDescription,Quantity,price,barCode,thresholdQuantity,orderQuantity);
		String result = entityListController.addProduct(product);
		assertNotNull(result);

	}

}
