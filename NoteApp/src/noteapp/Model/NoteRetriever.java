package noteapp.Model;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

// @author donald-stolz
// This class is used to retrieve files and favorites from the selected directory
public class NoteRetriever{

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

	public ArrayList<String> getFavorites(String dirPath) throws IOException{
		ArrayList<String> favorites = new ArrayList<>();
		// Check for favorites file
		String favPath = dirPath + "/*favs*.txt";
		File favsFile = new File(favPath);
		// Checks if File: *favs*.txt exists
		// Create if it doesn't exists
		favsFile.createNewFile();
		// Read favorites file
		String content = readFile("*favs*.txt", dirPath);
		try (Stream<String> lines = Files.lines(Paths.get(favPath))) {
            lines.forEach(s -> favorites.add(s));
    } catch (IOException ex) {}
		// Return list of favorite
    return favorites;
	}

	public TreeMap<String, String> getFiles(String dirPath){
		TreeMap<String, String> files = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		// Get files from folder
		File folder = new File(dirPath);
		String[] fileNames = folder.list();

		// Add all txt files and their contents to list
		for (String file: fileNames) {
			if (file.endsWith(".txt")) {
				files.put(file, readFile(file, dirPath));
			}
		}
		// Check for favorites file - *favs*.txt
		if (files.containsKey("*favs*.txt")) {
			// Remove if present
			files.remove("*favs*.txt");
		}
		return files;
	}
}
