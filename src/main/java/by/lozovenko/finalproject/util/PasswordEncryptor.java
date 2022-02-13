package by.lozovenko.finalproject.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryptor {

    private PasswordEncryptor(){
    }

    public static String encryptMd5Apache(String string) {
        return DigestUtils.md5Hex(string);
    }
}
