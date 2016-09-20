package linhao.redridinghood.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 * 一周更新(节目单)
 */
public class WeekUpdate implements Serializable {

    @SerializedName("week")
    String weekDay;

    @SerializedName("list")
    private List<WeekListItem> weekLists;

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public List<WeekListItem> getWeekLists() {
        return weekLists;
    }

    public void setWeekLists(List<WeekListItem> weekLists) {
        this.weekLists = weekLists;
    }

    /**
     * 推荐的具体数据
     */
    public class WeekListItem {
        private String updateDate;
        private String name;
        private String url;

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
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
