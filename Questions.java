import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Questions {
	
	public static Set<String> getPerson(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		String name = doc.sentences.get(0);
		
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
			if (sen.startsWith("he was")) {
				questions.add("Was " + name + " " + sen.substring(7));
			} else if (sen.startsWith("she was")) {
				questions.add("Was " + name + " " + sen.substring(8));
			}
			
			if (sen.startsWith("he is")) {
				questions.add("Is " + name + " " + sen.substring(6));
			} else if (sen.startsWith("she is")) {
				questions.add("Is " + name + " " + sen.substring(7));
			}
		}
		
		questions.add("Where did " + name + "go to school?");
		
		return questions;
	}
	
	public static Set<String> getCity(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		
		String name = doc.sentences.get(0);
		
		questions.add("Is " + name + " a capital?");
		questions.add("What is the population of " + name + " ?");
		questions.add("Which country is " + name + " in?");
		questions.add("Is " + name + " near water?");
		
		return questions;
	}
	
	public static Set<String> getLang(MyDocument doc) {
		Set<String> questions = new HashSet<String>();
		
		String name = doc.sentences.get(0);
		
		if (name.contains("language")) {
			name = name.replaceAll(" language", "");
			name = name.replaceAll("_language", "");
		}
		
		questions.add("How many people speak " + name + "?");
		questions.add("Where is " + name + " primarily spoken?");
		
		for (String sen : doc.sentences) {
			if (sen.contains("official language of")) {
				questions.add("Is " + name + " the official language of any country?");
			}
			if (sen.contains("dialects") || sen.contains("dialect")) {
				questions.add("Does " + name + " have any dialects?");
			}
			if (sen.contains("vowels")) {
				questions.add("How many vowels does " + name + " have?");
			}
		}
		
		return questions;
	}

}
