package com.cali.geohole.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cali.geohole.R;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class Photo extends AppCompatActivity {

    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photo);
        EasyImage.configuration(this).setImagesFolderName("Images") // images folder name, default is "EasyImage"
                .saveInAppExternalFilesDir() // if you want to use root internal memory for storying images
                .saveInRootPicturesDirectory();
        image = findViewById(R.id.imageViewLogF);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                try {
                    File compressedFile = compressImage(getApplicationContext(),imageFile);
                    Uri imageUri= Uri.fromFile(compressedFile);
                    image.setImageURI(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getApplicationContext());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });

    }

    public static File compressImage(Context context, File fileToReduce) throws IOException {
        File compressedImage = new Compressor(context)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.PNG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath()).compressToFile(fileToReduce);

        return compressedImage;
    }
}
