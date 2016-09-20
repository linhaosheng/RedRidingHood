package linhao.redridinghood.model.entity;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linhao on 2016/8/13.
 * 新增/推荐 连载/完结 动漫
 */
public class AddRecommend implements Serializable{

    @SerializedName("tab")
    private Tab tabs;


    @SerializedName("list")
    private List<RecommandData>recommandDataList;


    public Tab getTabs() {
        return tabs;
    }

    public void setTabs(Tab tabs) {
        this.tabs = tabs;
    }

    public List<RecommandData> getRecommandDataList() {
        return recommandDataList;
    }

    public void setRecommandDataList(List<RecommandData> recommandDataList) {
        this.recommandDataList = recommandDataList;
    }

    /**
     * 具体推荐数据的标题
     */
    public class Tab{
        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * 具体推荐的数据
     */
    public class RecommandData{
        private String update;
        private String name;
        private String url;
        private String img;

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
