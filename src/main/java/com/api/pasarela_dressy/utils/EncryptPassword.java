package com.api.pasarela_dressy.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class EncryptPassword
{

    /**
     * Encripta la contraseño pasada como parámentro
     * @param password String
     * @return String
     */
    public String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifica si las contraseñas coinciden
     * @param password String
     * @param hash String
     * @return boolean
     */
    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
