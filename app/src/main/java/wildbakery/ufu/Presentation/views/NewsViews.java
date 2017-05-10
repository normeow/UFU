package wildbakery.ufu.Presentation.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */

public interface NewsViews extends MvpView {
    void showNews(List<NewsItem> news);
    void showDetail(NewsItem newsItem);
    void showProgressDialog();
    void hideProgressDialog();
    void showToastMessage(String msg);
    void appendRecycleView(List<NewsItem> items);
}
