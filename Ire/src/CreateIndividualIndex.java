import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class CreateIndividualIndex {
	   private static  Set<String> terms = new HashSet<String>();
	   private static int totalNoOfFiles = 0;
	   private static Terms vector1;
	   private static Terms vector2;
	   private static BufferedWriter similarityMatrix;
	   private static BufferedWriter dbug;
	   private static HashMap<Integer, Integer> ourIDToInternalID = new HashMap<Integer, Integer>(); 
private static int fid;
	   
	   public static void  createTerms(int k, IndexReader reader, int b) throws IOException{
		   Terms vector;
			vector = (Terms) reader.getTermVector(k, "Content");
			if(b == 1)
	        {
	        	vector1 = vector;
	        }
	        else
	        {
	        	vector2 = vector;
	        }
			TermsEnum termsEnum = null;
			termsEnum = vector.iterator(termsEnum);
			BytesRef text = null;
			while ((text = termsEnum.next()) != null) {	
				terms.add(text.utf8ToString());
			}
			for(String term: terms)
			{
			//dbug.write(term+ " % ");
			}
			//dbug.newLine();
			dbug.flush();
	   }
	   
	public static RealVector toRealVector(int b,IndexReader reader) {
		try {
	        
	        Terms vector = null;
	        if(b == 1)
	        {
	        	vector = vector1;
	        }
	        else
	        {
	        	vector=vector2;
	        }
	        	
			TermsEnum termsEnum = null;
			termsEnum = vector.iterator(termsEnum);
			Map<String, Integer> frequencies = new HashMap<String, Integer>();
			
			//TODO : remove stop words if required
			BytesRef text = null;
			while ((text = termsEnum.next()) != null) {
				int freq = (int) termsEnum.totalTermFreq();
	//				System.out.println("term = " + term + " freq = " + freq);
				frequencies.put(text.utf8ToString(), freq);
			}
			
			
			RealVector realVector = new ArrayRealVector(terms.size());
			System.out.println("haha"+terms.size());
			//System.in.read();
	        int i=0;	        
	        for (String term : terms) {
	            int value = frequencies.containsKey(term) ? frequencies.get(term) : 0;
	            realVector.setEntry(i++, value);
	        }
	        	        return (RealVector) realVector.mapDivide(realVector.getL1Norm());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		

		try {
			File f1 = new File(Constants.PATH_OF_INPUT_FILES);
			
			Map<Integer, String> IdToNameMapping = new HashMap<Integer, String>();
			BufferedReader mappingFile = new BufferedReader(new FileReader(new File(Constants.PATH_OF_MAPPING_FILE)));
			String line;
			while((line = mappingFile.readLine())!= null)
			{
				String[] parts = line.split(",");
				int num = Integer.parseInt(parts[0]);
				IdToNameMapping.put(num, parts[1]);
			}
			
			totalNoOfFiles = f1.listFiles().length;
			System.out.println(totalNoOfFiles);
			for(int i=1;i<=totalNoOfFiles;i++ ){
				//System.out.println("Check12");
				Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
				IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_42, analyzer);
				FSDirectory dir = FSDirectory.open(new File(Constants.PATH_OF_TERM_VECTOR_INDEX_FILES));
						//open(new File(Constants.PATH_OF_TERM_VECTOR_INDEX_FILES));
				//System.out.println("Check3");
				IndexWriter writer = new IndexWriter(dir, iwc);
				//System.out.println("Check1");
				Document doc = new Document();
				//System.out.println(Constants.PATH_OF_INPUT_FILES +IdToNameMapping.get(i));
				FileReader fr = new FileReader(Constants.PATH_OF_INPUT_FILES + IdToNameMapping.get(i));
				BufferedReader br1 = new BufferedReader(fr, 30000);
				
				doc.add(new IntField("DocID", i, Field.Store.YES));
				doc.add( new VecTextField("Content", br1, Store.NO));
				//doc.add(new IndexableField)
				//System.out.println("Check4");
				System.out.println(doc);
				writer.addDocument(doc);
				//System.out.println("Check5");
				writer.close();
			}
	
			
			
//			//INDEX READING
			System.out.println(Constants.PATH_OF_SIMILARITY_MATRIX_FILE);
			similarityMatrix = new BufferedWriter(new FileWriter(new File(Constants.PATH_OF_SIMILARITY_MATRIX_FILE)));
			dbug = new BufferedWriter(new FileWriter(new File(Constants.PATH_OF_INDIVIDUAL_DEBUG_LOG)));
			similarityMatrix.write(totalNoOfFiles+"\t"+totalNoOfFiles*totalNoOfFiles); //TODO: Check if extra \t required
			similarityMatrix.newLine();

			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(Constants.PATH_OF_TERM_VECTOR_INDEX_FILES)));
			System.out.println("reader.numDocs()" + reader.numDocs());
			System.out.println("reader.maxDoc()" + reader.maxDoc());
			for(int i=0;i<reader.maxDoc();i++)
			{
				Document d = reader.document(i);
				int ID = Integer.parseInt(d.get("DocID"));
				System.out.println("ID"+ID);
                ourIDToInternalID.put(ID, i);				
				Terms vector = reader.getTermVector(i, "Content");
				TermsEnum termsEnum = null;
				termsEnum = vector.iterator(termsEnum);
				System.out.println(termsEnum);
				System.out.println("Printed\n");
				BytesRef text = null;
				//dbug.write();
				while ((text = termsEnum.next()) != null) {
					System.out.println((text.utf8ToString()+ " "));
				}
				//dbug.newLine();
				dbug.flush();
				 
			}
			
			
			for(int i=1;i<=totalNoOfFiles;i++)
			{
				
			GetSimilarityForThisRow(i, reader);
				similarityMatrix.newLine();
			}
			similarityMatrix.close();
			
			//TODO : delete files in PATH_OF_TERM_VECTOR_INDEX_FILES
			long end = System.currentTimeMillis();
			System.out.println("Time = "+(end-start));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error indexing " + Constants.PATH_OF_TERM_VECTOR_INDEX_FILES + " : "
					+ e.getMessage());
		}
	}
	
	private static void GetSimilarityForThisRow(int i,IndexReader reader) throws IOException{
		
		System.out.println(i);
		for(int j=1;j<=totalNoOfFiles/100;j++) //TODO: j > i
		{
			createTerms(ourIDToInternalID.get(i), reader, 1);
			createTerms(ourIDToInternalID.get(j), reader, 2);
			RealVector v1 = toRealVector(1,reader);
			RealVector v2 = toRealVector(2,reader);
			
			try
			{
				double similarity = (v1.dotProduct(v2)) / (v1.getNorm() * v2.getNorm());
			
				similarityMatrix.write("\t" + j + "\t" + similarity);
				//System.out.println("in try");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e.getMessage();
			}
			terms.clear();

		}
	}
}