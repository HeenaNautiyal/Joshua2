package com.heena.D2DMedical;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
    boolean[] checkBoxState;
    int Resource;
    ViewHolder holder;
    String name;

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
            //holder.chb = (CheckBox) v.findViewById(R.id.chkbox);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvDOB = (TextView) v.findViewById(R.id.tvmanufacture);
            holder.tvHeight = (TextView) v.findViewById(R.id.tvdescription);
            holder.addby=(TextView)v.findViewById(R.id.added);
            holder.imageview=(ImageView)v.findViewById(R.id.image_upload);
            v.setTag(holder);
            holder.box=(CheckBox)v.findViewById(R.id.chk);
            holder.box.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Chemist country = (Chemist) cb.getTag();
                    Log.e("main1", (String) cb.getText());
                    country.setSelected(cb.isChecked());
                }
            });

        } else {
            holder = (ViewHolder) v.getTag();
        }
       /* holder.imageview.setImageResource(R.drawable.bandaid);
        new DownloadImageTask(holder.imageview).execute(actorList.get(position).getProductImage());*/
        Chemist actors = actorList.get(position);
        holder.tvName.setText(actorList.get(position).getMedicine());
        holder.tvDOB.setText("Manufactured By: " + actorList.get(position).getMenufacuter());
        holder.tvHeight.setText("Composition: "+actorList.get(position).getCompostion());
        holder.addby.setText("Description: "+actorList.get(position).getDescription());
        holder.box.setChecked(actors.isSelected());
        holder.box.setTag(actors);
       // holder.chb.setChecked(actors.isSelected());
        return v;
    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvDOB;
        public TextView addby;
        public TextView tvHeight;
        public CheckBox box;
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

  /*  private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView imageview) {
            this.bmImage = imageview;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String a= params.toString();
            //String urldisplay = params[0];
            Bitmap decodedImage = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] imageBytes = baos.toByteArray();
                imageBytes = Base64.decode(a, Base64.CRLF);
                 decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
              *//*  imageview.setImageBitmap(decodedImage);*//*
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return decodedImage;
        }
        protected void onPostExecute(Bitmap result) {
            //bmImage.setImageBitmap(result);
            Log.e("image in View Holder",result.toString());
        }


    }*/
}
