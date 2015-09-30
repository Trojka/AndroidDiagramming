package be.trojkasoftware.android.sample;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class AvailableNodeListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> items;

    public AvailableNodeListAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.designeritem_row, null);
            }
            String o = items.get(position);
            if (o != null) {
                TextView dt = (TextView) v.findViewById(R.id.itemdesription);
                if (dt != null) {
                	dt.setText(o);                            }

            }
            return v;
    }
}
