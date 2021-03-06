package wildbakery.ufu.Presentation.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */

public interface NewsView extends MvpView {
    void showNews(List<NewsItem> news);
    void showDetail(NewsItem newsItem);
    void hideDetail();
    void showProgressBar();
    void hideProgressBar();
    void showToastMessage(String msg);
    void appendRecycleView(List<NewsItem> items);
    void showLoadingBatchError();
    void showOnRefreshError();
    void showBottomProgressBar();
    void hideBottomProgressBar();
    void showNoDataMessage();
    void hideNoDataMessage();
    void showGettingDataMessage();
    void hideGettingDataMessage();
    void refresh();
}
