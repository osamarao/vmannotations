package osama.me.viewmodelannotations;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends BaseAdapter {

    private List<AKindOfObject> data = new ArrayList<>();

    public MainRecyclerViewAdapter(List<AKindOfObject> data) {
        super();
        this.data = data;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new MainItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((BaseViewHolder) holder).bind(getObject(position));
    }

    private Object getObject(int index) {
        return data.get(index);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}