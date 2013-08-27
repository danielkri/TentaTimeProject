package se.joda.tentatime;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by daniel on 8/7/13.
 *
 * @author Daniel Kristoffersson
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class Result extends Activity {

    String url;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        url = b.getString("url");
    }
}