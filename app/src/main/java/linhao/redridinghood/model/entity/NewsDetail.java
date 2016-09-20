package linhao.redridinghood.model.entity;

import java.io.Serializable;

import javax.sql.StatementEvent;

/**
 * Created by linhao on 2016/8/13.
 */
public class NewsDetail implements Serializable {

    /**
     *  "title": "红旅公众号今天开始更新动漫资讯啦",
     "time": "2016-07-06 10:47:46 ",
     "content": "\r\n
     */

    private String title;
    private String time;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
