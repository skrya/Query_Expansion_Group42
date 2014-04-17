import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EnumerateDocuments
{
	public static void main(String[] args)
	{
		try
		{
			BufferedWriter mappingFile = null;

			mappingFile = new BufferedWriter(new FileWriter(new File(Constants.PATH_OF_MAPPING_FILE)));

	
			File file = new File(Constants.PATH_OF_INPUT_FILES);
			int id = 1;
			
//			File farray[] = file.listFiles();
//			System.out.println("LENGTH = " + (farray.length));
			//Collections.sort(farray);
//			for (File f : file.listFiles())
//			{
//				mappingFile.write(f.getName());
//				mappingFile.newLine();
//			}
			System.out.println(Constants.PATH_OF_INPUT_FILES);
			if (file.isDirectory())
			{
				for (File f : file.listFiles())
				{
					System.out.println(id + "," + f.getName());
					mappingFile.write(id + "," + f.getName());
					mappingFile.newLine();
					id++;
				}
			}
			mappingFile.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}
