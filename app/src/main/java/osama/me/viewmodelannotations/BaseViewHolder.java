package osama.me.viewmodelannotations;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private T boundItem;

    public BaseViewHolder(final View itemView) {
        super(itemView);
    }

    protected void bind(T item) {
        boundItem = item;
    }

}