package baruch.rothkoff.dafyomi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

/**
 * Created by baruc on 23/01/2017.
 */

public class DafViewHolder extends RecyclerView.ViewHolder {
    private CheckedTextView checkedTextView;
    private boolean isDone;
    private View myView;

    public DafViewHolder(View itemView) {
        super(itemView);
        checkedTextView = (CheckedTextView) itemView.findViewById(R.id.item_daf_text);
        myView = itemView;

    }

    public void Init(String daf,boolean isDone){
        checkedTextView.setText(daf);
        checkedTextView.setChecked(isDone);
    }

    public void setVisibility(boolean visibile) {
        myView.setVisibility(visibile?View.VISIBLE:View.GONE);
    }
}
