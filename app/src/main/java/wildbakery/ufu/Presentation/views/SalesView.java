package wildbakery.ufu.Presentation.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.SaleItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public interface SalesView extends MvpView {
    void showSales(List<SaleItem> sales);
    void showDetail(SaleItem saleItem);
    void showProgressBar();
    void hideProgressBar();
    void showToastMessage(String msg);
    void appendRecycleView(List<SaleItem> items);
    void showLoadingBatchError();
    void showBottomProgressBar();
    void hideBottomProgressBar();
}
