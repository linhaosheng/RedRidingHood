package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Ranking;
import linhao.redridinghood.ui.listener.ItemOnClick;

/**
 * Created by linhao on 2016/9/2.
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {


    private List<Ranking> rankingList;
    private Context context;
    private LayoutInflater inflater;
    private int Ranking_Flag=0;
    private ItemOnClick itemOnClick;

    public RankingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    public void setRankingList(List<Ranking> rankingList){
        this.rankingList = rankingList;
    }

    public List<Ranking> getRankingList(){
        return this.rankingList;
    }

    public void setRanking_Flag(int Ranking_Flag){
        this.Ranking_Flag=Ranking_Flag;
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rangk_content_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClick.onClick(v,(String)v.getTag());
            }
        });
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
         holder.update(rankingList.get(Ranking_Flag),position);
    }

    @Override
    public int getItemCount() {
        return rankingList == null ? 0 : rankingList.get(Ranking_Flag).getListDataList().size();
    }


    class RankingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.num)
        TextView num;

        public RankingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(Ranking data,int position){
            title.setText(data.getListDataList().get(position).getName());
            if (data.getListDataList().get(position).getEpisode().equals("")){
                num.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,0,30,0);
                title.setLayoutParams(layoutParams);
            }
            num.setText(data.getListDataList().get(position).getEpisode());
            this.itemView.setTag(data.getListDataList().get(position).getUrl());
        }
    }
}
