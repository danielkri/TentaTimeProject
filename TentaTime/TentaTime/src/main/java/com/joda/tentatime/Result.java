package com.joda.tentatime;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by daniel on 8/7/13.
 *
 * @author Daniel Kristoffersson
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class Result extends ListActivity {

    public void onCreate(Bundle savedInstanceState) {
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
    }
}