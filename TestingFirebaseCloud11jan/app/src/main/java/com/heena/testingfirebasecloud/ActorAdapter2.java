package com.heena.testingfirebasecloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.util.ArrayList;

/**
 * Created by Heena on 12/30/2016.
 */
public class ActorAdapter2 extends ArrayAdapter<Chemist> {
    ArrayList<Chemist> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public ActorAdapter2(Context context, int resource, ArrayList<Chemist> objects) {
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
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvDOB = (TextView) v.findViewById(R.id.tvDateOfBirth);
            holder.tvHeight = (TextView) v.findViewById(R.id.tvHeight);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.med_icon);
        holder.tvName.setText(actorList.get(position).getMedicine());
        holder.tvDOB.setText("Menufacure by: " + actorList.get(position).getMenufacuter());
        holder.tvHeight.setText("Composition: "+actorList.get(position).getCompostion());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;

        public TextView tvDOB;

        public TextView tvHeight;
    }



    public void updateAdapter(ArrayList<Chemist> actorList){
        this.actorList=actorList;
    }
}
