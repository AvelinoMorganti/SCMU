package com.whaves.scmu;

/**
 * Created by avelino on 21/03/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;


import java.net.CookieManager;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Button buttonSettings;
    private boolean login = false;
    private EditText editLogin;
    private EditText editPassword;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        buttonSettings = (Button) findViewById(R.id.buttonLogin);
        buttonSettings.setOnClickListener(this);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSettings || login == true) {
            login = true;

            String login = editLogin.getText().toString();
            String pass = editPassword.getText().toString();
            String json = "http://whaves.com/app/api.json";
            String url_login = "http://whaves.com/scmu/login";

            /********************-----------AUTENTICACAO POST --------------*********************/
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", login) );
            postParameters.add(new BasicNameValuePair("password", json) );

            authPost(url_login, postParameters);

            /*************------********************************************/


                /*CookieManager cookieManager = new CookieManager();
                CookieHandler.setDefault(cookieManager);*/

                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setMessage("login");
                dialog.setNeutralButton("OK", null);
                dialog.show();


                Intent it = new Intent(this, MainActivity.class);
                //it.putExtra("VALOR",edtValor.getText().toString());
                startActivity(it);



        }
    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.whaves.scmu/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.whaves.scmu/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    /*private void autenticar() {
        CookieHandler.setDefault(new CookieManager());

        try {
            String page = getPageContent("whaves.com/api/login?username=avelino&password=123");
            String postParams = getFormParams(page, "membro", "membro");

            sendPost("", postParams);

            String result = getPageContent(url);

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }*/


    private static void authPost(String url, ArrayList<NameValuePair> postParameters){

        try {

            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost("http://whaves.com/scmu/login" );
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);

            CookieStore cookieStore = ((DefaultHttpClient) client).getCookieStore();

            /******* PRINT COOKIES ************/
            List<Cookie> listCookies = cookieStore.getCookies();
            int i=0;
            for(Cookie l: listCookies){
                System.out.println(l.getName() + "-" + l.getValue());
                i++;
                System.out.println(i);
            }
            /**********************************/

            HttpClient clientWithCookie = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
 
            HttpPost request_with_cookies = new HttpPost("http://whaves.com/scmu/getState");
            //UrlEncodedFormEntity formEntity_with_cookies = new UrlEncodedFormEntity(postParameters);
            //request.setEntity(formEntity);
            HttpResponse response_with_cookies = clientWithCookie.execute(request_with_cookies);


            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());
            System.out.println(responseAsString );

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    private static HttpClient getHttpClient() {

        HttpClient mHttpClient = new DefaultHttpClient();
        final HttpParams params = mHttpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);
        ConnManagerParams.setTimeout(params, 5000);

        return mHttpClient;
    }
}
