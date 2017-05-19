package wildbakery.ufu.Presentation.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.EventItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public interface EventsView extends MvpView {
    void showEvents(List<EventItem> events);
    void showDetail(EventItem eventItem);
    void showProgressBar();
    void hideProgressBar();
    void showToastMessage(String msg);
    void appendRecycleView(List<EventItem> items);
    void showLoadingBatchError();
    void showOnRefreshError();
    void showNoDataMessage();
    void hideNoDataMessage();
    void showGettingDataMessage();
    void hideGettingDataMessage();
    void showBottomProgressBar();
    void hideBottomProgressBar();
    void refresh();
}
