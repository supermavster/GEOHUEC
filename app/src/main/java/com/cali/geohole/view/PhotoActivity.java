package com.cali.geohole.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cali.geohole.R;
import com.cali.geohole.model.Hole;
import com.cali.geohole.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class PhotoActivity extends Activity {

    // Object User
    User user = null;
    Uri imageUri;
    // Variables
    private ImageView image;
    private Button btnPhoto;
    private Button btnSend;
    private EasyImage easyImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photo);

        // Get Data B
        this.user = (User) getIntent().getSerializableExtra("user");

        // Init Components
        image = findViewById(R.id.imageViewLogF);
        btnPhoto = findViewById(R.id.buttonCap);
        btnSend = findViewById(R.id.btnSend);


        // Init Camera Data
        Nammu.init(this);
        EasyImage.configuration(this)
                .setImagesFolderName("imagesApp") // images folder name, default is "EasyImage"
                .saveInAppExternalFilesDir() // if you want to use root internal memory for storying images
                .saveInRootPicturesDirectory();

        // Actions Button
        openPhotoSend();
    }

    public void openPhotoSend() {
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onPhotoRegister();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                endObject();
            }
        });
    }

    public void endObject(){
        if(imageUri != null) {
            ArrayList<Hole> holes = user.getHoles();
            int lastHole = holes.size() - 1;
            Hole hole = holes.get(lastHole);
            hole.setPhoto(imageUri);
            holes.set(lastHole, hole);
            user.setHoles(holes);

            // send new data
            Intent viewFinish = new Intent(getApplicationContext(), Finish.class);
            // Send data A
            viewFinish.putExtra("user", user);
            // Start new view
            startActivity(viewFinish);
        }else {
            Toast.makeText(PhotoActivity.this, "Debe de tomar o elegir una foto para poder continuar", Toast.LENGTH_LONG);
        }
    }

    public void onPhotoRegister() {
        requesPermissionsForCameraAndGallery(new PermissionCallback() {
            @Override
            public void permissionGranted() {
                EasyImage.openChooserWithGallery(PhotoActivity.this, "Seleccione Origen", 0);
            }

            @Override
            public void permissionRefused() {
                Toast.makeText(PhotoActivity.this, "No hay permisos", Toast.LENGTH_LONG);
            }
        });
    }


    protected void requesPermissionsForCameraAndGallery(PermissionCallback permissionCallback) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, permissions[0]);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, permissions[1]);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, permissions, permissionCallback);
        } else {
            permissionCallback.permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    File compressedFile = compressImage(getApplicationContext(), imageFile);
                    imageUri = Uri.fromFile(compressedFile);
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

        return new Compressor(context)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.PNG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath()).compressToFile(fileToReduce);
    }
}
