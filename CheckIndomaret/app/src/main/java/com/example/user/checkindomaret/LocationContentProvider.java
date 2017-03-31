package com.example.user.checkindomaret;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

/**
 * Created by user on 17/03/2017.
 */

public class LocationContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "com.example.user.checkindomaret";

    /** A uri to do operations on locations table. A content provider is identified by its uri */
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/locations" );

    /** Constant to identify the requested operation */
    private static final int LOCATIONS = 1;

    private static final UriMatcher uriMatcher ;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "locations", LOCATIONS);
    }

    /** This content provider does the database operations by this object */
    LocationDB mLocationDB;

    @Override
    public boolean onCreate() {
        mLocationDB = new LocationDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uriMatcher.match(uri)==LOCATIONS){
            return mLocationDB.getAllLocations();
        }
        return null;
    }

    @Override
    public String getType( Uri uri) {
        return null;
    }

    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        long rowID = mLocationDB.insert(values);
        Uri _uri=null;
        if(rowID>0){
            _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
        }else {
            try {
                throw new SQLException("Failed to insert : " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _uri;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        int cnt = 0;
        cnt = mLocationDB.del();
        return cnt;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
