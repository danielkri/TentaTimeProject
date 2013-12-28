package com.joda.tentatime;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by daniel on 8/7/13.
 *
 * @author Daniel Kristoffersson
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class Result extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();
        ExamResult mExam = (ExamResult) b.getSerializable("key");

        setupWidgets(mExam);
    }

    private void setupWidgets(ExamResult exam){
        TextView name = (TextView) findViewById(R.id.examName); if (name == null) { Log.w("", "TextView is null"); }
        name.append(exam.getName().trim());
        TextView code = (TextView) findViewById(R.id.examCode); if (code == null) { Log.w("", "TextView is null"); }
        code.append(exam.getCode());
        TextView date = (TextView) findViewById(R.id.examDate); if (date == null) { Log.w("", "TextView is null"); }
        date.append(exam.getExamDate());
        TextView time = (TextView) findViewById(R.id.examTime); if (time == null) { Log.w("", "TextView is null"); }
        time.append(exam.getBeginsAt());
        TextView description = (TextView) findViewById(R.id.examDescription); if (description == null) { Log.w("", "TextView is null"); }
        description.append(exam.getExamDate());
    }

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        List<ExamResult> result = (List<ExamResult>)b.getSerializable("key");

        String examNames[] = new String[result.size()];
        int pos = 0;

        for(ExamResult exam: result) {
            examNames[pos] = exam.getCode() + ": " + exam.getName() + exam.getBeginsAt() + " " + exam.getExamDate();
            pos++;
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examNames));
    }*/
}