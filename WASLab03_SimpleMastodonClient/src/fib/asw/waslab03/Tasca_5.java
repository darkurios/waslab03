package fib.asw.waslab03;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONObject;

public class Tasca_5 {
	private static final String LOCALE = "ca";
	public static void main(String[] args) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'a les' HH:mm:ss", new Locale(LOCALE));
				String now = sdf.format(new Date());

				String my_status = "Hola, @fib_asw@mastodont.cat, ja sóc aquí! #waslab03\n[" + now + "]";

				JSONObject body = new JSONObject();
				body.put("status", my_status);
				body.put("language", LOCALE);

				String URI = "https://mastodont.cat/api/v1/accounts/109274815459984133/statuses?limit=1";
				String TOKEN = Token.get();

				try {
					String output = Request.get(URI)
							//.bodyString(body.toString(), ContentType.parse("application/json"))
							.addHeader("Authorization","Bearer "+TOKEN)
							.execute()
							.returnContent()
							.asString();

					JSONObject result = new JSONObject(output.substring(1));
					String id = result.getString("id");
					String url = result.getString("url");
					String content = result.getString("content");
					System.out.format("\n TUT ID:\n - id: %s\n - url: %s\n", id, url);
					System.out.println("CONTENT");
					System.out.println(content);
				
					URI = "https://mastodont.cat/api/v1/statuses/"+id+"/reblog";
					output = Request.post(URI)
							.bodyString(body.toString(), ContentType.parse("application/json"))
							.addHeader("Authorization","Bearer "+TOKEN)
							.execute()
							.returnContent()
							.asString();
					
					System.out.println(output);
					

				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			

	}

}
