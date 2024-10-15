package com.example.cardgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CelebrationActivity extends AppCompatActivity {

    private ImageView Marriage, Christmas, Diwali,Ghanesh,Rakhi,Small_party;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_celebration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = getIntent().getStringExtra("name");
        Marriage = findViewById(R.id.marriage);
        Christmas = findViewById(R.id.christmas);
        Diwali = findViewById(R.id.diwali);
        Ghanesh = findViewById(R.id.ganesh);
        Rakhi = findViewById(R.id.rakhi);
        Small_party = findViewById(R.id.Small_party);


        Marriage.setOnClickListener(view -> onImageClick(R.drawable.marriage));
        Christmas.setOnClickListener(view -> onImageClick(R.drawable.christmas));
        Diwali.setOnClickListener(view -> onImageClick(R.drawable.diwali));
        Ghanesh.setOnClickListener(view -> onImageClick(R.drawable.ganesh));
        Rakhi.setOnClickListener(view -> onImageClick(R.drawable.rakhi1));
        Small_party.setOnClickListener(view -> onImageClick(R.drawable.small_party));
    }

    private void onImageClick(int imageResId) {
        // Create an intent to start the second activity
        Intent intent = new Intent(CelebrationActivity.this, CardGenerated.class);

        // Put the clicked image resource ID into the intent
        intent.putExtra("clickedImageResId", imageResId);
        intent.putExtra("name", name);

        // Start the second activity
        startActivity(intent);
    }
}