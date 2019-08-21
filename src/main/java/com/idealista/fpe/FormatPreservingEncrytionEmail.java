package com.idealista.fpe;

import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.config.Alphabet;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;
import com.idealista.fpe.namefile.Extract;
import com.idealista.fpe.namefile.Email;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class FormatPreservingEncrytionEmail {
	private String emailPrefix;
	private String emailSuffix;
	private int count;  //ģָ��
	private int emailSize;

	/*
	public static void main(String[] args){
		String key = "1234567";
		String name = "��˫";
		try {
			byte[] akey=FormatPreservingEncryption.generateKey(key);
			FormatPreservingEncrytionName fpe = new FormatPreservingEncrytionName();
			FormatPreservingEncrytionEmail fpe1 = new FormatPreservingEncrytionEmail();
			byte[] tweak= fpe.tweakOfEncrypt(name);
			String cipher = fpe1.encryptEmail("lishuang16@qq.com", akey,tweak);
			String plain = fpe1.decryptEmail(cipher, akey, tweak);
			//System.out.println(index+ "plain:" + name+ " cipher:" + cipher);
			System.out.println("plain:" + name+ " cipher:" + cipher +" plaintext:"+ plain);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}*/

	public String encryptEmail(String email, byte[]key, byte[] tweak){
		emailPrefix = email.split("@")[0];
		emailSuffix = email.split("@")[1];

		//�����׺
		Extract extract = new Extract();
		String suffixCode = extract.extractEmail(emailSuffix);

		//���ܺ�׺
		FormatPreservingEncryption defaultFormatPreservingEncryption = defaultFormatPreservingEncryption(key);
		String newSuffixCode = defaultFormatPreservingEncryption.encrypt(suffixCode,tweak);

		//�ⳤ
		Email emailList = new Email();
		emailSize = emailList.getOriginEmail().size();
		count = 0;
		if(Integer.parseInt(newSuffixCode)>= emailSize) {//���ܽ����������Ϣ�ռ�
			count = Integer.parseInt(newSuffixCode) / emailSize;
			newSuffixCode = Integer.toString(Integer.parseInt(newSuffixCode) % emailSize);
		}
		//System.out.println(last);
		//ӳ��ص绰����ǰ��λ
		String newSuffix = emailList.getOriginEmail().get(Integer.parseInt(newSuffixCode));

		//����ǰ׺
		FormatPreservingEncryption formatPreservingEncryption = formatPreservingEncryption(key);
		String newPrefix = formatPreservingEncryption.encrypt(emailPrefix,tweak);

		StringBuilder cipherEmail = new StringBuilder();
		cipherEmail.append(newPrefix);
		cipherEmail.append(newSuffix);

		return cipherEmail.toString();
	}


	public String decryptEmail(String email, byte[]key, byte[] tweak){
		emailPrefix = email.split("@")[0];
		emailSuffix = email.split("@")[1];

		//�����׺
		Extract extract = new Extract();
		String suffixCode = extract.extractEmail(emailSuffix);
		suffixCode = Integer.toString(Integer.parseInt(suffixCode)+count*emailSize);
		if(Integer.parseInt(suffixCode)<10){
			suffixCode = '0'+suffixCode;
		}

		//���ܺ�׺
		FormatPreservingEncryption defaultFormatPreservingEncryption = defaultFormatPreservingEncryption(key);
		String plainSuffixCode = defaultFormatPreservingEncryption.decrypt(suffixCode,tweak);

		Email emailList = new Email();
		String  plainSuffix= emailList.getOriginEmail().get(Integer.parseInt(plainSuffixCode));

		//����ǰ׺
		FormatPreservingEncryption formatPreservingEncryption = formatPreservingEncryption(key);
		String plainPrefix = formatPreservingEncryption.decrypt(emailPrefix,tweak);

		StringBuilder plainEmail = new StringBuilder();
		plainEmail.append(plainPrefix);
		plainEmail.append(plainSuffix);

		return plainEmail.toString();
	}
	/*
	/**
	 * ��idת��Ϊ�������ӣ���λ��ǰ����λ�ں�
	 * @param value
	 * @return
	 *
	public byte[] tweakOfEncrypt(int value) {
		byte[] tweak = new byte[16];
		for(int i=15,j=0;i<16;i--,j=j+8){
			tweak[i] = (byte)((value>>j)&0xFF);
		}
		return tweak;
	}*/

	 
	 private static FormatPreservingEncryption defaultFormatPreservingEncryption(byte[] anyKey) {
	    	return FormatPreservingEncryptionBuilder.ff1Implementation()
	                    .withDefaultDomain()
	                    .withDefaultPseudoRandomFunction(anyKey)
	                    .withDefaultLengthRange()
	                    //.withLengthRange(new lengthRange(4,20))
	                    .build();
	 }

	private static FormatPreservingEncryption formatPreservingEncryption(byte[] anyKey) {
	 	Alphabet alphabet = new Alphabet() {
			private char[] chars = {'0', '1', '2','3', '4', '5', '6', '7',
					'8', '9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
					'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

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
				.withDomain(new GenericDomain(alphabet , new GenericTransformations(alphabet .availableCharacters()), new GenericTransformations(alphabet .availableCharacters())))
				.withDefaultPseudoRandomFunction(anyKey)
				.withDefaultLengthRange()
				//.withLengthRange(new lengthRange(4,20))
				.build();
	}

}

