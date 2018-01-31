package osama.me.viewmodelannotations;

import android.view.View;
import android.widget.TextView;

import osama.me.vmannotation.BindFields;

public class MainItemViewHolder extends BaseViewHolder<AKindOfObject> {

    private View itemView;

    public MainItemViewHolder(final View itemView) {
        super(itemView);

        this.itemView = itemView;
    }

    @BindFields
    @Override
    protected void bind(final AKindOfObject item) {
        super.bind(item);
        ((TextView) itemView.findViewById(R.id.name)).setText(item.name);
        ((TextView) itemView.findViewById(R.id.amount)).setText(item.amount);
    }
}