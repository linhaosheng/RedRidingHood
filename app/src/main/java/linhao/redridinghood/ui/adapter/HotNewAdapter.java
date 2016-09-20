package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.HotNewAnimate;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/9/11.
 */
public class HotNewAdapter extends CommentAdapter<HotNewAnimate.NewItemContent> {

    private Context context;
    private List<HotNewAnimate> hotNewAnimateList;
    private int Year_Fragment_Flag;
    private int Month_Fragment_Flag;
    private List<HotNewAnimate.NewItemContent> newItemContentList;
    private ItemOnClick itemOnClick;

    public HotNewAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    public List<HotNewAnimate.NewItemContent> getNewItemContentList() {
        return newItemContentList;
    }

    public void setNewItemContentList(List<HotNewAnimate.NewItemContent> newItemContentList) {
        this.newItemContentList = newItemContentList;
    }

    public void setType(int Year_Fragment_Flag, int Month_Fragment_Flag) {
        this.Year_Fragment_Flag = Year_Fragment_Flag;
        this.Month_Fragment_Flag = Month_Fragment_Flag;
        if (hotNewAnimateList != null) {
            newItemContentList = hotNewAnimateList.get(Year_Fragment_Flag).getChild().get(Month_Fragment_Flag).getNewItemContents();
            settList(newItemContentList);
        }
    }

    public int getMonth_Fragment_Flag() {
        return Month_Fragment_Flag;
    }

    public int getYear_Fragment_Flag() {
        return Year_Fragment_Flag;
    }

    public void setHotNewAnimateList(List<HotNewAnimate> hotNewAnimateList) {
        this.hotNewAnimateList = hotNewAnimateList;
    }

    public List<HotNewAnimate> getHotNewAnimateList() {
        return this.hotNewAnimateList;
    }

    @Override
    public int getLayoutId() {
        return R.layout.grid_item_layout;
    }

    @Override
    public void convert(CommentViewHolder viewHolder, HotNewAnimate.NewItemContent newItemContent, int position) {
        TextView contentNum = viewHolder.getView(R.id.content_num);
        TextView contentTitle = viewHolder.getView(R.id.content_title);
        ImageView contentImg = viewHolder.getView(R.id.content_img);
        LinearLayout linearLayout = viewHolder.getView(R.id.linear1);
        if (newItemContent != null) {
            contentNum.setText(newItemContent.getEpisode());
            contentTitle.setText(newItemContent.getName());
            GlideUtil.LoadImg(context, contentImg, newItemContent.getImg());
            viewHolder.itemView.setTag(newItemContent.getUrl());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClick.onClick(v,(String)v.getTag());
                }
            });
        } else {
            linearLayout.setAlpha(0.0f);
            contentImg.setVisibility(View.GONE);
            contentTitle.setVisibility(View.GONE);
            contentImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return newItemContentList == null ? 0 : newItemContentList.size();
    }
}
