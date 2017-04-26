package wildbakery.ufu.Presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Model.Models.QueryModel;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.Models.NewsItem;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Presentation.views.NewsViews;
import wildbakery.ufu.Services.NewsService;

/**
 * Created by Tatiana on 24/04/2017.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsViews> {

    NewsModel model;
    private Call<QueryModel<NewsItem>> call;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        tryGetNews();
    }

    public void tryGetNews(){
        // TODO ask service to load data into model, waiting for callback, refresh view
        NewsService service = new NewsService();
        call = VuzAPI.Factory.getInstance().getNews();
        call.enqueue(new Callback<QueryModel<NewsItem>>() {
            @Override
            public void onResponse(Call<QueryModel<NewsItem>> call, Response<QueryModel<NewsItem>> response) {
                getViewState().hideProgressDialog();
                getViewState().showNews(response.body().getItems());
            }

            @Override
            public void onFailure(Call<QueryModel<NewsItem>> call, Throwable t) {
                getViewState().hideProgressDialog();
                getViewState().showToastMessage("Failed to get news");
            }
        });

    }

    public void showDetailFragment(NewsItem item){
        getViewState().showDetail(item);
    }
}
