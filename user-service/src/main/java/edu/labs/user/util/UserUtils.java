package edu.labs.user.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import edu.labs.user.entities.User;

public class UserUtils {
	public static boolean isNotBlank(String value) {
		return StringUtils.isNotBlank(value);
	}
	
	public static void encryptPassword(User user, String value) {
		user.setSalt(BCrypt.gensalt());
    	user.setPassword(BCrypt.hashpw(value, user.getSalt()));;
	}
}
