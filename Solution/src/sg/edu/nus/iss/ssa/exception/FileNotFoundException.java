package sg.edu.nus.iss.ssa.exception;

/**
 * This Exception will be thrown if file is not present.
 * @author Amarjeet B Singh
 *
 */
public class FileNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5128606477717236704L;

	public FileNotFoundException(String message){
		super(message);
	}

}
