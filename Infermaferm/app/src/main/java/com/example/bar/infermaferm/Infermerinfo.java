package com.example.bar.infermaferm;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.net.URL;
import java.util.zip.Inflater;

import static java.lang.Thread.sleep;

/**
 * Created by Bar on 12/28/2015.
 */
public class Infermerinfo extends AppCompatActivity {
    private CoordinatorLayout cl;
    private InfermeryNode Infermary;
    private LayoutInflater inflater;
    private static ScrollView sv;
    private static LinearLayout ll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cl = (CoordinatorLayout) inflater.inflate(R.layout.info_holder, null);
        sv = (ScrollView) cl.getChildAt(1);
        ll = (LinearLayout) sv.getChildAt(0);
        setContentView(cl);
    }

    protected void onStart() {
        super.onStart();
        Intent me = this.getIntent();
        Infermary = (InfermeryNode) me.getSerializableExtra("infermary");
        final ImageView image = (ImageView) ll.getChildAt(0);
        final String urls = Infermary.pic;
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(urls);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image.setImageBitmap(bmp);

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
        TextView title = (TextView) ll.getChildAt(1);
        title.setText(Infermary.name);
        TextView notes = (TextView) ll.getChildAt(2);
        notes.setText(Infermary.notes);
        TextView address = (TextView) ll.getChildAt(3);
        address.setText(Infermary.address);
        TextView phone = (TextView) ll.getChildAt(4);
        phone.setText(String.valueOf(Infermary.phone));
        ll.invalidate();
    }

    public void onOkButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ScrollActivity.class);
        intent.putExtra("infermarray", (Serializable) Infermary);
        startActivity(intent);
    }

    public void callNumber(View view) {

        //int permissionCheck = ContextCompat.checkSelfPermission(this,
         //       Manifest.permission.CALL_PHONE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            final Activity act = this;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                            Manifest.permission.CALL_PHONE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(act,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                1);
                    }
                }
            });
            t.start();
            try {
                if (true) t.join();

            } catch (Exception e) {
                System.out.println(e + " ----------------------------goes the weasle in the thread join");

            }


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            System.out.println("didnt get permission to call :::::(((((((");
             ActivityCompat.requestPermissions(this,
                     new String[]{Manifest.permission.READ_CONTACTS},
                     1);
            //return;
        }

    }
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String uriString = "tel:" + Long.toString(Infermary.phone);
        callIntent.setData(Uri.parse(uriString));
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}


