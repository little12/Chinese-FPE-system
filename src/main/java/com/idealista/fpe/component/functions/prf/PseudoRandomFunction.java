package com.idealista.fpe.component.functions.prf;

/**
 * the interface of implement of AES
 * @author 1
 *
 */
public interface PseudoRandomFunction {
    byte[] apply(byte[] text);
}
