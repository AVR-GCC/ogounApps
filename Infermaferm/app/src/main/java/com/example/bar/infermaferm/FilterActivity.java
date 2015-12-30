package com.example.bar.infermaferm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

/**
 * Created by Bar on 12/20/2015.
 */
public class FilterActivity  extends AppCompatActivity {
    private CoordinatorLayout cv;
    private SeekBar sb;
    private TextView dt;
    private InfermeryNode Infermarray;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent me = this.getIntent();
        Infermarray = (InfermeryNode) me.getSerializableExtra("infermarray");
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cv = (CoordinatorLayout) inflater.inflate(R.layout.filter_holder, null);
        LinearLayout ll = (LinearLayout)cv.getChildAt(1);
        dt = (TextView)ll.getChildAt(0);
        sb = (SeekBar)ll.getChildAt(2);
        String tvtext = "max " + MainActivity.maxDistance + "km";
        dt.setText(tvtext);
        sb.setProgress(MainActivity.maxDistance);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                String tvtext = "max " + progress + "km";
                dt.setText(tvtext);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setContentView(cv);
    }
    public void onSbChange(){

    }
    public void onOkButtonClick (View view){

        MainActivity.maxDistance = sb.getProgress();
        Intent intent = new Intent(getApplicationContext(), ScrollActivity.class);
        intent.putExtra("infermarray", (Serializable) Infermarray);
        intent.putExtra("callingActivity", "FilterActivity");
        startActivity(intent);
    }
}
