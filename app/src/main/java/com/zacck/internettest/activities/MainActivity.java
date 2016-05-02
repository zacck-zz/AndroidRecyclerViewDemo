package com.zacck.internettest.activities;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zacck.internettest.R;
import com.zacck.internettest.model.Item;
import com.zacck.internettest.utils.HandleContent;
import com.zacck.internettest.utils.ItemsAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init UI
        setContentView(R.layout.activity_main);

        itemsView = (RecyclerView)findViewById(R.id.rvItems);

        //check for internet
        if(isConnected(MainActivity.this))
        {
            //fetch downloads list
            getXML gx = new getXML();
            gx.execute("https://glacial-sands-39825.herokuapp.com");
        }
        else
        {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
        }



    }

    public class getXML extends  AsyncTask<String, Void, Void>
    {
        //get Content from the internet
        HandleContent hc;
        ArrayList<Item> mitems;
        ItemsAdapter mAdp;
        @Override
        protected Void doInBackground(String... voids) {
            hc = new HandleContent(0,voids[0]);
            hc.fetchData();
            mAdp = new ItemsAdapter(Item.addItems(hc.mItems));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            itemsView.setAdapter(mAdp);
            itemsView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
    }

    //check for internet
    private boolean isConnected(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
