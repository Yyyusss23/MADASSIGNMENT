package com.example.madassignment4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.madassignment4.DailyWellnessModule.HydrationIntakeModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Name and Version
    private static final String DATABASE_NAME = "FitJourney.db";
    private static final int DATABASE_VERSION = 10;

    // Table Names
    public static final String TABLE_USER = "User";
    public static final String TABLE_USER_PROFILE = "UserProfile";
    public static final String TABLE_HEALTH_STATUS = "HealthStatus";
    public static final String TABLE_USER_HEALTH_STATUS = "UserHealthStatus";
    public static final String TABLE_LANGUAGE = "Language";
    public static final String TABLE_USER_LANGUAGE_SETTING = "UserLanguageSetting";
    public static final String TABLE_PRIVACY_POLICY = "PrivacyPolicy";
    public static final String TABLE_HYDRATION_GOAL = "HydrationGoal";
    public static final String TABLE_HYDRATION_INTAKE = "HydrationIntake";
    public static final String TABLE_STEP_TRACKING = "StepTracking";
    public static final String TABLE_MOOD_LOG = "MoodLog";
    public static final String TABLE_JOURNAL = "Journal";
    public static final String TABLE_MIND_FULNESS_EXERCISE = "MindfulnessExercise";
    public static final String TABLE_FITNESS_SETTING = "FitnessSetting";
    public static final String TABLE_GOAL_SETTING = "GoalSetting";
    public static final String TABLE_EXERCISE_LOG = "ExerciseLog";


    // User Table Columns
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USER_NAME = "UserName";
    public static final String COLUMN_CREATED_AT = "CreatedAt";
    public static final String COLUMN_LAST_LOGIN = "LastLogin";

    // User Profile Columns
    public static final String COLUMN_GENDER = "Gender";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_YEAR_OF_BIRTH = "YearOfBirth";
    public static final String COLUMN_UPDATED_AT = "UpdatedAt";

    // Health Status Table Columns
    public static final String COLUMN_HEALTH_STATUS_ID = "HealthStatusID";
    public static final String COLUMN_HEALTH_STATUS_DESC = "HealthStatusDesc";

    // User Health Status Columns
    public static final String COLUMN_USER_HEALTH_STATUS_ID = "UserHealthStatusID";
    public static final String COLUMN_USER_HEALTH_DATE = "Date";

    // Language Columns
    public static final String COLUMN_LANGUAGE_ID = "LanguageID";
    public static final String COLUMN_LANGUAGE_NAME = "LanguageName";
    public static final String COLUMN_LANGUAGE_CODE = "LanguageCode";

    // User Language Setting Columns
    public static final String COLUMN_USER_LANGUAGE_ID = "UserLanguageID";

    // Privacy Policy Columns
    public static final String COLUMN_PRIVACY_POLICY_ID = "ID";
    public static final String COLUMN_PRIVACY_POLICY_CONTENT = "Content";
    public static final String COLUMN_PRIVACY_POLICY_LAST_UPDATED = "LastUpdated";

    // Hydration Goal Columns
    public static final String COLUMN_GOAL_ID = "GoalID";
    public static final String COLUMN_HYDRATION_GOAL = "HydrationGoal";  // Hydration goal in ml
    public static final String COLUMN_GOAL_DATE = "Date";

    // Hydration Intake Columns
    public static final String COLUMN_INTAKE_ID = "IntakeID";
    public static final String COLUMN_INTAKE_TIMESTAMP = "IntakeTimeStamp";
    public static final String COLUMN_QUANTITY_OF_WATER = "QuantityofWater";  // Intake in ml

    // Step Tracking Columns
    public static final String COLUMN_STEP_GOAL_ID = "StepGoalID";
    public static final String COLUMN_STEP_GOAL = "StepGoal";
    public static final String COLUMN_STEP_COUNT = "StepCount";
    public static final String COLUMN_DISTANCE_WALKED = "DistanceWalked";
    public static final String COLUMN_CALORIES_BURNED = "CaloriesBurned";
    public static final String COLUMN_STEP_TRACK_DATE = "Date";

    // Mood Log Columns
    public static final String COLUMN_MOOD_DATE = "MoodDate";
    public static final String COLUMN_MOOD_TYPE = "MoodType";

    // Journal Columns
    public static final String COLUMN_JOURNAL_DATE = "JournalDate";
    public static final String COLUMN_JOURNAL_PHOTO_PATH = "PhotoPath";
    public static final String COLUMN_JOURNAL_WEATHER = "Weather";
    public static final String COLUMN_JOURNAL_NOTE = "Note";

    // Mindfulness Exercise Columns
    public static final String COLUMN_MINDFULNESS_ID = "MindfulnessID";
    public static final String COLUMN_MINDFULNESS_TYPE = "MindfulnessType";
    public static final String COLUMN_MINDFULNESS_DURATION = "Duration";
    public static final String COLUMN_MINDFULNESS_DATE = "MindfulnessDate";

    //Fitness Settings Columns
    public static final String COLUMN_FITNESS_ID = "FitnessID";
    public static final String COLUMN_TIME_FRAME = "TimeFrame";
    public static final String COLUMN_FITNESS_CREATED_AT = "Created_At";

    //Goal Setting Columns
    public static final String COLUMN_FITNESS_GOAL_ID = "GoalID";
    public static final String COLUMN_GOAL_FITNESS_ID = "FitnessID";
    public static final String COLUMN_GOAL_EXERCISE_TYPE = "ExerciseType";
    public static final String COLUMN_GOAL_ATTRIBUTES = "Attributes";
    public static final String COLUMN_GOAL_CREATED_AT = "Created_At";

    //Exercise Log Columns
    public static final String COLUMN_LOG_ID = "LogID";
    public static final String COLUMN_LOG_DATE = "Date";
    public static final String COLUMN_LOG_EXERCISE_TYPE = "ExerciseType";
    public static final String COLUMN_LOG_ATTRIBUTES = "Attributes";
    public static final String COLUMN_LOG_FITNESS_ID = "FitnessID";
    public static final String COLUMN_LOG_CREATED_AT = "Created_At";

    // Define language constants
    private static final String LANGUAGE_ENGLISH_ID = "en";
    private static final String LANGUAGE_CHINESE_ID = "zh";
    private static final String LANGUAGE_MALAY_ID = "ms";
    private static final String LANGUAGE_HINDI_ID = "hi";



    // SQL for creating User Table3#3#3##3333
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_CREATED_AT + " TIMESTAMP, " +
                    COLUMN_LAST_LOGIN + " TIMESTAMP);";

    // SQL for creating UserProfile Table (Foreign Key to User Table)
    private static final String CREATE_USER_PROFILE_TABLE =
            "CREATE TABLE " + TABLE_USER_PROFILE + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +  // Foreign Key reference to User table
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_HEIGHT + " DOUBLE, " +
                    COLUMN_WEIGHT + " DOUBLE, " +
                    COLUMN_YEAR_OF_BIRTH + " INTEGER, " +
                    COLUMN_UPDATED_AT + " TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Health Status Table
    private static final String CREATE_HEALTH_STATUS_TABLE =
            "CREATE TABLE " + TABLE_HEALTH_STATUS + " (" +
                    COLUMN_HEALTH_STATUS_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_HEALTH_STATUS_DESC + " TEXT);";

    // SQL for creating User Health Status Table
    private static final String CREATE_USER_HEALTH_STATUS_TABLE =
            "CREATE TABLE " + TABLE_USER_HEALTH_STATUS + " (" +
                    COLUMN_USER_HEALTH_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_HEALTH_STATUS_ID + " TEXT, " +
                    COLUMN_USER_HEALTH_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_HEALTH_STATUS_ID + ") REFERENCES " + TABLE_HEALTH_STATUS + "(" + COLUMN_HEALTH_STATUS_ID + "));";

    // SQL for creating Language Table
    private static final String CREATE_LANGUAGE_TABLE =
            "CREATE TABLE " + TABLE_LANGUAGE + " (" +
                    COLUMN_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_LANGUAGE_NAME + " TEXT, " +
                    COLUMN_LANGUAGE_CODE + " TEXT);";

    // SQL for creating User Language Setting Table
    private static final String CREATE_USER_LANGUAGE_SETTING_TABLE =
            "CREATE TABLE " + TABLE_USER_LANGUAGE_SETTING + " (" +
                    COLUMN_USER_LANGUAGE_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_LANGUAGE_ID + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_LANGUAGE_ID + ") REFERENCES " + TABLE_LANGUAGE + "(" + COLUMN_LANGUAGE_ID + "));";

    // SQL for creating Privacy Policy Table
    private static final String CREATE_PRIVACY_POLICY_TABLE =
            "CREATE TABLE " + TABLE_PRIVACY_POLICY + " (" +
                    COLUMN_PRIVACY_POLICY_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_PRIVACY_POLICY_CONTENT + " TEXT, " +
                    COLUMN_PRIVACY_POLICY_LAST_UPDATED + " TIMESTAMP);";

    // SQL for creating HydrationGoal Table
    private static final String CREATE_HYDRATION_GOAL_TABLE =
            "CREATE TABLE " + TABLE_HYDRATION_GOAL + " (" +
                    COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_GOAL_DATE + " TEXT, " +
                    COLUMN_HYDRATION_GOAL + " INTEGER, " +  // Hydration goal in ml
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating HydrationIntake Table
    private static final String CREATE_HYDRATION_INTAKE_TABLE =
            "CREATE TABLE " + TABLE_HYDRATION_INTAKE + " (" +
                    COLUMN_INTAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_INTAKE_TIMESTAMP + " TEXT, " +
                    COLUMN_QUANTITY_OF_WATER + " INTEGER, " +
                    COLUMN_GOAL_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating StepTracking Table
    private static final String CREATE_STEP_TRACKING_TABLE =
            "CREATE TABLE " + TABLE_STEP_TRACKING + " (" +
                    COLUMN_STEP_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_STEP_GOAL + " INTEGER, " +
                    COLUMN_STEP_COUNT + " INTEGER, " +
                    COLUMN_DISTANCE_WALKED + " DOUBLE, " +
                    COLUMN_CALORIES_BURNED + " DOUBLE, " +
                    COLUMN_STEP_TRACK_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL for creating Mood Log Table
    private static final String CREATE_MOOD_LOG_TABLE =
            "CREATE TABLE " + TABLE_MOOD_LOG + " (" +
                    COLUMN_MOOD_DATE + " TEXT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_MOOD_TYPE + " TEXT, " +
                    "PRIMARY KEY(" + COLUMN_MOOD_DATE + ", " + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    // SQL for creating Journal Table
    private static final String CREATE_JOURNAL_TABLE =
            "CREATE TABLE " + TABLE_JOURNAL + " (" +
                    COLUMN_JOURNAL_DATE + " TEXT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_JOURNAL_PHOTO_PATH + " BLOB, " +
                    COLUMN_JOURNAL_WEATHER + " TEXT, " +
                    COLUMN_JOURNAL_NOTE + " TEXT, " +
                    "PRIMARY KEY(" + COLUMN_JOURNAL_DATE + ", " + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    // SQL for creating Mindfulness Exercise Table
    private static final String CREATE_MINDFULNESS_EXERCISE_TABLE =
            "CREATE TABLE " + TABLE_MIND_FULNESS_EXERCISE + " (" +
                    COLUMN_MINDFULNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_MINDFULNESS_TYPE + " TEXT, " +
                    COLUMN_MINDFULNESS_DURATION + " TEXT, " +
                    COLUMN_MINDFULNESS_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    //SQL for creating Fitness Setting Table
    private static final String CREATE_FITNESS_SETTING_TABLE =
            "CREATE TABLE " + TABLE_FITNESS_SETTING + " (" +
                    COLUMN_FITNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_TIME_FRAME + " TEXT NOT NULL CHECK(" + COLUMN_TIME_FRAME + " IN ('Daily', 'Weekly', 'Monthly')), " +
                    COLUMN_FITNESS_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    //SQL for creating Goal Settings Table
    // SQL for creating Goal Settings Table
    private static final String CREATE_GOAL_SETTING_TABLE =
            "CREATE TABLE " + TABLE_GOAL_SETTING + " (" +
                    COLUMN_FITNESS_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GOAL_FITNESS_ID + " INTEGER NOT NULL, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_GOAL_EXERCISE_TYPE + " TEXT NOT NULL, " +
                    COLUMN_GOAL_ATTRIBUTES + " TEXT, " +
                    COLUMN_GOAL_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_GOAL_FITNESS_ID + ") REFERENCES " + TABLE_FITNESS_SETTING + "(" + COLUMN_FITNESS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // SQL to create Exercise Log Table
    private static final String CREATE_EXERCISE_LOG_TABLE =
            "CREATE TABLE " + TABLE_EXERCISE_LOG + " (" +
                    COLUMN_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_LOG_DATE + " TEXT NOT NULL, " +
                    COLUMN_LOG_EXERCISE_TYPE + " TEXT NOT NULL, " +
                    COLUMN_LOG_ATTRIBUTES + " TEXT, " +
                    COLUMN_LOG_FITNESS_ID + " INTEGER NOT NULL, " +
                    COLUMN_LOG_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (" + COLUMN_LOG_FITNESS_ID + ") REFERENCES " + TABLE_FITNESS_SETTING + "(" + COLUMN_FITNESS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;"); // Enable foreign key support33#33#333#33
        // Create tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_PROFILE_TABLE);
        db.execSQL(CREATE_HEALTH_STATUS_TABLE);
        db.execSQL(CREATE_USER_HEALTH_STATUS_TABLE);
        db.execSQL(CREATE_LANGUAGE_TABLE);
        db.execSQL(CREATE_USER_LANGUAGE_SETTING_TABLE);
        db.execSQL(CREATE_PRIVACY_POLICY_TABLE);
        db.execSQL(CREATE_HYDRATION_GOAL_TABLE);
        db.execSQL(CREATE_HYDRATION_INTAKE_TABLE);
        db.execSQL(CREATE_STEP_TRACKING_TABLE);
        db.execSQL(CREATE_MOOD_LOG_TABLE);
        db.execSQL(CREATE_JOURNAL_TABLE);
        db.execSQL(CREATE_MINDFULNESS_EXERCISE_TABLE);
        db.execSQL(CREATE_FITNESS_SETTING_TABLE);
        db.execSQL(CREATE_GOAL_SETTING_TABLE);
        db.execSQL(CREATE_EXERCISE_LOG_TABLE);

        insertPredefinedLanguages(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HEALTH_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LANGUAGE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIVACY_POLICY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYDRATION_GOAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYDRATION_INTAKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEP_TRACKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOD_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MIND_FULNESS_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FITNESS_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_GOAL_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_EXERCISE_LOG);
        onCreate(db);
    }

    public void saveOrUpdateUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String userId = UUID.randomUUID().toString(); // Generate a unique User ID
        long currentTime = System.currentTimeMillis();

        Cursor cursor = db.query(TABLE_USER, null, COLUMN_USER_NAME + " = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // User exists, update last login
            String existingUserId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            ContentValues values = new ContentValues();
            values.put(COLUMN_LAST_LOGIN, currentTime);
            db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{existingUserId});
        } else {
            // User does not exist, insert a new user
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_USER_NAME, username);
            values.put(COLUMN_CREATED_AT, currentTime);
            values.put(COLUMN_LAST_LOGIN, currentTime);
            db.insert(TABLE_USER, null, values);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;
        Cursor cursor = null;
        try {
            // Query to fetch the username
            String query = "SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_USER + " ORDER BY " + COLUMN_LAST_LOGIN + " DESC LIMIT 1";
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return username;
    }

    public String getUserIdByMostRecentLogin() {
        SQLiteDatabase db = this.getReadableDatabase();
        String userId = null;

        // Query to get the user with the most recent login
        String query = "SELECT " + COLUMN_USER_ID +
                " FROM " + TABLE_USER +
                " ORDER BY " + COLUMN_LAST_LOGIN + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            cursor.close();
        }

        db.close();
        return userId;
    }

    public void populateHealthStatusTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the table already contains data
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HEALTH_STATUS, null);
        if (cursor.getCount() == 0) { // Only populate if the table is empty
            ContentValues values = new ContentValues();

            // Add health statuses
            String[] healthStatuses = {"Active", "Sick", "Take a Break", "Injured"};
            for (String status : healthStatuses) {
                String healthStatusId = UUID.randomUUID().toString();
                values.put(COLUMN_HEALTH_STATUS_ID, healthStatusId);
                values.put(COLUMN_HEALTH_STATUS_DESC, status);
                db.insert(TABLE_HEALTH_STATUS, null, values);
                values.clear();
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public void saveOrUpdateUserHealthStatus(String userId, String healthStatusId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long currentDate = getCurrentDateOnly(); // Helper method to get today's date only

        // Query to check if a health status already exists for the user on the same day
        String query = "SELECT " + COLUMN_USER_HEALTH_STATUS_ID +
                " FROM " + TABLE_USER_HEALTH_STATUS +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_USER_HEALTH_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, String.valueOf(currentDate)});

        if (cursor != null && cursor.moveToFirst()) {
            // Health status exists for today, update it
            int userHealthStatusId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_HEALTH_STATUS_ID));
            ContentValues values = new ContentValues();
            values.put(COLUMN_HEALTH_STATUS_ID, healthStatusId);
            db.update(TABLE_USER_HEALTH_STATUS, values, COLUMN_USER_HEALTH_STATUS_ID + " = ?", new String[]{String.valueOf(userHealthStatusId)});
        } else {
            // No health status for today, insert a new row
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_HEALTH_STATUS_ID, healthStatusId);
            values.put(COLUMN_USER_HEALTH_DATE, currentDate);
            db.insert(TABLE_USER_HEALTH_STATUS, null, values);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public String getUserHealthStatus(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_HEALTH_STATUS_ID +
                " FROM " + TABLE_USER_HEALTH_STATUS +
                " WHERE " + COLUMN_USER_ID + " = ?" +
                " ORDER BY " + COLUMN_USER_HEALTH_DATE + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{userId});

        String healthStatus = null;
        if (cursor.moveToFirst()) {
            int healthStatusIndex = cursor.getColumnIndex(COLUMN_HEALTH_STATUS_ID);
            healthStatus = cursor.getString(healthStatusIndex);
        }
        cursor.close();
        return healthStatus;
    }



    private long getCurrentDateOnly() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public Map<String, Object> getUserProfile(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Object> profileData = new HashMap<>();

        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER_PROFILE +
                " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId})) {
            if (cursor != null && cursor.moveToFirst()) {
                profileData.put(COLUMN_HEIGHT, cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT)));
                profileData.put(COLUMN_WEIGHT, cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT)));
                profileData.put(COLUMN_GENDER, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                profileData.put(COLUMN_YEAR_OF_BIRTH, cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR_OF_BIRTH)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profileData;
    }

    public boolean saveOrUpdateUserProfile(String userId, String gender, double height, double weight, int yearOfBirth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Populate ContentValues with user profile data
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_YEAR_OF_BIRTH, yearOfBirth);
        values.put(COLUMN_UPDATED_AT, System.currentTimeMillis());

        // Check if user profile already exists
        String query = "SELECT 1 FROM " + TABLE_USER_PROFILE + " WHERE " + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        boolean isUpdated;
        try {
            if (cursor != null && cursor.moveToFirst()) {
                // Profile exists, perform an update
                int rowsUpdated = db.update(TABLE_USER_PROFILE, values, COLUMN_USER_ID + " = ?", new String[]{userId});
                isUpdated = rowsUpdated > 0;
            } else {
                // Profile does not exist, perform an insert
                long rowId = db.insert(TABLE_USER_PROFILE, null, values);
                isUpdated = rowId != -1;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isUpdated;
    }

    public void saveOrUpdatePrivacyPolicy(String policyId, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRIVACY_POLICY_ID, policyId);
        values.put(COLUMN_PRIVACY_POLICY_CONTENT, content);
        values.put(COLUMN_PRIVACY_POLICY_LAST_UPDATED, System.currentTimeMillis());

        // Use replace to insert or update based on primary key
        long rowId = db.replace(TABLE_PRIVACY_POLICY, null, values);

        if (rowId == -1) {
            Log.e("DatabaseHelper", "Failed to save or update privacy policy");
        } else {
            Log.d("DatabaseHelper", "Privacy policy saved or updated successfully");
        }
        db.close();
    }

    public Cursor getPrivacyPolicy() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRIVACY_POLICY + " LIMIT 1";
        return db.rawQuery(query, null);
    }


    public void saveHydrationGoal(String userId, String date, int hydrationGoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserID", userId);
        values.put("Date", date);
        values.put("HydrationGoal", hydrationGoal);

        // Insert or update the goal
        db.replace(TABLE_HYDRATION_GOAL, null, values);
        db.close();
    }

    public int getHydrationGoal(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HYDRATION_GOAL, new String[]{"HydrationGoal"},
                "UserID = ? AND Date = ?", new String[]{userId, date}, null, null, null);

        int goal = -1; // Default value if no goal is found

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("HydrationGoal");
                if (columnIndex != -1) {
                    goal = cursor.getInt(columnIndex);
                }
            }
            cursor.close();
        }

        return goal;
    }

    // Method to save hydration intake
    public boolean saveHydrationIntake(String userId, String date, String timeStamp, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserID", userId);
        values.put("Date", date);
        values.put("IntakeTimeStamp", timeStamp);
        values.put("QuantityofWater", quantity);

        long result = db.insert(TABLE_HYDRATION_INTAKE, null, values);
        db.close();
        return result != -1;
    }

    // Method to fetch hydration intake data
    public List<HydrationIntakeModel> getTodayHydrationRecords(String userId, String todayDate) {
        List<HydrationIntakeModel> intakeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query only today's hydration intake
        Cursor cursor = db.query(TABLE_HYDRATION_INTAKE, new String[]{"IntakeTimeStamp", "QuantityofWater"},
                "UserID = ? AND Date = ?", new String[]{userId, todayDate}, null, null, "IntakeTimeStamp ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int intakeIimeStampIndex= cursor.getColumnIndex("IntakeTimeStamp");
                int quantityOfWaterIndex = cursor.getColumnIndex("QuantityofWater");
                String intakeTimestamp = cursor.getString(intakeIimeStampIndex);
                int quantityOfWater = cursor.getInt(quantityOfWaterIndex);
                HydrationIntakeModel intake = new HydrationIntakeModel(intakeTimestamp, quantityOfWater);
                intakeList.add(intake);
            }
            cursor.close();
        }

        return intakeList;
    }

    public int getTotalWaterIntake(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HYDRATION_INTAKE, new String[]{"QuantityofWater"},
                "UserID = ? AND Date = ?", new String[]{userId, date}, null, null, null);

        int totalIntake = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int quantityOfWaterIndex = cursor.getColumnIndex("QuantityofWater");
                int quantityOfWater = cursor.getInt(quantityOfWaterIndex);
                totalIntake += quantityOfWater;
            }
            cursor.close();
        }

        return totalIntake;
    }

    public Map<String, Integer> getTotalWaterIntakeByDay(String userId, String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> dailyIntake = new LinkedHashMap<>();

        // Query to sum water intake grouped by Date
        String query = "SELECT Date, SUM(QuantityofWater) AS TotalIntake " +
                "FROM " + TABLE_HYDRATION_INTAKE +
                " WHERE UserID = ? AND Date BETWEEN ? AND ? " +
                "GROUP BY Date ORDER BY Date";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{userId, startDate, endDate});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int DateIndex=cursor.getColumnIndex("Date");
                int TotalIntakeIndex=cursor.getColumnIndex("TotalIntake");
                String date = cursor.getString(DateIndex);
                int totalIntake = cursor.getInt(TotalIntakeIndex);
                dailyIntake.put(date, totalIntake); // Store date and total intake in the map
            }
            cursor.close();
        }

        return dailyIntake; // Return totals grouped by date
    }

    public void saveDailyStepTracking(String userId, String date, int stepCount, float distanceWalked, float caloriesBurned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("UserID", userId);
        values.put("Date", date);
        values.put("StepCount", stepCount);
        values.put("DistanceWalked", distanceWalked);
        values.put("CaloriesBurned", caloriesBurned);

        // Insert or replace the record for the given date
        db.insertWithOnConflict(TABLE_STEP_TRACKING, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveStepGoal(String userId, String date, int goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        int existingGoal = getStepGoal(userId, date);

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STEP_TRACK_DATE, date);
        values.put(COLUMN_STEP_GOAL, goal);

        if (existingGoal == -1) {
            db.insert(TABLE_STEP_TRACKING, null, values);
        } else {
            db.update(TABLE_STEP_TRACKING, values, COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?",
                    new String[]{userId, date});
        }
    }

    public int getStepGoal(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        int goal = -1;

        // Query the database for the user's goal on the given date
        String query = "SELECT " + COLUMN_STEP_GOAL + " FROM " + TABLE_STEP_TRACKING +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, date});

        if (cursor != null && cursor.moveToFirst()) {
            // If a record exists, get the goal value
            int goalIndex = cursor.getColumnIndex(COLUMN_STEP_GOAL);
            goal = cursor.getInt(goalIndex);
            cursor.close();
        }

        return goal;
    }

    public void saveOrUpdateStepTrackingData(String userId, int stepGoal, int stepCount, float distanceWalked, float caloriesBurned, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STEP_COUNT, stepCount);
        values.put(COLUMN_DISTANCE_WALKED, distanceWalked);
        values.put(COLUMN_CALORIES_BURNED, caloriesBurned);
        values.put(COLUMN_STEP_TRACK_DATE, date);

        // Check if a goal is already set for this user and date
        int existingGoal = getStepGoal(userId, date);

        // If no goal is set for the day, insert the step goal
        if (existingGoal == -1) {
            // If there is no goal set for the day, insert the goal along with the other tracking data
            values.put(COLUMN_STEP_GOAL, stepGoal);  // Add step goal if it doesn't exist
            db.insert(TABLE_STEP_TRACKING, null, values);
        } else {
            // If a goal is already set for the day, don't update the goal, just update the other tracking data
            db.update(TABLE_STEP_TRACKING, values, COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?",
                    new String[]{userId, date});
        }
    }

    public int getTotalStepsForDay(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STEP_TRACKING, new String[]{COLUMN_STEP_COUNT}, COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?",
                new String[]{userId, date}, null, null, null);

        int totalSteps = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int StepCountIndex=cursor.getColumnIndex(COLUMN_STEP_COUNT);
            totalSteps = cursor.getInt(StepCountIndex);
            cursor.close();
        }

        return totalSteps;
    }

    public float getDistanceWalkedForDay(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        float distanceWalked = 0.0f;

        // Query the database for the user's distance walked on the given date
        String query = "SELECT " + COLUMN_DISTANCE_WALKED +
                " FROM " + TABLE_STEP_TRACKING +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, new String[]{userId, date});

            if (cursor != null && cursor.moveToFirst()) {
                int distanceWalkedIndex = cursor.getColumnIndex(COLUMN_DISTANCE_WALKED);
                distanceWalked = cursor.getFloat(distanceWalkedIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Ensure the cursor is closed
            }
        }

        return distanceWalked;
    }


    public float getCaloriesBurnedForDay(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        float caloriesBurned = 0.0f;

        // Query the database for the user's calories burned on the given date
        String query = "SELECT " + COLUMN_CALORIES_BURNED +
                " FROM " + TABLE_STEP_TRACKING +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STEP_TRACK_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, date});

        if (cursor != null && cursor.moveToFirst()) {
            int caloriesBurnedIndex = cursor.getColumnIndex(COLUMN_CALORIES_BURNED);
            caloriesBurned = cursor.getFloat(caloriesBurnedIndex);
            cursor.close();
        }

        return caloriesBurned;
    }
    public void saveMood(String userId, String date, String mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_MOOD_DATE, date);
        values.put(COLUMN_MOOD_TYPE, mood);
        db.insertWithOnConflict(TABLE_MOOD_LOG, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public String getMood(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOOD_LOG,
                new String[]{COLUMN_MOOD_TYPE},
                COLUMN_USER_ID + "=? AND " + COLUMN_MOOD_DATE + "=?",
                new String[]{userId, date},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String mood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOOD_TYPE));
            cursor.close();
            return mood;
        }

        return null;
    }

    public void saveJournal(String userID, String date, byte[] newPhoto, String newWeather, String newNote) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Retrieve existing data for the date
        Cursor cursor = getJournal(userID, date);
        byte[] existingPhoto = null;
        String existingWeather = null;
        String existingNote = null;

        if (cursor != null && cursor.moveToFirst()) {
            existingPhoto = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_PHOTO_PATH));
            existingWeather = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_WEATHER));
            existingNote = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_NOTE));
            cursor.close();
        }

        // Use new data if provided; otherwise, use existing data
        byte[] finalPhoto = (newPhoto != null) ? newPhoto : existingPhoto;
        String finalWeather = (newWeather != null && !newWeather.isEmpty()) ? newWeather : existingWeather;
        String finalNote = (newNote != null && !newNote.isEmpty()) ? newNote : existingNote;

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userID);
        values.put(COLUMN_JOURNAL_DATE, date);
        values.put(COLUMN_JOURNAL_PHOTO_PATH, finalPhoto);
        values.put(COLUMN_JOURNAL_WEATHER, finalWeather);
        values.put(COLUMN_JOURNAL_NOTE, finalNote);

        db.insertWithOnConflict(TABLE_JOURNAL, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getJournal(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_JOURNAL,
                null,
                COLUMN_USER_ID + "=? AND " + COLUMN_JOURNAL_DATE + "=?",
                new String[]{userId, date},
                null, null, null
        );
    }


    public byte[] getPhoto(String userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] photo = null;

        // Query to get the photo column for the given userId and date
        Cursor cursor = db.query(
                TABLE_JOURNAL,
                new String[]{COLUMN_JOURNAL_PHOTO_PATH}, // Only retrieve the photo column
                COLUMN_USER_ID + "=? AND " + COLUMN_JOURNAL_DATE + "=?", // Where clause
                new String[]{userId, date},         // Arguments for the where clause
                null,                               // Group by
                null,                               // Having
                null                                // Order by
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                photo = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_PHOTO_PATH));
            }
            cursor.close();
        }

        return photo; // Returns null if no photo is found
    }

    public Cursor getLatestFitnessSettingAndGoals(SQLiteDatabase db) {
        String query = "SELECT " +
                "g." + COLUMN_GOAL_EXERCISE_TYPE + " AS ExerciseType, " +
                "g." + COLUMN_GOAL_ATTRIBUTES + " AS Attributes " +
                "FROM " + TABLE_GOAL_SETTING + " g " +
                "JOIN " + TABLE_FITNESS_SETTING + " f ON g." + COLUMN_GOAL_FITNESS_ID + " = f." + COLUMN_FITNESS_ID + " " +
                "WHERE f." + COLUMN_FITNESS_ID + " = (SELECT MAX(" + COLUMN_FITNESS_ID + ") FROM " + TABLE_FITNESS_SETTING + ")";
        return db.rawQuery(query, null);
    }

    // Method to insert predefined languages into the Language table
    private void insertPredefinedLanguages(SQLiteDatabase db) {
        // Add English language
        addLanguage(db, LANGUAGE_ENGLISH_ID, "English", "en");

        // Add Chinese language
        addLanguage(db, LANGUAGE_CHINESE_ID, "Chinese", "zh");

        // Add Malay language
        addLanguage(db, LANGUAGE_MALAY_ID, "Malay", "ms");

        // Add Hindi language
        addLanguage(db, LANGUAGE_HINDI_ID, "Hindi", "hi");
    }

    // Method to add a language to the Language table
    private void addLanguage(SQLiteDatabase db, String languageId, String languageName, String languageCode) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE_ID, languageId);
        values.put(COLUMN_LANGUAGE_NAME, languageName);
        values.put(COLUMN_LANGUAGE_CODE, languageCode);
        db.insert(TABLE_LANGUAGE, null, values);
    }

    // Method to insert a new language setting for a user
    public void insertLanguageSetting(String userId, String languageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_LANGUAGE_ID, languageId);

        // Update the language if already exists or insert a new record
        int rowsUpdated = db.update(TABLE_USER_LANGUAGE_SETTING, values, COLUMN_USER_ID + " = ?", new String[]{userId});

        if (rowsUpdated == 0) {
            // If no rows were updated, insert a new language setting for the user
            db.insert(TABLE_USER_LANGUAGE_SETTING, null, values);
        }
        db.close();
    }



    public String getUserLanguage(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_LANGUAGE_ID + " FROM " + TABLE_USER_LANGUAGE_SETTING +
                " WHERE " + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor != null && cursor.moveToFirst()) {
            int index=cursor.getColumnIndex(COLUMN_LANGUAGE_ID);
            String languageId = cursor.getString(index);
            cursor.close();
            return languageId;
        } else {
            cursor.close();
            return null; // No language found
        }
    }

    // Method to get language code based on language ID
    public String getLanguageName(String languageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_LANGUAGE_CODE + " FROM " + TABLE_LANGUAGE +
                " WHERE " + COLUMN_LANGUAGE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{languageId});

        if (cursor != null && cursor.moveToFirst()) {
            int index=cursor.getColumnIndex(COLUMN_LANGUAGE_CODE);
            String languageCode = cursor.getString(index);
            cursor.close();
            return languageCode;
        } else {
            cursor.close();
            return "en"; // Default to English if not found
        }
    }


    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }





}




