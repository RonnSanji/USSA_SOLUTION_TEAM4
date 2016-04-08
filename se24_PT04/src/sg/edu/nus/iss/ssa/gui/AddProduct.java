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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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
    setTitle("Adding New Product");
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
    txtProductCategory = new JComboBox();
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

    JLabel lblProductDescription = new JLabel("Product Description:");
    lblProductDescription.setBounds(59, 132, 130, 16);
    contentPanel.add(lblProductDescription);

    txtProductDescription = new JTextField();
    txtProductDescription.setBounds(214, 127, 130, 26);
    contentPanel.add(txtProductDescription);
    txtProductDescription.setColumns(10);

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

    JLabel lblNewLabel = new JLabel("ReOrder Quantity:");
    lblNewLabel.setBounds(59, 261, 119, 16);
    contentPanel.add(lblNewLabel);

    txtReorderQuantity = new JTextField();
    txtReorderQuantity.setBounds(214, 256, 130, 26);
    txtReorderQuantity.setDocument(new PlainDocument() {

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.matches("[0-9]*"))
          super.insertString(offs, str, a);
      }
    });
    contentPanel.add(txtReorderQuantity);
    txtReorderQuantity.setColumns(10);

    JLabel lblOrderQuantity = new JLabel("Order Quantity:");
    lblOrderQuantity.setBounds(59, 303, 108, 16);
    contentPanel.add(lblOrderQuantity);

    txtOrderQuantity = new JTextField();
    txtOrderQuantity.setBounds(214, 298, 130, 26);
    txtOrderQuantity.setDocument(new PlainDocument() {

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.matches("[0-9]*"))
          super.insertString(offs, str, a);
      }
    });
    contentPanel.add(txtOrderQuantity);
    txtOrderQuantity.setColumns(10);
    
        JButton btnAddProduct = new JButton("OK");
        btnAddProduct.setBounds(80, 365, 100, 50);
        contentPanel.add(btnAddProduct);

        btnAddProduct.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fetchValuesFromTextFields();
            String validatorMessage = FormValidator.addProductValidateForm(productCategory, productName, productDescription,
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
                productMap.put(String.valueOf(barCode), product);
                IOService<?> ioManager = new IOService<Entity>();
                ioManager.writeToFile(productMap.values(), new Product());
              } catch (Exception ex) {
                DisplayUtil.displayValidationError(contentPanel,
                    StoreConstants.ERROR + " creating new product");
                ex.printStackTrace();
              }

              JButton btnCancel = new JButton("Cancel");
              btnCancel.setBounds(234, 365, 100, 50);
              contentPanel.add(btnCancel);
              btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  dispose();
                }
              });

              // update the table data in MemberManagerWindow
              productManagerWindow.refreshTable(product.getProductArray());
              dispose();
            }
          }
        });
  }

  private void fetchValuesFromTextFields() {
    productCategory = (String) txtProductCategory.getSelectedItem();
    productName = txtProductName.getText().trim();
    productDescription = txtProductDescription.getText().trim();
    quantityAvailable = txtQuantityAvailable.getText().trim();
    price = txtPrice.getText().trim();
    reorderQuantity = txtReorderQuantity.getText().trim();
    orderQuantity = txtOrderQuantity.getText().trim();
  }
}
