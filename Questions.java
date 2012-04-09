import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Questions {
	
	public static Set<String> getPerson(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		String name = doc.sentences.get(0);
		name = name.replace("_", " ");
		
		questions.add("When was " + name + " born?");
		questions.add("Is " + name + " alive?");
		
		String pronoun;
		if (doc.bagOfWords.containsKey("he") && !doc.bagOfWords.containsKey("she")) {
			pronoun = "he";
		} else if (doc.bagOfWords.containsKey("she") && !doc.bagOfWords.containsKey("he")) {
			pronoun = "she";
		} else if (!doc.bagOfWords.containsKey("he") && !doc.bagOfWords.containsKey("she")) {
			pronoun = "he"; // because we live in a male-dominated society
			System.out.println("nope, chauvenists!");
		} else if (doc.bagOfWords.get("he") > doc.bagOfWords.get("she")) {
			pronoun = "he";
		} else {
			pronoun = "she";
		}
		
		for (String sen : doc.sentences) {
			if (sen.startsWith(pronoun + " was")) {
				questions.add("Was " + name + " " + sen.substring(7));
			}
			
			if (sen.startsWith(pronoun + " is")) {
				questions.add("Is " + name + " " + sen.substring(6));
			} 
			addGeneral(questions, sen, name);
		}
		
		questions.add("Where did " + name + "go to school?");
		
		return questions;
	}
	
	public static Set<String> getCity(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		
		String name = doc.sentences.get(0);
		name = name.replace("_", " ");
		
		questions.add("Is " + name + " a capital?");
		questions.add("What is the population of " + name + " ?");
		questions.add("Which country is " + name + " in?");
		questions.add("Is " + name + " near water?");
		questions.add("When was " + name + " founded?");
		
		for (String sen : doc.sentences) {
			if (sen.contains("economy")) {
				questions.add("What kind of an economy does " + name + " have?");
			}
			if (sen.contains("named after")) {
				questions.add("What/Who was " + name + " named after?");
			}
			if (sen.contains("climate")) {
				questions.add("What kind of climate does " + name + " have?");
			}
			addGeneral(questions, sen, name);
		}
		
		return questions;
	}
	
	public static Set<String> getLang(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		
		String name = doc.sentences.get(0);
		name = name.replace("_", " ");
		
		if (name.contains("language")) {
			name = name.replaceAll(" language", "");
			name = name.replaceAll("_language", "");
		}
		
		questions.add("How many people speak " + name + "?");
		questions.add("Where is " + name + " primarily spoken?");
		
		for (String sen : doc.sentences) {
			if (sen.contains("official language of")) {
				questions.add("Is " + name + " the official language of any country?");
				if (!sen.contains("not")) {
					questions.add("Which place has its official language as " + name + "?");
				}
			}
			if (sen.contains("dialects") || sen.contains("dialect")) {
				questions.add("Does " + name + " have any dialects?");
			}
			if (sen.contains("vowels")) {
				questions.add("How many vowels does " + name + " have?");
			}
			addGeneral(questions, sen, name);
		}
		
		return questions;
	}
	
	public static Set<String> getInst(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		
		String name = doc.sentences.get(0);
		name = name.replace("_", " ");
		
		for (String sen : doc.sentences) {
			addGeneral(questions, sen, name);
		}
		
		return questions;
		
	}
	
	private static void addGeneral(Set<String> questions, String sen, String name) {
		List<String> words = Arrays.asList(sen.split("[ ]+"));
		if (sen.contains(name + " have")) {
			if (!(sen.contains("been") || sen.contains("become") || sen.contains(":"))){
				int start = words.indexOf("have") + 1;
				int end = findEnd(words, start);
				String clause = join(words, start, end);
				String lastWord = clause.split("[ ]+")[0];
				if (lastWord.lastIndexOf("ed") != (lastWord.length() - 2)) {
					questions.add("Have " + name + " " + clause + "?");
				}
			}
		}
		if (sen.contains(name + " has")) {
			if (!(sen.contains("been") || sen.contains("become") || sen.contains(":"))){
				int start = words.indexOf("has") + 1;
				int end = findEnd(words, start);
				String clause = join(words, start, end);
				String lastWord = clause.split("[ ]+")[0];
				if (lastWord.lastIndexOf("ed") != (lastWord.length() - 2)) {
					questions.add("Does " + name + " have " + clause + "?");
				}
			}
		}
		if (sen.contains(name + " had")) {
			if (!(sen.contains("been") || sen.contains("become") || sen.contains(":"))){
				int start = words.indexOf("had") + 1;
				int end = findEnd(words, start);
				String clause = join(words, start, end);
				questions.add("Did " + name + " have " + clause + "?");
			}
		}
		if (sen.contains(name + " is")) {
			if (!(sen.contains(":"))){
				int start = words.indexOf("is") + 1;
				int end = findEnd(words, start);
				String clause = join(words, start, end);
				questions.add("Is " + name + " " + clause + "?");
			}
		}
	}
	
	private static String join(List<String> words, int start, int end) {
		StringBuffer buf = new StringBuffer();
		for (int i = start; i < end; i++){
			if (words.get(i).equals("also")) continue;
			buf.append(words.get(i));
			buf.append(" ");
		}
		return buf.toString();
	}
	
	private static int findEnd(List<String> words, int start) {
		int trav = start;
		int len = words.size();
		while (trav < len && !MyDocument.delimiters.contains(words.get(trav))) {
			trav++;
		}
		return trav;
	}

}
