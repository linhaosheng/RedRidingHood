package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.TopicsDetail;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/9/15.
 */
public class TopicDetailAdapter extends CommentAdapter<TopicsDetail.DetailList> {


    private Context context;
    private boolean IS_TYPE_FOOTER;
    private PageLoadMoreListener loadMoreListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<TopicsDetail.DetailList> detailLists;
    private ItemOnClick itemOnClick;

    public TopicDetailAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public void setLoadMoreListener(PageLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void settList(List<TopicsDetail.DetailList> detailLists) {
        super.settList(detailLists);
        this.detailLists = detailLists;
    }

    @Override
    public int getLayoutId() {
        if (IS_TYPE_FOOTER) {
            return R.layout.footer_load_more;
        } else {
            return R.layout.week_update_item;
        }
    }

    @Override
    public void convert(final CommentViewHolder viewHolder, TopicsDetail.DetailList detailList, int position) {

        if (IS_TYPE_FOOTER) {
            final TextView load_more = (TextView) viewHolder.getView(R.id.load_text);
            load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressWheel progressWheel = (ProgressWheel) viewHolder.getView(R.id.icon_loading);
                    loadMoreListener.loadMore(load_more, progressWheel);
                }
            });
        } else {
            viewHolder.setTextView(R.id.content_title, detailList.getName());
            viewHolder.setTextView(R.id.content_collection, detailList.getLastest());
            viewHolder.setTextView(R.id.content_download, detailList.getDownload());
            viewHolder.setTextView(R.id.content_type, detailList.getType());
            viewHolder.setTextView(R.id.content_zone, detailList.getLocal());
            CircleImageView circleImageView = (CircleImageView) viewHolder.getView(R.id.content_img);
            GlideUtil.LoadImg(context, circleImageView, detailList.getImg());
            viewHolder.itemView.setTag(detailList.getUrl());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClick.onClick(v,(String)v.getTag());
                }
            });
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
        return detailLists == null ? 0 : detailLists.size() + 1;
    }
}
