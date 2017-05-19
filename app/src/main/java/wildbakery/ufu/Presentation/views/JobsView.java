package wildbakery.ufu.Presentation.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.JobItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public interface JobsView extends MvpView {
    void showJobs(List<JobItem> jobs);
    void showDetail(JobItem jobItem);
    void hideDetail();
    void showProgressBar();
    void hideProgressBar();
    void showToastMessage(String msg);
    void appendRecycleView(List<JobItem> items);
    void showLoadingBatchError();
    void showOnRefreshError();
    void showBottomProgressBar();
    void hideBottomProgressBar();
    void refresh();
}
