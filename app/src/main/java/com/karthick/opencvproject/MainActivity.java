package com.karthick.opencvproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import java.io.IOException;

public class MainActivity extends AppCompatActivity  {
    ImageView imageView;
    Uri imageUri;
    Bitmap grayBitMap,imageBitMap,plainBitMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView) findViewById(R.id.imageView);
        OpenCVLoader.initDebug();

    }

    public void openGallery(View v)
    {
        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(myIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == RESULT_OK && data!=null)
        {
            imageUri = data.getData();
            try {
                imageBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(imageBitMap);
        }

    }

    public void convertToGray(View v)
    {
        Mat Rgba = new Mat();
        Mat grayMat = new Mat();
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDither=false;
        o.inSampleSize=4;
        int width = imageBitMap.getWidth();
        int height = imageBitMap.getHeight();
        grayBitMap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
        //bitmap to mat
        Utils.bitmapToMat(imageBitMap, Rgba);
        Imgproc.cvtColor(Rgba,grayMat,Imgproc.COLOR_RGB2GRAY);
        Utils.matToBitmap(grayMat,grayBitMap);
        imageView.setImageBitmap(grayBitMap);
    }
}