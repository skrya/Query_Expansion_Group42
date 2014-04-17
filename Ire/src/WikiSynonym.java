import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class WikiSynonym extends Thread
{
	String synonymString;
	int index;
	Reader reader = null;
	public WikiSynonym(String synonymString, Reader reader, int index){
		this.synonymString = synonymString;
		this.reader = reader;
		this.index = index;
		start();
	}
	
	@Override
	public void run(){
		reader.storeSynonyms(ParseStringIntoList(GetSynonyms()), index);
	}
	
	/*public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		String query = "IIT";
		ArrayList<String> synonymsList = WikiSynonym.ParseStringIntoList(WikiSynonym.GetSynonyms(query));
		//String synonyms1 = WikiSynonym.GetSynonyms("IIIT");
		//String synonyms2 = WikiSynonym.GetSynonyms("NIT");
		//String synonyms3 = WikiSynonym.GetSynonyms("VIT");
	//	System.out.println(synonyms);
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}
	*/
	
	public JSONArray ParseStringIntoList(String synonymString)
	{
		JSONObject obj=(JSONObject) JSONValue.parse(synonymString);
		if(obj == null)
			return null;
		Object msg = obj.get("message");
		System.out.println(msg);
		if(msg.equals("success") )
		{
			return (JSONArray) obj.get("terms");
/*		     JSONArray terms = (JSONArray) obj.get("terms");
	        Iterator iterator = terms.iterator();
	        while (iterator.hasNext()) {
	        	JSONObject term = (JSONObject)iterator.next();
	            
	        	System.out.println(term.get("term"));
	            System.out.println("===");
	        }*/
		}   
//		Map terms = (Map)(JSONObject) obj.get("terms");
//		
////		JSONArray jsonPersonData = jsonArray.getJSONArray(1);
//
//		    Iterator iter = terms.entrySet().iterator();
//		    System.out.println("==iterate result==");
//		    while(iter.hasNext()){
//		      Map.Entry entry = (Map.Entry)iter.next();
//		      System.out.println(entry.getKey() + "=>" + entry.getValue());
//		    }
		    return null;
	}

	public String GetSynonyms()
	{
		String urlToRead = "http://wikisynonyms.ipeirotis.com/api/"+synonymString;
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.iiit.ac.in", 8080));
		   url = new URL(urlToRead);
		   conn = (HttpURLConnection) url.openConnection(proxy);
		   conn.setRequestMethod("GET");
		   rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		   while ((line = rd.readLine()) != null) {
		      result += line;
		   }
		   rd.close();
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return result;
	}

}
