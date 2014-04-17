import org.xeustechnologies.googleapi.spelling.Language;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellRequest;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

public class SpellCheckTester
{
	public static String GetCorrection(String input)
	{
		SpellChecker checker = new SpellChecker();
		
		checker.setOverHttps(true); // Use https (default true from v1.1)
		checker.setLanguage(Language.ENGLISH); // Use English (default)
		
		SpellRequest request = new SpellRequest();
		request.setText(input);
		request.setIgnoreDuplicates(true); // Ignore duplicates
		
		SpellResponse spellResponse = checker.check(request);

		StringBuilder possibilities = new StringBuilder();
		if(spellResponse.getCorrections() == null)
		{
			return "";
		}
		for (SpellCorrection sc : spellResponse.getCorrections())
		{
			possibilities.append(sc.getValue());
			possibilities.append(" ");
		}
		return possibilities.toString();
	}
}
