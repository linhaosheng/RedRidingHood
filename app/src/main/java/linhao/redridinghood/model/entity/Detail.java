package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 * 动漫详情
 */
public class Detail implements Serializable {
    /**
     *   "name": "线上游戏的老婆不可能是女生？",
     "img": "http://pic.hltm.tv/uploads/editor/2016/03/20160331021653954.jpg",
     "intro": "\r\n
     */
    private String name;
    private String img;
    private String intro;

    @SerializedName("opts")
    private List<Options>optionsList;

    @SerializedName("download")
    private List<Download> downloadList;

    @SerializedName("recommend")
    private List<Recommand>recommandList;

    @SerializedName("editRecommend")
    private List<EditRecommend>editRecommendList;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Options> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Options> optionsList) {
        this.optionsList = optionsList;
    }

    public List<Download> getDownloadList() {
        return downloadList;
    }

    public void setDownloadList(List<Download> downloadList) {
        this.downloadList = downloadList;
    }

    public List<Recommand> getRecommandList() {
        return recommandList;
    }

    public void setRecommandList(List<Recommand> recommandList) {
        this.recommandList = recommandList;
    }

    public List<EditRecommend> getEditRecommendList() {
        return editRecommendList;
    }

    public void setEditRecommendList(List<EditRecommend> editRecommendList) {
        this.editRecommendList = editRecommendList;
    }

    public class Options{
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class Download{

        //下载类型
        @SerializedName("tab")
        private String tab;


        @SerializedName("list")
        private List<DownloadData>downloadDataList;

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public List<DownloadData> getDownloadDataList() {
            return downloadDataList;
        }

        public void setDownloadDataList(List<DownloadData> downloadDataList) {
            this.downloadDataList = downloadDataList;
        }
    }


    /**
     * 下载列表
     */
    public class DownloadData{
        private String name;
        private String url;

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
    }

    /**
     * 推荐下载
     */
    public class Recommand{

    }

    /**
     * 编辑下载
     */
    public class EditRecommend{
        /**
         *   "sort": "1",
         "count": "87550",
         "name": "田中君总是如此慵懒",
         "url": "http://www.hltm.tv/view/12404.html"
         */

        private String sort;
        private String count;
        private String name;
        private String url;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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
    }
}
