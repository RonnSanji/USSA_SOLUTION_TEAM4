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
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.controller.EntityListController;
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
  private final JPanel buttonPanel = new JPanel();
  private JTextField txtProductName;
  private JTextField txtProductDescription;
  private JTextField txtQuantityAvailable;
  private JTextField txtPrice;
  private JTextField txtReorderQuantity;
  private JTextField txtOrderQuantity;
  private JComboBox txtProductCategory;
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
   * Create the application.
   */
  public AddProduct(Map productMap, ManageProductWindow productManagerWindow) {
    setTitle("Adding New Member");
    setResizable(false);
    setLocationRelativeTo(null);
    getContentPane().setLayout(new BorderLayout());
    setSize(800, 600);
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    JLabel lblProductCategory = new JLabel("Product Category");
    lblProductCategory.setBounds(20, 32, 108, 60);
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
    txtProductCategory = new JComboBox();
    for (String c : ctgs) {
      txtProductCategory.addItem(c);
    }
    txtProductCategory.setBounds(164, 55, 120, 16);
    contentPanel.add(txtProductCategory);

    JLabel lblProductTitle = new JLabel("Product Name");
    lblProductTitle.setBounds(20, 90, 98, 16);
    contentPanel.add(lblProductTitle);

    txtProductName = new JTextField();
    txtProductName.setBounds(154, 85, 130, 26);
    contentPanel.add(txtProductName);
    txtProductName.setColumns(10);

    JLabel lblProductDescription = new JLabel("Product Description");
    lblProductDescription.setBounds(20, 132, 130, 16);
    contentPanel.add(lblProductDescription);

    txtProductDescription = new JTextField();
    txtProductDescription.setBounds(154, 127, 130, 26);
    contentPanel.add(txtProductDescription);
    txtProductDescription.setColumns(10);

    JLabel lblQuantityAvailable = new JLabel("Quantity Available");
    lblQuantityAvailable.setBounds(20, 172, 130, 16);
    contentPanel.add(lblQuantityAvailable);

    txtQuantityAvailable = new JTextField();
    txtQuantityAvailable.setBounds(154, 167, 130, 26);
    contentPanel.add(txtQuantityAvailable);
    txtQuantityAvailable.setColumns(10);

    JLabel lblPrice = new JLabel("Price");
    lblPrice.setBounds(20, 200, 61, 16);
    contentPanel.add(lblPrice);

    txtPrice = new JTextField();
    txtPrice.setBounds(154, 195, 130, 26);
    contentPanel.add(txtPrice);
    txtPrice.setColumns(10);

    JLabel lblNewLabel = new JLabel("ReOrder Quantity");
    lblNewLabel.setBounds(20, 283, 119, 16);
    contentPanel.add(lblNewLabel);

    txtReorderQuantity = new JTextField();
    txtReorderQuantity.setBounds(154, 277, 130, 26);
    contentPanel.add(txtReorderQuantity);
    txtReorderQuantity.setColumns(10);

    JLabel lblOrderQuantity = new JLabel("Order Quantity");
    lblOrderQuantity.setBounds(20, 332, 108, 16);
    contentPanel.add(lblOrderQuantity);

    txtOrderQuantity = new JTextField();
    txtOrderQuantity.setBounds(154, 327, 130, 26);
    contentPanel.add(txtOrderQuantity);
    txtOrderQuantity.setColumns(10);

    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);

    JButton btnAddProduct = new JButton("Add Product");
    btnAddProduct.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fetchValuesFromTextFields();
        String validatorMessage = FormValidator.addProductValidateForm(productCategory, productName,
            quantityAvailable, price, reorderQuantity);
        if (validatorMessage != null) {
          JOptionPane.showMessageDialog(btnAddProduct, validatorMessage, "Error",
              JOptionPane.ERROR_MESSAGE);
        } else {
          Collection<Product> products = productMap.values();
          ProductIdGenerator productIdGenerator = new ProductIdGenerator();
          BarCodeGenerator barCodeGenerator = new BarCodeGenerator();
          int barCode = barCodeGenerator.generateBarCode();
          Product product = new Product(
              productIdGenerator.getProductId(products, productCategory),
              productName, productDescription,
              Long.valueOf(quantityAvailable), Double.valueOf(
                  price),
              barCode, Long.valueOf(reorderQuantity),
              Long.valueOf(orderQuantity));
          // add new member to memory
          try {
            productMap.put(barCode, product);
          } catch (Exception ex) {
            DisplayUtil.displayValidationError(contentPanel,
                StoreConstants.ERROR + " creating new product");
          }

          // write new memeber from memory to .dat file
          IOService<?> ioManager = new IOService<Entity>();
          try {
            ioManager.writeToFile(productMap.values(), new sg.edu.nus.iss.ssa.model.Product());
            ioManager = null;
          } catch (Exception ex)

          {
            DisplayUtil.displayValidationError(contentPanel,
                StoreConstants.ERROR + " saving new product");
            ioManager = null;
          }

          // update the table data in MemberManagerWindow
          productManagerWindow.refreshTable(product.getProductArray());
          dispose();
        }
      }
    });
    buttonPanel.add(btnAddProduct);

    JButton btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    buttonPanel.add(btnCancel);
  }

  private void fetchValuesFromTextFields() {
    productCategory = (String) txtProductCategory.getSelectedItem();
    productName = txtProductName.getText();
    productDescription = txtProductDescription.getText();
    quantityAvailable = txtQuantityAvailable.getText();
    price = txtPrice.getText();
    reorderQuantity = txtReorderQuantity.getText();
    orderQuantity = txtOrderQuantity.getText();
  }
}
