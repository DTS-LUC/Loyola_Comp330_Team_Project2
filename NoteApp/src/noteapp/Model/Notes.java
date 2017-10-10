 package noteapp.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
 import java.util.*;

 public class Notes{
 	// Key: fileName 	Value: content
 	TreeMap<String,String> files	= new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
 	// Key: ^id 			Value: fileName
 	TreeMap<String,String> 	ids 	= new TreeMap<>();
 	// Key: @identifier Value: fileNames
 	TreeMap<String,ArrayList<String>> mentions 	= new TreeMap<>();
 	// Key: #identifier Value: fileNames
 	TreeMap<String,ArrayList<String>> topics 		= new TreeMap<>();

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
		setIDs(sorter.sortIDs(files));
 	}
 	// Methods for setting values
 	public void setMentions(TreeMap<String,ArrayList<String>> mentions){this.mentions = mentions;}
 	public void setTopics(TreeMap<String,ArrayList<String>> topics){this.topics = topics;}
 	public void setIDs(TreeMap<String,String> ids){this.ids = ids;}

 	// Methods for retrieving all values in sorted order
  public List<String> allNames(){
	  List<String> names = new ArrayList<>();
          names.addAll(files.keySet());
    // String name : names
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
 		TreeMap<String,ArrayList<String>> selection = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
 		ArrayList<String> selected;

 		for(String s:search) {
 			selected = new ArrayList<>(mentions.get("@" +s));
 			selection.put( "@" + s, selected);
 		}

 		return selection;
 	}
 	// Method for retrieving select Topic value(s)
 	public TreeMap<String,ArrayList<String>> findTopics(ArrayList<String> search){
 		search.remove(0);
 		TreeMap<String,ArrayList<String>> selection = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
 		ArrayList<String> selected;

 		for(String s:search) {
 			selected = mentions.get( "#" + s);
 			selection.put( "#" + s, selected);
	 		}

	 	return selection;
 	}
 	// Method for retrieving select ID value(s)
 	public TreeMap<String,String> findIDs(ArrayList<String> search){
 		search.remove(0);
 		TreeMap<String,String> selection = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
 		String selected;

 		for(String s:search) {
 			selected = ids.get( "!" + s);
 			selection.put( "!" + s, selected);
 		}

 		return selection;
 	}

  // Method for retrieving note contents
  public String getNote(String noteName){
      String content = files.get(noteName + ".txt");
      return content;
  }

 	// Method for updating/adding a note
 	public void updateNote(String noteName, String content) throws FileNotFoundException{
 		String fileName = noteName +".txt";
 		String filePath = dirPath +"/" + fileName;
 		editor.updateFile(filePath, content);
 		// add to files
 		files.put(fileName, content);
 		// Re-sort
 		sortNotes();
 	}

 	// Method for removing a note
 	public void removeNote(String noteName) throws IOException{
 		String fileName = noteName +".txt";
 		String filePath = dirPath + "/" +fileName;
    // Remove from file System
    editor.removeFile(filePath);
    // Remove from files map
    files.remove(fileName);

		// Re-sort
 		sortNotes();
 	}
 }
