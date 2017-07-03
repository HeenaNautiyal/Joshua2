package com.heena.seaching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heena on 3/7/2017.
 */
public class ActorAdapter extends ArrayAdapter<Actors> {
        ArrayList<Actors> actorList;
        LayoutInflater vi;
        private List<Actors> worldpopulationlist = null;
        int Resource;
        ViewHolder holder;

public ActorAdapter(Context context, int resource, ArrayList<Actors> objects) {
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
        holder.tvDescription = (TextView) v.findViewById(R.id.tvDescriptionn);
        holder.tvDOB = (TextView) v.findViewById(R.id.tvDateOfBirth);
        holder.tvCountry = (TextView) v.findViewById(R.id.tvCountry);
        holder.tvHeight = (TextView) v.findViewById(R.id.tvHeight);
        holder.tvSpouse = (TextView) v.findViewById(R.id.tvSpouse);
        holder.tvChildren = (TextView) v.findViewById(R.id.tvChildren);
        v.setTag(holder);
        } else {
        holder = (ViewHolder) v.getTag();
        }

        holder.tvName.setText(actorList.get(position).getName());
        holder.tvDescription.setText(actorList.get(position).getDescription());
        holder.tvDOB.setText("B'day: " + actorList.get(position).getDob());
        holder.tvCountry.setText(actorList.get(position).getCountry());
        holder.tvHeight.setText("Height: " + actorList.get(position).getHeight());
        holder.tvSpouse.setText("Spouse: " + actorList.get(position).getSpouse());
        holder.tvChildren.setText("Children: " + actorList.get(position).getChildren());
        return v;

        }

    public void filter(String text) {
        text = text.toString();

            for (Actors wp : actorList) {
                if (wp.getCountry().contains(text)) {
                    worldpopulationlist.add(wp);
                }
                actorList.addAll(worldpopulationlist);

        }
        notifyDataSetChanged();
    }

    static class ViewHolder {

    public TextView tvName;
    public TextView tvDescription;
    public TextView tvDOB;
    public TextView tvCountry;
    public TextView tvHeight;
    public TextView tvSpouse;
    public TextView tvChildren;

}





}

