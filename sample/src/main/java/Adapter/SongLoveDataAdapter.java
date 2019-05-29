package Adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andremion.floatingnavigationview.sample.R;

import java.util.ArrayList;
import java.util.List;

import Entity.Song;

public class SongLoveDataAdapter extends RecyclerView.Adapter<SongLoveDataAdapter.PlayerViewHolder> {
   public  ArrayList<Song> songs;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView title=null;
        private TextView singer=null;
        private ImageView icon=null;
        private View convertView;

        public PlayerViewHolder(View row) {
            super(row);
            icon = (ImageView)row.findViewById(R.id.list_image);
            title = (TextView)row.findViewById(R.id.title);
            singer = (TextView)row.findViewById(R.id.singer);
            convertView=row;
        }
    }

    public SongLoveDataAdapter( ArrayList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.title.setText(song.getTitle());
        holder.singer.setText(song.getSinger());

        //Uri hình ảnh
        String uri_icon = "drawable/" + song.getIcon();
        int ImageResoure = holder.convertView.getContext().getResources().getIdentifier(uri_icon, null, holder.convertView.getContext().getApplicationContext().getPackageName());
        Drawable image = holder.convertView.getContext().getResources().getDrawable(ImageResoure);
        holder.icon.setImageDrawable(image);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
