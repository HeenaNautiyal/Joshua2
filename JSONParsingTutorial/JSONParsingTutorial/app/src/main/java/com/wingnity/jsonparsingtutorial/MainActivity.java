package com.wingnity.jsonparsingtutorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
	
	ArrayList<Actors> actorsList;

	ActorAdapter adapter;
	Button btn_continue;
	String TAG="MainActivity";
	String abc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		actorsList = new ArrayList<Actors>();

		new JSONAsyncTask().execute("http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=28&medicine_id=17&user_eamil=singh@gmail.com&chemist_email=heenanautiyalcse@outlook.com&order_qty=2");
		
		ListView listview = (ListView)findViewById(R.id.card_listView);
		btn_continue=(Button)findViewById(R.id.btn_continue);
		adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {


		    	Actors actors=actorsList.get(position);
				actors.setIsRowSelected(true);
				 actorsList.set(position,actors);
				adapter.updateAdapter(actorsList);
			}
		});

		btn_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e(TAG,"-----------------------------");

						ArrayList<Actors> countryList = adapter.actorList;
						for(int i=0;i<countryList.size();i++){
							Actors actors = countryList.get(i);
							if(actors.isSelected()){
								abc=actors.getProduct(i);
					}
				}
			}
		});
	}


	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				

				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject emp=(new JSONObject(data)).getJSONObject("employee");
					String empname=emp.getString("id");
					String empsalary=emp.getString("medicinename");
					Log.e("employe Id",empname);
					Log.e("medicine Name",empsalary);
				
					/*JSONObject jsono = new JSONObject(data);
					JSONArray jarray = jsono.getJSONArray("actors");
					
					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);
					
						Actors actor = new Actors();
						
						actor.setName(object.getString("name"));
						actor.setDescription(object.getString("description"));
						actor.setDob(object.getString("dob"));
						actor.setCountry(object.getString("country"));
						actor.setHeight(object.getString("height"));
						actor.setSpouse(object.getString("spouse"));
						actor.setChildren(object.getString("children"));
						actor.setImage(object.getString("image"));
						
						actorsList.add(actor);
					}*/
					return true;
				}

			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

		}
	}
}
