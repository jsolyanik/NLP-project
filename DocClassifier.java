import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import cc.mallet.classify.*;
import cc.mallet.grmm.inference.MessageArray;
import cc.mallet.grmm.inference.MessageArray.Iterator;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.types.*;

public class DocClassifier {
	
	public static String best_cat = null;

	/* change this from main to some function
	 * pass it the input file
	 */
    public static String classify(String test_file) throws IOException {
    	    	
    	File[] files = {new File("training_cats")};
    	//String output = "output.txt";
    	
    	ImportExample importer = new ImportExample();
        InstanceList instances = importer.readDirectories(files);
        //instances.save(new File(output));
        
        Classifier trainer = trainClassifier(instances);
        printLabelings(trainer, new File(test_file));
        
        return best_cat;
    	
    }
	
    public static Classifier trainClassifier(InstanceList trainingInstances) {
    	
    	// Training options: NaiveBayesTrainer, DecisionTreeTrainer, C45Trainer, MaxEntTrainer
    	
        ClassifierTrainer trainer = new MaxEntTrainer();
        return trainer.train(trainingInstances);
    }
    
    public static void printLabelings(Classifier classifier, File file) throws IOException {

        // Create a new iterator that will read raw instance data from                                     
        //  the lines of a file.                                                                           
        // Lines should be formatted as:                                                                   
        //                                                                                                 
        //   [name] [label] [data ... ]                                                                    
        //                                                                                                 
        //  in this case, "label" is ignored.                                                              

        CsvIterator reader =
            new CsvIterator(new FileReader(file),
                            "(\\w+)\\s+(\\w+)\\s+(.*)",
                            3, 2, 1);  // (data, label, name) field indices               

        // Create an iterator that will pass each instance through                                         
        //  the same pipe that was used to create the training data                                        
        //  for the classifier.                                                                            
        java.util.Iterator<Instance> instances =
            classifier.getInstancePipe().newIteratorFrom(reader);

        // Classifier.classify() returns a Classification object                                           
        //  that includes the instance, the classifier, and the                                            
        //  classification results (the labeling). Here we only                                            
        //  care about the Labeling.                                                                       
        while (instances.hasNext()) {
            Labeling labeling = classifier.classify(instances.next()).getLabeling();

            // print the labels with their weights in descending order (ie best first)
            
            
            best_cat = labeling.getLabelAtRank(0).toString();
            
//            for (int rank = 0; rank < labeling.numLocations(); rank++){
//                System.out.print(labeling.getLabelAtRank(rank) + ":" +
//                                 labeling.getValueAtRank(rank) + " ");
//            }
            System.out.println();

        }
    }
    
    
    
    
}
