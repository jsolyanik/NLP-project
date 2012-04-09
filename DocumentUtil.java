import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DocumentUtil {

	public static Set<MyDocument> getDocuments() throws IOException {
		File dir = new File("training");
		String files[] = dir.list();
		
		Set<MyDocument> docs = new HashSet<MyDocument>();
		BreakIterator senItr = BreakIterator.getSentenceInstance();
		
		for (String file : files) {
			MyDocument doc = new MyDocument();
			
			if (!file.contains(".txt")) {
				continue;
			}
			
			BufferedReader in = new BufferedReader(new FileReader("training/" + file));
			
			String cat = in.readLine(); // first line is category
			doc.cat = cat;
			
			List<String> sens = new ArrayList<String>();
			String line;
			
			HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();
			
			while ((line = in.readLine()) != null) {				

				senItr.setText(line);
				int last = 0;
				int next;
				while ((next = senItr.next()) != BreakIterator.DONE) {
					String it = line.substring(last, next);
					it = it.replaceAll("\\. ", " \\. ");
					it = it.replaceAll("[,]", " , ");
					it = it.replaceAll("[;]", " ; ");
					it = it.replaceAll("[:]", " : ");
					it = it.replaceAll("[!]", " ! ");
					it = it.replaceAll("[?]", " ? ");
					it = it.replaceAll("[(]", " ( ");
					it = it.replaceAll("[)]", " ) ");
					sens.add(it.toLowerCase());
					last = next;
				}
				
				String[] words = line.split("[ *]"); // split on whitespace for words
				for (String word : words) {
					word = word.toLowerCase().replaceAll("[.,;?!)(]", ""); // standardize word
					if (bagOfWords.containsKey(word)) {
						bagOfWords.put(word, bagOfWords.get(word) + 1);
					} else {
						bagOfWords.put(word, 1);
					}
				}
			}
			
			doc.bagOfWords = bagOfWords;
			doc.sentences = sens;
			
			docs.add(doc);
		}
		
		return docs;
	}	
	
}
