import java.io.*;
import java.text.BreakIterator;
import java.util.*;

/*
 * CATEGORIES:
 * 	- CITY
 * 	- PERSON
 * 	- LANGUAGE
 * 	- INSTRUMENT
 */
public class DocClassify {
	
	public static String classify(List<String> testing, HashMap<String, HashMap<String, Double>> catProbs) {
		if (catProbs == null) {
			return "No probabilities, something went wrong";
		}
		
		String cat = "No categories?";
		double best = Integer.MIN_VALUE;
		for (String catOpt : catProbs.keySet()) { // for each category
			double catOptProb = 0;
			HashMap<String, Double> wordProbs = catProbs.get(catOpt);
			for (String word : testing) { // examine probabilities for each word
				if (wordProbs.containsKey(word)) {
					catOptProb += Math.log(wordProbs.get(word)); 
					// don't need to factor in probabilities of categories because constant for all
				} 
				System.out.println(catOptProb + "--------" + wordProbs.get(word));
			}
			System.out.println("Category " + catOpt + ", Prob " + catOptProb);
			if (catOptProb > best) {
				cat = catOpt;
				best = catOptProb;
			}
		}
		return cat;
	}

	public static List<String> getWords(String file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		List<String> fileWords = new ArrayList<String>();
		
		String line;
		while ((line = in.readLine()) != null) {
			String[] words = line.split("[ *]");
			for (String word : words) {
				word = word.toLowerCase().replaceAll("[.,;?!)(]", ""); // standardize word
				fileWords.add(word);
			}
		}

		return fileWords;
	}
	
	public static HashMap<String, Integer> getBagOfWords(String file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
		String line;
		while ((line = in.readLine()) != null) {
			String[] words = line.split("[ *]");
			for (String word : words) {
				word = word.toLowerCase().replaceAll("[.,;?!)(]", ""); // standardize word
				if (wordCounts.containsKey(word)) {
					wordCounts.put(word, wordCounts.get(word) + 1);
				} else {
					wordCounts.put(word, 1);
				}
			}
		}

		return wordCounts;
	}

	public static void fillProbs(HashMap<String, HashMap<String, Integer>> trainingDocs, HashMap<String, Integer> catWords,
			HashMap<String, HashMap<String, Double>> catProbs) {
		
		for (String cat : trainingDocs.keySet()) { // for each category
			HashMap<String, Integer> wordCounts = trainingDocs.get(cat); // for this category, how many times each word occurs
			int totalWords = catWords.get(cat);
			HashMap<String, Double> wordProbs = new HashMap<String, Double>();
			for (String word : wordCounts.keySet()) { // for each word in the category
				wordProbs.put(word, (double)wordCounts.get(word)/(double)totalWords);
			}
			catProbs.put(cat, wordProbs);
		}
	}

	public static void readTraining(HashMap<String, HashMap<String, Integer>> docs, HashMap<String, Integer> catWords) throws IOException {
		// read in the file
		File dir = new File("training");
		String files[] = dir.list();
//		String[] katz = {"PERSON", "LANGUAGE", "CITY", "INSTRUMENT"};
//		List<String> strings = Arrays.asList(katz);
		
		for (String file : files) {
			
			if (!file.contains(".txt")) {
				continue;
			}
			BufferedReader in = new BufferedReader(new FileReader("training/" + file));
			String cat = in.readLine(); // first line is category
			catWords.put(cat, 0); // initialize

			HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();
			String line;
			while ((line = in.readLine()) != null) { // for each line in the text file
				String[] words = line.split("[ *]"); // split on whitespace for words
				for (String word : words) {
					catWords.put(cat, catWords.get(cat) + 1); // count total words per category

					word = word.toLowerCase().replaceAll("[.,;?!)(]", ""); // standardize word
					if (bagOfWords.containsKey(word)) {
						bagOfWords.put(word, bagOfWords.get(word) + 1);
					} else {
						bagOfWords.put(word, 1);
					}
				}
			}
			
			docs.put(cat, bagOfWords);
			in.close();
		}
		System.out.println(catWords.size());
	}
}
