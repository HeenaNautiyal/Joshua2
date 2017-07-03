package com.heena.D2DMedical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.util.ArrayList;

/**
 * Created by Heena on 1/10/2017.
 */
public class ActorAdapter4 extends ArrayAdapter<Chemist> {
    ArrayList<Chemist> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public ActorAdapter4(Context context, int resource, ArrayList<Chemist> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (TextView) v.findViewById(R.id.quantity);
            holder.tvName = (TextView) v.findViewById(R.id.medname);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setText(actorList.get(position).getQuantity());
        holder.tvName.setText(actorList.get(position).getMedicine());

        return v;

    }

    static class ViewHolder {
        public TextView imageview;
        public TextView tvName;

    }



    public void updateAdapter(ArrayList<Chemist> actorList){
        this.actorList=actorList;
    }
}
