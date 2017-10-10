package noteapp.Model;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NoteRetriever{

	public String[] listFiles(final File folder) {

		String[] fileNames;

		fileNames = folder.list();
		//	for(String file:fileNames) {
		//		System.out.println(file);
		//	}
		return fileNames;
	}

	public String readFile(String fileName, String dirPath){
		String filePath = dirPath + "/" + fileName;
		String content = "";

		try{
			content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
		}
		catch (IOException e){
			e.printStackTrace();
		}

		return content;
	}

	public TreeMap<String, String> getFiles(String dirPath){
		TreeMap<String, String> files = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		File folder = new File(dirPath);
		String[] fileNames = listFiles(folder);

		for (String file: fileNames) {
			files.put(file, readFile(file, dirPath));
		}

		return files;
	}
}
