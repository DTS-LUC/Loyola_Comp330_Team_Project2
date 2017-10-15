package noteapp.Model;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
	public ArrayList<String> getFavorites(String dirPath) throws IOException{
		ArrayList<String> favorites = new ArrayList<>();
		// Check for favorites file
		String favPath = dirPath + "/*favs*.txt";
		File favsFile = new File(favPath);
		// Checks if file exists and
		// Create one if it doesn't exists
		favsFile.createNewFile();
		// Read favorites file
		String content = readFile("*favs*.txt", dirPath);
		try (Stream<String> lines = Files.lines(Paths.get(favPath))) {
            lines.forEach(s -> favorites.add(s));
    } catch (IOException ex) {

    }
		// Return list of favorite
    return favorites;
	}

	public TreeMap<String, String> getFiles(String dirPath){
		TreeMap<String, String> files = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		File folder = new File(dirPath);
		String[] fileNames = listFiles(folder);

		for (String file: fileNames) {
			files.put(file, readFile(file, dirPath));
		}
		// Check for favorites file - *favs*.txt
		if (files.containsKey("*favs*.txt")) {
			// Remove if present
			files.remove("*favs*.txt");
		}
		return files;
	}
}
