package com.example.jsontry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    TextView textView;

//   String jsonUrl="http://api.aladhan.com/v1/calendar?latitude=51.508515&longitude=-0.1254872&method=2&month=4&year=2017";
   String jsonUrl ="https://jsonplaceholder.typicode.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.txt);
        new backgroundTask().execute();

    }
    public class backgroundTask extends AsyncTask<Void,Void,String>{
          ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Wait");
            pd.setMessage("Downloading...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder builder = null;
            try {
                URL uri = new URL(jsonUrl);
                HttpURLConnection con = (HttpURLConnection)uri.openConnection();
                InputStreamReader reader = new InputStreamReader(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line ="";
                builder = new StringBuilder();
                while ((line= bufferedReader.readLine())!= null){
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Log.e("Error",builder.toString());
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            StringBuilder stringBuilder = new StringBuilder();

            try {
//                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = new JSONArray(s);

                for (int i=0;i<array.length();i++){
                    JSONObject object= array.getJSONObject(i);
                    String user = object.getString("username");
                    String address = object.getString("address");
                    JSONObject object1=new JSONObject(address);
                    String city = object1.getString("city");
                    String geo = object1.getString("geo");
                    JSONObject object2=new JSONObject(geo);
                    String lat = object2.getString("lat");
                    stringBuilder.append("username is "+user+"city is "+lat+ "\n");

                     textView.setText(stringBuilder.toString());
//                    Log.e("data", String.valueOf(array));
//                    Log.e("Geo",geo);
                }
               Log.e("data", String.valueOf(array));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}