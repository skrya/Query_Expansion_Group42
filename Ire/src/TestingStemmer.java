
public class TestingStemmer
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Stemmer stemmer = new Stemmer();
		String s = "player";
		stemmer.add(s.toCharArray(),s.length());
		stemmer.stem();
		System.out.println(stemmer.toString());
	}

}
