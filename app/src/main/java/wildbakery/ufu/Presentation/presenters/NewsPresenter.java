package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.DataFetchers.NewsFetcher;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.DAO.NewsDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Presentation.views.NewsViews;

/**
 * Created by Tatiana on 24/04/2017.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsViews> implements NewsFetcher.CallbacksListener {

    private NewsModel model;
    private Call<QueryModel<NewsItem>> call;
    private boolean isLoading = false;

    private NewsFetcher newsFetcher;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        newsFetcher = new NewsFetcher(this);
        model = NewsModel.getInstanse();
        getNewsFromDb();
        tryGetNews();
    }

    public void getNewsFromDb(){
        getViewState().showProgressDialog();
        newsFetcher.fetchDB();
    }

    public void tryGetNews(){
        getViewState().showProgressDialog();
        newsFetcher.fetchServer();
    }

    public void showDetailFragment(NewsItem item){
        getViewState().showDetail(item);

    }

    @Override
    public void onDestroy() {
        // todo cancel all tasks in newsFetcher
        super.onDestroy();
    }

    public void onError(){
        getViewState().showToastMessage("Failed to get news");
    }


    @Override
    public void onFailure() {
        // something goes wrong when tried to fetch data
        onError();
    }

    @Override
    public void onFetchDataFromServerFinished() {
        getViewState().hideProgressDialog();
        List<NewsItem> items = model.getItems();
        getViewState().showNews(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // NewsFetcher filled NewsModel with cached data
        getViewState().hideProgressDialog();
        List<NewsItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            getViewState().hideProgressDialog();
            getViewState().showNews(items);
        }
    }

    public void onScrollToTheEnd(NewsItem lastItem){
        //fetch next 20 items from server

    }
}
