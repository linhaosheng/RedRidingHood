package linhao.redridinghood.presenter.contract;

import linhao.redridinghood.model.entity.WeekUpdate;

/**
 * Created by linhao on 2016/9/4.
 */
public interface WeekUpdateData {
    void getWeekUpdateData();
    void getWeekUpdataItem(String key, String url, int pageNum, WeekUpdate weekUpdate, int position);
}
