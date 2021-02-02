package external;


import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnResponse;
import com.monkeylearn.MonkeyLearnException;

public class testmonkey {
	
	private static final String API_Key = "aaa0b32453142c3bae9f7c484511bd542d2d38a5";
	public static final String modelId = "ex_YCya9nrn";
	
    public static void main( String[] args ) {
        
        String[] textList = {"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look."};
        List<List<String>> words = extractKeywords(textList);
        System.out.println(words);
        for (List<String> ws : words) {
        	for (String w: ws) {
        		System.out.println(w);
        	}
        	System.out.println();
        }
    }
    
    public static List<List<String>> extractKeywords(String[] text) {
    	if (text == null || text.length == 0) {
    		return new ArrayList<>();
    	}    	
        MonkeyLearn ml = new MonkeyLearn(API_Key);
    	ExtraParam[] extraParams = { new ExtraParam("max_keywords", "3")};   	
    	MonkeyLearnResponse response;    	
    	try {
			response = ml.extractors.extract(modelId, text, extraParams);
			JSONArray resultArray = response.arrayResult;
			return getKeywords(resultArray);
		} catch (MonkeyLearnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	    	
    	return new ArrayList<>();   	
    }
    
    private static List<List<String>> getKeywords(JSONArray mlResultArray) {
    	List<List<String>> topKeywords = new ArrayList<>();
    	for (int i = 0; i < mlResultArray.size(); ++i) {
    		List<String> keywords = new ArrayList<>();
    		JSONArray keywordsArray = (JSONArray) mlResultArray.get(i);
    		for (int j = 0; j < keywordsArray.size(); ++j) {
    			JSONObject keywordObject = (JSONObject) keywordsArray.get(j);
    			String keyword = (String) keywordObject.get("keyword");
    			keywords.add(keyword);
    		}
    		topKeywords.add(keywords);
    	}
    	return topKeywords;
    }
}