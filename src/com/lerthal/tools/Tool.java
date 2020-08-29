package com.lerthal.tools;

import java.io.File;

public class Tool {
	
	public static File loadFilefromName(String fileName) {
		File directory = new File("");
		File file = new File(directory.getAbsolutePath().replaceAll("\\.", "/")
				.concat("/res/")
				.concat(fileName));
		if (!file.exists())
			throw new RuntimeException("Arquivo " + fileName + " não econtrado");
		return file;		
	}

}
