package sg.edu.nus.iss.ssa.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
import sg.edu.nus.iss.ssa.exception.BarCodeException;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Category;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.BarCodeGenerator;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.util.ProductIdGenerator;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class AddProduct extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField txtProductName;
  private JTextField txtProductDescription;
  private JTextField txtQuantityAvailable;
  private JTextField txtPrice;
  private JTextField txtThreshold;
  private JTextField txtReOrderQuantity;
  private JComboBox<String> txtProductCategory;
  private String productName;
  private String productDescription;
  private String quantityAvailable;
  private String price;
  private String threshold;
  private String reorderQuantity;
  private String productCategory;
  private IOService<?> ioService = new IOService<Object>();
  private EntityListController entityListController = new EntityListController();

  /**
   * Create the application.
   */
  public AddProduct(Map<String, Product> productMap, ManageProductWindow productManagerWindow) {
    setTitle("Add Product");
    setResizable(false);
    setLocationRelativeTo(null);
    getContentPane().setLayout(new BorderLayout());
    setSize(417, 454);
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    JLabel lblProductCategory = new JLabel("Product Category:");
    lblProductCategory.setBounds(59, 48, 108, 16);
    contentPanel.add(lblProductCategory);
    try {
      ioService.readFromFile(FileDataWrapper.categoryMap, null, new Category());
    } catch (FileNotFoundException | FieldMismatchExcepion e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String, Category> categories = FileDataWrapper.categoryMap;
    Set<String> ctgs = categories.keySet();
    txtProductCategory = new JComboBox<String>();
    for (String c : ctgs) {
      txtProductCategory.addItem(c);
    }
    txtProductCategory.setBounds(214, 43, 130, 26);
    contentPanel.add(txtProductCategory);

    JLabel lblProductTitle = new JLabel("Product Name:");
    lblProductTitle.setBounds(59, 90, 98, 16);
    contentPanel.add(lblProductTitle);

    txtProductName = new JTextField();
    txtProductName.setBounds(214, 85, 130, 26);
    contentPanel.add(txtProductName);
    txtProductName.setColumns(10);
    txtProductName.setDocument(new PlainDocument() {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (!str.contains(","))
				super.insertString(offs, str, a);
		}
	});

    JLabel lblProductDescription = new JLabel("Product Description:");
    lblProductDescription.setBounds(59, 132, 130, 16);
    contentPanel.add(lblProductDescription);

    txtProductDescription = new JTextField();
    txtProductDescription.setBounds(214, 127, 130, 26);
    contentPanel.add(txtProductDescription);
    txtProductDescription.setColumns(10);
    txtProductDescription.setDocument(new PlainDocument() {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (!str.contains(","))
				super.insertString(offs, str, a);
		}
	});

    JLabel lblQuantityAvailable = new JLabel("Quantity Available:");
    lblQuantityAvailable.setBounds(59, 175, 130, 16);
    contentPanel.add(lblQuantityAvailable);

    txtQuantityAvailable = new JTextField();
    txtQuantityAvailable.setBounds(214, 170, 130, 26);
    txtQuantityAvailable.setDocument(new PlainDocument() {

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.matches("[0-9]*"))
          super.insertString(offs, str, a);
      }
    });
    contentPanel.add(txtQuantityAvailable);
    txtQuantityAvailable.setColumns(10);

    JLabel lblPrice = new JLabel("Price:");
    lblPrice.setBounds(59, 218, 61, 16);
    contentPanel.add(lblPrice);

    txtPrice = new JTextField();
    txtPrice.setBounds(214, 213, 130, 26);
    txtPrice.setDocument(new PlainDocument() {
      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.matches("(?:\\d*\\.)?\\d+") || str.matches("\\."))
          super.insertString(offs, str, a);
      }
    });
    contentPanel.add(txtPrice);
    txtPrice.setColumns(10);

    JLabel lblNewLabel = new JLabel("Threshold:");
    lblNewLabel.setBounds(59, 261, 119, 16);
    contentPanel.add(lblNewLabel);

    txtThreshold = new JTextField();
    txtThreshold.setBounds(214, 256, 130, 26);
    txtThreshold.setDocument(new PlainDocument() {

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.matches("[0-9]*"))
          super.insertString(offs, str, a);
      }
    });
    contentPanel.add(txtThreshold);
    txtThreshold.setColumns(10);

    JLabel lblOrderQuantity = new JLabel("Reorder Quantity:");
    lblOrderQuantity.setBounds(59, 303, 108, 16);
    contentPanel.add(lblOrderQuantity);

    txtReOrderQuantity = new JTextField();
    txtReOrderQuantity.setBounds(214, 298, 130, 26);
    txtReOrderQuantity.setDocument(new PlainDocument() {

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.matches("[0-9]*"))
          super.insertString(offs, str, a);
      }
    });
    contentPanel.add(txtReOrderQuantity);
    txtReOrderQuantity.setColumns(10);
    
        JButton btnAddProduct = new JButton("OK");
        btnAddProduct.setBounds(80, 365, 100, 50);
        contentPanel.add(btnAddProduct);

        btnAddProduct.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fetchValuesFromTextFields();
            String validatorMessage = FormValidator.addProductValidateForm(productCategory, productName, productDescription,
                quantityAvailable, price, threshold, reorderQuantity);
            if (validatorMessage != null) {
              JOptionPane.showMessageDialog(btnAddProduct, validatorMessage, "Error",
                  JOptionPane.ERROR_MESSAGE);
            } else {
              Collection<Product> products = productMap.values();
              ProductIdGenerator productIdGenerator = new ProductIdGenerator();
              BarCodeGenerator barCodeGenerator = new BarCodeGenerator();
              int barCode = barCodeGenerator.generateBarCode();
              List<Integer> allBarCodes = new ArrayList<>();
              Collection<Product> allProducts = FileDataWrapper.productMap.values();
              for(Product product:allProducts){
               allBarCodes.add(product.getBarCode());
              }
              try{
                if(allBarCodes.contains(barCode)){
                  throw new Exception("Bar Code Already Exists");
                }
              } catch(Exception e2){
                e2.printStackTrace();
              }

              Product product = new Product(
                  productIdGenerator.getProductId(products, productCategory),
                  productName, productDescription,
                  Long.valueOf(quantityAvailable), Double.valueOf(
                      price),
                  barCode, Long.valueOf(threshold),
                  Long.valueOf(reorderQuantity));
              // add new member to memory
              try {
                entityListController.addProduct(product);
              } catch (Exception ex) {
                DisplayUtil.displayValidationError(contentPanel,
                    StoreConstants.ERROR + " creating new product");
                ex.printStackTrace();
              }
   
              // update the table data in MemberManagerWindow
              productManagerWindow.refreshTable(product.getProductArray());
              DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.PRODUCT_ADDED_SUCCESSFULLY_);
              dispose();
            }
          }
        });
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(234, 365, 100, 50);
        contentPanel.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });
  }

  private void fetchValuesFromTextFields() {
    productCategory = (String) txtProductCategory.getSelectedItem();
    productName = txtProductName.getText().trim();
    productDescription = txtProductDescription.getText().trim();
    quantityAvailable = txtQuantityAvailable.getText().trim();
    price = txtPrice.getText().trim();
    threshold = txtThreshold.getText().trim();
    reorderQuantity = txtReOrderQuantity.getText().trim();
  }
}
