package sg.edu.nus.iss.ssa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import sg.edu.nus.iss.ssa.bo.FileDataWrapper;
import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.model.Entity;
import sg.edu.nus.iss.ssa.model.Product;
import sg.edu.nus.iss.ssa.util.DisplayUtil;
import sg.edu.nus.iss.ssa.util.IOService;
import sg.edu.nus.iss.ssa.util.Printer;

public class ManageProductWindow extends JPanel {

  private JScrollPane scrollPane;
  private JPanel buttonPanel;
  private JTable table;
  private JButton btnAddNewProduct;
  private JButton btnRemoveProduct;
  private DefaultTableModel model;
  private JTextField txtProductSearch;
  private Collection<Product> productList;
  private List<Product> productListResult;
  JComboBox comboBoxProductSearch;
  private String[] comboBoxSearchByItem = new String[] { "Product Name", "Bar Code" };
  private String[] columns = new String[] { "Product Id","Product Name",
      "Product Description", "Quantity Available", "Price","Bar Code","Reorder Quantity", "Order Quantity" };
  private String searchBy;
  private Product productToEdit;
  private int selectedRow;
  private JTextField txtPrintCopy;

  /**
   * Create the application.
   */
  public ManageProductWindow() {

    ManageProductWindow manageProductWindow = this;

    setSize(800, 600);

    this.setOpaque(false);
    setLayout(null);
    scrollPane = new JScrollPane();
    scrollPane.setBounds(12, 87, 782, 364);
    this.add(scrollPane);
    buttonPanel = new JPanel();
    buttonPanel.setBounds(12, 481, 782, 66);
    this.add(buttonPanel);
    buttonPanel.setOpaque(false);

    btnAddNewProduct = new JButton("Add Product");
    btnAddNewProduct.setBounds(20, 5, 160, 55);
    btnAddNewProduct.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AddProduct addProductWindow = new AddProduct(FileDataWrapper.productMap,
            manageProductWindow);
        addProductWindow.setModal(true);
        addProductWindow.setLocation(manageProductWindow.getLocationOnScreen());
        addProductWindow.setVisible(true);
      }
    });
    buttonPanel.setLayout(null);
    buttonPanel.add(btnAddNewProduct);

    btnRemoveProduct = new JButton("Remove Product");
    btnRemoveProduct.setBounds(603, 5, 160, 55);
    btnRemoveProduct.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        String selectedRowKey = null;
        try {
          selectedRowKey = table.getValueAt(selectedRow, 5).toString();
        } catch (Exception es) {
        	DisplayUtil.displayValidationError(table, StoreConstants.SELECT_PRODUCT);
        //  JOptionPane.showMessageDialog(table, StoreConstants.SELECT_PRODUCT, "Error",
             // JOptionPane.ERROR_MESSAGE);
        }
        // check if any row is selected
        if (selectedRow != -1) {
         // System.out.print(selectedRow);
          int option = DisplayUtil.displayConfirmationMessage(table, StoreConstants.CONFIRM_REMOVE_PRODUCT);
        		  //JOptionPane.showConfirmDialog(table, "Confirm to remove this Product?",
             // "Confirmation", JOptionPane.OK_CANCEL_OPTION);
         // System.out.println(option);
          if (option == 0) {
            // Confirm Remove
            model.removeRow(selectedRow);
            FileDataWrapper.productMap.remove(selectedRowKey);
            // update the .dat file
            updateDatFile();
          }

        }

      }
    });
    buttonPanel.add(btnRemoveProduct);

    JButton btnEditProduct = new JButton("Edit Product");
    btnEditProduct.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

            selectedRow = table.getSelectedRow();
            String selectedRowKey =null;
            try
            {
              selectedRowKey = table.getValueAt(selectedRow, 5).toString();
            }
            catch( Exception es)
            {
              JOptionPane.showMessageDialog(table, "Please select at least one row", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if(selectedRow != -1){
              productToEdit = FileDataWrapper.productMap.get(table.getValueAt(selectedRow, 5));
              ProductEditWindow productEditWindow = new ProductEditWindow(productToEdit,manageProductWindow);
              productEditWindow.setModal(true);
              productEditWindow.setVisible(true);
            }
          }
        });
    btnEditProduct.setBounds(316, 5, 160, 55);
    buttonPanel.add(btnEditProduct);

    
    
    // jTable Data Display

    Object[] products = FileDataWrapper.productMap.values().toArray();
    Object[][] data = new Object[products.length][];

    // actual data for the table in a 2d array
    for (int i = 0; i <products.length; i++) {
      data[i] = ((Product) products[i]).getProductArray();
    }
    model = new DefaultTableModel(data, columns) {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    model.isCellEditable(0, 0);
    table = new JTable(model);
    table.setFillsViewportHeight(true);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scrollPane.setViewportView(table);
    
    JLabel lblSearchBy = new JLabel("Search by:");
    lblSearchBy.setBounds(12, 33, 73, 16);
    add(lblSearchBy);

    comboBoxProductSearch = new JComboBox();
    comboBoxProductSearch.setModel(new DefaultComboBoxModel(comboBoxSearchByItem));
    comboBoxProductSearch.setBounds(75, 27, 125, 28);
    add(comboBoxProductSearch);

    txtProductSearch = new JTextField();
    txtProductSearch.setBounds(210, 27, 174, 28);
    add(txtProductSearch);
    txtProductSearch.setColumns(10);

    JButton btnProductSearch = new JButton("Search");
    btnProductSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        productSearch();
      }
    });
    btnProductSearch.setBounds(394, 27, 111, 28);
    add(btnProductSearch);

    JButton btnClear = new JButton("Clear");
    btnClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	txtProductSearch.setText("");
        table.setModel(model);
        
        // set resultlist
        productList = (Collection<Product>) FileDataWrapper.productMap.values();
        productListResult = new ArrayList<Product>();
        for (Product product : productList) {
        	productListResult.add(product);
        }
      }
    });
    btnClear.setBounds(515, 27, 111, 28);
    add(btnClear);
    
    JPanel panel = new JPanel();
    panel.setBounds(636, 20, 158, 40);
    add(panel);
    panel.setLayout(null);
    
    JButton btnPrintLabel = new JButton("Print Label");
    btnPrintLabel.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		printLabel();
    	}
    });
    btnPrintLabel.setBounds(5, 7, 96, 28);
    panel.add(btnPrintLabel);
    
    JLabel lblNewLabel = new JLabel("X");
    lblNewLabel.setBounds(105, 12, 17, 14);
    panel.add(lblNewLabel);
    
    txtPrintCopy = new JTextField();
    txtPrintCopy.setBounds(115, 8, 35, 25);
    panel.add(txtPrintCopy);
    txtPrintCopy.setColumns(10);
    txtPrintCopy.setDocument(new PlainDocument() {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str.matches("[0-9]*") && getLength() + str.length() <= 2)
				super.insertString(offs, str, a);
		}
	});
    
    // frist load set resultlist
    productList = (Collection<Product>) FileDataWrapper.productMap.values();
    productListResult = new ArrayList<Product>();
    for (Product product : productList) {
    	productListResult.add(product);
    }
    
  }

  protected void printLabel() {
	if(txtPrintCopy.getText() == "")
	{
		DisplayUtil.displayValidationError(table, StoreConstants.ENTER_PRINT_COPY);
		return;
	}
	int copy = 0;
	try
	{
		copy = Integer.parseInt(txtPrintCopy.getText().trim());
	}
	catch(Exception ex)
	{
		DisplayUtil.displayValidationError(table, StoreConstants.INVALID_PRINT_COPY);
		return;
	}
	AutoCloseMessageWindow win = new AutoCloseMessageWindow("Message", "Printing labels...", 2);
	win.setLocation(table.getLocationOnScreen());
	win.setVisible(true);
	
	Printer printer = new Printer();
	printer.printLabel((ArrayList<Product>) productListResult, copy);
	
}

public void refreshTable(String[] productProperty) {
    this.model.addRow(productProperty);
  }

  public void updateEditedProduct(){
    model.setValueAt(productToEdit.getProductName(), selectedRow, 1);
    model.setValueAt(productToEdit.getProductDesc(), selectedRow, 2);
    model.setValueAt(productToEdit.getQuantity(), selectedRow, 3);
    model.setValueAt(productToEdit.getPrice(), selectedRow, 4);
    model.setValueAt(productToEdit.getThresholdQuantity(), selectedRow, 5);
    model.setValueAt(productToEdit.getOrderQuantity(), selectedRow, 6);
    FileDataWrapper.productMap.put(productToEdit.getBarCode(), productToEdit);
    table.setModel(model);
    updateDatFile();
  }

  private void productSearch() {
    productList = (Collection<Product>) FileDataWrapper.productMap.values();
    productListResult = new ArrayList<Product>();
    if (comboBoxProductSearch.getSelectedItem() != null) {
      searchBy = comboBoxProductSearch.getSelectedItem().toString();
      for (Product product : productList) {
        if (searchBy == comboBoxSearchByItem[0]) {
          if (product.getProductName().toUpperCase()
              .contains(txtProductSearch.getText().toUpperCase())) {
            productListResult.add(product);
          }
        }
        else if (searchBy == comboBoxSearchByItem[1]) {
          if (product.getProductId().toUpperCase()
              .contains(txtProductSearch.getText().toUpperCase())) {
            productListResult.add(product);
          }
        }
      }
    }
    showSearchResult(productListResult.toArray());
  }

  private void showSearchResult(Object[] products) {

    Object[][] data = new Object[products.length][];
    for (int i = 0; i < products.length; i++) {
      data[i] = ((Product) products[i]).getProductArray();
    }
    DefaultTableModel modelResult = new DefaultTableModel(data, columns) {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    modelResult.isCellEditable(0, 0);

    table.setModel(modelResult);

  }

  private void updateDatFile(){
    // update the .dat file
    IOService<?> ioManager = new IOService<Entity>();
    try {
      ioManager.writeToFile(FileDataWrapper.productMap.values(), new Product());
      ioManager = null;
    } catch (Exception ex)
    {
      DisplayUtil.displayValidationError(buttonPanel, StoreConstants.ERROR + " saving new product");
      ioManager = null;
    }
  }
}
