package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 */
public class TopicsDetail implements Serializable {

    /**
     *   "page": 1,
     "pageSize": 21,
     "totalCount": 38,
     "totalPage": 2,
     "list": [
     "lastPage": false,
     "firstPage": true
     */

    private int page;
    private int pageSize;
    private int totalCount;

    @SerializedName("list")
    private List<DetailList>detailListList;

    private boolean lastPage;
    private boolean firstPage;

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

    public List<DetailList> getDetailListList() {
        return detailListList;
    }

    public void setDetailListList(List<DetailList> detailListList) {
        this.detailListList = detailListList;
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

    public class DetailList{
        /**
         *   "url": "http://www.hltm.tv/view/12482.html",
         "name": "ReLIFE/重返17岁",
         "lastest": "13集全",
         "img": "http://pic.hltm.tv/uploads/editor/2016/06/20160625120054851.jpg",
         "download": "99262",
         "type": "完结动漫",
         "local": "日本"
         */

        private String url;
        private String name;
        private String lastest;
        private String img;
        private String download;
        private String type;
        private String local;

        public String getLastest() {
            return lastest;
        }

        public void setLastest(String lastest) {
            this.lastest = lastest;
        }

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }
    }
}
