package sg.edu.nus.iss.ssa.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.BarCodeGenerator;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.util.ProductIdGenerator;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class AddProduct extends JDialog{

	private JPanel frame;
	private JTextField txtProductName;
	private JTextField txtProductDescription;
	private JTextField txtQuantityAvailable;
	private JTextField txtPrice;
	private JTextField txtReorderQuantity;
	private JTextField txtOrderQuantity;
	private JComboBox txtProductCategory ;
	private String productName;
	private String productDescription;
	private String quantityAvailable;
	private String price;
	private String reorderQuantity;
	private String orderQuantity;
	private String productCategory;
	private IOService ioService = new IOService();
	private EntityListController entityListController = new EntityListController();

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
		//this.addWindowListener(new MyWindowListener());
		setResizable(false);
		setTitle("Add Product Page");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 330);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setLayout(null);

		JLabel lblProductCategory = new JLabel("Product Category");
		lblProductCategory.setBounds(20, 32, 108, 60);
		frame.add(lblProductCategory);
		try{
			ioService.readFromFile(FileDataWrapper.categoryMap,null,new Category());
		} catch(FileNotFoundException | FieldMismatchExcepion e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,Category> categories = FileDataWrapper.categoryMap;
		Set<String> ctgs = categories.keySet();
		txtProductCategory = new JComboBox();
		for(String c:ctgs){
			txtProductCategory.addItem(c);
		}
		txtProductCategory.setBounds(164, 55, 120,16);
		frame.add(txtProductCategory);

		JLabel lblProductTitle = new JLabel("Product Name");
		lblProductTitle.setBounds(20, 90, 98, 16);
		frame.add(lblProductTitle);

		txtProductName = new JTextField();
		txtProductName.setBounds(154, 85, 130, 26);
		frame.add(txtProductName);
		txtProductName.setColumns(10);

		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setBounds(20, 132, 130, 16);
		frame.add(lblProductDescription);

		txtProductDescription = new JTextField();
		txtProductDescription.setBounds(154, 127, 130, 26);
		frame.add(txtProductDescription);
		txtProductDescription.setColumns(10);

		JLabel lblQuantityAvailable = new JLabel("Quantity Available");
		lblQuantityAvailable.setBounds(20, 172, 130, 16);
		frame.add(lblQuantityAvailable);

		txtQuantityAvailable = new JTextField();
		txtQuantityAvailable.setBounds(154, 167, 130, 26);
		frame.add(txtQuantityAvailable);
		txtQuantityAvailable.setColumns(10);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(20, 200, 61, 16);
		frame.add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setBounds(154, 195, 130, 26);
		frame.add(txtPrice);
		txtPrice.setColumns(10);

		JLabel lblNewLabel = new JLabel("ReOrder Quantity");
		lblNewLabel.setBounds(20, 283, 119, 16);
		frame.add(lblNewLabel);

		txtReorderQuantity = new JTextField();
		txtReorderQuantity.setBounds(154, 277, 130, 26);
		frame.add(txtReorderQuantity);
		txtReorderQuantity.setColumns(10);

		JLabel lblOrderQuantity = new JLabel("Order Quantity");
		lblOrderQuantity.setBounds(20, 332, 108, 16);
		frame.add(lblOrderQuantity);

		txtOrderQuantity = new JTextField();
		txtOrderQuantity.setBounds(154, 327, 130, 26);
		frame.add(txtOrderQuantity);
		txtOrderQuantity.setColumns(10);


		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int dialogResult = -1;
					boolean isValid = doValidation();
					if(isValid){
           boolean isAdditionSuccesfull = addProduct();
						if (isAdditionSuccesfull) {
							dialogResult = DisplayUtil.displayConfirmationMessage(frame,
									StoreConstants.CATEGORY_ADDED_SUCCESSFULLY);
							if (dialogResult == 0) {
								clearFields();
							}
							else if (dialogResult == 1) {
								dispose();
							}
						}
						reloadData();
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		btnAddProduct.setBounds(98,240,89,23);
		frame.add(btnAddProduct);

		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnClose.setBounds(249, 240, 89, 23);
		frame.add(btnClose);
    frame.setVisible(true);
	}

	private boolean doValidation() throws FieldMismatchExcepion,IOException{
		fetchValuesFromTextFields();
		String validatorMessage = FormValidator.addProductValidateForm(productCategory,productName,quantityAvailable,price,reorderQuantity);
		if(validatorMessage!=null){
			DisplayUtil.displayValidationError(frame,validatorMessage);
			return false;
		}
		return true;
	}

	private void fetchValuesFromTextFields(){
		productCategory = (String)txtProductCategory.getSelectedItem();
		productName = txtProductName.getText();
		productDescription = txtProductDescription.getText();
		quantityAvailable = txtQuantityAvailable.getText();
		price = txtPrice.getText();
		reorderQuantity = txtReorderQuantity.getText();
		orderQuantity = txtOrderQuantity.getText();
	}

	private boolean addProduct() throws FieldMismatchExcepion,IOException {
		Map<Integer,Product> mapProduct = FileDataWrapper.productMap;
		ioService.readFromFile(mapProduct,null,new Product());
		Collection<Product> products = mapProduct.values();
		ProductIdGenerator productIdGenerator = new ProductIdGenerator();
		BarCodeGenerator barCodeGenerator = new BarCodeGenerator();
		int barCode = barCodeGenerator.generateBarCode();
		Product product = new Product(productIdGenerator.getProductId(products,productCategory), productName, productDescription,
				Long.valueOf(quantityAvailable), Double.valueOf(
				price),barCode, Long.valueOf(reorderQuantity),
				Long.valueOf(orderQuantity));
		String isProductAdded = entityListController.addProduct(product);
		if(isProductAdded!=null){
			DisplayUtil.displayValidationError(frame,isProductAdded);
			return false;
		}
		return true;
	}

	private void clearFields() {
   txtProductDescription.setText("");
	 txtProductName.setText("");
		txtOrderQuantity.setText("");
		txtPrice.setText("");
		txtQuantityAvailable.setText("");
		txtReorderQuantity.setText("");
	}

	private void reloadData() {
    entityListController.reloadProductData();
	}

	class MyWindowListener implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0)
		{
			// TODO Auto-generated method stub
			entityListController = null;
		}

		@Override
		public void windowClosing(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0)
		{
			// TODO Auto-generated method stub

		}

	}
}
