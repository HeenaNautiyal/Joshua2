package com.bizhawkz.d2dmedical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Heena on 2/20/2017.
 */
public class ActorAdapter1 extends ArrayAdapter<Chemist> {
    ArrayList<Chemist> actorList;
    LayoutInflater vi;

    int Resource;
    ViewHolder holder;
    String name;

    public ActorAdapter1(Context context, int resource, ArrayList<Chemist> objects) {
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
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvDOB = (TextView) v.findViewById(R.id.tvmanufacture);
            holder.tvHeight = (TextView) v.findViewById(R.id.tvdescription);
            holder.addby=(TextView)v.findViewById(R.id.added);
            holder.imageview=(ImageView)v.findViewById(R.id.image_upload);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Chemist actors = actorList.get(position);
        holder.tvName.setText(actorList.get(position).getMedicine());
        holder.tvDOB.setText("Manufactured By: " + actorList.get(position).getMenufacuter());
        holder.tvHeight.setText("Composition: "+actorList.get(position).getCompostion());
        holder.addby.setText("Description: "+actorList.get(position).getDescription());

        // holder.chb.setChecked(actors.isSelected());
        return v;
    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvDOB;
        public TextView addby;
        public TextView tvHeight;
    }


    ArrayList<Chemist> getBox() {
        ArrayList<Chemist> box = new ArrayList<Chemist>();
        for (Chemist p : actorList) {
            if (p.box)
                box.add(p);
        }
        return box;
    }
    public void updateAdapter(ArrayList<Chemist> actorList){
        this.actorList=actorList;
    }
}
