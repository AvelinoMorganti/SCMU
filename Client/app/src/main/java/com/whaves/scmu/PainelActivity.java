package com.whaves.scmu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PainelActivity extends Activity implements View.OnClickListener {

    private Button buttonSettings;
    private TextView textLum;
    private TextView textGas;
    private Switch switchLamp;
    private Switch switchAlarm;


    int time = 5000;             //call function every 3 seconds
    int i = 0;
    private Timer myTimer;
    private String username;
    private String password;
    private Request request;
    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.painel);

        textLum = (TextView) findViewById(R.id.textLum);
        textGas = (TextView) findViewById(R.id.textGas);

        switchLamp = (Switch) findViewById(R.id.switchLamp);
        switchAlarm = (Switch) findViewById(R.id.switchAlarm);



        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, time);

        Intent i = getIntent();
        username = i.getStringExtra("USERNAME");
        password = i.getStringExtra("PASSWORD");
    }


    @Override
    public void onClick(View v) {
        if (v == buttonSettings) {

            Intent i = getIntent();
            CookieStoreImpl data = (CookieStoreImpl) i.getSerializableExtra("COOKIESTORE");

            List<CookiesImpl> cookiesList = (List<CookiesImpl>) data.getData();

            Log.wtf("0000000", cookiesList.get(0).getValue());
            Log.wtf("1111111", cookiesList.get(1).getValue());
            Log.wtf("2222222", cookiesList.get(2).getValue());


            /*
            Request request = new Request();
            String response = request.getStateJSON(cookieStore);
            showMessage(response, "OK");
            */


            Intent itSettings = new Intent(this, SettingsActivity.class);
            itSettings.putExtra("USERNAME", username);
            itSettings.putExtra("PASSWORD", password);
            startActivity(itSettings);

        }
    }

    private void TimerMethod() {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.
        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);

    }


    private Runnable Timer_Tick = new Runnable() {
        //This method runs in the same thread as the UI.
        //Do something to the UI thread here
        public void run() {
            //textLum.setText(i + "s");
            //i+=5;

            double lum =  60.0 + Math.random() * 40;
            double air = Math.random() * 100;

            NumberFormat nf= new DecimalFormat("#.##");
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            //nf.setRoundingMode(RoundingMode.HALF_UP);

            //DecimalFormat df = new DecimalFormat("#0,00");

            textLum.setText("Luminosity: " + String.valueOf(nf.format(lum))+ "%");
            textGas.setText("Air quality: " + String.valueOf(nf.format(air)) +"% Pollution");

            //Requests funcionando...
            /*
            request = new Request();
            String req = request.getStateJSON(username, password);

            Gson gson = new Gson();
            State a = gson.fromJson(req, State.class);
            textGas.setText(String.valueOf(a.getHarmfulGases()));
            textLum.setText(String.valueOf(a.getLuminosity()));*/
            //TODO - GET from getState

        }
    };

    public void showMessage(String msg, String button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PainelActivity.this);
        dialog.setMessage(msg);
        dialog.setNeutralButton(button, null);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
    }
}


/*new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    textLum.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    textGas.setText("done!");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setMessage("baaaaaaam");
                    dialog.setNeutralButton("Done", null);
                    dialog.show();
                }
            }.start();*/