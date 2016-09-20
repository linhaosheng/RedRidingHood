package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 */
public class News implements Serializable {
    /**
     *   "page": 1,
     "pageSize": 15,
     "totalCount": 211,
     "totalPage": 15,
     list
     "lastPage": false,
     "firstPage": true,
     "ranking": []
     */
    private int page;
    private int pageSize;
    private int totalCount;
    private int totalPage;

    @SerializedName("list")
    private List<NewList>newListList;

    private boolean lastPage;
    private boolean firstPage;

    @SerializedName("ranking")
    private List<Ranking>rankingList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<NewList> getNewListList() {
        return newListList;
    }

    public void setNewListList(List<NewList> newListList) {
        this.newListList = newListList;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public List<Ranking> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<Ranking> rankingList) {
        this.rankingList = rankingList;
    }

    /**
     *  "url": "http://www.hltm.tv/new/286.html",
     "img": "http://zxpic.hltm.tv/zxpic/160812/vpkx0hnjvd0.jpg",
     "title": "「噬血狂袭」第2期OVA系列启动 声优阵容不变！",
     "description": "「噬血狂袭」第2期OVA系列开始启动，声优阵容不变。第2期OVA系列将以「噬血狂袭」轻小说第九卷「黑剑巫」为背景，讲诉晓古城和姬柊雪菜之间的...",
     "time": "2016-08-12"
     */

   public class NewList{
        private String url;
        private String img;
        private String title;
        private String description;
        private String time;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    class  Ranking{

    }
}
