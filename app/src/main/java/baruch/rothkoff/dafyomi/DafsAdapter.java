package baruch.rothkoff.dafyomi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.sourceforge.zmanim.hebrewcalendar.Daf;
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.Inflater;

import static android.view.View.GONE;


public class DafsAdapter extends RecyclerView.Adapter<DafViewHolder> {

    private List<KeyAndValue<JewishCalendar, Boolean>> list;
    private Context context;
    private boolean showDones;

    public DafsAdapter(Context context, List<KeyAndValue<JewishCalendar, Boolean>> list) {
        this.context = context;
        this.list = list;
        showDones = true;
    }

    @Override
    public DafViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new DafViewHolder(LayoutInflater.from(context).inflate(R.layout.item_daf, parent, false));
    }

    @Override
    public void onBindViewHolder(DafViewHolder holder, int position) {
        boolean isDone = list.get(position).getValue();
            Daf daf = list.get(position).getKey().getDafYomiBavli();
            String dafString = MainActivity.hebrewDateFormatter.formatDafYomiBavli(daf);
            holder.Init(dafString, isDone);

        holder.setVisibility(!(!showDones&&isDone));
    }

    public void ShowDones(boolean showDones) {
        this.showDones = showDones;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
