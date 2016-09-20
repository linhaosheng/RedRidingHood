package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/9/3.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private View convertView;
    private Context context;

    public CommentViewHolder(View itemView,Context context) {
        super(itemView);
        this.convertView = itemView;
        views = new SparseArray<>();
        this.context =context;
    }

    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    protected void setTextView(int viewId, String content) {
        TextView textView = (TextView)getView(viewId);
        textView.setText(content);
    }

    protected void setImageView(int viewId, String imgUrl) {
        ImageView imageView = (ImageView)getView(viewId);
        GlideUtil.LoadImg(context,imageView,imgUrl);
    }

    protected void setImageView(int viewId, Bitmap bitmap){
        ImageView imageView=(ImageView)getView(viewId);
        imageView.setImageBitmap(bitmap);
    }
}
