package com.idealista.fpe.namefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LastName {
	static final String PATH = "D:\\Program Files\\eclipse-workspace\\FF1Encrypt\\src\\com\\idealista\\fpe\\namefile";
	static final String FILENAME_OF_LASTNAME = "lastnameIn.txt";
	
	/**
	 * 将姓氏txt文件转化成list集合
	 * @return lastNameList
	 * @throws Exception
	 */
	public static List<String> getOriginLastname() {
		File lastnameFile = new File(PATH, FILENAME_OF_LASTNAME);
		FileReader fileReader;
		try {
			fileReader = new FileReader(lastnameFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			List<String> lastNameList = new ArrayList<String>();
			String str = null;

			while((str = bufferedReader.readLine())!= null) {
				lastNameList.add(str); 

			}
			bufferedReader.close();
			fileReader.close();
			return lastNameList;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public List<String> getOriginLastIndex(){
		List<String> nameList = getOriginLastname();
		
		int size = nameList.size();
		List<String> lastIndexList = new ArrayList<String>(size);
		for(int i=0;i<size;i++) {
			lastIndexList.add(Integer.toString(i));
		}
		return lastIndexList;
		
	}
}
