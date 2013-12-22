package com.joda.tentatime;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by daniel on 8/6/13.
 * <p/>
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class FindExamActivity extends Activity implements OnClickListener {

    ListView examsView;
    EditText mGetExam;
    //    String url = "http://tentatime-jhejderup.rhcloud.com/v1/exams.json/dat050";
    String url = "http://tentatime-jhejderup.rhcloud.com/v1/exams.json?course_name=matematik";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_exam);
        setupWidgets();
    }

    private void setupWidgets() {
        Button btn = (Button) findViewById(R.id.searchB);
        btn.setOnClickListener(this);
        mGetExam = (EditText) findViewById(R.id.getExam);
        examsView = (ListView) findViewById(R.id.examsLV);
        //examsView.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public void onClick(View v) {
        // TODO: if not empty...

        if (v.getId() == R.id.searchB) {
            DownloadJsonTask task = new DownloadJsonTask();
            task.execute(userChoice(mGetExam.getText().toString()));
        }
    }

    private String userChoice(String choice) {

        //reset EditText to ""
        mGetExam.setText("");

        if (InputUtility.validateCourseID(choice))
            return "http://tentatime-jhejderup.rhcloud.com/v1/exams.json/" + choice;
        else if (InputUtility.validateName(choice))
            return "http://tentatime-jhejderup.rhcloud.com/v1/exams.json?course_name=" + choice;
        else
            return "";
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
                    //System.out.print(content.toString());
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
            //TODO: fix return to main thread... DON'T NEED

            List<ExamResult> results;

            if (response != null) {
                results = response.getResults();

               // for(ExamResult exam: results)
                    //examsView.

                Intent intent = new Intent(FindExamActivity.this, Result.class);
                Bundle b = new Bundle();
                b.putSerializable("key", (Serializable) results);
                intent.putExtras(b); //put the list to the next Intent
                startActivity(intent);
                finish();

                //for(ExamResult exam: results)
                //examsView.append(exam.getName() + "\n");
            } else
                //Todo: make a toast: list is empty... DONE
                Toast.makeText(getApplicationContext(), "No exams were found, please try again!", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}