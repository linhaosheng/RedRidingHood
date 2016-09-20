package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.AddRecommend;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/8/25.
 */
public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {

    private List<AddRecommend> addRecommendList;
    private LayoutInflater inflater;
    private Context context;
    private int fragment_flag;
    private ItemOnClick itemOnClick;

    public GridViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }
    public List<AddRecommend> getAddRecommendList() {
        return addRecommendList;
    }


    public GridViewAdapter() {
    }

    public void setAddRecommendList(List<AddRecommend> addRecommendList) {
        this.addRecommendList = addRecommendList;
    }

    public void setFragment_flag(int fragment_flag) {
        this.fragment_flag = fragment_flag;
    }

    @Override
    public GridViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_item_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClick.onClick(v,(String)v.getTag());
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewAdapter.ViewHolder holder, int position) {
        holder.update(addRecommendList.get(fragment_flag), position);
    }

    @Override
    public int getItemCount() {
        return addRecommendList != null ? addRecommendList.get(fragment_flag).getRecommandDataList().size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content_img)
        protected ImageView contentImg;
        @BindView(R.id.content_num)
        protected TextView contentNum;
        @BindView(R.id.content_title)
        protected TextView contentTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(AddRecommend addRecommend, int position) {
            GlideUtil.LoadImg(context, contentImg, addRecommend.getRecommandDataList().get(position).getImg());
            contentNum.setText(addRecommend.getRecommandDataList().get(position).getUpdate());
            contentTitle.setText(addRecommend.getRecommandDataList().get(position).getName());
            this.itemView.setTag(addRecommend.getRecommandDataList().get(position).getUrl());
        }

    }

}
