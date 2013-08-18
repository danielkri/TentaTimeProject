package se.joda.tentatime;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by daniel on 8/7/13.
 */
public class RetreiveExamsTask extends AsyncTask<String, Void, InputStream> {


    protected InputStream doInBackground(String... urls) {

        DefaultHttpClient client = new DefaultHttpClient();
        URL url;
        HttpGet getRequest;

        try {
            url = new URL(urls[0]);
            getRequest = new HttpGet(url.toString());

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();
        } catch (IOException e) {
            //getRequest.abort();
            //Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }

        return null;
    }
}
