package Adapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.andremion.floatingnavigationview.sample.R;

import java.util.ArrayList;

public class GridAdapter2 extends BaseAdapter {

    ArrayList<String> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GridAdapter2(Context context,  ArrayList<String> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.itemcatchword2, null);
            holder = new ViewHolder();
            holder.btn=(TextView)convertView.findViewById(R.id.word2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String data = this.listData.get(position);
        holder.btn.setText(data);

        return convertView;
    }

    static class ViewHolder {
        TextView btn;
    }
}


