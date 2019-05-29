package Adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andremion.floatingnavigationview.sample.R;
import Entity.Song;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Song> {

    Activity context = null;
    int layoutId;
    public ArrayList<Song> arr = null;
    //Contructor này dùng để lấy về những giá trị được truyền vào từ MainActivity
    public MyArrayAdapter(Activity context, int layoutId, ArrayList<Song> list){
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row=convertView;
        SongHolder holder=null;
        if (row==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutId, parent,false);
            holder=new SongHolder(row);
            row.setTag(holder);
        }
        else {
            holder=(SongHolder)row.getTag();
        }
        holder.populateFrom(arr.get(position));
        return(row);
    }
    static class SongHolder {
        private TextView title=null;
        private TextView singer=null;
        private ImageView icon=null;
        private View convertView;
        SongHolder(View row) {
            icon = (ImageView)row.findViewById(R.id.list_image);
            title = (TextView)row.findViewById(R.id.title);
            singer = (TextView)row.findViewById(R.id.singer);
            convertView=row;
        }
        void populateFrom(Song song) {
            title.setText(song.getTitle());
            singer.setText(song.getSinger());
            String uri_icon = "drawable/" + song.getIcon();
            int ImageResoure = convertView.getContext().getResources().getIdentifier(uri_icon, null, convertView.getContext().getApplicationContext().getPackageName());
            Drawable image = convertView.getContext().getResources().getDrawable(ImageResoure);
            icon.setImageDrawable(image);

        }
    }
}