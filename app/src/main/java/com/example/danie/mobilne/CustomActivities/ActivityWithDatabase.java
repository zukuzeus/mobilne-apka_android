package com.example.danie.mobilne.CustomActivities;

import android.app.Activity;

import com.example.danie.mobilne.InterActivityVariablesSingleton;
import com.example.danie.mobilne.database.DatabaseHelper;

/**
 * Created by danie on 02.12.2017.
 */

public class ActivityWithDatabase extends Activity {
    protected static DatabaseHelper DATABASE;

    protected void createDatabase() {
        DATABASE = new DatabaseHelper(this, InterActivityVariablesSingleton.getInstance().getUSER());
    }
}
