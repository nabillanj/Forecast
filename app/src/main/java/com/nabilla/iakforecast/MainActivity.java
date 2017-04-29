package com.nabilla.iakforecast;

import android.app.ProgressDialog;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv_weather_data;
    private ProgressDialog progressDialog;
    private List<DataCuaca> dataCuacaList =  new ArrayList<>();
    private RecyclerviewAdapter adapter;
    private DataCuaca dataCuaca;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* String[] data = {
                "Hari ini, 29 April 2017 - Mendung",
                "Besok, 30 April 2017 - Cerah",
                "Senin, 1 April 2017 - Berawan"
        };

        for (String arrayData : data){
            //Kalo langsung set text dia munculin satu data, jadi pake append
            tv_weather_data.append(arrayData+"\n");
        } */

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerviewAdapter(this, dataCuacaList);
        recyclerView.setAdapter(adapter);

        new getWeatherData().execute();
    }

    public class getWeatherData extends AsyncTask<Void, Void, List<DataCuaca>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Tunggu sebentear");
            progressDialog.show();
        }

        @Override
        protected List<DataCuaca> doInBackground(Void... voids) {
            //Buat akses internetr
            HttpURLConnection httpURLConnection = null;

            //get data
            BufferedReader bufferedReader = null;

            //Buat return data karena parameter fungsinya String
            String stringJson = null;

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Bandung&appid=7f15aab53685a7e10ce0cd0f03a1f228&cnt=6&mode=json&units=metric");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                //Cek input yg masuk
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null){
                    return null;
                }else {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                }

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }

                if (stringBuffer.length() == 0){
                    return null;
                }
                stringJson = stringBuffer.toString();

                JSONObject jsonObject = new JSONObject(stringJson);
                JSONArray data_json = jsonObject.getJSONArray("list");
                dataCuaca = new DataCuaca();

                //Get Maks. Mininum suhu
                for (int i = 0; i< data_json.length(); i++){
                    JSONObject object = data_json.getJSONObject(i);
                    JSONObject temp = object.getJSONObject("temp");

                    dataCuaca.setMax(temp.getDouble("max"));
                    dataCuaca.setMin(temp.getDouble("min"));
                    dataCuacaList.add(dataCuaca);
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.d("ERROR", e.toString());
            }

            Log.d("Jumlah Data", String.valueOf(dataCuacaList.size()));
            return dataCuacaList;
        }

        @Override
        protected void onPostExecute(List<DataCuaca> dataCuacaList) {
            super.onPostExecute(dataCuacaList);
            progressDialog.hide();
            adapter.notifyDataSetChanged();
        }
    }
}
