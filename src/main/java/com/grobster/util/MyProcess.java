package com.grobster.util;

import java.io.*;

public class MyProcess {
	public static boolean isRunning(String name) {
		String line;
		String pidInfo = "";
		try {
			Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			BufferedReader reader =  new BufferedReader(new InputStreamReader(process.getInputStream()));

			while ((line = reader.readLine()) != null) {
				pidInfo += line; 
			}
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(pidInfo.toLowerCase().contains(name.toLowerCase())) {
			return true;
		}
	
		return false;
	}
	
	public static void main(String[] args) {
		String process1 = "OUTLOOK.EXE";
		boolean result = isRunning(process1);
		if(result) {
			System.out.println("It is running");
		} else {
			System.out.println("It is not running.");
		}
		
	}

}