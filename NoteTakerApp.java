import java.util.*;
import java.io.File;

public class NoteTakerApp{

	public static void printIDs(TreeMap<String,String> tm){

		for(Map.Entry<String,String> entry : tm.entrySet()) {
		  String key = entry.getKey();
		  String value = entry.getValue();

		  System.out.println(key + " => " + value);
		}
	}

	public static void printTree(TreeMap<String,ArrayList<String>> tm){

		for(Map.Entry<String,ArrayList<String>> entry : tm.entrySet()) {
		  String key = entry.getKey();
		  ArrayList<String> value = entry.getValue();

		  System.out.println(key + " => " + value);
		}
	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		NoteRetriever retriever = new NoteRetriever();
		NoteSorter		sorter		= new NoteSorter();
		Notes					notes			= new Notes();
		HashMap<String, String> files = new HashMap<String, String>();

		//Get directory path from user
		boolean validDir = false;
		String dirPath = "";
		while(!validDir){
			System.out.println("Enter directory path:");
			dirPath = input.nextLine();
			File f = new File(dirPath);
			if(f.exists() && f.isDirectory()){
          validDir = true;
      }else{
          System.err.println("Doesn't exist or is not a directory.");
				}
			}
		// dirPath = "/Users/don/Loyola_Comp330_Team_Project1/TestNotes";

		// Send directory path to NoteRetriever
		files = retriever.getFiles(dirPath);

		// Set values for notes


                notes.setWords(sorter.sort(files, "[\s][\D+\d]"));  // new part, will get total words -1


		notes.setMentions(sorter.sort(files, "@[a-zA-Z_][0-9a-zA-Z_]*"));
		notes.setTopics(sorter.sort(files, "#[a-zA-Z_][0-9a-zA-Z_]*"));
		// notes.setURLS(sorter.sort(files, "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"));
		notes.setIDs(sorter.getIDs(files));

		//Command Line Interface
		boolean runApp = true;
		ArrayList<String> listArgs;
		String userArgs;
		String cmd;
		int numArgs;
		while(runApp){
			System.out.println("Enter a command or \"Q/q\" to quit:");
			userArgs = input.nextLine();

			listArgs = new ArrayList<String>(Arrays.asList(userArgs.split(" ")));
			numArgs = listArgs.size();
			// Swtich cases match character from input[0]
			cmd = listArgs.get(0);
			if (numArgs == 1) {
				// Get all
				switch (cmd){
                                        case "&" :
						printTree(notes.getAllWords());
						break;

					case "@" :
						printTree(notes.getAllMentions());
						break;
					case "#" :
						printTree(notes.getAllTopics());
						break;
					// case "url" :
					// 	printTree(notes.getAllURLS());
					case "!" :
						printIDs(notes.getAllIDs());
						break;
					case "Q" :
					case "q" :
						runApp = false;
						break;
					default:
						System.out.println("Please enter a valid command");
				}
			}
			else if (numArgs > 1) {
				// Get selectively
				switch (cmd){


					case "&" :
						printTree(notes.getWords(listArgs));  // new part
						break;
					case "#" :
						printTree(notes.getTopics(listArgs));
						break;
						// case "url":
						// printTree(notes.getURLs(listArgs));
					case "!" :
						printIDs(notes.getIDs(listArgs));
						break;
					case "Q" :
					case "q" :
						runApp = false;
						break;
					default:
						System.out.println("Please enter a valid command");
				}

			}
			else{
				runApp = false;
			}
		}
	}
}

//"\\^[a-zA-Z_][0-9a-zA-Z_]*"