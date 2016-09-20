package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.News;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;

/**
 * Created by linhao on 2016/9/3.
 */
public class NewsAdapter extends CommentAdapter<News.NewList> {


    private List<News.NewList> newses;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean IS_TYPE_FOOTER;
    private Context context;
    private PageLoadMoreListener loadMoreListener;
    public NewsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void setLoadMoreListener(PageLoadMoreListener pageLoadMoreListener){
        this.loadMoreListener=pageLoadMoreListener;
    }

    @Override
    public void settList(List<News.NewList> newses) {
        super.settList(newses);
        this.newses = newses;
    }

    @Override
    public List<News.NewList> getList() {
        return newses;
    }

    @Override
    public int getLayoutId() {
        if (IS_TYPE_FOOTER) {
            return R.layout.footer_load_more;
        } else {
            return R.layout.news_item;
        }
    }

    @Override
    public void convert(final CommentViewHolder viewHolder, News.NewList newList, int position) {
        if (IS_TYPE_FOOTER) {
            final TextView load_more=(TextView)viewHolder.getView(R.id.load_text);
            load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressWheel progressWheel=(ProgressWheel)viewHolder.getView(R.id.icon_loading);
                    progressWheel.setVisibility(View.VISIBLE);
                    loadMoreListener.loadMore(load_more,progressWheel);
                }
            });
        } else {
            viewHolder.setTextView(R.id.new_title, newList.getTitle());
            viewHolder.setTextView(R.id.new_content, newList.getDescription());
            viewHolder.setTextView(R.id.new_time, newList.getTime());
            viewHolder.setImageView(R.id.new_img, newList.getImg());
        }
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
        return newses == null ? 0 : newses.size() + 1;
    }
}
