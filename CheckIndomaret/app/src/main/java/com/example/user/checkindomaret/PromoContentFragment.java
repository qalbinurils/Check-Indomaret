package com.example.user.checkindomaret;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoContentFragment extends Fragment {

    private GridLayoutManager lLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_promo, null);
        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(view.getContext(), 2);

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(view.getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Beras", R.drawable.promo1));
        allItems.add(new ItemObject("Tissue", R.drawable.promo2));
        allItems.add(new ItemObject("Biskuit", R.drawable.promo3));
        allItems.add(new ItemObject("Air Putih", R.drawable.promo4));
        allItems.add(new ItemObject("Anlene", R.drawable.promo5));
        allItems.add(new ItemObject("White Coffee", R.drawable.promo6));
        allItems.add(new ItemObject("Rinso", R.drawable.promo7));
        allItems.add(new ItemObject("Mie", R.drawable.promo8));

        return allItems;
    }




}
