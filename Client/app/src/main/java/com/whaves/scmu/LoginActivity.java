package com.whaves.scmu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.net.Uri.parse;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Button buttonSettings;
    private TextView textErrorMsg;
    private TextView textUsername;
    private TextView textPassword;
    private EditText editLogin;
    private EditText editPassword;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.login);

        buttonSettings = (Button) findViewById(R.id.buttonLogin);
        buttonSettings.setOnClickListener(this);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textErrorMsg = (TextView) findViewById(R.id.textErrorMsg);
        textUsername = (TextView) findViewById(R.id.textUsername);
        textPassword = (TextView) findViewById(R.id.textPassword);

        Log.e("LoginActivity", "Login foi criado");

        editLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editLogin.setHint("");
                else
                    editLogin.setHint("example@whaves.com");
            }
        });
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editPassword.setHint("");
                else
                    editPassword.setHint("••••••••••");
            }
        });

       /* textUsername.setOnClickListener(new View.OnClickListener() {
            public void onFocusChange (View v,boolean hasFocus){
                if (hasFocus)
                    Toast.makeText(getApplicationContext(),
                            "Username", Toast.LENGTH_SHORT).show();
            }
        });*/

/*TODO

-> quando pressionar no textview mudar o foco para o edit view correspondente
->

*/
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSettings) {
            String username = editLogin.getText().toString();
            String password = editPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "Username or password is empty", Toast.LENGTH_SHORT).show();

            } else {
                //Autenticação
                Request w = new Request();
                CookieStore cookieStore = w.authPost(username, password);
                if (cookieStore == null) {
                    //Usuário não autenticado..
                    Log.wtf("Cookie null?", "NULL");
                    //textErrorMsg.setText("Username or password is incorrect");
                    //textErrorMsg.setTextColor(Color.parseColor("#FFFF0000"));
                    //showMessage("Login ou senha incorretos", "OK");
                    Toast.makeText(getApplicationContext(),
                            "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    textErrorMsg.setText("");
                    textErrorMsg.setTextColor(Color.parseColor("#FFFFFFFF"));

                    Intent itPainel = new Intent(this, PainelActivity.class);

                    List<Cookie> cookies = cookieStore.getCookies();
                    List<CookiesImpl> listCookies = new ArrayList<CookiesImpl>();
                    for (Cookie c : cookies) {
                        listCookies.add(
                                new CookiesImpl(c.getName(),
                                        c.getValue(),
                                        c.getComment(),
                                        c.getCommentURL(),
                                        c.getDomain(),
                                        c.getExpiryDate(),
                                        c.getPath(),
                                        c.getPorts(),
                                        c.getVersion(),
                                        c.isPersistent(),
                                        c.isSecure(),
                                        c.isExpired(new Date())));
                    }


                    itPainel.putExtra("COOKIESTORE", new CookieStoreImpl(listCookies));
                    itPainel.putExtra("BLOGIN", "true");
                    itPainel.putExtra("USERNAME", username);
                    itPainel.putExtra("PASSWORD", password);

               /*CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.getCookie()*/

                    startActivity(itPainel);
                }
            }
        }
    }

    //Quando o botao: "voltar", for pressionado, volta a Painel.
    @Override
    public void onBackPressed() {
        //Intent it = new Intent(this, PainelActivity.class);
        //startActivity(it);
    }

    /*
    @Override
    public void onRestart() {
        super.onRestart();
        showMessage("onRestart", "onRestart");
    }

    @Override
    public void onPause() {
        super.onPause();
        showMessage("onPause", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        showMessage("onResume", "onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        showMessage("onStart", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        showMessage("onStop", "onStop");
    }

    */

    public void showMessage(String msg, String button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setMessage(msg);
        dialog.setNeutralButton(button, null);
        dialog.show();
    }
}


//w.setStateJSON(cs, "{\"id\":16,\"lamp\":true,\"alarm\":true,\"smsNotifications\":true,\"latitude\":\"1\",\"longitude\":\"1\",\"harmfulGases\":1.0,\"luminosity\":1.0}");

//ret = w.getStateJSON(cs);
//Log.i("JSON 3: ", "retorno 3: " + ret);
//showMessage("JSON 3: " + ret, "OK");
           /* Intent intent = new Intent(this, MainActivity.class);

            List<Cookie> cookies =  cookieStore.getCookies();
            int i=0;
            for(Cookie c : cookies ){
                intent.putExtra("cookieKey"+i, c.getName().toString());
                intent.putExtra("cookieValue"+i, c.getValue().toString());
                i++;
            }
            startActivity(intent);
            i=0;*/


////////************************************************
           /*  List<Cookie> cookies = cookieStore.getCookies();
            Intent i = new Intent(this, MainActivity.class);


            List<CookiesImpl> listCookies = new ArrayList<CookiesImpl>();
            for (Cookie c : cookies)
                listCookies.add(new CookiesImpl(c.getName(), c.getValue()));

            i.putExtra("COOKIESTORE", new CookieStoreImpl(listCookies));


            startActivity(i);

            /***********************************************/
            /*blogin = true;

            Intent it = new Intent(this, PainelActivity.class);
            startActivity(it);
            */