public class Constants
{
	public static final String TYPE = "input";
	//public static final String root  ="/media/9EAEE08CAEE05DF1/Yr 1 Sem 2/IRE/Major Project/";
	public static final String root  ="/home/sudhir/Ire/Ire Project/Ire code/IREDATASET/";
	public static final String PATH_OF_INPUT_FILES = root+ TYPE +"/";
	public static final String PATH_OF_MAPPING_FILE = root + TYPE + "Mapping.txt";
	public static final String PATH_OF_SEARCH_INDEX_FILES = root + TYPE + "IndexFiles";
	public static final String PATH_OF_SIMILARITY_MATRIX_FILE = root + TYPE + "SimilarityMatrix.csv";
	public static final String PATH_OF_TERM_VECTOR_INDEX_FILES = root + TYPE + "individualIndex";
	public static final String PATH_OF_CLUSTER_FILE = root +/* TYPE +*/  "inputSimilarityMatrix.csv.clustering.121";	
	public static final String PATH_OF_TOP_TERMS_FOR_CLUSTER = root + "TopTermsForCluster"+TYPE+".txt";
	public static final int TOP_K_TERMS_IN_CLUSTER = 10;
	public static final String PATH_OF_DEBUG_LOG = root + TYPE + "DebugLog.txt";
	public static final String PATH_OF_INDIVIDUAL_DEBUG_LOG = /*root + TYPE*/  "/media/C460F67E60F67710/IndividualDebugLog.txt";
	public static final int CONSIDER_TOP_N_RESULTS = 3;
	public static final int SCORE_ADDITION_FOR_PRESENCE_IN_CLUSTER = 50;
}
