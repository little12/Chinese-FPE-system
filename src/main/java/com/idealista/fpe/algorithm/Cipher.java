package com.idealista.fpe.algorithm;

import com.idealista.fpe.component.functions.prf.PseudoRandomFunction;

/**
 * encrypt or decrypt interface
 * @author 1
 *
 */
public interface Cipher {
    int[] encrypt(int[] plainText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction);
    int[] decrypt(int[] cipherText, Integer radix, byte[] tweak, PseudoRandomFunction pseudoRandomFunction);
}
