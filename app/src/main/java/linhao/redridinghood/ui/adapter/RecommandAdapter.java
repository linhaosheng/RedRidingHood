package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.View;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Detail;
import linhao.redridinghood.ui.listener.ItemOnClick;

/**
 * Created by linhao on 2016/9/16.
 */
public class RecommandAdapter extends CommentAdapter<Detail.EditRecommend> {

    private ItemOnClick itemOnClick;
    public RecommandAdapter(Context context) {
        super(context);
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.recommandand;
    }

    @Override
    public void convert(CommentViewHolder viewHolder, Detail.EditRecommend editRecommend, int position) {
        if (editRecommend != null) {
            viewHolder.setTextView(R.id.recommand_sort, editRecommend.getSort());
            viewHolder.setTextView(R.id.recommand_content, editRecommend.getName());
            viewHolder.itemView.setTag(editRecommend.getUrl());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClick.onClick(v,(String)v.getTag());
                }
            });
        }
    }
}
