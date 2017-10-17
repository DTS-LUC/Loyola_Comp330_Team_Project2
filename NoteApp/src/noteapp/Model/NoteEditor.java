package noteapp.Model;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

// @author ksrikanth97
// This class is used create, update, and remove notes using the file system
public class NoteEditor{

	public void updateFile(String filePath, String content) throws FileNotFoundException{
		// Create PrintWriter for filePath
		PrintWriter out = new PrintWriter(filePath);
		// Write content to file
		out.println(content);
		// Close PrintWrite
		out.close ();
	}

	public void removeFile(String filePath) throws IOException{
		// Get file at specified path
		Path path = Paths.get(filePath);
		// Check if file exists and remove it if it does
		try {
			Files.delete(path);
		}catch (NoSuchFileException x){
			System.err.format("No Such File ", path);
		}
	}
}
