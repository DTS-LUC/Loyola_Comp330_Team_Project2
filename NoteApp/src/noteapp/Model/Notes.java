 package noteapp.Model;

 import java.util.*;

 public class Notes{
 	// Key: fileName 	Value: content
 	TreeMap<String,String> files	= new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
 	// Key: ^id 			Value: fileName
 	TreeMap<String,String> 	ids 	= new TreeMap<String,String>();
 	// Key: @identifier Value: fileNames
 	TreeMap<String,ArrayList<String>> mentions 	= new TreeMap<String,ArrayList<String>>();
 	// Key: #identifier Value: fileNames
 	TreeMap<String,ArrayList<String>> topics 		= new TreeMap<String,ArrayList<String>>();

 	NoteRetriever retriever = new NoteRetriever();
 	NoteSorter		sorter		= new NoteSorter();
 	NoteEditor		editor		= new NoteEditor();

 	private final String dirPath;

 	public Notes(String dirPath){
 		this.dirPath = dirPath;
 		this.files = retriever.getFiles(dirPath);
 		sortNotes();
 	}

 	public void sortNotes(){
 		setMentions(sorter.sort(files, "@[a-zA-Z_][0-9a-zA-Z_]*"));
 		setTopics(sorter.sort(files, "#[a-zA-Z_][0-9a-zA-Z_]*"));
		// 		setIDs(sorter.getIDs(files));
 	}
 	// Methods for setting values
 	public void setMentions(TreeMap<String,ArrayList<String>> mentions){this.mentions = mentions;}
 	public void setTopics(TreeMap<String,ArrayList<String>> topics){this.topics = topics;}
 	public void setIDs(TreeMap<String,String> ids){this.ids = ids;}

 	// Methods for retrieving all values in sorted order
  public List<String> allNames(){
	  List<String> names = new ArrayList(files.keySet());
		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
                        name = name.substring(0, name.lastIndexOf("."));
			names.set(i, name);
		}
	  return names;
	}
 	public TreeMap<String,ArrayList<String>> allMentions(){return mentions;}
 	public TreeMap<String,ArrayList<String>> allTopics(){return topics;}
 	public TreeMap<String,String> allIDs(){return ids;}

 	// Method for retrieving select Mention value(s)
 	public TreeMap<String,ArrayList<String>> findMentions(ArrayList<String> search){
 		search.remove(0);
 		TreeMap<String,ArrayList<String>> selection = new TreeMap<String,ArrayList<String>>(String.CASE_INSENSITIVE_ORDER);
 		ArrayList<String> selected;

 		for(String s:search) {
 			selected = new ArrayList<String>(mentions.get("@" +s));
 			selection.put( "@" + s, selected);
 		}

 		return selection;
 	}
 	// Method for retrieving select Topic value(s)
 	public TreeMap<String,ArrayList<String>> findTopics(ArrayList<String> search){
 		search.remove(0);
 		TreeMap<String,ArrayList<String>> selection = new TreeMap<String,ArrayList<String>>(String.CASE_INSENSITIVE_ORDER);
 		ArrayList<String> selected;

 		for(String s:search) {
 			selected = new ArrayList<String>();
 			selected = mentions.get( "#" + s);
 			selection.put( "#" + s, selected);
	 		}

	 	return selection;
 	}
 	// Method for retrieving select ID value(s)
 	public TreeMap<String,String> findIDs(ArrayList<String> search){
 		search.remove(0);
 		TreeMap<String,String> selection = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
 		String selected;

 		for(String s:search) {
 			selected = ids.get( "!" + s);
 			selection.put( "!" + s, selected);
 		}

 		return selection;
 	}

 	// Method for updating a note
 	public void updateNote(String noteName, String content){
 		String fileName = noteName +".txt";
 		String filePath = dirPath + fileName;
 		editor.updateFile(filePath, content);
 		// add to files
 		files.put(fileName, content);
 		// Resort
 		sortNotes();
 	}
 	// Method for adding a note

 	// Method for removing a note
 	public void removeNote(String noteName){
 		String fileName = noteName +".txt";
 		String filePath = dirPath + fileName;
		//editor.updateFile(filePath);

 		// Remove from files

 		// Resort
 		sortNotes();
 	}
 }
