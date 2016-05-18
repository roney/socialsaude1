package com.socialsaude.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialsaude.R;
import com.socialsaude.socialsaudecommons.model.HealthUnit;

import java.util.List;

public class MedicationsAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater=null;
    private List<HealthUnit> items;

    public MedicationsAdapter(Activity activity, List<HealthUnit> items) {
        this.activity = activity;
        this.items=items;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView name = (TextView)vi.findViewById(R.id.title); // title
        TextView address = (TextView)vi.findViewById(R.id.address); // artist name
        TextView expedient = (TextView)vi.findViewById(R.id.expedient); // duration
        //TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        HealthUnit item = items.get(position);

        // Setting all values in listview
        name.setText(item.getName());
        address.setText(item.getStreet() + " - " + item.getCity());
        expedient.setText(item.getOpenhours());
        //duration.setText(item);
        return vi;
    }
}