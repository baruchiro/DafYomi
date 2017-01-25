package baruch.rothkoff.dafyomi;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ObjectInputValidation;


public class DafsAdapter extends RecyclerView.Adapter<DafViewHolder> implements DafViewHolder.OnClickListener {

    private DBhelper dBhelper;
    private Cursor cursor;
    private Context context;
    private boolean showDones;

    public DafsAdapter(Context context, boolean showDones) {
        if (!(context instanceof OnUpdateListener))
            throw new IllegalArgumentException("contect must omplement OnUpdateListener");
        this.context = context;
        this.dBhelper = new DBhelper(context);
        this.showDones = showDones;
        this.Refresh();
    }

    public void Refresh() {
        if (showDones) cursor = dBhelper.getAllDafs();
        else cursor = dBhelper.getNotDone();
        notifyDataSetChanged();
    }

    @Override
    public DafViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daf, parent, false);
        return new DafViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(DafViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Daf daf = new Daf(
                cursor.getLong(cursor.getColumnIndex(Daf.DAF_FIELD_ID)),
                cursor.getString(cursor.getColumnIndex(Daf.DAF_FIELD_NAME)),
                cursor.getInt(cursor.getColumnIndex(Daf.DAF_FIELD_DONE)) == 1
        );
        holder.Init(daf);
    }

    public void ShowDones(boolean showDones) {
        this.showDones = showDones;
        Refresh();
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onClick(DafViewHolder holder, Daf daf) {
        daf.setDone(!daf.isDone());
        dBhelper.updateDaf(daf);
        ((OnUpdateListener)context).onUpdate(daf);
    }

    public void add(Daf daf) {
        dBhelper.insertDaf(daf);
        Refresh();
    }

    public void update(Daf daf) {
        dBhelper.updateDaf(daf);
        Refresh();
    }

    public interface OnUpdateListener {
        void onUpdate(Daf daf);
    }
}
