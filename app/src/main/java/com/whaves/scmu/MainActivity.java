package com.whaves.scmu;

import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

/**
 * Created by avelino on 16/03/16.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Button buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSettings){
            Intent it = new Intent(this, SettingsActivity.class);
            //it.putExtra("VALOR",edtValor.getText().toString());
            startActivity(it);
        }
    }
}
