import java.io.IOException;


public class GenerateInputForDocToMat
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		try
		{
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("ls");
			int i=0;
			i++;
			System.out.println("Over");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
