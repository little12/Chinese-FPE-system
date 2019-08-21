package com.idealista.fpe.namefile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Extract {
	static final String PATH = "D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile";
	static final String Filename_Of_Lastname = "lastnameIn.txt";
	static final String Filename_Of_Firstname = "firstnameIn.txt";
	static final String Filename_Of_Phone = "phone.txt";
	static final String Filename_Of_Email = "email.txt";

	static final  boolean LAST = true;
	static final boolean FIRST = false;

	public String extractEmail(String emailSuffix){
        List<String> emailList= Email.getOriginEmail();

        int size = emailList.size();
        String emailCode=null;
        for(int i=0;i<size;i++){
            if(emailSuffix.equals(emailList.get(i).substring(1))){
                if(i<10){
                    emailCode = '0'+Integer.toString(i);
                }
                else{
                    emailCode = Integer.toString(i);
                }
                break;
            }
        }
        if (emailCode == null){
            addPhone(emailSuffix);
            emailCode = Integer.toString(size);
        }

        return emailCode;
    }
	/**
	 * ���ر�����10λ�ֻ��ţ�������Ϊ��λ�������0
	 * @param phone
	 * @return
	 */
	public String extractPhone(String phone){
		String phoneFirst = phone.substring(0,3);
		String phoneSecond = phone.substring(3);

		List<String> phoneList= Phone.getOriginPhone();

		int size = phoneList.size();
		String newFirst=null;
		for(int i=0;i<size;i++){
			if(phoneFirst.equals(phoneList.get(i))){
				if(i<10){
					newFirst = '0'+Integer.toString(i);
				}
				else{
					newFirst = Integer.toString(i);
				}
				break;
			}
		}
		if (newFirst == null){
			addPhone(phoneFirst);
			newFirst = Integer.toString(size);
		}

		StringBuilder phoneCode = new StringBuilder();
		phoneCode.append(newFirst);
		phoneCode.append(phoneSecond);

		return phoneCode.toString();

	}
	/**
	 * ������ȡ�㷨
	 * @param name
	 * @return
	 */
	public List<String> extracName(String name){
		List<String> lastnameList= LastName.getOriginLastname();
		List<String> firstnameList=FirstName.getOriginFirstname();
		
		List<String> newNameList = new ArrayList<String>(3);
		//�����Ͽ��в���
		String lastname = null;//�ҵ�������
		String firstname = null;
		int lastIndex = 0;
		int nameLength = name.length();
		for(int index=0, size = lastnameList.size();index<size;index++) {
			for(int i=0;i<nameLength;i++) {
				//�ƥ��ԭ��
				String str = name.substring(0, nameLength-i);
				if(lastnameList.get(index).equals(str)){
					lastname = lastnameList.get(index);
					lastIndex = index;
				}
			}
			if((lastname!= null) && ((nameLength == 4 && lastname.length() == nameLength-2) ||(nameLength == 3 && lastname.length()== nameLength-1)||(nameLength == 2 && lastname.length()== nameLength-1))) {
				//newNameList.add(index);//����ѯ�����յ���������List,��ż��λ�浵����
				break;
			}
			
		}
		
		//�޳����ϣ�ֻʣ����
		if(lastname == null) {//���Ͽ���û��
			if(nameLength==2 || nameLength == 3) {
				lastname = name.substring(0, nameLength/2);
				firstname = name.substring(nameLength/2);
				
			}
			else if(nameLength == 4) {//����
				lastname = name.substring(0,2);
				firstname = name.substring(2);
			}
			
			System.out.println("�����ϡ�" + lastname +"����ӵ����Ͽ���");
			addName(lastname, LAST); //�������Ͽ�
			lastIndex = lastnameList.size();
			//newNameList.add(lastnameList.size());
		}else {
			int lengthOfLastname = lastname.length();
			firstname =  name.substring(lengthOfLastname);
		}
		
		newNameList.add(Integer.toString(lastIndex));
		//newNameList.add(lastname);//��������list
		
		//�����ֲ�֣�������list
		List<String> firstName = new ArrayList<String>(2);
		for(int i=0, length = firstname.length();i<length;i++) {
			firstName.add(firstname.substring(i, i+1));
			//newNameList.add(firstname.substring(i, i+1));
		}
			
	    //�������в������֣����û�������
		int firstListSize = firstnameList.size();
		for(int i=0, size = firstName.size();i<size;i++) {
			String str = null;
			for(int j=0;j<firstListSize;j++) {
				if(firstName.get(i).equals(firstnameList.get(j))) {
					str = firstName.get(i);
					newNameList.add(Integer.toString(j));
					break;
				}
			}
			if(str == null) {//not found the firstname
				System.out.println("�����֡�" + firstName.get(i) +"����ӵ�������");
				addName(firstName.get(i), FIRST);
				newNameList.add(Integer.toString(firstListSize));
				//���������б�
				firstnameList=FirstName.getOriginFirstname();
				firstListSize = firstnameList.size();
				
			}
			//newNameList.add(firstName.get(i));
		}
		return newNameList;
	}
		
		
	/**
	 * ������չ�㷨ʵ��
	 * @param name
	 * @param mode
	 */
	public void addName(String name, boolean mode) {
		File originFile;
		if(mode) {
			originFile = new File(PATH, Filename_Of_Lastname);
		}
		else {
			originFile = new File(PATH, Filename_Of_Firstname);
		}

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(originFile, true);//true������Ԥԭ�ļ�����
			BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			//iΪlist������,ÿ������ֵ����һ������,jΪÿһ�����еĵڼ�����,˫��ѭ����֤ÿ�ζ�ȡ��һ���ִ����ļ���
			bufWriter.write(name+"\n");
			//bufWriter.newLine();

			bufWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addPhone(String phone) {
		File originFile = new File(PATH, Filename_Of_Phone);

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(originFile, true);//true������Ԥԭ�ļ�����
			BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			//iΪlist������,ÿ������ֵ����һ������,jΪÿһ�����еĵڼ�����,˫��ѭ����֤ÿ�ζ�ȡ��һ���ִ����ļ���
			bufWriter.write(phone+"\n");
			//bufWriter.newLine();

			bufWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

    public void addEmail(String emailSuffix) {
        File originFile = new File(PATH, Filename_Of_Email);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(originFile, true);//true������Ԥԭ�ļ�����
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            //iΪlist������,ÿ������ֵ����һ������,jΪÿһ�����еĵڼ�����,˫��ѭ����֤ÿ�ζ�ȡ��һ���ִ����ļ���
            bufWriter.write(emailSuffix+"\n");
            //bufWriter.newLine();

            bufWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
	
	
	/*
	public static void main(String[] args) {
		String name = "ŷ��˫";
		Extract t = new Extract();
		
		System.out.println(t.extracName(name));
	}*/
}
