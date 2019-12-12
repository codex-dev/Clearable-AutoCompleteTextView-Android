package com.example.searchable.autocompletetextview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Country> countryList;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillCountryList();
    }

    private void fillCountryList() {
        countryList = new ArrayList<>();
        gson = new Gson();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://raw.githubusercontent.com/CoalaWeb/cw-country-iso-code/master/src/cw-country-iso-code.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Request Successful!", Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray jArray = new JSONArray(response);
                            for (int i = 0; i < jArray.length(); i++) {
                                Country country = gson.fromJson(jArray.get(i).toString(), Country.class);
                                countryList.add(country);
                            }

                            AutoCompleteTextView editText = findViewById(R.id.actv);
                            AutoCompleteCountryAdapter adapter = new AutoCompleteCountryAdapter(
                                    MainActivity.this, R.layout.country_autocomplete_row, countryList); //android.R.layout.simple_dropdown_item_1line
                            editText.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }
}
