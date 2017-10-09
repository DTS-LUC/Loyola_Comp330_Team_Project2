import java.util.*;

public class Notes{
	// Key: @identifier Value: fileNames
	TreeMap<String,ArrayList<String>> mentions = new TreeMap<String,ArrayList<String>>();
	// Key: #identifier Value: fileNames
	TreeMap<String,ArrayList<String>> topics = new TreeMap<String,ArrayList<String>>();
	// Key: id 					Value: fileName
	TreeMap<String,String> ids = new TreeMap<String,String>();

//	private final String dirPath;

	// TODO Create a constructor that recieves valid dirPath

	// Methods for setting values
	public void setMentions(TreeMap<String,ArrayList<String>> mentions){this.mentions = mentions;}
	public void setTopics(TreeMap<String,ArrayList<String>> topics){this.topics = topics;}
	public void setIDs(TreeMap<String,String> ids){this.ids = ids;}

	// Methods for retrieving all values
	public TreeMap<String,ArrayList<String>> getAllMentions(){return mentions;}
	public TreeMap<String,ArrayList<String>> getAllTopics(){return topics;}
	public TreeMap<String,String> getAllIDs(){return ids;}

	// Method for retrieving select Mention value(s)
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
	// Method for retrieving select Topic value(s)
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
	// Method for retrieving select ID value(s)
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
