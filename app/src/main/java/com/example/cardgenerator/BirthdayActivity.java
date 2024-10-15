package com.example.cardgenerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BirthdayActivity extends AppCompatActivity {
    private ImageView categoryFunny, categoryFormal, categoryFriendly,categoryBf,categoryDad,categoryMom;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_birthday);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = getIntent().getStringExtra("name");
        categoryFunny = findViewById(R.id.category_funny);
        categoryFormal = findViewById(R.id.category_formal);
        categoryFriendly = findViewById(R.id.category_friendly);
        categoryBf = findViewById(R.id.category_bf);
        categoryDad = findViewById(R.id.category_dad);
        categoryMom = findViewById(R.id.category_mom);

        categoryBf.setOnClickListener(view -> onImageClick(R.drawable.bestfriend_category_image));
        categoryFunny.setOnClickListener(view -> onImageClick(R.drawable.funny_category_image));
        categoryFormal.setOnClickListener(view -> onImageClick(R.drawable.formal_category_image));
        categoryFriendly.setOnClickListener(view -> onImageClick(R.drawable.friendly_category_image));
        categoryDad.setOnClickListener(view -> onImageClick(R.drawable.dad_category_image));
        categoryMom.setOnClickListener(view -> onImageClick(R.drawable.mom_category_image));

    }
    private void onImageClick(int imageResId) {
        // Create an intent to start the second activity
        Intent intent = new Intent(BirthdayActivity.this, CardGenerated.class);

        // Put the clicked image resource ID into the intent
        intent.putExtra("clickedImageResId", imageResId);
        intent.putExtra("name", name);

        // Start the second activity
        startActivity(intent);
    }
}