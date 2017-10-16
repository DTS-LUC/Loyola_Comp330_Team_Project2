 package noteapp.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
 import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

 public class Notes{
 	// Key: fileName 	Value: content
 	TreeMap<String,String> files	= new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
 	// Key: ^id 			Value: fileName
 	TreeMap<String,String> 	ids 	= new TreeMap<>();
 	// Key: @identifier Value: fileNames
 	TreeMap<String,ArrayList<String>> mentions 	= new TreeMap<>();
 	// Key: #identifier Value: fileNames
 	TreeMap<String,ArrayList<String>> topics 		= new TreeMap<>();
	// List of Favorited note names
	//*favs*.txt
	ArrayList<String> favs = new ArrayList<>();

 	NoteRetriever retriever = new NoteRetriever();
 	NoteSorter		sorter		= new NoteSorter();
 	NoteEditor		editor		= new NoteEditor();

 	private final String dirPath;

 	public Notes(String dirPath) throws IOException{
 		this.dirPath = dirPath;
 		this.files = retriever.getFiles(dirPath);
		// Get favorites
		this.favs = retriever.getFavorites(dirPath);
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
	public void setFavs(ArrayList<String> favs){this.favs = favs;}

 	// Methods for retrieving all values in sorted order
  public ArrayList<String> allNames(){
	  ArrayList<String> names = new ArrayList<>();
          names.addAll(files.keySet());
    // String name : names
		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
      name = name.substring(0, name.lastIndexOf("."));
			names.set(i, name);
		}
	  return names;
	}
 	public ArrayList<String> allMentions(){
		ArrayList<String> list = new ArrayList<>(mentions.keySet());
		return list;
	}
 	public ArrayList<String> allTopics(){
		ArrayList<String> list = new ArrayList<>(topics.keySet());
		return list;
			}
 	public ArrayList<String> allIDs(){
		ArrayList<String> list = new ArrayList<>(ids.keySet());
		return list;
        }
	public ArrayList<String> allFavs(){
    if (favs.isEmpty()) {
      ArrayList<String> noFavs = new ArrayList<>();
      noFavs.add("No Favorites Added");
      return noFavs;
    }
    return this.favs;
    }

 	// Method for retrieving file names that match select Mention value(s)
 	public ArrayList<String> matchMentions(String search){
 		ArrayList<String> selected = new ArrayList<>(mentions.get("@" + search));

    if(selected.isEmpty()){
        selected.add("No results");
    }
		for (int i = 0; i < selected.size(); i++) {
			String name = selected.get(i);
      name = name.substring(0, name.lastIndexOf("."));
			selected.set(i, name);
		}
		return selected;
		}

 	// Method for retrieving file names that match select Topic value(s)
	public ArrayList<String> matchTopics(String search){
 		ArrayList<String> selected = new ArrayList<>(topics.get("#" + search));;

	  if(selected.isEmpty()){
	      selected.add("No results");
	  }
		for (int i = 0; i < selected.size(); i++) {
			String name = selected.get(i);
	    name = name.substring(0, name.lastIndexOf("."));
			selected.set(i, name);
												}
		return selected;
 		}

 	// Method for retrieving file names that match select ID value(s)
	public String matchId(String search){
 		String selected;

 			selected = ids.get("!" + search);
                        selected = selected.substring(0, selected.lastIndexOf("."));
			return selected;
 		}

  // Method for retrieving note contents
  public String getNote(String noteName){
      String content = files.get(noteName + ".txt");
			if (content == null) {
				return "No note found.";
			}
      return content;
  }
	// Method for retrieving keyword by ID
	public String findByID(String keyword){
		return ids.get(keyword);
	}
	// Method for retrieving keyword matches
	public String getKeywordInfo(String keyword){
		List<String> matches = new ArrayList<>();
		String keywordInfo;
		char cmd = keyword.charAt(0);
		switch (cmd){
			case '@':
							matches = mentions.get(keyword);
							break;
      case '#':
              matches = topics.get(keyword);
              break;
      case '!':
              String fileName= ids.get(keyword);
              return keyword = files.get(fileName);

		}
		keywordInfo = String.join("\n", matches);
		return keywordInfo;
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

	// Method for checking if a note is favorited
	public boolean checkFav(String noteName){return favs.contains(noteName);}

	public void addFavorite(String noteName){
		// Add to ArrayList
    favs.add(noteName);
    Collections.sort(favs);
		// Convert list into single String
    String favorites = String.join("\n", favs);
		// Update *favs* file
    String filePath = dirPath + "/*favs*.txt";
             try {
                 editor.updateFile(filePath, favorites);
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(Notes.class.getName()).log(Level.SEVERE, null, ex);
             }
	}

	public void removeFavorite(String noteName){
		// Remove from ArrayList
    favs.remove(noteName);
		// Convert list into single String
    String favorites = String.join("\n", favs);
		// Update *favs* file
    String filePath = dirPath + "/*favs*.txt";
             try {
                 editor.updateFile(filePath, favorites);
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(Notes.class.getName()).log(Level.SEVERE, null, ex);
             }
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

  public int countWords(String content){
    return sorter.countWords(content);
  }
 }
