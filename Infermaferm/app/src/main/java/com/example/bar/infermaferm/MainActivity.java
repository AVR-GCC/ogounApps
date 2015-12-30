package com.example.bar.infermaferm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static int maxDistance = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*String jsoncontent = "[{\"name\":\"polar_infermary_for_clubed_seels\",\"phone\":\"24242424242\",\"long\":\"0\",\"lat\":\"0\",\"notes\":\"open_only_in_summer\"},{\"name\":\"the_other_infermary\",\"phone\":\"42424242424\",\"long\":\"32\",\"lat\":\"64\",\"notes\":\"is_different_from_the_polar_infermery\"}];";
        String fp = "thejson2.json";
        try {
            FileOutputStream fos = openFileOutput(fp, 0);
            fos.write(jsoncontent.getBytes());
            fos.close();
        }
        catch(java.io.FileNotFoundException e){
            Log.d("exp", "file not found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        catch(java.io.IOException e){
            Log.d("exp", "io exception!!!!!!!");
        }
*//*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "moveTooScroll", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                moveTooScroll();
            }
        });
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onloginButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ScrollActivity.class);
        System.out.println("intent initialized");
        intent.putExtra("callingActivity", "MainActivity");
        System.out.println("calling activity put");
        /*String jstring = loadJSONFromAsset();
        try {
            JSONObject obj = new JSONObject(jstring);
        }catch (JSONException ex){
            ex.printStackTrace();
            return;
        }
        */
        try {
            InputStream is = getResources().openRawResource(com.example.bar.infermaferm.R.raw.thejson2);
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            InfermeryNode infermarray = new InfermeryNode();
            reader.beginArray();
            int i = 0;
            InfermeryNode temp = infermarray;
            InfermeryNode infermari;
            while(reader.hasNext() && i < 50){
                infermari = getInfermalistFromJsonReader(reader);
                temp.next = infermari;
                temp = temp.next;
                i++;
            }
            reader.endArray();
            intent.putExtra("infermarray", (Serializable) infermarray.next);
            intent.putExtra("infermarraylen", i);
            reader.close();
            is.close();
            System.out.println("sending:");
            i = 0;
            temp = infermarray;
            while (temp != null){
                System.out.println("infermery " + i);
                System.out.println("name - " + temp.name);
                System.out.println("phone - " + temp.phone);
                System.out.println("long - " + temp.longe);
                System.out.println("lat - " + temp.lat);
                System.out.println("notes - " + temp.notes);
                temp = temp.next;
            }
        }catch (IOException ex){
            ex.printStackTrace();
            return;
        }


        startActivity(intent);
    }

    private InfermeryNode getInfermalistFromJsonReader(JsonReader reader)  throws IOException {
        InfermeryNode inf = new InfermeryNode();
        reader.beginObject();
        System.out.println("object started!!");
        while (reader.hasNext()) {

            String name = reader.nextName();
            System.out.println("doing field: " + name);
            if (name.equals("name")) {
                inf.name = reader.nextString();
            } else if (name.equals("phone")) {
                inf.phone = reader.nextLong();

            } else if (name.equals("long")) {

                inf.longe = reader.nextDouble();
            } else if (name.equals("address")) {

                inf.address = reader.nextString();
            } else if (name.equals("notes")) {

                inf.notes = reader.nextString();
            } else if (name.equals("lat")) {
                inf.lat = reader.nextDouble();

            } else if (name.equals("pic")) {
                inf.pic = reader.nextString();
            } else {

                reader.skipValue();
            }
        }
        reader.endObject();
        return inf;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("thejson2");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");



        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
