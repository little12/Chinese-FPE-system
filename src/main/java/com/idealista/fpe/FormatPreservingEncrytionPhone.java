package com.idealista.fpe;

import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.namefile.Extract;
import com.idealista.fpe.namefile.Phone;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class FormatPreservingEncrytionPhone {
	private String newPhone; //�����ĵ绰����
	//private int count;  //ģָ��
	private  int round; //����
	private String first;
	private int phoneSize;

/*
	public static void main(String[] args) {
		String key = "123456";
		String name = "��˫";
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
	 * ���ܵ绰����
	 * @param phone
	 * @param key
	 * @param tweak
	 * @return
	 */
	public String encryptPhone(String phone, byte[]key, byte[] tweak){
		//����ǰ��λ
		Extract extract = new Extract();
		newPhone = extract.extractPhone(phone);

		//���ܺ�λ
		FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
		String cipher = formatPreservingEncryption.encrypt(newPhone.substring(2),tweak);

		//�ⳤ
		Phone phoneList = new Phone();
		phoneSize = phoneList.getOriginPhone().size();

		//��ȡǰ��λ
		first = newPhone.substring(0, 2);
		round = phoneSize;
		/*
		if(Integer.parseInt(first)>= phoneSize) {//���ܽ����������Ϣ�ռ�
			count = Integer.parseInt(first) / phoneSize;
			first = Integer.toString(Integer.parseInt(first) % phoneSize);
		}*/
		while(round>=phoneSize) {
			first = formatPreservingEncryption.encrypt(first, tweak);
			round = Integer.parseInt(first);
		}
		//ӳ��ص绰����ǰ��λ
		String phoneFirst = phoneList.getOriginPhone().get(round);

		StringBuilder cipherPhone = new StringBuilder();
		cipherPhone.append(phoneFirst);
		cipherPhone.append(cipher);

		return cipherPhone.toString();
	}

	public String decryptPhone(String phone, byte[]key, byte[] tweak){
		//����ǰ��λ
		Extract extract = new Extract();
		newPhone = extract.extractPhone(phone);

		//����ǰ׺
		first = newPhone.substring(0,2);
		FormatPreservingEncryption formatPreservingEncryption = defaultFormatPreservingEncryption(key);
		round = phoneSize;
		while (round >= phoneSize){
			first = formatPreservingEncryption.decrypt(first,tweak);
			round = Integer.parseInt(first);
		}
		//ӳ��ص绰����ǰ��λ
		Phone phoneList = new Phone();
		String phoneFirst = phoneList.getOriginPhone().get(round);

		//���ܺ�׺
		String phoneLast = formatPreservingEncryption.decrypt(newPhone.substring(2),tweak);

		StringBuilder plainPhone = new StringBuilder();
		plainPhone.append(phoneFirst);
		plainPhone.append(phoneLast);

		return plainPhone.toString();
	}

	/**
	 * ��idת��Ϊ�������ӣ���λ��ǰ����λ�ں�
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

