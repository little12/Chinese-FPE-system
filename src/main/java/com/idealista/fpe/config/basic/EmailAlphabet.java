package com.idealista.fpe.config.basic;

import com.idealista.fpe.config.Alphabet;

public class EmailAlphabet implements Alphabet {
    private static final char[] LOWER_CASE_CHARS =  new char[] {'0', '1', '2','3', '4', '5', '6', '7',
            '8', '9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    @Override
    public char[] availableCharacters() {
        return new char[0];
    }

    @Override
    public Integer radix() {
        return null;
    }
}

