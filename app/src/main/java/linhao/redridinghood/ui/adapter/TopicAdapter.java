package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Topics;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/9/15.
 */
public class TopicAdapter extends CommentAdapter<Topics>{

    private Context context;
    private LayoutInflater inflater;
    private ItemOnClick itemOnClick;

    public TopicAdapter(Context context) {
        super(context);
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.topic_item;
    }

    @Override
    public void convert(CommentViewHolder viewHolder, Topics topics, int position) {
        ImageView img=viewHolder.getView(R.id.topic_img);
        TextView title=viewHolder.getView(R.id.topic_title);
        GlideUtil.LoadImg(context,img,topics.getImg());
        title.setText(topics.getTitle());
        viewHolder.itemView.setTag(topics.getUrl());
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(getLayoutId(), parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               itemOnClick.onClick(v,(String) v.getTag());
            }
        });
        return new CommentViewHolder(view, context);
    }
}
