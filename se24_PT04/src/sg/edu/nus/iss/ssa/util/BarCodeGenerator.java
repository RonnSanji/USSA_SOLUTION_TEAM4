package sg.edu.nus.iss.ssa.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur Jain <br/>
 * Date: 3/20/16 <br/>
 * Time: 3:21 PM <br/>
 */
public class BarCodeGenerator {
public int generateBarCode(){
  AtomicInteger sequence = new AtomicInteger();
  int nextVal = sequence.incrementAndGet();
   return nextVal;
}

}
