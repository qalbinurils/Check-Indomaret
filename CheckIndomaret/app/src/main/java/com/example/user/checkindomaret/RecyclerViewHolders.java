package com.example.user.checkindomaret;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 18/03/2017.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView promoName;
    public ImageView promoPhoto;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        promoName = (TextView)itemView.findViewById(R.id.promo_name);
        promoPhoto = (ImageView)itemView.findViewById(R.id.promo_photo);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(v.getContext(), "", Toast.LENGTH_SHORT).show();
    }
}
