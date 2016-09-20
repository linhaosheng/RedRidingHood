package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 * 热门新番
 */
public class HotNewAnimate implements Serializable {

    @SerializedName("tab")
    private String tab;
    @SerializedName("child")
    private List<HotNewChild> child;

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public List<HotNewChild> getChild() {
        return child;
    }

    public void setChild(List<HotNewChild> child) {
        this.child = child;
    }

    public class HotNewChild {

        private String tab;
        @SerializedName("list")
        private List<NewItemContent> newItemContents;
        @SerializedName("otherList")
        private List<NewItemOtherContent> newItemOtherContents;

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public List<NewItemContent> getNewItemContents() {
            return newItemContents;
        }

        public void setNewItemContents(List<NewItemContent> newItemContents) {
            this.newItemContents = newItemContents;
        }

        public List<NewItemOtherContent> getNewItemOtherContents() {
            return newItemOtherContents;
        }

        public void setNewItemOtherContents(List<NewItemOtherContent> newItemOtherContents) {
            this.newItemOtherContents = newItemOtherContents;
        }
    }

    public class NewItemContentList {
        private List<NewItemContent> newItemContentList;

        public List<NewItemContent> getNewItemContentList() {
            return newItemContentList;
        }

        public void setNewItemContentList(List<NewItemContent> newItemContentList) {
            this.newItemContentList = newItemContentList;
        }
    }

    public class NewItemContent {
        private String url;
        private String img;
        private String episode;
        private String name;

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

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class NewItemOtherContentList {
        private List<NewItemOtherContent> newItemOtherContentList;

        public List<NewItemOtherContent> getNewItemOtherContentList() {
            return newItemOtherContentList;
        }

        public void setNewItemOtherContentList(List<NewItemOtherContent> newItemOtherContentList) {
            this.newItemOtherContentList = newItemOtherContentList;
        }
    }

    public class NewItemOtherContent {
        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
