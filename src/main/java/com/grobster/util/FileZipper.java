package com.grobster.util;

import java.io.*;
import java.util.zip.*;
import java.util.*;
import java.nio.file.*;

public class FileZipper {
	public static int BUFFER = 1024 * 4;
	final public static int MIN_BUFFER = 2;
	final public static int MED_BUFFER = 4;
	final public static int MAX_BUFFER = 8;
	final public static String ZIP_ENDING = ".zip";
	private String in, out;
	private ArrayList<FileZipperObserver> observers;
	private long dataCount;
	
	public FileZipper(String in, String out) {
		this.in = in;
		this.out = out;
		dataCount = 0;
		observers = new ArrayList<>();
	}
	
	public FileZipper() {
		dataCount = 0;
		observers = new ArrayList<>();
	}
	
	/**
	  * Zips a file.
	  */
	public void compress() {
		BufferedInputStream bis = null;
		ZipOutputStream zos = null;
		
		try {
			FileOutputStream fos = new FileOutputStream(out);
			zos = new ZipOutputStream(new BufferedOutputStream(fos));
			zos.setMethod(ZipOutputStream.DEFLATED);
			
			byte data[] = new byte[BUFFER];
			
			FileInputStream fis = new FileInputStream(in);
			bis = new BufferedInputStream(fis, BUFFER);
			ZipEntry zip = new ZipEntry(new File(in).getName());
			zos.putNextEntry(zip);
			
			int count;
			while((count = bis.read(data, 0, BUFFER)) != -1 ) {
				zos.write(data, 0, count);
				dataCount = Long.valueOf(count);
				notifyObservers();
				System.out.println("File count: " + dataCount);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				bis.close();
				zos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public long getDataCount() {
		return dataCount;
	}
	
	public void addObserver(FileZipperObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(FileZipperObserver o) {
		int pos = observers.indexOf(o);
		if(pos != -1) {
			observers.remove(o);
		}
	}
	
	public void notifyObservers() {
		for(FileZipperObserver o: observers) {
			o.update();
		}
	}
	
	public String getFileBeingZipped() {
		return in;
	}
	
	public Long fileSize() {
		long size = 0;
		try {
			size = Files.size(Paths.get(in));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}
	
	public void setIn(String in) {
		this.in = in;
	}
	
	public void setOut(String out) {
		this.out = out;
	}
}