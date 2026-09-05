package com.fashionstore.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtility {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}