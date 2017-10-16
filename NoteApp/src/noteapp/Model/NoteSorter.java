package noteapp.Model;

import java.util.regex.*;
import java.util.*;

public class NoteSorter{

	public TreeMap<String,ArrayList<String>> sort(TreeMap<String,String> files, String regPattern){
	  // TreeMap store list of all identifiers
	  // Key: identifier 	Value: fileName(s);
	  // Key: String			Value: Arraylist to dynamically add Strings
	  TreeMap<String,ArrayList<String>> tm = new TreeMap<String,ArrayList<String>>(String.CASE_INSENSITIVE_ORDER);

	  HashSet<String> tmKeys = new HashSet<String>();

	  ArrayList<String> tempList;
	  String fileName;
	  // Loop through files
	  for(Map.Entry<String,String> file : files.entrySet()) {
	    fileName = file.getKey();
	    //Get all identifiers from the file text with regexChecker
	    tmKeys = regexChecker(regPattern, file.getValue());
	    // Add mention keys to tm TreeMap
	    for(String m:tmKeys) {
	      // check if value exists
	      if (tm.containsKey(m)){
	        // True: Add to ArrayList
	        tempList = new ArrayList<String>();
	        tempList = tm.get(m);
	        tempList.add(fileName);
	        tm.put(m, tempList);
	        // System.out.println(tempList);
	      }
	      else {
	        // False: Add key - value
	        tempList = new ArrayList<String>();
	        tempList.add(fileName);
	        tm.put(m, tempList);
	        // System.out.println(tm);
		      }
			   }
			  }

	  return tm;
	}

	public TreeMap<String,String> sortIDs(TreeMap<String,String> files){
		TreeMap<String,String> tm = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);

	  HashSet<String> tmKeys = new HashSet<String>();

	  ArrayList<String> tempList;
	  String fileName;
	  // Loop through files
	  for(Map.Entry<String,String> file : files.entrySet()) {
	    fileName = file.getKey();
	    //Get all identifiers from the file text with regexChecker
	    tmKeys = regexChecker("![a-zA-Z_][0-9a-zA-Z_]*", file.getValue());
	    // Add mention keys to tm TreeMap
	    for(String m:tmKeys) {
	      // check if value exists
	      if (tm.containsKey(m) != true){
		// False: Add key - value
	        tm.put(m, fileName);
	      }
			 }
			}

	  return tm;
	}

	public static HashSet<String> regexChecker(String theRegex, String str2Check){
	  // Using HashSet to ensure unique values
	  HashSet<String> matches = new HashSet<String>();

	  Pattern checkRegex = Pattern.compile(theRegex);
	  Matcher regexMatcher = checkRegex.matcher(str2Check);
	  // System.out.println("Checking: " + fileName);
	  while(regexMatcher.find()){
	    if (regexMatcher.group().length() != 0) {
	      matches.add(regexMatcher.group().trim());
	    }
	  }
	  return matches;
	}

	public static int countWords(String content)
	  {
		HashSet<String> words = new HashSet<>();
		words = regexChecker("\\b\\S+\\b", content);
		  
	  	return (words.size());
	  }
}
