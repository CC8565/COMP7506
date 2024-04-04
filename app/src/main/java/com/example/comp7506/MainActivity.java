package com.example.comp7506;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comp7506.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private class getAPIData extends AsyncTask<String, String, String> {
        private Context context;
        ArrayList<PetAdoption> data;

        public getAPIData(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            String url = "https://sheets.googleapis.com/v4/spreadsheets/1zJDWR96nD6h20x8e3Mr8NHsrT4KpGzalaWXwv_pLKag?includeGridData=true&key=AIzaSyClTM6kyb_y6Ww3xcWWwK_Kv_c1OIWvyeE";

            data = new ArrayList<PetAdoption>();

            Response response = null;
            try {
                Request request = new Request.Builder().url(url).build();
                response = client.newCall(request).execute();

                String result = response.body().string();

                int lineNum = 0;

                JSONObject mainObject = new JSONObject(result);
                JSONArray sheetsArray = mainObject.getJSONArray("sheets");

                // First Sheet
                JSONObject sheetObject = sheetsArray.getJSONObject(0);
                JSONArray dataArray = sheetObject.getJSONArray("data");
                JSONObject dataObject = dataArray.getJSONObject(0);

                // First Sheet data object
                JSONArray rowDataArray = dataObject.getJSONArray("rowData");
                lineNum = rowDataArray.length();

                // Header Line (Not Use)
                JSONObject headerObject = rowDataArray.getJSONObject(0);

                // Row Data
                for(int i = 1; i < lineNum; i++) {
                    String organization = "";
                    String type = "";
                    String gender = "";
                    String name = "";
                    String adpotionNumber = "";
                    String source = "";
                    String date = "";

                    JSONObject eachRowObject = rowDataArray.getJSONObject(i);
                    JSONArray valueArray = eachRowObject.getJSONArray("values");

                    // Field Data
                    // 0 = Index Number
                    // 1 = Organization
                    // 2 = Type
                    // 3 = Gender
                    // 4 = Name
                    // 5 = Adoption Number
                    // 6 = Source
                    // 7 = Date

                    // 0 Index Number (Not Used)
                    JSONObject fieldObect = valueArray.getJSONObject(0);
                    JSONObject valueObject = fieldObect.getJSONObject("userEnteredValue");

                    // 1 Organization
                    fieldObect = valueArray.getJSONObject(1);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    organization = valueObject.getString("stringValue");

                    // 2 Type
                    fieldObect = valueArray.getJSONObject(2);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    type = valueObject.getString("stringValue");

                    // 3 Gender
                    fieldObect = valueArray.getJSONObject(3);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    gender = valueObject.getString("stringValue");

                    // 4 Name
                    fieldObect = valueArray.getJSONObject(4);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    name = valueObject.getString("stringValue");

                    // 5 Adoption Number
                    fieldObect = valueArray.getJSONObject(5);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    adpotionNumber = valueObject.getString("stringValue");

                    // 6 Source
                    fieldObect = valueArray.getJSONObject(6);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    source = valueObject.getString("stringValue");

                    // 7 Date
                    fieldObect = valueArray.getJSONObject(7);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    date = valueObject.getString("stringValue");

                    // Prepare PetAdoption Record
                    PetAdoption rec = new PetAdoption(organization, type, gender, name, adpotionNumber, source, date);

                    data.add(rec);
                }
            } catch (Exception e) {
                System.out.print("Unknown Exception: ");
                System.out.println(e);
            }
            return response.toString();
        }

        protected  void onPostExecute(String x) {
            // HERE is the place to switch activity page !!!!!!!!!!

            //Intent intent = new Intent(context, ShowDataActivity.class);
            //intent.putExtra("sheetData", data);
            //context.startActivity(intent);
        }

    }


}