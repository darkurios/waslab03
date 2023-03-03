package fib.asw.waslab03;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.tools.javac.util.List;

public class Tasca_6 {
	private static final String LOCALE = "ca";
	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'a les' HH:mm:ss", new Locale(LOCALE));
		String now = sdf.format(new Date());

		String my_status = "Hola, @fib_asw@mastodont.cat, ja sóc aquí! #waslab03\n[" + now + "]";

		JSONObject body = new JSONObject();
		body.put("status", my_status);
		body.put("language", LOCALE);

		String URI = "https://mastodont.cat/api/v1/trends/tags?limit=10";
		String TOKEN = Token.get();

		try {
			String output = Request.get(URI)
					//.bodyString(body.toString(), ContentType.parse("application/json"))
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();

			JSONArray result = new JSONArray(output);
			for(int i=0;i<result.length();i++) {
				URI = "https://mastodont.cat/api/v1/timelines/tag/"+result.getJSONObject(i).getString("name")+"?limit=5";
				output = Request.get(URI)
						//.bodyString(body.toString(), ContentType.parse("application/json"))
						.addHeader("Authorization","Bearer "+TOKEN)
						.execute()
						.returnContent()
						.asString();
				JSONObject result2 = new JSONObject(output.substring(1));
				JSONObject account = result2.getJSONObject("account");
				System.out.println("DISPLAY NAME: "+account.getString("display_name"));
				System.out.print("@"+account.getString("username"));
				String url = account.getString("url");
				if(!url.contains("mastodont.cat")) {
					System.out.print("@"+url.substring(8,url.lastIndexOf("/")));
				}
				System.out.println();
				System.out.println("TWEET:");
				System.out.println(result2.getString("content").replaceAll("\\<.*?\\>", ""));
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
