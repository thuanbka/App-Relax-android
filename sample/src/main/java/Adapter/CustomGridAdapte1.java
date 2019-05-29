package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.andremion.floatingnavigationview.sample.R;

import java.util.ArrayList;

public class CustomGridAdapte1 extends ArrayAdapter<String> {

    Activity context = null;
    int layoutId;
    ArrayList<String> arr = null;

    public CustomGridAdapte1(Activity context, int layoutId, ArrayList<String> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        /*
         position: là vị trí của bàu hát trong list
         convertView: dùng để lấy về các control của mỗi item
         parent: chính là datasource được truyền vào từ MainActivity
         */
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }
        //Lấy về bài hát ở vị trí được yêu cầu
        String a = arr.get(position);
        //Lấy ra những control được định nghĩa trong cấu trúc của mỗi item
        TextView btn =(TextView) convertView.findViewById(R.id.word1) ;

        //Gán giá trị cho những control đó
        btn.setText(a);
        return convertView;
    }

}