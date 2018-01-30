package osama.me.viewmodelannotations

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class MainRecyclerViewAdapter(val data: List<AKindOfObject>) : BaseAdapter() {


    override fun onBindViewHolder(holder: BaseViewHolder<AKindOfObject>?, position: Int) {
        holder?.bind(getObject(position) as AKindOfObject)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<AKindOfObject> {
        return MainItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.layout_item, parent, false))
    }

    private fun getObject(index: Int): Any {
        return data[index]
    }
}


abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder<AKindOfObject>>()