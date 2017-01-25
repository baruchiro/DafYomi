package baruch.rothkoff.dafyomi;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;
import net.sourceforge.zmanim.hebrewcalendar.JewishDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String DATE_START_PREF = "rothkoff.baruch.DATE_START";
    private TextView txtToday;
    private CheckedTextView chkToday;
    private CheckedTextView chkShowDones;
    private JewishCalendar jewishCalendar;
    private RecyclerView recyclerView;
    private DafsAdapter dafsAdapter;
    public static HebrewDateFormatter hebrewDateFormatter;
    //private SharedPreferences sharedPreferences;
    private List<KeyAndValue<JewishCalendar, Boolean>> list;
    private JewishDate dateStart;


    private final String SHARED_NAME = "rothkoff.baruch.shared.file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitMembers();
        BehaviorMembers();
    }

    private void InitMembers() {

        txtToday = (TextView) findViewById(R.id.main_txt_today);
        chkToday = (CheckedTextView) findViewById(R.id.main_check_markdone);
        chkShowDones = (CheckedTextView) findViewById(R.id.main_check_showdone);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);

        jewishCalendar = new JewishCalendar();
        hebrewDateFormatter = new HebrewDateFormatter();

        list = new ArrayList<>();
    }

    private void BehaviorMembers() {
        chkToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeStatus(new JewishCalendar(), (CheckedTextView) v);
            }
        });

        chkShowDones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkShowDones.setChecked(!chkShowDones.isChecked());
                dafsAdapter.ShowDones(chkShowDones.isChecked());
            }
        });

        hebrewDateFormatter.setHebrewFormat(true);

        //Daf yomi Today in Hebrew
        txtToday.setText(hebrewDateFormatter.formatDafYomiBavli(jewishCalendar.getDafYomiBavli()));

        InitList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        dafsAdapter = new DafsAdapter(this, list);
        recyclerView.setAdapter(dafsAdapter);
    }

    private void ChangeStatus(JewishCalendar jewishCalendar, CheckedTextView v) {
        v.setChecked(!v.isChecked());
    }

    public static long getLongFromCalendar(JewishCalendar jewishCalendar) {
        return new GregorianCalendar(
                jewishCalendar.getGregorianYear(),
                jewishCalendar.getGregorianMonth(),
                jewishCalendar.getGregorianDayOfMonth())
                .getTimeInMillis();
    }

    public static long getLongFromCalendar(Calendar calendar) {
        return new GregorianCalendar(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .getTimeInMillis();
    }

    private void InitList() {
        DBhelper dBhelper = new DBhelper(this);
        //long bbb = getLongFromCalendar(new JewishCalendar(new GregorianCalendar(2012, 8, 3)));
        long d = dBhelper.insertDaf(new Daf(Daf.START_13));
    }
}
