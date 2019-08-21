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
import com.idealista.fpe.namefile.Phone;

public class FormatPreservingEncrytionName {
	
	private int lastMod = 0;
	private int firstMod = 0;
	private int firstMod2 = 0;
	private int lastSize=0;
	private int firstSize=0;
	
	public String encryptName(String name, byte[]key, byte[] tweak){
    	FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
		
		//提取名字
    	Extract extract = new Extract();
    	StringBuilder encryptName = new StringBuilder();
    	
    	StringBuilder testName = new StringBuilder();
	    List<String> nameList = extract.extracName(name);

	    //lastname
	    //System.out.println(nameList);
	    String last = nameList.get(0);
	    
	    if(last.length()<2) {
	    	last = "00" + last;
	    }
	    	
	    else if(last.length()<3) {
	    	last = "0"+last;
	    }
		
	    testName.append(last);
	    
	    String first = null;
	 
	    for(int i=1;i<nameList.size();i++) {
	    	first = nameList.get(i);
	    	if(first.length()<2) {
	    		first = "000" + first;
	    	}
	    	else if(first.length()<3) {
	    		first = "00" + first;
	    	}
	    	else if(first.length()<4) {
	    		first = "0"+first;
	    	}
	    	
	    	testName.append(first);
	    }
	    
	    String text = testName.toString();  
	    String cipher = formatPreservingEncryption.encrypt(text, tweak);
	   // System.out.println(cipher);
	    
	    lastSize = LastName.getOriginLastname().size();
	    lastMod = 0;
	    
	    last = cipher.substring(0, 3);
	    if(Integer.parseInt(last)>= lastSize) {//加密结果超过了消息空间
	    	lastMod = Integer.parseInt(last) / lastSize;
	    	last = Integer.toString(Integer.parseInt(last) % lastSize);
	    }
		while(last.substring(0,1).equals("0") && last.length()>1) {
			last = last.substring(1);
		}
		//System.out.println(last);
	    encryptName.append(LastName.getOriginLastname().get(Integer.parseInt(last)));
	    
	    //初始化
	    firstSize = FirstName.getOriginFirstname().size();
	    firstMod=0;
	    firstMod2=0;
	    
	    for(int i=3;i<testName.length();i+=4) {
		    first = cipher.substring(i, i+4);
		    if(Integer.parseInt(first)>= firstSize) {
		    	if(i==3) {
		    		firstMod = Integer.parseInt(first) / firstSize;
		    	}
		    	else if(i==7) {
		    		firstMod2= Integer.parseInt(first) / firstSize;
		    	}
		    	first = Integer.toString(Integer.parseInt(first) % firstSize);
		    }
		    while(first.substring(0,1).equals("0") && first.length()>1) {
				first = first.substring(1);
			}
		    //System.out.println(first);
		    encryptName.append(FirstName.getOriginFirstname().get(Integer.parseInt(first)));
		    
	    }
    	return encryptName.toString();
    }
 
	 public String decryptName(String name, byte[]key, byte[] tweak){
		 FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
			//提取名字
	    	Extract extract = new Extract();
	    	StringBuilder encryptName = new StringBuilder();
	    	
	    	StringBuilder testName = new StringBuilder();
		    List<String> nameList = extract.extracName(name);
		   
		    //System.out.println(nameList);
		    String last = nameList.get(0);
		    last = Integer.toString(Integer.parseInt(last)+lastMod*lastSize);
		    
		    if(last.length()<2) {
		    	last = "00" + last;
		    }
		    	
		    else if(last.length()<3) {
		    	last = "0"+last;
		    }
		    testName.append(last);
		    
		    //System.out.println(last);
		    String first = null;

		    for(int i=1;i<nameList.size();i++) {
		    	first = nameList.get(i);
		    	if(i==1) {
		    		first = Integer.toString(Integer.parseInt(first) + firstMod*firstSize);
		    	}
		    	else if(i==2) {
		    		first = Integer.toString(Integer.parseInt(first) + firstMod2*firstSize);
		    	}
		    	
		    	if(first.length()<2) {
		    		first = "000" + first;
		    	}
		    	else if(first.length()<3) {
		    		first = "00" + first;
		    	}
		    	else if(first.length()<4) {
		    		first = "0"+first;
		    	}
		        //System.out.println(first);
		    	testName.append(first);
		    }
		    String text = testName.toString();
		    //System.out.println("姓名整型编码为：" + text);
		    String cipher = formatPreservingEncryption.decrypt(text, tweak);
		    
		    
		    last = cipher.substring(0, 3);
		    
			while(last.substring(0,1).equals("0") && last.length()>1) {
				last = last.substring(1);
			}
			
		    encryptName.append(LastName.getOriginLastname().get(Integer.parseInt(last)));
		    
		    for(int i=3;i<testName.length();i+=4) {
			    first = cipher.substring(i, i+4);
			    while(first.substring(0,1).equals("0") && first.length()>1) {
					first = first.substring(1);
				}
			    encryptName.append(FirstName.getOriginFirstname().get(Integer.parseInt(first)));
			    
		    }
	    	return encryptName.toString();
	    }
	 
	 public static List<String> readFile() throws IOException{
		 	File nameFile = new File("D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile\\name.txt"); 
		 	//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_1000.txt");
		 	//File nameFile = new File("D:\\Program Files\\eclipse-workspace\\Prefix\\src\\init", "name_4000.txt"); 
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
	 
	 /**
	  * 将name转换为unicde编码，将名字作为其调整因子
	  * @param name
	  * @return
	 * @throws UnsupportedEncodingException 
	  */
	 public byte[] tweakOfEncrypt(String name) throws UnsupportedEncodingException {
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
	  /*
	 public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		long startTime = System.currentTimeMillis();		
		List<String> name0 = readFile();
	 	
		
	 	String key = "1234567";
	 	byte[] akey=FormatPreservingEncryption.generateKey(key);
	 	//byte[] aTweak = new byte[] {(byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x04};
		
	 	FormatPreservingEncrytionName fpe = new FormatPreservingEncrytionName();
	 	int index=0;
	 	
	 	//String[] name0 = {"李明瀚","薄天林","刘彧"};
	 	//String[] name0 = {"徐洁","张亚军","张令一","李双双","欧阳天"};
	 	/*
	 	String name = "郭东现";
	 	//计算调整因子
		byte[] tweak= fpe.tweakOfEncrypt(name);
	 	String cipher = fpe.encryptName(name, akey,tweak);
	 	String plain = fpe.decryptName(cipher, akey, tweak);
	 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
	 	System.out.println(index+ "plain:" + name+ " cipher:" + cipher +" plaintext:"+ plain);


	 	for(String name:name0) {
		 	
		 	//计算调整因子
	 		System.out.println("测试" + index);
	    	byte[] tweak= fpe.tweakOfEncrypt(name);
		 	String cipher = fpe.encryptName(name, akey,tweak);
		    String plain = fpe.decryptName(cipher, akey, tweak);
		    System.out.println(" 明文姓名为：" + name + "  加密后的姓名为：" + cipher + "   解密后的姓名为：" + plain);
		 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
		 	//System.out.println(index+ "plain:" + name+ " cipher:" + cipher +" plaintext:"+ plain);
		 	index++;
	 
	 	}
	 	long endTime = System.currentTimeMillis();
		System.out.println("当前程序耗时：" + (endTime-startTime) + "ms");
	 		
	 }*/
}

