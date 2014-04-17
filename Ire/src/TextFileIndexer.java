import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.io.*;
import java.util.ArrayList;

public class TextFileIndexer {
  private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

  private IndexWriter writer;
  private ArrayList<File> queue = new ArrayList<File>();


  public static void main(String[] args) throws IOException {

    TextFileIndexer indexer = null;
 
    
    try {
    	System.out.println(Constants.PATH_OF_SEARCH_INDEX_FILES);
	     indexer = new TextFileIndexer(Constants.PATH_OF_SEARCH_INDEX_FILES);
	    
	      File folder = new File(Constants.PATH_OF_INPUT_FILES);
	       for(File f : folder.listFiles()){
	           indexer.indexFileOrDirectory(f.getAbsolutePath());
	       }
	       System.out.println("ok");
      }
      catch (Exception e) {
        System.out.println("Error indexing " + Constants.PATH_OF_SEARCH_INDEX_FILES + " : " + e.getMessage());
    }

    //call closeIndex for creation of index    
    indexer.closeIndex();
  }

  /**
   * Constructor
   * @param indexDir the name of the folder in which the index should be created
   * @throws java.io.IOException when exception creating index.
   */
  TextFileIndexer(String indexDir) throws IOException {
    // the boolean true parameter means to create a new index everytime, 
    // potentially overwriting any existing files there.
    FSDirectory dir = FSDirectory.open(new File(indexDir));


    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);

    writer = new IndexWriter(dir, config);
  
  }

  /**
   * Indexes a file or directory
   * @param fileName the name of a text file or a folder we wish to add to the index
   * @throws java.io.IOException when exception
   */
  
  public void indexFileOrDirectory(String fileName) throws IOException {
    //===================================================
    //gets the list of files in a folder (if user has submitted
    //the name of a folder) or gets a single file name (is user
    //has submitted only the file name) 
    //===================================================
    addFiles(new File(fileName));
    
    int originalNumDocs = writer.numDocs();
    for (File f : queue) {
      FileReader fr = null;
      try {
    	  System.out.println(writer);
    	  System.out.println("T1");
        Document doc1 = new Document();
        System.out.println("T2");
        //===================================================
        // add contents of file
        //===================================================
        
        fr = new FileReader(f);
       // System.out.println(fr.);
        System.out.println(fr);
        System.out.println("T3");
        doc1.add(new TextField("contents", fr));
        System.out.println("T4");
        doc1.add(new StringField("path", f.getPath(), Field.Store.YES));
        System.out.println(f.getPath());
        doc1.add(new StringField("filename", f.getName(), Field.Store.YES));
        System.out.println(doc1.get("contents"));
        System.out.println(doc1.iterator());
        System.out.println(doc1);
        writer.numDocs();
        System.out.println("T6");
        
        writer.addDocument(doc1);
        
        System.out.println("T7");
        System.out.println("Added: " + f);
      } catch (Exception e) {
        System.out.println("Could not add: " + f + " " + e);
      } finally {
        fr.close();
      }
    }
    
    int newNumDocs = writer.numDocs();
    System.out.println("");
    System.out.println("************************");
    System.out.println((newNumDocs - originalNumDocs) + " documents added.");
    System.out.println(newNumDocs);
    System.out.println("************************");

    queue.clear();
  }

  private void addFiles(File file) {

    if (!file.exists()) {
      System.out.println(file + " does not exist.");
    }
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        addFiles(f);
      }
    } else {
      String filename = file.getName().toLowerCase();
      
        queue.add(file);
      
    }
  }

  /**
   * Close the index.
   * @throws java.io.IOException when exception closing
   */
  public void closeIndex() throws IOException {
    writer.close();
  }
}