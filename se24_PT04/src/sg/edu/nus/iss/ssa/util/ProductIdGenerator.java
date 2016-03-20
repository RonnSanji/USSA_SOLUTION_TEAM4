package sg.edu.nus.iss.ssa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.iss.ssa.model.Product;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur Jain <br/>
 * Date: 3/20/16 <br/>
 * Time: 11:55 AM <br/>
 */
public class ProductIdGenerator {
public String getProductId(Collection<Product> products,String category){
  List<String> productIds = new ArrayList<>();
  String newProductId = null;
  Long maxCount = 0L;
  for(Product p: products){
    productIds.add(p.getProductId());
  }
  for(String productId: productIds){
    if(productId.contains(category)){
      String parts[] = productId.split("/");
      if(Long.valueOf(parts[1])>maxCount){
       maxCount=Long.valueOf(parts[1]);
      }
    }
  }
   maxCount=maxCount+1L;
   newProductId = category+"/"+maxCount;
  return newProductId;
}
}
