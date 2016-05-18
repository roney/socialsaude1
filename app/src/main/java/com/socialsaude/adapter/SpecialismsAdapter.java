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
import com.socialsaude.socialsaudecommons.model.Specialism;

import java.util.List;

public class SpecialismsAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater=null;
    private List<Specialism> items;

    public SpecialismsAdapter(Activity activity, List<Specialism> items) {
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
            vi = inflater.inflate(R.layout.list_row_clean, null);

        TextView name = (TextView)vi.findViewById(R.id.title); // title
        TextView address = (TextView)vi.findViewById(R.id.address); // artist name
        TextView expedient = (TextView)vi.findViewById(R.id.expedient); // duration
        //TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        thumb_image.setImageResource(R.drawable.ic_default_specialism);


        Specialism item = items.get(position);

        // Setting all values in listview
        name.setText(item.getName());
        //address.setText("");
        //expedient.setText("");
        //duration.setText(item);
        return vi;
    }
}