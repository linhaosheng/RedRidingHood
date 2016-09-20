package linhao.redridinghood.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhao on 2016/8/13.
 * 最新更新
 */
public class RecentUpdate implements Serializable {

        private String time;
        private String sort;
        private String type;
        private String name;
        private String url;
        private String episode;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }
    }

