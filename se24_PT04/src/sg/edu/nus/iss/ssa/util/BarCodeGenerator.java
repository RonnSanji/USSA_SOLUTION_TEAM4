package sg.edu.nus.iss.ssa.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur Jain <br/>
 * Date: 3/20/16 <br/>
 * Time: 3:21 PM <br/>
 */
public class BarCodeGenerator {
  int min = 100;
  int max = 1000000000;
public int generateBarCode(){
  Random r = new Random();
  return r.ints(min,(max+1)).findFirst().getAsInt();
}

}
