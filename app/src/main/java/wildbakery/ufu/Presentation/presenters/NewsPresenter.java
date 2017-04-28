package wildbakery.ufu.Presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Presentation.views.NewsViews;

/**
 * Created by Tatiana on 24/04/2017.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsViews> {

    private NewsModel model;
    private Call<QueryModel<NewsItem>> call;
    private boolean isLoading = false;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        tryGetNews();
    }

    public void tryGetNews(){
        getViewState().showProgressDialog();
        isLoading = true;
        // TODO ask service to load data into model, waiting for callback, refresh view

        call = VuzAPI.Factory.getInstance().getNews();
        call.enqueue(new Callback<QueryModel<NewsItem>>() {
            @Override
            public void onResponse(Call<QueryModel<NewsItem>> call, Response<QueryModel<NewsItem>> response) {
                getViewState().hideProgressDialog();
                getViewState().showNews(response.body().getItems());
                isLoading = false;
            }

            @Override
            public void onFailure(Call<QueryModel<NewsItem>> call, Throwable t) {
                getViewState().hideProgressDialog();
                isLoading = false;
                onError();

            }
        });

    }

    public void showDetailFragment(NewsItem item){
        getViewState().showDetail(item);

    }

    public void onError(){
        getViewState().showToastMessage("Failed to get news");
    }
}
