package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Search;
import linhao.redridinghood.model.entity.WeekUpdate;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/9/4.
 */
public class WeekUpdateAdapter extends CommentAdapter<List<Search.ListItem>> {

    private Context context;
    private Map<String, List<List<Search.ListItem>>> searchMap;
    private List<List<Search.ListItem>> listList;
    private ItemOnClick itemOnClick;
    private int Week_Data_Type = 0;
    private List<WeekUpdate> updateList;
    private List<List<Search.ListItem>> dataList;


    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick=itemOnClick;
    }

    public void setListList(List<Search.ListItem> listList) {
        dataList.add(listList);
    }

    public List<List<Search.ListItem>> getListList() {
        return this.dataList;
    }

    public void initList(){
        dataList=null;
        dataList = new ArrayList<>();
    }



    public void setUpdateList(List<WeekUpdate> updateList) {
        this.updateList = updateList;
    }

    public List<WeekUpdate> getUpdateList() {
        return this.updateList;
    }

    public WeekUpdateAdapter(Context context) {
        super(context);
        this.context = context;
        dataList = new ArrayList<>();
        searchMap = new HashMap<>();
        listList=new ArrayList<>();
    }

        public void setWeek_Data_Type(int Week_Data_Type) {
            List<List<Search.ListItem>>listList=null;
            switch (Week_Data_Type){
                case 0:
                    listList= searchMap.get("一");
                    break;
                case 1:
                    listList= searchMap.get("二");
                    break;
                case 2:
                    listList= searchMap.get("三");
                    break;
                case 3:
                    listList= searchMap.get("四");
                    break;
                case 4:
                    listList= searchMap.get("五");
                    break;
                case 5:
                    listList= searchMap.get("六");
                    break;
                case 6:
                    listList= searchMap.get("日");
                    break;
            }
            super.settList(listList);
    }


    public void setSearchMap(String day, List<List<Search.ListItem>> listDatas) {
        if (searchMap != null) {
            searchMap.put(day, listDatas);
        }
    }

    public Map<String, List<List<Search.ListItem>>> getSearchMap() {
        return searchMap;
    }

    @Override
    public int getLayoutId() {
        return R.layout.week_update_item;
    }

    @Override
    public void convert(CommentViewHolder viewHolder, List<Search.ListItem> lists, int position) {
        if (lists!=null) {
            Search.ListItem listData = lists.get(0);
            viewHolder.setTextView(R.id.content_title, listData.getName());
            viewHolder.setTextView(R.id.content_collection, listData.getLastest());
            viewHolder.setTextView(R.id.content_download, listData.getDownload());
            viewHolder.setTextView(R.id.content_type, listData.getType());
            viewHolder.setTextView(R.id.content_zone, listData.getLocal());
            CircleImageView circleImageView = (CircleImageView) viewHolder.getView(R.id.content_img);
            GlideUtil.LoadImg(context, circleImageView, listData.getImg());
            viewHolder.itemView.setTag(listData.getUrl());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 itemOnClick.onClick(v,(String)v.getTag());
                }
            });
        }
    }
}
