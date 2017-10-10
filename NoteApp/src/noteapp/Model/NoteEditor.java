// This class will be used create, update,
//  and remove notes using the file system
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NoteEditor{

	public void updateFile(String filePath, String content)
	{
		PrintWriter out = new PrintWriter(filePath);
		out.println(content);
		printWriter.close ();
	}



	public void createFile(String filePath, String content)
	{
		PrintWriter out = new PrintWriter(filePath);
		out.println(content);
		printWriter.close ();
	}


	public void removeFile(String filePath)
	{
		try {
	    		Files.delete(path);
			}catch (NoSuchFileException x)
			{
	    		System.err.format("No Such File", path);
			}
	}
}
