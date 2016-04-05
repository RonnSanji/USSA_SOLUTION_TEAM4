package sg.edu.nus.iss.ssa.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Product;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Adya on 4/5/2016.
 */
public class OrderValidatorTest {

    OrderValidator orderValidator;

    @Before
    public void setUp() throws Exception {
        orderValidator = new OrderValidator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidateProductPurchaseInput() throws Exception {
        final String productBarCode = "1234";
        final String quantity = "100";
        String return1 = orderValidator.validateProductPurchaseInput(productBarCode,quantity);
        assertNull(return1);

        final String productBarCode1 = "";
        final String quantity1 = "";
        String return2 = orderValidator.validateProductPurchaseInput(productBarCode1,quantity1);
        assertNotNull(return2);
        assertEquals(StoreConstants.BLANK_INPUT_FOR_PRODUCT_PURCHASE,return2);
    }

    @Test
    public void testValidateSelectedProduct() throws Exception {
        final int productBarCode = 111 ;
        final Map<String,Product> productMap = new HashMap<String,Product>();
        productMap.put("111",createProduct(productBarCode));
        productMap.put("222",createProduct(222));
        productMap.put("333", createProduct(333));
        String return1 = orderValidator.validateSelectedProduct(String.valueOf(productBarCode), productMap);
        assertNull(return1);
    }

    @Test
    public void testValidateProductOrder() throws Exception {

    }

    @Test
    public void testValidateRedeemedPoints() throws Exception {

    }

    @Test
    public void testCheckAmountToProcessPayment() throws Exception {

    }

    private Product createProduct(int barCode){
        Product product = new Product("1", "test product", "Test product description", 500, 100.0, barCode, 100,
        200);
        return product;
    }
}