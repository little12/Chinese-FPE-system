package com.idealista.fpe.namefile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Phone {
	static final String PATH = "D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile";
	static final String FILENAME_OF_PHONE = "phone.txt";
	
	public static List<String> getOriginPhone() {
		File firstnameFile = new File(PATH, FILENAME_OF_PHONE);
		FileReader fileReader;
		try {
			fileReader = new FileReader(firstnameFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			List<String> fisrtnameList = new ArrayList<String>();
			String str = null;
			while((str = bufferedReader.readLine())!= null) {
				fisrtnameList.add(str); 
			}
			bufferedReader.close();
			fileReader.close();
			return fisrtnameList;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getOriginPhoneIndex(){
		List<String> nameList = getOriginPhone();
		
		int size = nameList.size();
		List<String> firstIndexList = new ArrayList<String>(size);
		for(int i=0;i<size;i++) {
			firstIndexList.add(Integer.toString(i));
		}
		return firstIndexList;
		
	}
}
