package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Search;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.ui.view.LoadMoreView;

/**
 * Created by linhao on 2016/9/18.
 */
public class SearchAdapter extends CommentAdapter<Search.ListItem> {

    private boolean IS_TYPE_FOOTER;
    private PageLoadMoreListener loadMoreListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<Search.ListItem> listItems;
    private ItemOnClick itemOnClick;

    public SearchAdapter(Context context) {
        super(context);
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    public void setLoadMoreListener(PageLoadMoreListener pageLoadMoreListener){
        this.loadMoreListener=pageLoadMoreListener;
    }

    @Override
    public void settList(List<Search.ListItem> listItems) {
        super.settList(listItems);
        this.listItems=listItems;
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
    public void convert(final CommentViewHolder viewHolder, final Search.ListItem listItem, int position) {
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
            viewHolder.setTextView(R.id.comic_title, listItem.getName());
            viewHolder.setTextView(R.id.episode, listItem.getType());
            viewHolder.setTextView(R.id.updateTime, listItem.getLastest());
            viewHolder.setTextView(R.id.downloadCount, listItem.getDownload());
            viewHolder.setImageView(R.id.comic_img, listItem.getImg());
            viewHolder.itemView.setTag(listItem.getUrl());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  itemOnClick.onClick(v,(String)v.getTag());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size() + 1;
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
}
