package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 * 2016年7月新番排行榜/2016年4月新番排行榜/动漫总排行榜/动漫资讯
 */
public class Ranking implements Serializable {

    private String tab;
    private String url;
    @SerializedName("list")
    private List<ListData>listDataList;

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ListData> getListDataList() {
        return listDataList;
    }

    public void setListDataList(List<ListData> listDataList) {
        this.listDataList = listDataList;
    }

    public class ListData{
        private String name;
        private String url;
        private String episode;

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

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }
    }
}
