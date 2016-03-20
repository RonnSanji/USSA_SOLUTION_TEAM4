package sg.edu.nus.iss.ssa.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.exception.FileUnableToWriteException;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.util.ProductIdGenerator;

public class AddProduct {

	private JFrame frame;
	private JTextField txtProductName;
	private JTextField txtProductDescription;
	private JTextField txtQuantityAvailable;
	private JTextField txtPrice;
	private JTextField txtBarCodeNumber;
	private JTextField txtReorderQuantity;
	private JTextField txtOrderQuantity;
	private JList txtProductCategory ;
	private String productName;
	private String productDescription;
	private String quantityAvailable;
	private String price;
	private String barCodeNumber;
	private String reorderQuantity;
	private String orderQuantity;
	private String productCategory;
	private IOService ioService = new IOService();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProduct window = new AddProduct();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddProduct() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAddProduct = new JLabel("Add Product");
		lblAddProduct.setBounds(177, 6, 144, 16);
		frame.getContentPane().add(lblAddProduct);

		JLabel lblProductCategory = new JLabel("Product Category");
		lblProductCategory.setBounds(20, 55, 108, 16);
		frame.getContentPane().add(lblProductCategory);
    try{
			ioService.readFromFile(FileDataWrapper.categoryMap,null,new Category());
		} catch(FileNotFoundException | FieldMismatchExcepion e){
    e.printStackTrace();
		}
		Map<String,Category> categories = FileDataWrapper.categoryMap;
		Set<String> ctgs = categories.keySet();
		Object arr[] = ctgs.toArray();
		txtProductCategory = new JList(arr);
		txtProductCategory.setBounds(164, 55, 120,16);
		frame.getContentPane().add(txtProductCategory);

		JLabel lblProductTitle = new JLabel("Product Name");
		lblProductTitle.setBounds(20, 90, 98, 16);
		frame.getContentPane().add(lblProductTitle);

		txtProductName = new JTextField();
		txtProductName.setBounds(154, 85, 130, 26);
		frame.getContentPane().add(txtProductName);
		txtProductName.setColumns(10);

		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setBounds(20, 132, 130, 16);
		frame.getContentPane().add(lblProductDescription);

		txtProductDescription = new JTextField();
		txtProductDescription.setBounds(154, 127, 130, 26);
		frame.getContentPane().add(txtProductDescription);
		txtProductDescription.setColumns(10);

		JLabel lblQuantityAvailable = new JLabel("Quantity Available");
		lblQuantityAvailable.setBounds(20, 172, 130, 16);
		frame.getContentPane().add(lblQuantityAvailable);

		txtQuantityAvailable = new JTextField();
		txtQuantityAvailable.setBounds(154, 167, 130, 26);
		frame.getContentPane().add(txtQuantityAvailable);
		txtQuantityAvailable.setColumns(10);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(20, 200, 61, 16);
		frame.getContentPane().add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setBounds(154, 195, 130, 26);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);

		JLabel lblBarCodeNumber = new JLabel("Bar Code Number");
		lblBarCodeNumber.setBounds(20, 244, 119, 16);
		frame.getContentPane().add(lblBarCodeNumber);

		txtBarCodeNumber = new JTextField();
		txtBarCodeNumber.setBounds(154, 239, 130, 26);
		frame.getContentPane().add(txtBarCodeNumber);
		txtBarCodeNumber.setColumns(10);

		JLabel lblNewLabel = new JLabel("ReOrder Quantity");
		lblNewLabel.setBounds(20, 283, 119, 16);
		frame.getContentPane().add(lblNewLabel);

		txtReorderQuantity = new JTextField();
		txtReorderQuantity.setBounds(154, 277, 130, 26);
		frame.getContentPane().add(txtReorderQuantity);
		txtReorderQuantity.setColumns(10);

		JLabel lblOrderQuantity = new JLabel("Order Quantity");
		lblOrderQuantity.setBounds(20, 332, 108, 16);
		frame.getContentPane().add(lblOrderQuantity);

		txtOrderQuantity = new JTextField();
		txtOrderQuantity.setBounds(154, 327, 130, 26);
		frame.getContentPane().add(txtOrderQuantity);
		txtOrderQuantity.setColumns(10);
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{

				fetchValuesFromTextFields();
				Map<Long,Product> mapProduct = FileDataWrapper.productMap;
				ioService.readFromFile(mapProduct,null,new Product());
				Collection<Product> products = mapProduct.values();
				ProductIdGenerator productIdGenerator = new ProductIdGenerator();
				Product product = new Product(productIdGenerator.getProductId(products,productCategory), productName, productDescription,
				Long.valueOf(quantityAvailable), Double.valueOf(
						price), Long.valueOf(barCodeNumber), Long.valueOf(reorderQuantity),
				Long.valueOf(orderQuantity));
				writeToFile(product);
				}
catch(Exception e1){
	e1.printStackTrace();
}
			}
		});

		btnAddProduct.setBounds(105, 384, 117, 29);
		frame.getContentPane().add(btnAddProduct);


	}

	private void writeToFile(Product product){
		try{
			FileDataWrapper.productMap.put(Long.valueOf(barCodeNumber),product);
			ioService.writeToFile(FileDataWrapper.productMap,new Product());
		} catch(IOException | FileUnableToWriteException e){
      e.printStackTrace();
		}

	}

	private void fetchValuesFromTextFields(){
		productCategory = (String)txtProductCategory.getSelectedValue();
		productName = txtProductName.getText();
		productDescription = txtProductDescription.getText();
		quantityAvailable = txtQuantityAvailable.getText();
		price = txtPrice.getText();
		barCodeNumber = txtBarCodeNumber.getText();
		reorderQuantity = txtReorderQuantity.getText();
		orderQuantity = txtOrderQuantity.getText();
	}
}
