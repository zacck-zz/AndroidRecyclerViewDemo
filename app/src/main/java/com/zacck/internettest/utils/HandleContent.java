package com.zacck.internettest.utils;

import android.util.Log;

import com.zacck.internettest.model.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 2016/05/02.
 */
public class HandleContent {

    String TAG = "HANDLECONTENT";

    //lets make pullParser Factory
    private String urlString = null;
    //use this to know if we are getting a list or getting a phrase
    private int contentType;
    XmlPullParserFactory pullParserFactory;
    public volatile boolean done = true;

    //info
    public ArrayList<Item> mItems;
    public String phrase ="";

    //constructor
    // takes in the type of result  type 0,1 url is the link

    public HandleContent(int type, String url)
    {
        this.urlString = url;
        this.contentType = type;

        mItems = new ArrayList<Item>();
    }

    //use this to parse the phrase of the item
    public String consumeItem(XmlPullParser p) {
        int event;
        String text = null;

        //get the phrase value of the item

        try
        {
            event = p.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = p.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = p.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("value")) {
                            Log.d(TAG, text);
                            phrase = text;

                        }
                        break;
                }
                event = p.next();
            }
            done = false;

        }
        catch(Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
        return phrase;
    }

    //use this to produce the list of items
    public void consumeList(XmlPullParser p)
    {
        int event;
        String text = null;
        Item myDownload;

        try{
            event = p.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
            //lets collect out information
                String name = p.getName();


                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = p.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("item")) {
                            Log.d(TAG, "currItem + "+text);
                            myDownload = new Item(text);
                            mItems.add(myDownload);
                        }
                        break;
                }
                event = p.next();
            }
            done = false;
        }
        catch(Exception e)
        {
            Log.d(TAG, e.toString());
        }
    }

    public void fetchData()
    {
        try
        {
            URL mUrl = new URL(HandleContent.this.urlString);
            HttpURLConnection conn = (HttpURLConnection)mUrl.openConnection();
            //conn.setReadTimeout(10000 );
            //conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            //lets parse the xml from the link
            InputStream mStream = conn.getInputStream();
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser p = pullParserFactory.newPullParser();

            p.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            p.setInput(mStream, null);

            if(contentType == 0)
            {
                consumeList(p);

            }
            else {
                phrase = consumeItem(p);
            }
            mStream.close();

        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }
    }
}
