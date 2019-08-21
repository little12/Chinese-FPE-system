package com.idealista.fpe;

import static com.idealista.fpe.FormatPreservingEncryptionErrorMessage.*;
import static com.idealista.fpe.config.Defaults.ALPHABET_NUM;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.idealista.fpe.algorithm.Cipher;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.builder.validate.BuildValidator;
import com.idealista.fpe.component.functions.prf.PseudoRandomFunction;
import com.idealista.fpe.config.*;

public class FormatPreservingEncryption {

    private final Cipher cipher;
    private final Domain selectedDomain;
    private final PseudoRandomFunction selectedPRF;
    private final LengthRange lengthRange;

    /*
    private static byte[] anyKey = new byte[]{
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
            (byte) 0x02, (byte) 0x02, (byte) 0x02, (byte) 0x02,
            (byte) 0x03, (byte) 0x03, (byte) 0x03, (byte) 0x03
    };*/
    
    public FormatPreservingEncryption(Cipher cipher, Domain selectedDomain, PseudoRandomFunction selectedPRF, LengthRange lengthRange) {
        new BuildValidator(selectedDomain.alphabet().radix(), lengthRange.min(), lengthRange.max()).validate();
        this.cipher = cipher;
        this.selectedDomain = selectedDomain;
        this.selectedPRF = selectedPRF;
        this.lengthRange = lengthRange;
    }
    
    public String encrypt(String plainText, byte[] tweak) {
        check(plainText, tweak);
        int[] numeralPlainText = selectedDomain.transform(plainText);
        int[] numeralCipher = cipher.encrypt(numeralPlainText, selectedDomain.alphabet().radix(), tweak, selectedPRF);
        return selectedDomain.transform(numeralCipher);
    }
    
    public String decrypt(String cipherText, byte[] tweak) {
        check(cipherText, tweak);
        int[] numeralCipherText = selectedDomain.transform(cipherText);
        int[] numeralPlainText = cipher.decrypt(numeralCipherText, selectedDomain.alphabet().radix(), tweak, selectedPRF);
        return selectedDomain.transform(numeralPlainText);
    }
    
    public static byte[] generateKey(String aKey) throws NoSuchAlgorithmException {
    	KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(aKey.getBytes());
		keyGen.init(128,secureRandom);
		SecretKey secretKey = keyGen.generateKey();
		byte[] key = secretKey.getEncoded();
		return key;
    }
    
    private void check(String text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(NULL_INPUT.toString());

        if (text.length() < lengthRange.min() ||text.length() > lengthRange.max())
            throw  new IllegalArgumentException(INVALID_SIZE + lengthRange.toString());

        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(TWEAK_INVALID_SIZE.toString() + lengthRange.max());

    }
    
    
    private static FormatPreservingEncryption defaultFormatPreservingEncryption(byte[] anyKey) {
    	return FormatPreservingEncryptionBuilder.ff1Implementation()
                    .withDefaultDomain()
                    .withDefaultPseudoRandomFunction(anyKey)
                    .withDefaultLengthRange()
                    //.withLengthRange(new lengthRange(4,20))
                    .build();
    }

    /*
    public static void main(String[] args){
        String key = "123456";
        try {
            for(int i=0;i<10;i++){
                byte[] akey = generateKey(key);
                String str = Base64.getEncoder().encodeToString(akey);
                System.out.println(str);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }*/
}
