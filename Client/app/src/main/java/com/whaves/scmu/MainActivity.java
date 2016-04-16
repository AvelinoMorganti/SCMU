package com.whaves.scmu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by avelino on 16/03/16.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button buttonSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //setContentView(R.layout.login);

        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == buttonSettings) {
            //Intent itSettings = new Intent(this, SettingsActivity.class);
            Intent itGPS = new Intent(this, GPS.class);
            //it.putExtra("VALOR",edtValor.getText().toString());


            //startActivity(itSettings);
            startActivity(itGPS);
            //it.putExtra("VALOR",edtValor.getText().toString());

        }
    }

  /* private void autenticar() {
        CookieHandler.setDefault(new CookieManager());

        try {
            String page = getPageContent("http://whaves.com/api/login");
            String postParams = getFormParams(page, "membro", "membro");

            sendPost("", postParams);

            String result = getPageContent(url);

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }*/

}
