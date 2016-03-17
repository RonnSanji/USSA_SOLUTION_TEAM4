package sg.edu.nus.iss.ssa.bo;

import sg.edu.nus.iss.ssa.model.StoreKeeper;

/**
 * 
 * @author Tang Han (Tom)
 *
 */

public class AuthenticationManager {
	
	protected String errorMessage = null;

	public boolean authenticateUser(String username, String password) {
		StoreKeeper storeKeeper = (StoreKeeper) FileDataWrapper.storeKeeperMap.get(username);
		if (storeKeeper == null) {
			errorMessage = "StoreKeeper not found.";
			return false;
		}
		if (!storeKeeper.getPassword().equals(password)) {
			errorMessage = "Incorrect password";
			return false;
		}		
		return true;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
