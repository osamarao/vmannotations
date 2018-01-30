package osama.me.viewmodelannotations

import android.view.View
import android.widget.TextView

//Proposed location for BindFields annotation to generate lines such as 11 and 12
class MainItemViewHolder(itemView : View) : BaseViewHolder<AKindOfObject>( itemView) {

    override fun bind(item: AKindOfObject?) {
        super.bind(item)
        itemView.findViewById<TextView>(R.id.name).text = item?.name
        itemView.findViewById<TextView>(R.id.amount).text = item?.amount.toString()
    }
}