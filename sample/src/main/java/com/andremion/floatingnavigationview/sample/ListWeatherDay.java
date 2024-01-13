package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapter.MyArrayAdapterWeather;
import Entity.Weather;

public class ListWeatherDay extends AppCompatActivity {
    ArrayList<Weather> arr = new ArrayList<Weather>();
    MyArrayAdapterWeather mayArr;
    TextView txtCity,txtCountry;
    ListView lv =null;
    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listday);
        Intent intent=getIntent();
        String city =intent.getStringExtra("name");
        txtCity=(TextView)findViewById(R.id.city);
        lv =(ListView)findViewById(R.id.list);
        btn=(Button)findViewById(R.id.iconback);
        txtCountry=(TextView)findViewById(R.id.country);
        GetSevenDayData(city);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void GetSevenDayData(String data) {
        String url= "https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&appid=2e81cb8b70256d62b3a2f8d3a4deee3e";
        final RequestQueue requestQueue = Volley.newRequestQueue(ListWeatherDay.this);
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Đối tượng dạng JSON
                            JSONObject jsonObject1 =new JSONObject(response);
                            //Lấy tên thành phố và tên nước
                            JSONObject jsonObjectCity = jsonObject1.getJSONObject("city");
                            String namecity =jsonObjectCity.getString("name");
                            txtCity.setText(namecity);
                            String namecountry =jsonObjectCity.getString("country");
                            txtCountry.setText(namecountry);
                            // Danh sách các đối tượng
                            JSONArray jsonArrayObject =jsonObject1.getJSONArray("list");

                            // Lấy Giờ ở lần quan sát thứ 4 trong ngày- mỗi ngày quan sát 8 lần trong 5 ngày tiếp theo
                            for(int i=4;i<40;i=i+8)
                            {
                                // Tạo đối tượng weather
                                Weather weather =new Weather();
                                // Lấy đối tượng json tịa vị trí thứ i
                                JSONObject jsonObject = jsonArrayObject.getJSONObject(i);
                                // lấy dữ liệu thời gian
                                String day =jsonObject.getString("dt");
                                weather.setTxtday(FormatDay(day));
                                //Lấy dữ liệu trạng thái và icon trọng thái thời tiết
                                JSONArray jsonArrayWeather =jsonObject.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("main");
                                String icon =jsonObjectWeather.getString("icon");
                                weather.setImageStatus(icon);
                                weather.setTxtStatus(status);
                                // Lấy dữ liệu nhiệt độ, độ ẩm
                                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                                String temp =jsonObjectMain.getString("temp_min");
                                String doam=jsonObjectMain.getString("humidity");
                                weather.setHum(doam +"%");
                                weather.setTemp(TreatTemp(temp)+"°C");
                                // Lấy dữ liệu tốc độ gió
                                JSONObject jsonObjectWind =jsonObject.getJSONObject("wind");
                                String speedWind =jsonObjectWind.getString("speed");
                                weather.setSpeed(speedWind + "m/s");
                                // lấy dữ liệu % mây
                                JSONObject jsonObjectCloud =jsonObject.getJSONObject("clouds");
                                String may = jsonObjectCloud.getString("all");
                                weather.setCloud(may+"%");
                                // Thêm thời tiết vào danh sách
                                arr.add(weather);
                            }
                            mayArr = new MyArrayAdapterWeather(ListWeatherDay.this, R.layout.item_listview, arr);
                            lv.setAdapter(mayArr);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);

    }

    private String TreatTemp(String temp) {
        Double a=Double.valueOf(temp);
        return String.valueOf(a.intValue());
    }

    private String FormatDay(String day) {
        long l= Long.valueOf(day);
        Date date =new Date(l*1000L);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("EEEE dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }
}
