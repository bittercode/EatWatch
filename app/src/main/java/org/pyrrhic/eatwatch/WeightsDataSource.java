package org.pyrrhic.eatwatch;

/**
 * Created by jr.peck on 2/4/14.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class WeightsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DATE, MySQLiteHelper.COLUMN_WEIGHT, MySQLiteHelper.COLUMN_AVERAGE };

    public WeightsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public WeightInfo createWeight(String wdate, String weight, String average) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DATE, wdate);
        values.put(MySQLiteHelper.COLUMN_WEIGHT, weight);
        values.put(MySQLiteHelper.COLUMN_AVERAGE, average);
        long insertId = database.insert(MySQLiteHelper.TABLE_WEIGHTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_WEIGHTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        WeightInfo newWeight = cursorToWeight(cursor);
        cursor.close();
        return newWeight;
    }

    public void deleteWeight(WeightInfo weight) {
        long id = weight.getId();
        System.out.println("Weight deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_WEIGHTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<WeightInfo> getAllWeights() {
        List<WeightInfo> weights = new ArrayList<WeightInfo>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_WEIGHTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WeightInfo weight = cursorToWeight(cursor);
            weights.add(weight);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return weights;
    }

    private WeightInfo cursorToWeight(Cursor cursor) {
        WeightInfo weight = new WeightInfo();
        weight.setId(cursor.getLong(0));
        weight.setWdate(cursor.getString(1));
        weight.setWeight(cursor.getString(2));
        weight.setAverage(cursor.getString(3));
        return weight;
    }

}
