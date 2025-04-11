package com.example.repeatwithme.utility;

import org.apache.commons.codec.digest.DigestUtils;

public class Cripting {
    public static String encryptMD5(String testoChiaro){
        String key = DigestUtils.md5Hex(testoChiaro).toUpperCase();
        return key;
    }
    public static boolean checkMD5(String password, String testoChiaro){
        if (password.equals(encryptMD5(testoChiaro).toUpperCase()))
            return true;
        else
            return false;
    }
}
