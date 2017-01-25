package baruch.rothkoff.dafyomi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public final class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String DAFS_TABLE_NAME = "dafs";
    public static final String DAFS_COLUMN_ID = "id";
    public static final String DAFS_COLUMN_NAME = "name";
    public static final String DAFS_COLUMN_DONE = "done";
    private HashMap hp;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + DAFS_TABLE_NAME +
                        " (id long primary key, name text, done boolean)"
        );
        Log.i("DBHelper","Create table: "+DAFS_TABLE_NAME);

        long now = MainActivity.getLongFromCalendar(new JewishCalendar());
        Log.i("DBHelper", "now: "+now);
        JewishCalendar i = new JewishCalendar(new Date(Daf.START_13));

        while (MainActivity.getLongFromCalendar(i) < now) {
            Daf daf = new Daf(i, true);
            insertDaf(db, daf);
            Log.i("DBHelper",daf.toString() );
            i.forward();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DAFS_TABLE_NAME);
        onCreate(db);
    }

    public long insertDaf(Daf daf) {
        SQLiteDatabase db = this.getWritableDatabase();
        return insertDaf(db, daf);
    }

    public Cursor getDaf(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+DAFS_TABLE_NAME+" WHERE id="+id+"",null);
    }

    private long insertDaf(SQLiteDatabase db, Daf daf) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAFS_COLUMN_ID, daf.getId());
        contentValues.put(DAFS_COLUMN_NAME, daf.getName());
        contentValues.put(DAFS_COLUMN_DONE, daf.isDone());
        return db.insert(DAFS_TABLE_NAME, null, contentValues);
    }
}
