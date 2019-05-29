package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andremion.floatingnavigationview.sample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Entity.Weather;

public class MyArrayAdapterWeather extends ArrayAdapter<Weather> {

    Activity context = null;
    int layoutId;
    public ArrayList<Weather> arr = null;
    public MyArrayAdapterWeather(Activity context, int layoutId, ArrayList<Weather> list){
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row=convertView;
        WeatherHolder holder=null;
        if (row==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutId,null);
            holder=new WeatherHolder(row,context);
            row.setTag(holder);
        }
        else {
            holder=(WeatherHolder)row.getTag();
        }
        holder.populateFrom(arr.get(position));
        return(row);
    }
    static class WeatherHolder {
        private TextView txtday=null;
        private TextView txtSatus=null;
        private  TextView minTemp=null,maxTemp=null,hum=null,speed=null,cloud=null;
        ImageView iconStatus=null;
        View convertView;
        Activity context;
        WeatherHolder(View row,Activity context) {
            txtday = (TextView)row.findViewById(R.id.txtday);
            txtSatus = (TextView)row.findViewById(R.id.status_weather);
            minTemp = (TextView)row.findViewById(R.id.temp);
            hum = (TextView)row.findViewById(R.id.huminity);
            speed = (TextView)row.findViewById(R.id.wind);
            cloud = (TextView)row.findViewById(R.id.clouds);
            iconStatus = (ImageView)row.findViewById(R.id.iconweather);
            convertView=row;
            this.context=context;
        }
        void populateFrom(Weather weather) {
            txtday.setText(weather.getTxtday());
            minTemp.setText(weather.getTemp());
            cloud.setText(weather.getCloud());
            speed.setText(weather.getSpeed());
            hum.setText(weather.getHum());
            txtSatus.setText(weather.getTxtStatus());
            Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.getImageStatus()+".png").into(iconStatus);
        }
    }
}