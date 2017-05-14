  package com.nabilla.iakforecast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private TextView tv_weather_data;
    private ProgressDialog progressDialog;
    private List<DataCuaca> dataCuacaList =  new ArrayList<>();
    private RecyclerviewAdapter adapter;
    private DataCuaca dataCuaca;
    private RecyclerView recyclerView;
    private String location;
    private String numberOfDays;
    private final String BASE_URL = "";
    private int cnt;

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        location = sharedPreferences.getString("pref_location", "Bandung");
        numberOfDays = sharedPreferences.getString("pref_day", "7");
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        setTitle(location);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        location = sharedPreferences.getString(key, "Bandung");
        numberOfDays = sharedPreferences.getString("pref_day", "7");
        setTitle(location);
    }

    public class getWeatherData extends AsyncTask<Void, Void, List<DataCuaca>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected List<DataCuaca> doInBackground(Void... voids) {
            //Buat akses internet
            HttpURLConnection httpURLConnection = null;

            //get data
            BufferedReader bufferedReader = null;

            //Buat return data karena parameter fungsinya String
            String stringJson = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Bandung&appid=7f15aab53685a7e10ce0cd0f03a1f228&cnt=5&mode=json&units=metric");
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


                for (int i = 0; i< data_json.length(); i++){
                    JSONObject object = data_json.getJSONObject(i);
                    JSONObject temp = object.getJSONObject("temp");

                    dataCuaca.setDayTemperature(temp.getDouble("day"));
                    dataCuaca.setDate(convertDate(object.getLong("dt")));

                    JSONArray arrayWeather = object.getJSONArray("weather");

                    for (int j = 0; j < arrayWeather.length(); j++){
                        JSONObject objectWeather = arrayWeather.getJSONObject(j);
                        dataCuaca.setWeather(objectWeather.getString("main"));
                        dataCuaca.setDescription(objectWeather.getString("description"));
                    }

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
            adapter.notifyDataSetChanged();
        }

        private void fetchJsonWeather(String response){
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("list");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject temp = object.getJSONObject("temp");
                    JSONArray weather = object.getJSONArray("weather");

                    for (int j=0; j< weather.length(); i++){
                        JSONObject jsonObjectWeather = weather.getJSONObject(j);
                        Log.d("JsonObjectWeather", object.getString("main"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*Untuk ngeconvert long ke date*/


        private String convertDate(long dt){
            Date date = new Date(dt*1000);
            Format formatDate = new SimpleDateFormat("EEE, dd MMM");

            return formatDate.format(date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
