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

            Intent it = new Intent(this, MainActivity.class);
            //it.putExtra("VALOR",edtValor.getText().toString());
            startActivity(it);
        }
    }

    //  String page = getPageContent("whaves.com/api/login?username=avelino&password=123");


}