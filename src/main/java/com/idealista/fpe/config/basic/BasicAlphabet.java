package com.idealista.fpe.config.basic;

import com.idealista.fpe.config.Alphabet;

public class BasicAlphabet implements Alphabet{

    /*private static final char[] LOWER_CASE_CHARS = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    */
	private static final char[] LOWER_CASE_CHARS =  new char[] {'0', '1', '2','3', '4', '5', '6', '7',
			'8', '9'};
	
    @Override
    public char[] availableCharacters() {
        return LOWER_CASE_CHARS;
    }
   
    @Override
    public Integer radix() {
        return LOWER_CASE_CHARS.length;
    }
    
 }
