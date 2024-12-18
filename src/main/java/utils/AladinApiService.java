package utils;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class AladinApiService {
	public static String getBookCover(String bookTitle) {
	    String coverImage = "";
	    try {
	        String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbdongan01250115001&Query="
	                + URLEncoder.encode(bookTitle, "UTF-8")
	                + "&QueryType=Title&MaxResults=1&start=1&output=JS&Version=20131101";
	        String response = APIClient.fetch(apiUrl);

	        JSONObject jsonResponse = new JSONObject(response);
	        JSONArray items = jsonResponse.getJSONArray("item");
	        if (items.length() > 0) {
	            coverImage = items.getJSONObject(0).getString("cover");
	        }
	    } catch (Exception e) {
	        System.err.println("알라딘 API 호출 실패: " + e.getMessage());
	    }
	    return coverImage;
	}


}
