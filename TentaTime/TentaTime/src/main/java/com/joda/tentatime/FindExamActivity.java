package com.joda.tentatime;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


/**
 * Created by daniel on 8/6/13.
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class FindExamActivity extends Activity {

    Button btn;
    String url = "http://tentatime-jhejderup.rhcloud.com/v1/exams.json/dat050";
//    String url = "http://tentatime-jhejderup.rhcloud.com/v1/exams.json?course_name=matematik";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_exam);

        btn = (Button) findViewById(R.id.searchB);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: if not empty...

                DownloadJsonTask task = new DownloadJsonTask();
                task.execute(url);

                //Intent intent = new Intent(FindExamActivity.this, Result.class);
                //Bundle b = new Bundle();
                //b.putString("url", "http://tentatime-jhejderup.rhcloud.com/v1/exams.json/dat050"); // the url)
                //b.putString("url", url); // the url)
                //intent.putExtras(b); //put the url to the next Intent
                //startActivity(intent);
                //finish();
            }
        });
    }

    private class DownloadJsonTask extends AsyncTask<String, Void, SearchResponse> {
        @Override
        protected SearchResponse doInBackground(String... urls) {
            //TODO: check that only one url exists!
            SearchResponse response = new SearchResponse();
            for (String url : urls) {
                //TODO: add handling of missing internet conenctivity...
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                    System.out.print(content.toString());
                    InputStreamReader reader = new InputStreamReader(content);
                    Gson gson = new Gson();
                    response = gson.fromJson(reader, SearchResponse.class); //TODO: check json for null...
                } catch (Exception e) {
                    e.printStackTrace();
                    //onPostExecute will handle this instead...
                    response = null;
                }
            }
            return response;
        }
        @Override
        protected void onPostExecute(SearchResponse response) {
            //TODO: fix return to main thread.

            List<ExamResult> results;

            if(response != null)
                results = response.getResults();
            else
                //Todo: make a toast: list is empty...
                Toast.makeText(getApplicationContext(), "No exams were found, please try again!", Toast.LENGTH_LONG).show();

        }
    }
}