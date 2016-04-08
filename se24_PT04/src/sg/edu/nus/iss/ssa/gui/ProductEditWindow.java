package sg.edu.nus.iss.ssa.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.validation.FormValidator;

public class ProductEditWindow extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField textProductName;
  private JTextField textProductDescription;
  private JTextField textPrice;
  private JButton btnEdit;
  private JButton btnCancel;

  /**
   * Launch the application.
   */
  //	public static void main(String[] args) {
  //		EventQueue.invokeLater(new Runnable() {
  //			public void run() {
  //				try {
  //					MemberEditWindow window = new MemberEditWindow();
  //					window.setVisible(true);
  //				} catch (Exception e) {
  //					e.printStackTrace();
  //				}
  //			}
  //		});
  //	}

  /**
   * Create the application.
   */
  public ProductEditWindow(Product productToEdit, ManageProductWindow productManagerWindow) {

    getContentPane().setLayout(null);
    setTitle("Edit Product");
    setResizable(false);
    setSize(500, 300);
    setLocationRelativeTo(null);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);

    JLabel lblProductName = new JLabel("Product Name:");
    lblProductName.setBounds(63, 50, 120, 16);
    contentPanel.add(lblProductName);

    textProductName = new JTextField();
    textProductName.setBounds(206, 44, 213, 28);
    contentPanel.add(textProductName);
    textProductName.setText(productToEdit.getProductName());
    textProductName.setColumns(10);
   
    JLabel lblProductDescription = new JLabel("Product Description:");
    lblProductDescription.setBounds(63, 106, 120, 16);
    contentPanel.add(lblProductDescription);

    textProductDescription = new JTextField();
    textProductDescription.setBounds(206, 100, 213, 28);
    contentPanel.add(textProductDescription);
    textProductDescription.setText(productToEdit.getProductDesc());
    textProductDescription.setColumns(10);
  
   JLabel lblPrice = new JLabel("Price:");
    lblPrice.setBounds(64, 164, 106, 16);
    contentPanel.add(lblPrice);

    textPrice = new JTextField();
    textPrice.setBounds(206, 158, 213, 28);
    contentPanel.add(textPrice);
    textPrice.setText(String.valueOf(productToEdit.getPrice()));
    textPrice.setColumns(10);
    

    btnEdit = new JButton("Ok");
    btnEdit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        String newProductName = textProductName.getText();
        String newProductDescription = textProductDescription.getText();
        Double newPrice = null;
        try {
          newPrice = Double.parseDouble(textPrice.getText());
        } catch(NumberFormatException exception){
          //exception.printStackTrace();
        	DisplayUtil.displayValidationError(contentPanel, StoreConstants.INVALID_PRICE);
        	return;
        }
        if (EditValidation(newProductName,newProductDescription,newPrice)) {
          productToEdit.setProductName(newProductName);
          productToEdit.setProductDesc(newProductDescription);
          productToEdit.setPrice(newPrice);
          productManagerWindow.updateEditedProduct();
          //System.out.println(productToEdit);

          DisplayUtil.displayAcknowledgeMessage(contentPanel, StoreConstants.PRODUCT_UPDATED_SUCCESSFULLY);
          dispose();
        }

      }
    });
    btnEdit.setBounds(63, 220, 120, 45);
    contentPanel.add(btnEdit);

    btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    btnCancel.setBounds(301, 220, 120, 45);
    contentPanel.add(btnCancel);
       
  }

  // public MemberEditWindow(){}
  /**
   * Initialize the contents of the frame.
   */
  private boolean EditValidation(String newProductName, String newProductNo, Double newPrice) {
    FormValidator validator = new FormValidator();
    String validateResult = validator.editProductValidateForm(newProductName, newProductNo,newPrice);
    if (validateResult != null) {
      JOptionPane.showMessageDialog(btnEdit, validateResult, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    else {
      return true;
    }

  }
}
