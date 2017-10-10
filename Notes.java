import java.util.*;

public class Notes{

	// Key: &identifier Value: fileNames
	TreeMap<String,ArrayList<String>> words = new TreeMap<String,ArrayList<String>>();  // new part


	// Key: @identifier Value: fileNames
	TreeMap<String,ArrayList<String>> mentions = new TreeMap<String,ArrayList<String>>();
	// Key: #identifier Value: fileNames
	TreeMap<String,ArrayList<String>> topics = new TreeMap<String,ArrayList<String>>();
	// Key: id 				Value: fileName
	TreeMap<String,String> ids = new TreeMap<String,String>();
	// Key: fileName		sValue: ^identifiers
	// TreeMap<String,ArrayList<String>> refs = new TreeMap<String,ArrayList<String>>();


	// Methods for setting values

        public void setWords(TreeMap<String,ArrayList<String>> words){        // new part
		this.words = words;
	}

	public void setMentions(TreeMap<String,ArrayList<String>> mentions){
		this.mentions = mentions;
	}
	public void setTopics(TreeMap<String,ArrayList<String>> topics){
		this.topics = topics;
	}
	 
	public void setIDs(TreeMap<String,String> ids){
		this.ids = ids;
	}
	 

	// Methods for retrieving all values

        public TreeMap<String,ArrayList<String>> getAllWords(){   //new part
		return words;
	}

	public TreeMap<String,ArrayList<String>> getAllMentions(){
		return mentions;
	}
	public TreeMap<String,ArrayList<String>> getAllTopics(){
		return topics;
	}
	public TreeMap<String,String> getAllIDs(){
		return ids;
	}
 
	// Methods for retrieving select value

        public TreeMap<String,ArrayList<String>> getWords(ArrayList<String> search){   //  new part
		search.remove(0);
		TreeMap<String,ArrayList<String>> selection = new TreeMap<String,ArrayList<String>>(String.CASE_INSENSITIVE_ORDER);
		ArrayList<String> selected;

		for(String s:search) {
			selected = new ArrayList<String>(words.get(s));
			selection.put( 1 + s, selected);                  // Correct number of words
		}

		return selection;
	}

 
	public TreeMap<String,ArrayList<String>> getMentions(ArrayList<String> search){
		search.remove(0);
		TreeMap<String,ArrayList<String>> selection = new TreeMap<String,ArrayList<String>>(String.CASE_INSENSITIVE_ORDER);
		ArrayList<String> selected;

		for(String s:search) {
			selected = new ArrayList<String>(mentions.get("@" +s));
			selection.put( "@" + s, selected);
		}

		return selection;
	}
	public TreeMap<String,ArrayList<String>> getTopics(ArrayList<String> search){
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
	public TreeMap<String,String> getIDs(ArrayList<String> search){
		search.remove(0);
		TreeMap<String,String> selection = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
		String selected;

		for(String s:search) {
			selected = ids.get( "!" + s);
			selection.put( "!" + s, selected);
		}

		return selection;
	}
}