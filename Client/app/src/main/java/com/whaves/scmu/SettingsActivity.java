    package com.whaves.scmu;

    import android.app.Activity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;

    /**
     * Created by avelino on 16/03/16.
     */

    public class SettingsActivity extends Activity implements View.OnClickListener {

        private Button buttonBack;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.settings_layout);

            buttonBack = (Button) findViewById(R.id.buttonBack);
            buttonBack.setOnClickListener(this);

        }

        public void onClick(View v){
            if(v == buttonBack){
                finish();
            }
        }

    }