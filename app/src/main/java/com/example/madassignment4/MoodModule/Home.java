package com.example.madassignment4.MoodModule;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Home extends Fragment {


    private CalendarView calendarView;
    private TextView feeling;
    private ImageButton feelingAddBtn, journalAddBtn, mindfulnessExerciseBtn;
    private ImageView defaultEmoji, defaultPhoto;
    private String selectedDate;
    private DatabaseHelper databaseHelper;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.action_home3_to_trackerMain3);
                remove();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide BottomNavigationView
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.VISIBLE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        feeling = view.findViewById(R.id.Feeling_tv);
        feelingAddBtn = view.findViewById(R.id.feelingAdd_btn);
        journalAddBtn = view.findViewById(R.id.journalAdd_btn);
        mindfulnessExerciseBtn = view.findViewById(R.id.mindfulness_btn);
        defaultEmoji = view.findViewById(R.id.defaultemoji);
        defaultPhoto = view.findViewById(R.id.defaultphoto);

        databaseHelper = new DatabaseHelper(getContext());

        // Set the selected date
        selectedDate = getArguments() != null ? getArguments().getString("selectedDate") :
                new SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(new Date());

        // Set the calendar view to the selected date
        setCalendarViewToSelectedDate();

        // Calendar view date change listener
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            updateEmoji();
            updatePhoto();
        });

        feelingAddBtn.setOnClickListener(v -> navigateToSelectMood(v));
        journalAddBtn.setOnClickListener(v -> navigateToJournal(v));
        mindfulnessExerciseBtn.setOnClickListener(v -> navigateToMindfulnessExercise(v));

        updateEmoji();  // Update the mood based on the current date
        updatePhoto();  // Update the photo based on the current date

        ImageButton backButton = view.findViewById(R.id.homeback_btn);
        backButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_home3_to_trackerMain3);
        });

        return view;
    }

    // Set calendar to the selected date
    private void setCalendarViewToSelectedDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
            Date date = sdf.parse(selectedDate);
            if (date != null) {
                calendarView.setDate(date.getTime(), true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update the emoji based on the mood of the selected date
    private void updateEmoji() {
        String mood = databaseHelper.getMood(databaseHelper.getUserIdByMostRecentLogin(), selectedDate);
        if (mood == null) {
            defaultEmoji.setImageResource(R.drawable.transparentsquare); // Default emoji
            feeling.setText(R.string.feeling);
        } else {
            defaultEmoji.setImageResource(getEmojiResource(mood));
            feeling.setText(getMoodStringResource(mood));
        }
    }

    private void updatePhoto() {
        byte[] photo = databaseHelper.getPhoto(databaseHelper.getUserIdByMostRecentLogin(), selectedDate);
        if (photo == null) {
            defaultPhoto.setImageResource(R.drawable.transparentrectangle); // Default photo
        } else {
            // Convert byte[] to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            defaultPhoto.setImageBitmap(bitmap); // Set the decoded image
        }
    }

    // Map mood string to emoji resource
    private int getEmojiResource(String mood) {
        switch (mood) {
            case "Excited":
                return R.drawable.excited_stk;
            case "Happy":
                return R.drawable.happy_stk;
            case "Sad":
                return R.drawable.sad_stk;
            case "Neutral":
                return R.drawable.neutral_stk;
            case "Anxious":
                return R.drawable.anxious_stk;
            case "Angry":
                return R.drawable.angry_stk;
            default:
                return R.drawable.transparentsquare;
        }
    }

    private int getMoodStringResource(String mood) {
        switch (mood) {
            case "Excited":
                return R.string.excited;
            case "Happy":
                return R.string.happy;
            case "Sad":
                return R.string.sad;
            case "Neutral":
                return R.string.neutral;
            case "Anxious":
                return R.string.anxious;
            case "Angry":
                return R.string.angry;
            default:
                return R.string.feeling; // Default fallback
        }
    }

    // Navigation to Select Mood Fragment
    private void navigateToSelectMood(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", selectedDate);
        Navigation.findNavController(v).navigate(R.id.action_home3_to_selectMood, bundle);
    }

    // Navigation to Journal Fragment
    private void navigateToJournal(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", selectedDate);
        Navigation.findNavController(v).navigate(R.id.action_home3_to_journal, bundle);
    }

    // Navigation to Mindfulness Exercise Fragment
    private void navigateToMindfulnessExercise(View v) {
        Navigation.findNavController(v).navigate(R.id.action_home3_to_mindfulnessExercise);
    }
}
