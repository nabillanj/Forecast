package com.nabilla.iakforecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private Intent intent;
    private TextView tv_max, tv_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_max = (TextView) findViewById(R.id.tv_max);
        tv_min = (TextView) findViewById(R.id.tv_min);

        intent = getIntent();
        if (intent != null){
            tv_max.setText(intent.getStringExtra("MaxSuhu"));
            tv_min.setText(intent.getStringExtra("MinSuhu"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
