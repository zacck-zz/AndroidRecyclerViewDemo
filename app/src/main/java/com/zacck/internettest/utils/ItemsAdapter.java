package com.zacck.internettest.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zacck.internettest.R;
import com.zacck.internettest.model.Item;

import java.util.ArrayList;

/**
 * Created by isaac on 2016/05/02.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    //member Variable for the items
    private ArrayList<Item> mItems;
    Item it;
    public ItemsAdapter(ArrayList<Item> myPassedItems)
    {
        mItems = myPassedItems;

    }



    //draw the layout as we would like it
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(ctx);

        //draw
        View itemView = mInflater.inflate(R.layout.item_row_layout, parent, false);

        //return viewholder
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    //populate the data on to the layout
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String valueUrl = "https://glacial-sands-39825.herokuapp.com/downloads/";

        //get current item
        it = mItems.get(position);

        //set the data based on the model
        TextView mkey = holder.tvKey;
        final TextView mVal = holder.tvValue;

        //set value
        mkey.setText("Key: "+ it.key());

        new AsyncTask<String, Void, String>()
        {
            @Override
            protected String doInBackground(String... strings) {
                HandleContent innerHC = new HandleContent(1, strings[0]+it.key());
                innerHC.fetchData();
                return  innerHC.phrase;
            }

            @Override
            protected void onPostExecute(String s) {
                mVal.setText(s);
                it.setValue(s);

            }
        }.execute(valueUrl);


    }




    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //cache views in layout
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvKey;
        public TextView tvValue;


        public ViewHolder(View ItemView)
        {
            super(ItemView);
            tvKey = (TextView)ItemView.findViewById(R.id.tvKey);

            tvValue = (TextView)ItemView.findViewById(R.id.tvValue);



        }

    }
}
