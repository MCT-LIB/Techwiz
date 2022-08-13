package com.csupporter.techwiz.utils;
import android.content.Intent;


import androidx.activity.result.ActivityResultLauncher;


public class OpenGallery {
    private static OpenGallery openGallery;

    public static synchronized OpenGallery getInstance() {
        if (openGallery == null) {
            openGallery = new OpenGallery();
        }
        return openGallery;
    }

    public void selectImage(ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }

}
