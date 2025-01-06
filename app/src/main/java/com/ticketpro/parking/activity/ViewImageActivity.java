package com.ticketpro.parking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.ticketpro.parking.R;
import com.ticketpro.parking.dar.DarZoomImageView;
import com.ticketpro.parking.dar.ServiceRequest;

public class ViewImageActivity extends AppCompatActivity {
    String imagepath;
   ZoomageView mImageview;
   Button Btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        mImageview=findViewById(R.id.ZoomimageView);
        Btn_back=findViewById(R.id.ViewImage_back_btn);
        imagepath=getIntent().getStringExtra("imagepath");
        if (!imagepath.equals(""))
        {
          /* mImageview.setImageURI(Uri.parse(imagepath));*/
            Glide.with(ViewImageActivity.this)
                    .load(imagepath)
                    .into(mImageview);
        }
        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Intent intent= new Intent(ViewImageActivity.this,ServiceRequest.class);
                startActivity(intent);*/
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
            finish();
        super.onBackPressed();
    }
}