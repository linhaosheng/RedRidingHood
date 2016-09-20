package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.RecentUpdate;
import linhao.redridinghood.ui.listener.ItemOnClick;

/**
 * Created by linhao on 2016/9/1.
 */
public class RecentUpdateAdapter extends RecyclerView.Adapter<RecentUpdateAdapter.RecentUpdateViewHolder> {

    private Context context;
    private List<RecentUpdate> recentUpdateList;
    private ItemOnClick itemOnClick;

    public RecentUpdateAdapter(Context context) {
        this.context = context;
    }

    public void setRecentUpdateList(List<RecentUpdate> recentUpdateList) {
        this.recentUpdateList = recentUpdateList;
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    public List<RecentUpdate> getRecentUpdateList() {
        return recentUpdateList;
    }

    @Override
    public RecentUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_update_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClick.onClick(v,(String)v.getTag());
            }
        });
        return new RecentUpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentUpdateViewHolder holder, int position) {
     holder.update(recentUpdateList.get(position));
    }

    @Override
    public int getItemCount() {
        return recentUpdateList == null ? 0 : recentUpdateList.size();
    }


    public class RecentUpdateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.content_type)
        TextView contentType;
        @BindView(R.id.content_title)
        TextView contentTitle;
        @BindView(R.id.content_time)
        TextView contentTime;

        public RecentUpdateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(RecentUpdate recentUpdate){
            num.setText(recentUpdate.getSort());
            contentType.setText(recentUpdate.getType());
            contentTitle.setText(recentUpdate.getName());
            contentTime.setText(recentUpdate.getTime());
            this.itemView.setTag(recentUpdate.getUrl());
        }
    }
}
