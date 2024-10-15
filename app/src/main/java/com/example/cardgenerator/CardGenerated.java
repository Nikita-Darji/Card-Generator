package com.example.cardgenerator;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CardGenerated extends AppCompatActivity {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final String TAG = "GenerateCardActivity";
    private String name;
    private ImageView imageView;
    private Button saveButton;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_generated);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveImageToDCIM();
//            }
//        });
//
        imageView = findViewById(R.id.generatedCardImage);
        saveButton = findViewById(R.id.saveButton);

        name = getIntent().getStringExtra("name");

        int clickedImageResId = getIntent().getIntExtra("clickedImageResId", -1);

        if (clickedImageResId != -1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), clickedImageResId);
            Bitmap bitmapWithText = drawTextOnBitmap(bitmap, name);
            imageView.setImageBitmap(bitmapWithText);
        }

        saveButton.setOnClickListener(view -> {
            saveImageToDCIM();

        });
    }
    private void saveImageToDCIM() {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // Get the path to the DCIM directory
        String dcimPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        String fileName = "Card" + System.currentTimeMillis() + ".jpg";
        File file = new File(dcimPath, fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Toast.makeText(CardGenerated.this, "Image Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(CardGenerated.this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap drawTextOnBitmap(Bitmap bitmap, String text) {
        // Create a mutable copy of the Bitmap to allow modifications
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Create a Canvas object using the mutable Bitmap
        Canvas canvas = new Canvas(mutableBitmap);

        // Set up the Paint object for drawing the text
        Paint paint = new Paint();
        paint.setColor(Color.WHITE); // Text color
        paint.setTextSize(50); // Text size
        paint.setAntiAlias(true); // Smooth edges
        paint.setTextAlign(Paint.Align.CENTER); // Center the text

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.argb(80,00,00,00)); // Set the color
        backgroundPaint.setStyle(Paint.Style.FILL);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textWidth = paint.measureText(text);
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        // Calculate the position for the text at the bottom center of the image
        int xPos = canvas.getWidth() / 2; // Center horizontally
        int yPos = (int) (canvas.getHeight() - 50); // Position near the bottom

        // Calculate the background rectangle position based on the text size
        float padding = 10; // Padding around the text
        float rectLeft = xPos - (textWidth / 2) - padding;
        float rectRight = xPos + (textWidth / 2) + padding;
        float rectTop = yPos + fontMetrics.top - padding;
        float rectBottom = yPos + fontMetrics.bottom + padding;

        // Draw the background rectangle behind the text
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, backgroundPaint);

        // Draw the text on the Canvas (which draws on the Bitmap)
        canvas.drawText(text, xPos, yPos, paint);

        // Return the modified Bitmap with the text overlaid
        return mutableBitmap;
    }
}