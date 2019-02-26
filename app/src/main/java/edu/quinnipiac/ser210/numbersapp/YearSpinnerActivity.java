package edu.quinnipiac.ser210.numbersapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YearSpinnerActivity extends Activity {

    private final String LOG_TAG = YearSpinnerActivity.class.getSimpleName();
    // Will contain the raw JSON response as a string.
    String yearFactJsonStr = null;

    YearsHandler yrHandler = new YearsHandler();

    boolean userSelect = false;
    private String url1 = "https://numbersapi.p.rapidapi.com/";
    private String url2= "/year?fragment=true&json=true";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_spinner);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);


        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,yrHandler.years);

        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(yearsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (userSelect) {
                    final String item = (String) parent.getItemAtPosition(position);
                    Log.i("onItemSelected :year", item);

                    new FetchYearFact().execute(item);

                    userSelect = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userSelect = true;

    }
    private class FetchYearFact extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection =null;
            BufferedReader reader =null;
            String yearFact = null;

            try {
                URL url = new URL(url1 + params[0]
                        + url2);

               urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","UygwA3LnI1mshAPcqbrTdu6rvUkxp1Kd1q6jsnETjeLq2t3LzS");

                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }
                 reader  = new BufferedReader(new InputStreamReader(in));
                // call getBufferString to get the string from the buffer.

                String yearFactJsonString = getBufferStringFromBuffer(reader).toString();

                // call a method to parse the json data and return a string.
               yearFact=  yrHandler.getYearFact(yearFactJsonString);


            }catch(Exception e){
                Log.e(LOG_TAG,"Error" + e.getMessage());
                return null;
            }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG,"Error" + e.getMessage());
                        return null;
                    }
                }
            }

            return yearFact;
        }

        protected void onPostExecute(String result){
            if (result != null){
                Log.d(LOG_TAG, result);

                Intent intent = new Intent(YearSpinnerActivity.this,YearFactActivity.class);
                intent.putExtra("yearfact",result);

                startActivity(intent);

            }
        }
        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
            StringBuffer buffer = new StringBuffer();

            String line;
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0)
                return null;

            return buffer;
        }
    }


}
