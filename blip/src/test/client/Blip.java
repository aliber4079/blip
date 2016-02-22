package test.client;

 
 

import java.util.logging.Level;
//import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;

import com.google.gwt.dev.json.JsonArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.DevelopmentModeLogHandler;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Blip implements EntryPoint {
	
	 
	public void onModuleLoad() {
		DevelopmentModeLogHandler logger1 = new  DevelopmentModeLogHandler();
	    logger1.publish (new LogRecord(Level.ALL, "this message should get logged"));
	    ConsoleLogHandler logger2=new ConsoleLogHandler();
	    logger2.publish (new LogRecord(Level.ALL, "this message should get logged"));
		//String s="[{\"name\":\"jim\",\"age\":49},{\"name\":\"june\",\"age\":50}]";
        
		//updateTable(JsonUtils.<JsArray<Employee>> safeEval(s));
		
		
		JsArray<Employee> ea = //Employee.createEmployees();//parseJson(s);
		new Object(){
			private native JsArray<Employee> createEmployees() /*-{
			return [{"name":"jim","age":49},{"name":"june","age":50}];
			}-*/;
		}.createEmployees();
		for (int i=0;i<ea.length();i++){
			Employee e=ea.get(i);
			log(e.getAge());
		}
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "gwt.php");
		builder.setHeader("Content-Type", "application/json");
		builder.setHeader("Accept", "application/json");
		SvrReq req = createSvrReq("test1",ea);
		 
		
		try {
			Request request = builder.sendRequest(JsonUtils.stringify(req), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if (response.getStatusCode()==200){
						SvrReq req = parseJson(response.getText());
						JsArray<Employee> ea = req.getEmployees();
						for (int i=0;i<ea.length();i++){
							Employee e=ea.get(i);
							log(e.getAge());
						}

					}
					
				}

				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
				
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void updateTable(JsArray<Employee> safeEval) {
		// TODO Auto-generated method stub
		
	}
	native SvrReq createSvrReq(String method, JsArray<Employee> ea) /*-{
		return {method:method,ea:ea};
	}-*/;
	
	public static <T extends JavaScriptObject> T parseJson(String jsonStr)
	{		 
	  return  JsonUtils.safeEval(jsonStr);
	}

	private native void log(Object o) /*-{
	  $wnd.console.log(o);
		
	}-*/;
}
class SvrReq extends JavaScriptObject {
	protected SvrReq() {}
	public native final void setMethod(String method) /*-{
		this.method=method; 
	}-*/;
	public native final void setEmployees(JsArray<Employee> ea) /*-{
		this.ea=ea;
	}-*/;
	public native final JsArray<Employee> getEmployees() /*-{
		return this.ea;
	}-*/;

}

class Employee extends JavaScriptObject  {
	protected Employee (){}

	public final native static JsArray<Employee> createEmployees()/*-{
		return [{"name":"jim","age":49},{"name":"june","age":50}];
	}-*/;
	
	
	public final native String getName() /*-{
		return this.name;
	}-*/;
	
	public final native String getAge() /*-{
		return this.age;
	}-*/;
	
}