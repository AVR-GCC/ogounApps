package com.example.bar.infermaferm;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Bar on 12/9/2015.
 */
class InfermeryNode implements Serializable {
    String name;
    long phone;
    String address;
    double longe;
    double lat;
    String pic = null;
    String notes;
    InfermeryNode next = null;
}


public class ScrollActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public static InfermeryNode Infermarray;
    private double mylong;
    private double mylat;
    private static CoordinatorLayout wraper;
    private static ScrollView sv;
    private static LinearLayout ll;
    private static int ii;
    private static int smInitialized;


    protected void onStart() {
        super.onStart();
        Intent me = this.getIntent();
        Infermarray = (InfermeryNode) me.getSerializableExtra("infermarray");
        String ca = (String) me.getStringExtra("callingActivity");
        System.out.println("in on start'11134412111111111111111111");
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if((ca.equals("MainActivity") || ca.equals("FilterActivity")) && smInitialized != 1) {
            InfermeryNode temp = Infermarray;
            int i = 1;
            while (temp != null) {
                try {
                    ll.removeViewAt(i);
                    temp = temp.next;
                    i++;

                } catch (Exception e) {
                    System.out.println(e + " ----------------------------goes the weasle in the remove view");
                    break;
                }

            }
            smInitialized = 1;
            i = 0;
            temp = Infermarray;
            if (temp == null) System.out.println("temp is null!!!");
            int distance;
            LocationManager lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //if ((Math.abs(mylong - location.getLongitude()) > 0.01) || (Math.abs(mylat - location.getLatitude()) > 0.01)) {
                        mylong = location.getLongitude();
                        mylat = location.getLatitude();
                        System.out.println("location updated to: longetute: " + mylong + " latitude: " + mylat);
                    //}
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                System.out.println("couldnt get permissions :(");
                //return;
            }
            Location curloc = lm.getLastKnownLocation(lm.GPS_PROVIDER);
            if (curloc != null) {
                mylong = curloc.getLongitude();
                mylat = curloc.getLatitude();
            }

            System.out.println("location set to: longetute: " + mylong + " latitude: " + mylat);
            while (temp != null) {
                System.out.println("in the while");
                Double dist = calculateDistance(temp.longe, temp.lat);
                System.out.println("calculated distance " + dist);
                if (dist > MainActivity.maxDistance) {
                    temp = temp.next;
                    System.out.println("distance " + dist + " filtered!!!");

                    continue;
                }
                System.out.println("infermery: " + temp.name);
                final LinearLayout top = (LinearLayout) inflater.inflate(R.layout.infermerentry, null);
                RelativeLayout tv = (RelativeLayout) top.getChildAt(0);
                TextView nl = (TextView) tv.getChildAt(4);
                nl.setText(temp.name);
                System.out.println("name text set");
                TextView al = (TextView) tv.getChildAt(5);
                al.setText(temp.address);
                System.out.println("address text set");
                TextView dl = (TextView) tv.getChildAt(6);
                TextView id = (TextView) tv.getChildAt(7);
                id.setText(Integer.toString(i));
                //dl.setText((char)temp.longe);
                System.out.println("id set" + i);

                top.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        RelativeLayout tv = (RelativeLayout) top.getChildAt(0);
                        TextView id = (TextView) tv.getChildAt(7);
                        ii = Integer.parseInt((String) id.getText());
                        callInfermerinfo();
                    }
                });
                final ImageView iv = (ImageView) tv.getChildAt(0);
                final String urls = temp.pic;
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            URL url = new URL(urls);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            iv.setImageBitmap(bmp);

                        } catch (Exception e) {
                            System.out.println(e + " ----------------------------goes the weasle in the image load");

                        }

                    }
                });
                t.start();
                try {
                    if (true) t.join();

                } catch (Exception e) {
                    System.out.println(e + " ----------------------------goes the weasle in the thread join");

                }

                ll.addView(top);
                System.out.println("tab added");
                temp = temp.next;
                i++;
            }
            //sv.addView(ll);
            ll.invalidate();
            sv.invalidate();
            wraper.invalidate();
        }
    }

    private void callInfermerinfo() {
        Intent intent = new Intent(getApplicationContext(), Infermerinfo.class);
        intent.putExtra("infermary", (Serializable) getInfermeryFromId());
        startActivity(intent);
    }

    private InfermeryNode getInfermeryFromId() {
        InfermeryNode temp = Infermarray;
        int i = 0;
        while(i != ii) {
            temp = temp.next;
            i++;
        }
        return  temp;
    }

    private Double calculateDistance(double lg, double lt) {
        Double dlon = lg - mylong;
        Double dlat = lt - mylat;
        Double a = Math.sin(dlat / 2)*Math.sin(dlat / 2) + Math.cos(lt) * Math.cos(mylat) * Math.sin(dlon / 2)* Math.sin(dlon / 2);
        Double c = Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))*6373;
        return c;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wraper = (CoordinatorLayout) inflater.inflate(R.layout.scroll_holder, null);
        sv = (ScrollView) wraper.getChildAt(1);
        ll = (LinearLayout) sv.getChildAt(0);
        smInitialized = 0;
        setContentView(wraper);

    }
    public void onFilterButtonClick (View view){
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("infermarray", (Serializable) Infermarray);
        startActivity(intent);
    }

}
