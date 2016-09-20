package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.ComicList;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/9/15.
 */
public class ComicListAdapter extends CommentAdapter<ComicList.ListData> {

    private Context context;
    private boolean IS_TYPE_FOOTER;
    private PageLoadMoreListener loadMoreListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<ComicList.ListData>listDatas;
    private ItemOnClick itemOnClick;

    public ComicListAdapter(Context context) {
        super(context);
        this.context=context;
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }
    public void setLoadMoreListener(PageLoadMoreListener loadMoreListener){
        this.loadMoreListener=loadMoreListener;
    }

    public List<ComicList.ListData> getListDatas(){
        return this.listDatas;
    }

    @Override
    public void settList(List<ComicList.ListData> listData) {
        super.settList(listData);
        this.listDatas=listData;
    }


    @Override
    public int getLayoutId() {
        if (IS_TYPE_FOOTER) {
            return R.layout.footer_load_more;
        } else {
            return R.layout.comic_list_item;
        }
    }


    @Override
    public void convert(final CommentViewHolder viewHolder, ComicList.ListData listData, int position) {
        if (IS_TYPE_FOOTER) {
            final TextView load_more=(TextView)viewHolder.getView(R.id.load_text);
            load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressWheel progressWheel=(ProgressWheel)viewHolder.getView(R.id.icon_loading);
                    loadMoreListener.loadMore(load_more,progressWheel);
                }
            });
        } else {
            ImageView image = viewHolder.getView(R.id.comic_img);
            TextView title = viewHolder.getView(R.id.comic_title);
            TextView episode = viewHolder.getView(R.id.episode);
            TextView downloadCount = viewHolder.getView(R.id.downloadCount);
            GlideUtil.LoadImg(context, image, listData.getImg());
            title.setText(listData.getName());
            episode.setText(listData.getEpisode());
            downloadCount.setText(listData.getDownloadCount());
            viewHolder.itemView.setTag(listData.getUrl());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClick.onClick(v,(String)v.getTag());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            IS_TYPE_FOOTER = true;
            return TYPE_FOOTER;
        } else {
            IS_TYPE_FOOTER = false;
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size() + 1;
    }
}
