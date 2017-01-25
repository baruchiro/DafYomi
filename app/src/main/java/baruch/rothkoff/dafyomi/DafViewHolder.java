package baruch.rothkoff.dafyomi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;


public class DafViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private CheckedTextView checkedTextView;
    private Daf daf;
    private View myView;
    private OnClickListener listener;

    public DafViewHolder(View itemView, OnClickListener listener) {
        super(itemView);
        checkedTextView = (CheckedTextView) itemView.findViewById(R.id.item_daf_text);
        myView = itemView;
        myView.setOnClickListener(this);
        this.listener = listener;
    }

    public void Init(Daf daf) {
        this.daf = daf;
        checkedTextView.setText(daf.getName());
        checkedTextView.setChecked(daf.isDone());
    }

    @Override
    public void onClick(View v) {
        checkedTextView.setChecked(!checkedTextView.isChecked());
        listener.onClick(this, this.daf);
    }

    public interface OnClickListener {
        void onClick(DafViewHolder holder, Daf daf);
    }
}
