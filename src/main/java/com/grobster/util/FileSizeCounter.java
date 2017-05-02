package com.grobster.util;

public class FileSizeCounter implements FileZipperObserver {
	private long count;
	private FileZipper zipper;
	
	public FileSizeCounter(FileZipper zipper) {
		this.zipper = zipper;
		count = 0;
		zipper.addObserver((FileZipperObserver)this);
	}
	
	public void update() {
		count += zipper.getDataCount();
	}
	
	public long getCount() {
		return count;
	}
	
	
}