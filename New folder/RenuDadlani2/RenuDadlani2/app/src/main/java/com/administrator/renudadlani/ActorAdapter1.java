package com.administrator.renudadlani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 9/27/2016.
 */
public class ActorAdapter1  extends ArrayAdapter<com.administrator.renudadlani.Actors> {

    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public ActorAdapter1(Context context, int resource, ArrayList<Actors> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }

    public void updateAdapter(ArrayList<Actors> actorsList) {
        this.actorList=actorsList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);

            holder.tvName = (TextView) v.findViewById(R.id.textmessage);
            holder.tvDescription = (TextView) v.findViewById(R.id.textid);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.tvName.setText(actorList.get(position).getmessage());
        holder.tvDescription.setText(actorList.get(position).getemail());

        return v;

    }

    static class ViewHolder {

        public TextView tvName;
        public TextView tvDescription;


    }

}


