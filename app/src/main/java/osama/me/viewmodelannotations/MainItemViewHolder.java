package osama.me.viewmodelannotations;

import android.view.View;

import osama.me.vmannotation.BindFields;

public class MainItemViewHolder extends BaseViewHolder<AKindOfObject> {

    private View itemView;

    MainItemViewHolder(final View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    @BindFields(viewIds = {"name", "amount"}, viewName = "itemView")
    @Override
    protected void bind(final AKindOfObject item) {
        super.bind(item);
        MainItemViewHolder_BindFields.bindfields(item, itemView);
    }
}