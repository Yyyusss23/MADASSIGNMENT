package com.example.madassignment4.MoodModule;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Journal extends Fragment {

    private Button saveButton;
    private ImageButton photoAddButton, backButton;
    private ImageView photoView;
    private EditText noteEditText;
    private TextView dateTextView;
    private String selectedDate, selectedWeather;
    private ImageButton sunnyButton, cloudyButton, rainyButton, windyButton;
    private DatabaseHelper databaseHelper;

    public Journal() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide BottomNavigationView
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        // Initialize UI components
        initializeUIComponents(view);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(getContext());

        // Set weather button actions
        setupWeatherButtons();

        // Get the selected date from the arguments bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedDate = bundle.getString("selectedDate");
            dateTextView.setText(selectedDate);  // Update the TextView with selectedDate
        }

        // Load saved data for the selected date
        loadSavedData();

        // When photo button is clicked, open the photo picker
        photoAddButton.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photoIntent, 1);

        });

        saveButton.setOnClickListener(v -> {
            String date = selectedDate;
            Uri photoUri = (Uri) photoView.getTag();  // Get the photo URI stored as a tag in photoView

            if (photoUri != null) {
                try {
                    // Resize the image before saving
                    Bitmap resizedBitmap = resizeImage(photoUri, 800, 600);  // Resize to a maximum of 800x600

                    // Convert the resized Bitmap to byte[] (image data)
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);  // Compress image
                    byte[] photoByteArray = outputStream.toByteArray();

                    // Save date, photo (byte[]), weather, and note to the database
                    databaseHelper.saveJournal(databaseHelper.getUserIdByMostRecentLogin(), date, photoByteArray, selectedWeather, noteEditText.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle error resizing the image
                    Toast.makeText(getContext(), "Error resizing image", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle case where no photo is selected
                databaseHelper.saveJournal(databaseHelper.getUserIdByMostRecentLogin(), date, null, selectedWeather, noteEditText.getText().toString());
            }

            // Navigate back to HomeFragment
            Navigation.findNavController(requireView()).navigate(R.id.action_journal_to_home3, bundle);




            Toast.makeText(getContext(), "Journal saved!", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_journal_to_home3, bundle);
        });

        return view;
    }

    // Handle photo result in onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            photoView.setImageURI(selectedImage);

            // Store the URI in the tag to use later when saving
            photoView.setTag(selectedImage);
        }
    }

    private void initializeUIComponents(View view) {
        saveButton = view.findViewById(R.id.save_btn);
        photoAddButton = view.findViewById(R.id.photoAdd_btn);
        photoView = view.findViewById(R.id.photo);
        noteEditText = view.findViewById(R.id.editTextTextMultiLine);
        dateTextView = view.findViewById(R.id.jtv);

        sunnyButton = view.findViewById(R.id.sunny_btn);
        cloudyButton = view.findViewById(R.id.cloudy_btn);
        rainyButton = view.findViewById(R.id.rainy_btn);
        windyButton = view.findViewById(R.id.windy_btn);

        backButton = view.findViewById(R.id.jback_btn);
    }

    private void setupWeatherButtons() {
        // Use an array to hold the mutable flag
        final boolean[] weatherSelected = {false};

        View.OnClickListener weatherClickListener = v -> {
            resetWeatherButtons();
            v.setSelected(true);
            weatherSelected[0] = true; // Update the flag

            if (v.getId() == R.id.sunny_btn) {
                selectedWeather = "sunny";
            } else if (v.getId() == R.id.cloudy_btn) {
                selectedWeather = "cloudy";
            } else if (v.getId() == R.id.rainy_btn) {
                selectedWeather = "rainy";
            } else if (v.getId() == R.id.windy_btn) {
                selectedWeather = "windy";
            }
        };

        sunnyButton.setOnClickListener(weatherClickListener);
        cloudyButton.setOnClickListener(weatherClickListener);
        rainyButton.setOnClickListener(weatherClickListener);
        windyButton.setOnClickListener(weatherClickListener);

        // After all button clicks, check if any weather was selected
        if (!weatherSelected[0]) {
            selectedWeather = "null"; // Set to null if no button was selected
        }
    }

    private void resetWeatherButtons() {
        sunnyButton.setSelected(false);
        cloudyButton.setSelected(false);
        rainyButton.setSelected(false);
        windyButton.setSelected(false);
    }

    private Bitmap resizeImage(Uri imageUri, int maxWidth, int maxHeight) throws IOException {
        // Get the image dimensions without loading the full image into memory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
        BitmapFactory.decodeStream(inputStream, null, options);
        inputStream.close();

        // Calculate the sample size to scale the image
        int sampleSize = 1;
        if (options.outHeight > maxHeight || options.outWidth > maxWidth) {
            final int halfHeight = options.outHeight / 2;
            final int halfWidth = options.outWidth / 2;
            while ((halfHeight / sampleSize) > maxHeight && (halfWidth / sampleSize) > maxWidth) {
                sampleSize *= 2;
            }
        }

        // Decode the image with the calculated sample size
        BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
        resizeOptions.inSampleSize = sampleSize;
        inputStream = getContext().getContentResolver().openInputStream(imageUri);
        Bitmap resizedBitmap = BitmapFactory.decodeStream(inputStream, null, resizeOptions);
        inputStream.close();

        return resizedBitmap;
    }

    private void loadSavedData() {
        if (selectedDate == null) return;

        // Retrieve photo for the selected date
        byte[] photo = databaseHelper.getPhoto(databaseHelper.getUserIdByMostRecentLogin(),selectedDate);
        if (photo != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                photoView.setImageBitmap(bitmap); // Set the decoded image
            } catch (Exception e) {
                Log.e("JournalFragment", "Error decoding photo byte array", e);
                photoView.setImageResource(R.drawable.transparentrectangle); // Default transparent rectangle
            }
        } else {
            photoView.setImageResource(R.drawable.transparentrectangle); // Default transparent rectangle
        }

        // Retrieve journal data (note and weather)
        Cursor cursor = databaseHelper.getJournal(databaseHelper.getUserIdByMostRecentLogin(), selectedDate);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String note = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.COLUMN_JOURNAL_NOTE));
                String weather = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.COLUMN_JOURNAL_WEATHER));

                // Set note if available, otherwise leave empty
                noteEditText.setText(note != null ? note : "");

                // Update the weather button UI if weather data exists
                if (weather != null) {
                    updateWeatherUI(weather);
                }
            }
            cursor.close(); // Close the cursor to release resources
        } else {
            noteEditText.setText(""); // Clear note if no data
            resetWeatherButtons(); // Clear weather selection
        }
    }

    private void updateWeatherUI(String weather) {
        resetWeatherButtons(); // Clear all selections

        if (weather != null) {
            switch (weather) {
                case "sunny":
                    sunnyButton.setSelected(true);
                    break;
                case "cloudy":
                    cloudyButton.setSelected(true);
                    break;
                case "rainy":
                    rainyButton.setSelected(true);
                    break;
                case "windy":
                    windyButton.setSelected(true);
                    break;
            }
            selectedWeather = weather; // Update the current selection
        }
    }
}