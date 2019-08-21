package com.idealista.fpe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.namefile.Extract;
import com.idealista.fpe.namefile.FirstName;
import com.idealista.fpe.namefile.LastName;

public class FormatPreservingEncryptionForName {
	private int lastSize=0;
	private int firstSize=0; 
	public String encryptName(String name, byte[]key, byte[] tweak){
	    	FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
			
			//提取名字
	    	Extract extract = new Extract();
	    	StringBuilder encryptName = new StringBuilder();
		    List<String> nameList = extract.extracName(name);
		    
		    //lastname
		    int round = 0;
		    String last = nameList.get(0);
		   
		    if(last.length()<2) {
		    	last = "00" + last;
		    }
		    	
		    else if(last.length()<3) {
		    	last = "0"+last;
		    }
			
		    last = formatPreservingEncryption.encrypt(last, tweak);
		    
		    while(last.substring(0,1).equals("0") && last.length()>1) {
				last = last.substring(1);
			}
			round = Integer.parseInt(last);
			lastSize = LastName.getOriginLastname().size();
		    while(round>=lastSize) {
		    	last = formatPreservingEncryption.encrypt(last, tweak);
		    	while(last.substring(0,1).equals("0") && last.length() > 1) {
		    		last = last.substring(1);
		    	}
		    	round = Integer.parseInt(last);
		    }

		    encryptName.append(LastName.getOriginLastname().get(round));
		    	
		    int round2=0;
		    String first = null;
		    firstSize = FirstName.getOriginFirstname().size();
		    for(int i=1;i<nameList.size();i++) {
		    	first = nameList.get(i);
		    	if(first.length()<2) {
		    		first = "00" + first;
		    	}
		    	else if(first.length()<3) {
		    		first = "0" + first;
		    	}
		    	//else if(first.length()<4) {
		    	//	first = "0"+first;
		    	//}
		    	first = formatPreservingEncryption.encrypt(first, tweak);
		    	while(first.substring(0,1).equals("0") && first.length()>1) {
		    		first = first.substring(1);
		    	}
		    	round2 = Integer.parseInt(first);
		    	while(round2>=firstSize) {
		    		first = formatPreservingEncryption.encrypt(first, tweak);
		    		while(first.substring(0, 1).equals("0") && first.length()>1) {
		    			first=first.substring(1);
		    		}
		        	round2 = Integer.parseInt(first);
		        	
		    	}

		    	encryptName.append(FirstName.getOriginFirstname().get(round2));
		    }
	    	return encryptName.toString();
	    }
	 
	 public String decryptName(String name, byte[]key, byte[] tweak){
	    	FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
			
	    	Extract extract = new Extract();
	    	StringBuilder decryptName = new StringBuilder();
		    List<String> nameList = extract.extracName(name);
		    
		    //lastname
		    int round = 0;
		    String last = nameList.get(0);
		    if(last.length()<2) {
		    	last = "00" + last;
		    }
		    	
		    else if(last.length()<3) {
		    	last = "0"+last;
		    }

		    last = formatPreservingEncryption.decrypt(last, tweak);
		    
		    while(last.substring(0,1).equals("0") && last.length()>1 ) {
				last = last.substring(1);
			}
			round = Integer.parseInt(last);
			//System.out.println(round);
			//int lastSize = LastName.getOriginLastname().size();
		    while(round>=lastSize) {
		    	last = formatPreservingEncryption.decrypt(last, tweak);
		    	while(last.substring(0,1).equals("0") && last.length()>1 ) {
		    		last = last.substring(1);
		    	}
		    	round = Integer.parseInt(last);
		    }

		    decryptName.append(LastName.getOriginLastname().get(round));
		    	
		    int round2=0;
		    String first = null;
		    for(int i=1;i<nameList.size();i++) {
		    	first = nameList.get(i);
		    	if(first.length()<2) {
		    		first = "00" + first;
		    	}
		    	else if(first.length()<3) {
		    		first = "0" + first;
		    	}
		    	//else if(first.length()<4) {
		    	//	first = "0"+first;
		    	//}
		    	first = formatPreservingEncryption.decrypt(first, tweak);
		    	while(first.substring(0,1).equals("0") && first.length()>1) {
		    		first = first.substring(1);
		    	}
		    	round2 = Integer.parseInt(first);
		    	while(round2>=firstSize) {
		    		first = formatPreservingEncryption.decrypt(first, tweak);
		    		while(first.substring(0, 1).equals("0") && first.length()>1) {
		    			first=first.substring(1);
		    		}
		        	round2 = Integer.parseInt(first);
		    	}

		    	decryptName.append(FirstName.getOriginFirstname().get(round2));
		    	//encryptName.append(first);
		    }
	    	return decryptName.toString();
	    }
	 
	 public static List<String> readFile() throws IOException{
			//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_1000.txt");
			//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_4000.txt"); 
		 	//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_100.txt"); 
		 	//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_5000.txt");
		 	File nameFile = new File("D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile\\name.txt"); 
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
	 
	 /**
	  * 将name转换为unicde编码，将名字作为其调整因子
	  * @param name
	  * @return
	 * @throws UnsupportedEncodingException 
	  */
	 private byte[] tweakOfEncrypt(String name) throws UnsupportedEncodingException {
		 int length = name.length();
		 if(length>2) {
			 name = name.substring(1,3);		 
		 }
		 byte[] tweak = name.getBytes("UTF-8");
		 
		 return tweak;
	 }
	 
	 private static FormatPreservingEncryption defaultFormatPreservingEncryption(byte[] anyKey) {
	    	return FormatPreservingEncryptionBuilder.ff1Implementation()
	                    .withDefaultDomain()
	                    .withDefaultPseudoRandomFunction(anyKey)
	                    .withDefaultLengthRange()
	                    //.withLengthRange(new lengthRange(4,20))
	                    .build();
	    }
	 
	 public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		long startTime = System.currentTimeMillis();
		List<String> name0 = readFile();
	 	
		
	 	String key = "123456";
	 	byte[] akey=FormatPreservingEncryption.generateKey(key);
	 	//byte[] aTweak = new byte[] {(byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x04};
		
	 	FormatPreservingEncryptionForName fpe = new FormatPreservingEncryptionForName();
	 	int index=0;
	 	/*
	 	String name = "李盈盈";
	 	//计算调整因子
    	byte[] tweak= fpe.tweakOfEncrypt(name);
	 	String cipher = fpe.encryptName(name, akey,tweak);
	 	//String plain = fpe.decryptName(cipher, akey, tweak);
	 	System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
	 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher +" plaintext:"+ plain);
	 	*/
		
	 	for(String name:name0) {
		 	//String name = "李盈盈";
		 	
		 	//计算调整因子
	 		System.out.println("测试" + index);
	    	byte[] tweak= fpe.tweakOfEncrypt(name);
		 	String cipher = fpe.encryptName(name, akey,tweak);
		 	String plain = fpe.decryptName(cipher, akey, tweak);
		 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
		 	//System.out.println(index+ "plain:" + name+ "cipher:" + cipher );// +" plaintext:"+ plain);
		 	System.out.println(" 明文姓名为：" + name + "  加密后的姓名为：" + cipher + "   解密后的姓名为：" + plain);
		 	index++;
	 
	 	}
	 	long endTime = System.currentTimeMillis();
		System.out.println("当前程序耗时：" + (endTime-startTime) + "ms");
	 		
	 }
	 
}
