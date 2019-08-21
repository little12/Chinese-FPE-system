package com.idealista.fpe;

import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.namefile.Extract;
import com.idealista.fpe.namefile.Phone;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class FormatPreservingEncrytionPhone {
	private String newPhone; //编码后的电话号码
	//private int count;  //模指数
	private  int round; //轮数
	private String first;
	private int phoneSize;

/*
	public static void main(String[] args) {
		String key = "123456";
		String name = "李双";
		String phones[] = {"18482017928", "13982770352","13982700843"};
		try {
			byte[] akey = FormatPreservingEncryption.generateKey(key);
			FormatPreservingEncrytionName fpe = new FormatPreservingEncrytionName();
			FormatPreservingEncrytionPhone fpe1 = new FormatPreservingEncrytionPhone();
			byte[] tweak = fpe.tweakOfEncrypt(name);
			for(String phone:phones){
				String cipher = fpe1.encryptPhone(phone, akey, tweak);
				String plain = fpe1.decryptPhone(cipher, akey, tweak);
				//System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
				System.out.println("plain:" + name + " cipher:" + cipher + " plaintext:" + plain);
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * 加密电话号码
	 * @param phone
	 * @param key
	 * @param tweak
	 * @return
	 */
	public String encryptPhone(String phone, byte[]key, byte[] tweak){
		//编码前三位
		Extract extract = new Extract();
		newPhone = extract.extractPhone(phone);

		//加密后几位
		FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
		String cipher = formatPreservingEncryption.encrypt(newPhone.substring(2),tweak);

		//库长
		Phone phoneList = new Phone();
		phoneSize = phoneList.getOriginPhone().size();

		//截取前两位
		first = newPhone.substring(0, 2);
		round = phoneSize;
		/*
		if(Integer.parseInt(first)>= phoneSize) {//加密结果超过了消息空间
			count = Integer.parseInt(first) / phoneSize;
			first = Integer.toString(Integer.parseInt(first) % phoneSize);
		}*/
		while(round>=phoneSize) {
			first = formatPreservingEncryption.encrypt(first, tweak);
			round = Integer.parseInt(first);
		}
		//映射回电话号码前三位
		String phoneFirst = phoneList.getOriginPhone().get(round);

		StringBuilder cipherPhone = new StringBuilder();
		cipherPhone.append(phoneFirst);
		cipherPhone.append(cipher);

		return cipherPhone.toString();
	}

	public String decryptPhone(String phone, byte[]key, byte[] tweak){
		//编码前三位
		Extract extract = new Extract();
		newPhone = extract.extractPhone(phone);

		//解密前缀
		first = newPhone.substring(0,2);
		FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
		round = phoneSize;
		while (round >= phoneSize){
			first = formatPreservingEncryption.decrypt(first,tweak);
			round = Integer.parseInt(first);
		}
		//映射回电话号码前三位
		Phone phoneList = new Phone();
		String phoneFirst = phoneList.getOriginPhone().get(round);

		//解密后缀
		String phoneLast = formatPreservingEncryption.decrypt(newPhone.substring(2),tweak);

		StringBuilder plainPhone = new StringBuilder();
		plainPhone.append(phoneFirst);
		plainPhone.append(phoneLast);

		return plainPhone.toString();
	}

	/**
	 * 将id转化为调整因子，高位在前，低位在后
	 * @param value
	 * @return
	 */
	public byte[] tweakOfEncrypt(int value) {
		byte[] tweak = new byte[16];
		for(int i=15,j=0;i<16;i--,j=j+8){
			tweak[i] = (byte)((value>>j)&0xFF);
		}
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

}

