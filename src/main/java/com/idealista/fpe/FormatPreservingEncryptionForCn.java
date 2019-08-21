package com.idealista.fpe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.config.Alphabet;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;

public class FormatPreservingEncryptionForCn {
	    
		public String encryptUnicode(String name , byte[] akey,byte[] aTweak) {
			FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(akey);
			String code = cnToUnicode(name);
			StringBuilder codeString = new StringBuilder();
			for(int i=4;i<=code.length();i+=4) {
				String cipherCode = code.substring(i-4, i);
				cipherCode = formatPreservingEncryption.encrypt(cipherCode, aTweak);

		    	//截取unicode的第一个编码
				while((cipherCode.compareTo("4e00")<0) || (cipherCode.compareTo("9fa5")> 0)) {
		    		cipherCode = formatPreservingEncryption.encrypt(cipherCode, aTweak);
		    	}
				
				codeString.append(unicodeToCn("\\u"+cipherCode));
			}
	    	//System.out.println(code);
			
	    	return codeString.toString();
		}
		
		public String decryptUnicode(String name , byte[] akey,byte[] aTweak) {
			/*			
			String code = cnToUnicode(name);
			code = code.substring(2);
			//System.out.println(code);
			FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(akey);
		    String plainCode = formatPreservingEncryption.decrypt(code, aTweak);
		    //截取unicode的第一个编码
			
		    while(plainCode.compareTo("4e00")<0 && plainCode.compareTo("9fa5")> 0) {
		    	plainCode = formatPreservingEncryption.decrypt(plainCode, aTweak);
			}
				    	
		    String plain  = unicodeToCn("\\u" + plainCode);
						
		    return plain;*/
			FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(akey);
			String code = cnToUnicode(name);
			StringBuilder codeString = new StringBuilder();
			for(int i=4;i<=code.length();i+=4) {
				String plainCode = code.substring(i-4, i);
				plainCode = formatPreservingEncryption.decrypt(plainCode, aTweak);
		    	//截取unicode的第一个编码
				while((plainCode.compareTo("4e00")<0) || (plainCode.compareTo("9fa5")> 0)) {
		    		plainCode = formatPreservingEncryption.decrypt(plainCode, aTweak);
		    	}
				codeString.append(unicodeToCn("\\u"+plainCode));
			}
	    	//System.out.println(code);
			
	    	return codeString.toString();
		}
		
		private static String unicodeToCn(String unicode) {
			 /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
	        String[] strs = unicode.split("\\\\u");
	        String returnStr = "";
	        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
	        for (int i = 1; i < strs.length; i++) {
	          returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
	        }
	        return returnStr;
	    }
	    
	   
	    private static String cnToUnicode(String cn) {
	    	char[] chars = cn.toCharArray();
	        String returnStr = "";
	        for (int i = 0; i < chars.length; i++) {
	          returnStr += Integer.toString(chars[i], 16);
	        }
	        return returnStr;
	    }
	    
	    private static FormatPreservingEncryption defaultFormatPreservingEncryption(byte[] anyKey) {
	    	Alphabet alphabet = new Alphabet() {

	            private char[] chars = {'0', '1', '2','3', '4', '5', '6', '7',
	        			'8', '9','a','b','c','d','e','f'};

	            @Override
	            public char[] availableCharacters(){
	                return chars;
	            }

	            @Override
	            public Integer radix() {
	                return chars.length;
	            }
	        };
	    	return FormatPreservingEncryptionBuilder.ff1Implementation()
	    				.withDomain(new GenericDomain(alphabet, new GenericTransformations(alphabet.availableCharacters()), new GenericTransformations(alphabet.availableCharacters())))
	                    .withDefaultPseudoRandomFunction(anyKey)
	                    .withDefaultLengthRange()
	                    //.withLengthRange(new lengthRange(4,20))
	                    .build();
	    }
	    
	    public static List<String> readFile() throws IOException{
			//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_1000.txt");
			//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_4000.txt"); 
			File nameFile = new File("D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile\\name.txt"); 
	    	//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_5000.txt");
	    	FileReader fileReader = new FileReader(nameFile);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			List<String> nameList = new ArrayList<String>();
			String str = null;
			while((str = bufferedReader.readLine())!= null) {	
				nameList.add(str); 
					//countLine++;
			}
			bufferedReader.close();
			return nameList;
		}
	    
	   public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		    long startTime = System.currentTimeMillis();
		    
		    List<String> name0 = readFile();
		    
		   	String key = "123456";
	    	byte[] akey=FormatPreservingEncryption.generateKey(key);
	    	byte[] aTweak = new byte[] {(byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x04};
	    	FormatPreservingEncryptionForCn fpe = new FormatPreservingEncryptionForCn();
	    	int index=0;
	    	//String name = "李盈盈";
		 	
		 	//String cipher = fpe.encryptUnicode(name, akey,aTweak);
		 	//String plain = fpe.decryptUnicode(cipher, akey, aTweak);
		 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
		 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher +" plaintext:"+ plain);

		 	
	    	for(String name:name0) {
	    		System.out.println("测试" + index);
		    	String cipher = fpe.encryptUnicode(name, akey, aTweak);
		    	String plain = fpe.decryptUnicode(cipher, akey, aTweak);
		    	//System.out.println(index+ " plaintext:"+ name + "cipher:" + cipher);
		    	System.out.println(" 明文姓名为：" + name + "  加密后的姓名为：" + cipher + "   解密后的姓名为：" + plain);
		    	index++;
	    	}
	    	long endTime = System.currentTimeMillis();
			System.out.println("当前程序耗时：" + (endTime-startTime) + "ms");
	   }
}
