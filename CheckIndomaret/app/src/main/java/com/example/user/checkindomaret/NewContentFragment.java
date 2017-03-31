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
public class NewContentFragment extends Fragment {
    private GridLayoutManager lLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_new, null);
        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(view.getContext(), 2);

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_views);
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
        allItems.add(new ItemObject("Chitato 2000", R.drawable.new1));
        allItems.add(new ItemObject("Milna 6000", R.drawable.new2));
        allItems.add(new ItemObject("Filma 7000", R.drawable.new3));
        allItems.add(new ItemObject("Saus Sambal 8000", R.drawable.new4));
        allItems.add(new ItemObject("Doritos 7500", R.drawable.new5));
        allItems.add(new ItemObject("Zwitsal 10600", R.drawable.new6));
        allItems.add(new ItemObject("Clear 18400", R.drawable.new7));
        allItems.add(new ItemObject("Baygon 10700", R.drawable.new8));

        return allItems;
    }

}
