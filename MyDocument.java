import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyDocument {
	
	public static String person = "PERSON";
	public static String city = "CITY";
	public static String lang = "LANGUAGE";
	public static String inst = "INSTRUMENT";
	
	public static Set<String> delimiters = getDelimiters();
	
	String cat;
	HashMap<String, Integer> bagOfWords;
	List<String> sentences;
	
	private static Set<String> getDelimiters() {
		Set<String> set = new HashSet<String>();
		String[] arr = {"-", ".", ";", "," ,"or", "and", ":", "(", ")"};
		for (String a : arr) {
			set.add(a);
		}
		return set;
	}

	public MyDocument() {
		
	}
}