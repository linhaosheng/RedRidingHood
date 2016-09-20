package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by linhao on 2016/9/3.
 */
public abstract class CommentAdapter<T> extends RecyclerView.Adapter<CommentViewHolder> {


    private List<T> tList;
    private Context context;
    private LayoutInflater inflater;

    public CommentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void settList(List<T> tList) {
        this.tList = tList;
    }

    public List<T> getList() {
        return tList;
    }

    public abstract int getLayoutId();

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        if (position + 1 == getItemCount()) {
            convert(holder, null, position);
        } else {
            convert(holder, tList.get(position), position);
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(getLayoutId(), parent, false);
        return new CommentViewHolder(view, context);
    }

    @Override
    public int getItemCount() {
        return tList == null ? 0 : tList.size();
    }


    public abstract void convert(CommentViewHolder viewHolder, T t, int position);
}
