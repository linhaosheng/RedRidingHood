package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 * 连载动漫/完结动漫
 */
public class ComicList implements Serializable {

    /**
     *    "page": 1,
     "pageSize": 20,
     "totalCount": 79,
     "totalPage": 4,
     "list": [
     "lastPage": false,
     "firstPage": true,
     "ranking": [
     */

    private int page;
    private int pageSize;
    private int totalCount;
    private int totalPage;

    @SerializedName("list")
    private List<ListData>listDataList;

    private boolean lastPage;
    private boolean firstPage;

    @SerializedName("ranking")
    private List<RankingData>rankingDataList;

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

    public List<ListData> getListDataList() {
        return listDataList;
    }

    public void setListDataList(List<ListData> listDataList) {
        this.listDataList = listDataList;
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

    public List<RankingData> getRankingDataList() {
        return rankingDataList;
    }

    public void setRankingDataList(List<RankingData> rankingDataList) {
        this.rankingDataList = rankingDataList;
    }

    public class RankingData{
        @SerializedName("tab")
        private String tab;

        @SerializedName("list")
        private List<RankingList>rankingListList;

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public List<RankingList> getRankingListList() {
            return rankingListList;
        }

        public void setRankingListList(List<RankingList> rankingListList) {
            this.rankingListList = rankingListList;
        }
    }


    /**
     * 排行列表
     */
    public class RankingList{
        /**
         *  "name": "海贼王全集",
         "url": "http://www.hltm.tv/view/972.html",
         "count": "3134845",
         "sort": "1"
         */

        private String name;
        private String url;
        private String count;
        private String sort;

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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
    public class ListData{
        /**
         *  "url": "http://www.hltm.tv/view/12327.html",
         "img": "http://pic.hltm.tv/uploads/editor/2015/11/20151101094858509.jpg",
         "name": "宠物小精灵XY&Z/宠物小精灵XY第二季",
         "updateTime": "08-12 更新 ",
         "downloadCount": "40291",
         "episode": "连载至37集"
         */

        private String url;
        private String img;
        private String name;
        private String updateTime;
        private String downloadCount;
        private String episode;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(String downloadCount) {
            this.downloadCount = downloadCount;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }
    }
}
