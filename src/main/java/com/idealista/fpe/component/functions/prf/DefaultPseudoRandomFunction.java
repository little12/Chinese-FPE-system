package com.idealista.fpe.component.functions.prf;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Ä¬ÈÏËæ»úÖÃ»»º¯ÊýAES
 * @author 1
 *
 */
public class DefaultPseudoRandomFunction implements PseudoRandomFunction {

    private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
    private static final String KEY_ALGORITHM_NAME = "AES";

    private final byte[] key;
    private byte[] initializationVector = initializationVector();
    
    /**
     *  return the key
     * @throws NoSuchAlgorithmException 
     */
    public DefaultPseudoRandomFunction(byte[] key){
    	this.key = key;
    }
    
    /**
     * implement of AES
     */
    public byte[] apply(byte[] plain) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, KEY_ALGORITHM_NAME), new IvParameterSpec(initializationVector));
            byte[] result = cipher.doFinal(plain);
            return Arrays.copyOfRange(result, result.length - initializationVector.length, result.length);
        } catch (GeneralSecurityException e) {
            throw new SecurityException(e);
        }

    }
    
    /**
     * initial of capacity size
     */
    private static byte[] initializationVector() {
        byte[] initializationVector = new byte[16];
        for (int i = 0; i < initializationVector.length; i++) {
            initializationVector[i] = (byte) 0x00;
        }
        return initializationVector;
    }
}
