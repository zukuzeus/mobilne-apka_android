package com.example.danie.mobilne.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by danie on 03.12.2017.
 */

public class DatabaseContext extends ContextWrapper {
    private static final String DEBUG_CONTEXT = "DatabaseContext";
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "databases"
            + File.separator;
    //String dbfile12 = "/mnt" + "/sdcard" + File.separator + "databases" + File.separator;
    File sdcard = Environment.getExternalStorageDirectory();
    String dbfile2 = sdcard.getPath() + File.separator + "databases" + File.separator;

    public DatabaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        File sdcard = Environment.getExternalStorageDirectory();
        String dbfile = sdcard.getPath() + File.separator + "databases" + File.separator + name;
        //String dbfile = "/mnt" + "/sdcard" + File.separator + "databases" + File.separator + name;
//        String url = Environment.getExternalStorageDirectory().toString();
//        if(android.os.Build.DEVICE.contains("Samsung") || android.os.Build.MANUFACTURER.contains("Samsung")){
//            url = url + "/external_sd/";
//        }
        Log.d(DEBUG_CONTEXT, dbfile);
        if (!dbfile.endsWith(".db")) {
            dbfile += ".db";
        }

        File result = new File(dbfile);

        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT, "getDatabasePath(" + name + ") = " + result.getAbsolutePath());
        }

        return result;
    }

    /* this version is called for android devices >= api-11. thank to @damccull for fixing this. */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name, mode, factory);
    }
}
