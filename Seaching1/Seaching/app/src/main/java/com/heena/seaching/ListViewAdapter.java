package com.heena.seaching;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heena on 05-03-2017.
 */
public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Actors> worldpopulationlist = null;
    private ArrayList<Actors> arraylist;

    public ListViewAdapter(Context context,
                           ArrayList<Actors> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Actors>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView rank;
        TextView country;
        TextView population;

    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Object getItem(int i) {
        return worldpopulationlist.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.rank = (TextView) view.findViewById(R.id.rank);
            holder.country = (TextView) view.findViewById(R.id.country);
            holder.population = (TextView) view.findViewById(R.id.population);
            // Locate the ImageView in listview_item.xml

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.rank.setText(worldpopulationlist.get(position).getName());
        holder.country.setText(worldpopulationlist.get(position).getCountry());
        holder.population.setText(worldpopulationlist.get(position)
                .getSpouse());
        // Set the results into ImageView

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("rank",
                        (worldpopulationlist.get(position).getName()));
                // Pass all data country
                intent.putExtra("country",
                        (worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",
                        (worldpopulationlist.get(position).getSpouse()));
                // Pass all data flag

                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toString();
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (Actors wp : arraylist) {
                if (wp.getCountry().contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
