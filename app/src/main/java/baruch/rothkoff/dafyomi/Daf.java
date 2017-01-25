package baruch.rothkoff.dafyomi;

import android.support.v7.widget.LinearLayoutCompat;

import net.sourceforge.zmanim.hebrewcalendar.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Daf {
    private long id;
    private String name;
    private boolean done;
    public static final long START_13 = 1346619600000L;

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

    public Daf(JewishCalendar jewishCalendar, boolean done) {
        jewishCalendar = new JewishCalendar(
                jewishCalendar.getJewishYear(),
                jewishCalendar.getJewishMonth(),
                jewishCalendar.getJewishDayOfMonth());
        this.id = jewishCalendar.getTime().getTime();
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
}
