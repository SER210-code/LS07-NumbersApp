package edu.quinnipiac.ser210.numbersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class YearFactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_fact);

        String yearFact = (String) getIntent().getExtras().get("yearfact");

        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText(yearFact);



    }
}
