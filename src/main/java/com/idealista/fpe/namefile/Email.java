package com.idealista.fpe.namefile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Email {
	static final String PATH = "D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile";
	static final String FILENAME_OF_EMAIL = "email.txt";
	
	public static List<String> getOriginEmail() {
		File emailFile = new File(PATH, FILENAME_OF_EMAIL);
		FileReader fileReader;
		try {
			fileReader = new FileReader(emailFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			List<String> emailList = new ArrayList<String>();
			String str = null;
			while((str = bufferedReader.readLine())!= null) {
				emailList.add(str);
			}
			bufferedReader.close();
			fileReader.close();
			return emailList;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
