package linhao.redridinghood.presenter.contract;

/**
 * Created by linhao on 2016/9/11.
 */
public interface ComicListData {
    void getData(int edition,String letter,int sort,int tab,int page);

    void getLoadMoreData(int edition,String letter,int sort,int tab,int page);
}
