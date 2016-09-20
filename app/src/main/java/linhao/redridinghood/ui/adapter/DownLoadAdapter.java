package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.List;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Detail;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/9/16.
 */
public class DownLoadAdapter extends CommentAdapter<Detail.DownloadData> {

    private Context context;
    private ItemOnClick itemOnClick;
    private List<Detail.Download> downloadList;
    private boolean isFtp;
    private boolean isBaidu;
    private boolean isMagnetic;
    private ViewPager viewPager;

    public DownLoadAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setDownloadList(List<Detail.Download> downloadList) {
        this.downloadList = downloadList;
    }

    public void changeData(int currentPosition) {

        for (int i = 0; i < downloadList.size(); i++) {
            if (downloadList.get(i).getTab().equals(context.getResources().getString(R.string.ftp))) {
                isFtp = true;
            }
            if (downloadList.get(i).getTab().equals(context.getResources().getString(R.string.baidu))) {
                isBaidu = true;
            }
            if (downloadList.get(i).getTab().equals(context.getResources().getString(R.string.magnetic))) {
                isMagnetic = true;
            }
        }
        if (currentPosition == 0) {
            if (!isFtp) {
                settList(null);
            } else {
                settList(downloadList.get(currentPosition).getDownloadDataList());
            }
        }
        if (currentPosition == 1) {
            if (!isBaidu) {
                settList(null);
            } else {
                if (!isFtp) {
                    settList(downloadList.get(currentPosition - 1).getDownloadDataList());
                } else {
                    settList(downloadList.get(currentPosition).getDownloadDataList());
                }
            }
        }
        if (currentPosition == 2) {
            if (!isMagnetic) {
                settList(null);
            } else {
                if (!isFtp && !isBaidu) {
                    settList(downloadList.get(0).getDownloadDataList());
                } else if (!isFtp) {
                    settList(downloadList.get(currentPosition - 1).getDownloadDataList());
                } else {
                    settList(downloadList.get(currentPosition).getDownloadDataList());
                }
            }
        }
        if (getList() == null) {
            viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);
            int heightPixels = metrics.heightPixels;
            if (getList().get(0).getName().length() > 20) {
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getList().size() * heightPixels / 8));
            } else {
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getList().size() * heightPixels / 15));
            }
        }
    }

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.download_item;
    }

    @Override
    public void convert(CommentViewHolder viewHolder, Detail.DownloadData downloadData, int position) {
        if (downloadData != null) {
            viewHolder.setTextView(R.id.download_name, downloadData.getName());
            viewHolder.itemView.setTag(downloadData.getUrl());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClick.onClick(v, (String) v.getTag());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        if (downloadList.size() == position) {
            convert(holder, getList().get(position - 1), position);
        } else {
            convert(holder, getList().get(position), position);
        }
    }
}
