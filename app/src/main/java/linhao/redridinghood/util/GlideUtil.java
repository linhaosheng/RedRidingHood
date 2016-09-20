package linhao.redridinghood.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by linhao on 2016/8/13.
 */
public class GlideUtil {

    public static void LoadImg(Context context, ImageView imageView,String url){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void clear(Activity activity){
       // Glide.clear();
    }
}
