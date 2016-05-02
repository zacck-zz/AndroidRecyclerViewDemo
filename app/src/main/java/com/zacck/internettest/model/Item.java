package com.zacck.internettest.model;

import java.util.ArrayList;

/**
 * Created by isaac on 2016/05/02.
 */
public class Item {

    String key;
    String Value;

    public Item(String key)
    {
        this.key = key;
    }

    public String key()
    {
        return this.key;
    }

    public String value()
    {
        return this.Value;
    }

    public void setValue( String v)
    {
        this.Value = v;
    }

    public static ArrayList<Item> addItems(ArrayList<Item> myPassedItems)
    {
        ArrayList<Item> items = new ArrayList<>();
        for(int i=0; i<myPassedItems.size(); i++)
        {
            items.add(myPassedItems.get(i));
        }

        return  items;
    }
}
