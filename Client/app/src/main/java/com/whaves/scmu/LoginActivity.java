package com.whaves.scmu;

/**
 * Created by avelino on 21/03/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;


/**
 * Created by avelino on 16/03/16.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    private Button buttonSettings;
    private boolean login = false;
    private EditText editLogin;
    private EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        buttonSettings = (Button) findViewById(R.id.buttonLogin);
        buttonSettings.setOnClickListener(this);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSettings || login == true){
            login = true;

            String login = editLogin.getText().toString();
            String pass = editPassword.getText().toString();
            String json = "http://whaves.com/app/api.json";
            String url_login = "http://whaves.com/api/login";

            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            String res="";
            try {
                URL url = new URL(url_login);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                                List<String> values = new ArrayList<String>();
                values.add("login" + "=" + URLEncoder.encode(login, "UTF-8"));
                values.add("password" + "=" + URLEncoder.encode(pass, "UTF-8"));

                StringBuilder result = new StringBuilder();
                for (String param : values) {
                    if (result.length() == 0) {
                        result.append(param);
                    } else {
                        result.append("&" + param);
                    }
                }
                String toSend = result.toString();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(toSend);
                wr.flush();
                wr.close();

                int responseCode =  conn.getResponseCode();
                 res =  Integer.toString(responseCode);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
            dialog.setMessage(res);
            dialog.setNeutralButton("OK", null);
            dialog.show();


            Intent it = new Intent(this, MainActivity.class);
            //it.putExtra("VALOR",edtValor.getText().toString());
            startActivity(it);
        }
    }

    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "Hi"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
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

}
