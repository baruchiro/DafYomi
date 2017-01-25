package baruch.rothkoff.dafyomi;

import android.content.ContentValues;
import android.support.v7.widget.LinearLayoutCompat;

import net.sourceforge.zmanim.hebrewcalendar.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Daf {
    private long id;
    private String name;
    private boolean done;
    public static final long START_13 = 1343941200000L;

    public static final String DAF_FIELD_ID = "id";
    public static final String DAF_FIELD_NAME = "name";
    public static final String DAF_FIELD_DONE = "done";

    /**
     * Create Daf entry. the <b>name</b> is automatic and <b>done</b> is <b>false</b> by default.<br/>
     * if you want to change the default, use the second constractor.
     *
     * @param id the Date of Daf, converted to long.
     */
    public Daf(long id) {
        this.id = id;
        JewishCalendar jewishCalendar = new JewishCalendar(new Date(id));
        net.sourceforge.zmanim.hebrewcalendar.Daf d = jewishCalendar.getDafYomiBavli();
        name = MainActivity.hebrewDateFormatter.formatDafYomiBavli(d);
        done = false;
    }

    /**
     * Create Daf entry. the <b>name</b> is automatic.
     * @param id the Date of Daf, converted to long.
     * @param done
     */
    public Daf(long id, boolean done) {
        new Daf(id);
        this.done = done;
    }

    public Daf(long id,String name,boolean done){
        this.id = id;
        this.name = name;
        this.done = done;
    }

    public Daf(JewishCalendar jewishCalendar, boolean done) {
        this.id = MainActivity.getLongFromCalendar(jewishCalendar);
        this.name = MainActivity.hebrewDateFormatter.formatDafYomiBavli(jewishCalendar.getDafYomiBavli());
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString(){
        return id+" "+name+ " "+(done?"Done":"");
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAF_FIELD_ID,id);
        contentValues.put(DAF_FIELD_NAME,name);
        contentValues.put(DAF_FIELD_DONE,done);
        return contentValues;
    }
}
