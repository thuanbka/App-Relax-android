package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {
    public FloatingNavigationView mFloatingNavigationView;
    TextView contry,city,temp,txtstatus,huminity,cloud,wind,daycheck;
    Button btnSeach,checkDay;
    ImageView imageView;
    EditText editSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        startView();
        GetCurrentWeatherData("hanoi");
        btnSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editSearch.getText().toString();
                if(data.equals(""))
                {
                    data="hanoi";
                }
                GetCurrentWeatherData(data);
            }
        });
        checkDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(WeatherActivity.this, ListWeatherDay.class);
                intent.putExtra("name",city.getText().toString());
                startActivity(intent);
            }
        });

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getTitle().equals("Danh sách bài hát"))
                {
                    Intent intent = new Intent(WeatherActivity.this, ListMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Ai là triệu phú"))
                {
                    Intent intent =new Intent(WeatherActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Nghe nhạc"))
                {
                    Intent intent =new Intent(WeatherActivity.this,PlayMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Bài hát yêu thích"))
                {
                    Intent intent =new Intent(WeatherActivity.this,ListLoveMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Đuổi hình bắt chữ"))
                {
                    Intent intent =new Intent(WeatherActivity.this,MainCatchWord.class);
                    startActivity(intent);
                    finish();
                }

                mFloatingNavigationView.close();
                return true;
            }
        });
    }

    private void GetCurrentWeatherData(String data)
    {
        String url= "https://api.openweathermap.org/data/2.5/find?q="+data+"&units=metric&appid=2e81cb8b70256d62b3a2f8d3a4deee3e";
        final RequestQueue requestQueue = Volley.newRequestQueue(WeatherActivity.this);
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // ĐỐi tượng dạng JSON
                            JSONObject jsonObject1 =new JSONObject(response);
                            // Mảng các đối tượng dạng JSON
                            JSONArray jsonArrayObject =jsonObject1.getJSONArray("list");
                            // Lấy phần tử đầu tiên của mảng
                            JSONObject jsonObject = jsonArrayObject.getJSONObject(0);
                            // Lấy tên thành phố
                            String namecity =jsonObject.getString("name");
                            city.setText(namecity);
                            // Lấy thời gian quan sát
                            String day =jsonObject.getString("dt");
                            daycheck.setText(FormatDay(day));
                            // lấy icon trạng thái và trạng thái thời tiết
                            JSONArray jsonArrayWeather =jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon =jsonObjectWeather.getString("icon");
                            Picasso.with(WeatherActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imageView);
                            txtstatus.setText(status);
                            // Lấy nhiệt độ và độ ẩm thời tiết
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo =jsonObjectMain.getString("temp");
                            String doam=jsonObjectMain.getString("humidity");
                            temp.setText(nhietdo + "°C");
                            huminity.setText(doam +"%");
                            // Lấy dữ liệu tốc độ gió
                            JSONObject jsonObjectWind =jsonObject.getJSONObject("wind");
                            String speedWind =jsonObjectWind.getString("speed");
                            wind.setText(speedWind + "m/s");
                            // lấy dữ liệu % mây
                            JSONObject jsonObjectCloud =jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            cloud.setText(may+"%");
                            // Lấy dữ liệu tên đất nước
                            JSONObject jsonObjectSyn = jsonObject.getJSONObject("sys");
                            String namecountry =jsonObjectSyn.getString("country");
                            contry.setText(namecountry);

                        }
                        catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    private String FormatDay(String day) {
        long l= Long.valueOf(day);
        Date date =new Date(l*1000L);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private void startView() {
        contry=(TextView)findViewById(R.id.country);
        city=(TextView)findViewById(R.id.city);
        temp=(TextView)findViewById(R.id.temperature);
        txtstatus=(TextView)findViewById(R.id.status_weather);
        huminity=(TextView)findViewById(R.id.huminity);
        cloud=(TextView)findViewById(R.id.clouds);
        wind=(TextView)findViewById(R.id.wind);
        btnSeach=(Button)findViewById(R.id.search);
        checkDay=(Button)findViewById(R.id.check_day);
        imageView=(ImageView)findViewById(R.id.imageweather);
        editSearch=(EditText)findViewById(R.id.editcity);
        daycheck=(TextView)findViewById(R.id.day);
    }
}
