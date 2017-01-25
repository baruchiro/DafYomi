package baruch.rothkoff.dafyomi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements DafsAdapter.OnUpdateListener {

    private TextView txtToday;
    private Button btnUpdateList;
    private CheckedTextView chkToday;
    private CheckedTextView chkShowDones;
    private Daf today;
    private RecyclerView recyclerView;
    private DafsAdapter dafsAdapter;
    public static HebrewDateFormatter hebrewDateFormatter;


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
        btnUpdateList = (Button) findViewById(R.id.main_btn_updatelist);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);

        hebrewDateFormatter = new HebrewDateFormatter();
        hebrewDateFormatter.setHebrewFormat(true);
        today = new Daf(new JewishCalendar(), false);

    }

    private void BehaviorMembers() {
        chkToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeStatus((CheckedTextView) v);
            }
        });

        chkShowDones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkShowDones.setChecked(!chkShowDones.isChecked());
                dafsAdapter.ShowDones(chkShowDones.isChecked());
            }
        });

        btnUpdateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dafsAdapter.Refresh();
            }
        });

        txtToday.setText(today.getName());

        InitList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dafsAdapter = new DafsAdapter(this, chkShowDones.isChecked());
        recyclerView.setAdapter(dafsAdapter);

        dafsAdapter.add(today);
    }

    private void ChangeStatus(CheckedTextView v) {
        v.setChecked(!v.isChecked());
        today.setDone(v.isChecked());
        dafsAdapter.update(today);
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
        //long bbb = getLongFromCalendar(new JewishCalendar(new GregorianCalendar(2012, 7, 3)));
        //long d = dBhelper.insertDaf(new Daf(bbb));
    }

    @Override
    public void onUpdate(Daf daf) {
        if (today.getId() == daf.getId()){
            chkToday.setChecked(daf.isDone());
            today.setDone(daf.isDone());
        }
    }
}
