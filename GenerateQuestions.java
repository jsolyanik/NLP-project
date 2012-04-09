import java.io.IOException;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GenerateQuestions {

	/* first arg is filename, second arg is numquestion */
	public static void main(String[] args) {
//		// this map is from category to all words found in the category and their counts. our bag of words model for each document.
//		HashMap<String, HashMap<String, Integer>> trainingDocs = new HashMap<String, HashMap<String, Integer>>();
//		
//		// how many total words per document
//		HashMap<String, Integer> catWords = new HashMap<String, Integer>();
//		
//		try {
//			DocClassify.readTraining(trainingDocs, catWords);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		String cat = null;
		
		try {
			cat = DocClassifier.classify("testing.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		MyDocument doc = null;
		try {
			doc = DocumentUtil.getDocument("testing.txt");
			doc.cat = cat;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			if (doc.cat.equals(MyDocument.person)) {
				Set<String> questions = Questions.getPerson(doc);
				for (String q : questions) {
					System.out.println(q);
				}
			} else if (doc.cat.equals(MyDocument.city)){
				Set<String> questions = Questions.getCity(doc);
				for (String q : questions) {
					System.out.println(q);
				}
			} else if (doc.cat.equals(MyDocument.lang)){
				Set<String> questions = Questions.getLang(doc);
				for (String q : questions) {
					System.out.println(q);
				}
			} else if (doc.cat.equals(MyDocument.inst)){
				Set<String> questions = Questions.getInst(doc);
				for (String q : questions) {
					System.out.println(q);
				}
			}
		
		
		
		/*
		// keys are categories. values are maps that go from words to probabilities.
		HashMap<String, HashMap<String, Double>> catProbs = new HashMap<String, HashMap<String, Double>>();
		
		DocClassify.fillProbs(trainingDocs, catWords, catProbs);

		//HashMap<String, Integer> testing = null;
		List<String> testing = null;
		
		try {
			testing = DocClassify.getWords("testing.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(DocClassify.classify(testing, catProbs)); */
	}
	
}
