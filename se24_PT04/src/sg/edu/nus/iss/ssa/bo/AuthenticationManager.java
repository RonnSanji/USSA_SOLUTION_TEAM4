package sg.edu.nus.iss.ssa.bo;

import java.util.Arrays;

import sg.edu.nus.iss.ssa.model.StoreKeeper;

/**
 * 
 * @author Tang Han (Tom)
 *
 */

public class AuthenticationManager {
	
	protected String errorMessage = null;

	public boolean authenticateUser(String username, char[] password) {
		StoreKeeper storeKeeper = (StoreKeeper) FileDataWrapper.storeKeeperMap.get(username.toLowerCase());
		if (storeKeeper == null) {
			errorMessage = "StoreKeeper not found.";
			return false;
		}
		char[] desiredPassword = storeKeeper.getPassword().toCharArray();
		if (desiredPassword.length != password.length || !Arrays.equals(password, desiredPassword)) {
			errorMessage = "Incorrect password";
			return false;
		}		
		return true;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
