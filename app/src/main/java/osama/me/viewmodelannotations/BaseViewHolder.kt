package osama.me.viewmodelannotations

import android.support.v7.widget.RecyclerView
import android.view.View


abstract class BaseViewHolder<T>( itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var boundItem : T? = null

    open fun bind(item: T?) {
        boundItem = item
    }
}