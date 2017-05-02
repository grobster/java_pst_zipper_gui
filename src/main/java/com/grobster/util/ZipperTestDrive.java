package com.grobster.util;

public class ZipperTestDrive {
	public static void main(String[] args) {
		FileZipper zip = new FileZipper("C:\\Users\\quarl\\Pictures\\demo\\IMAG2289.jpg", "C:\\Users\\quarl\\Pictures\\demo\\IMAG2289.zip");
		//FileSizeCounter count = new FileSizeCounter(zip);
		//System.out.println("File being zipped: " + zip.getFileBeingZipped());
		//System.out.println("File's size before compression: " + zip.fileSize());
		//zip.compress();
		//System.out.println("File's size in bytes is " + count.getCount());
		ZipperGui gui = new ZipperGui(zip);
		gui.createView();
	}
}