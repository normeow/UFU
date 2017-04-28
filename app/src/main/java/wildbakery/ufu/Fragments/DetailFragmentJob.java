package wildbakery.ufu.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.R;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */

public class DetailFragmentJob extends Fragment {

    private static String ARG_ITEM;
    private JobItem item;

    private TextView tvDetailName,tvDetailWage,tvDetailDescription;
    public DetailFragmentJob(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (JobItem) getArguments().getSerializable("item");
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        tvDetailWage = (TextView) view.findViewById(R.id.tvWageJobDetail);
        tvDetailName = (TextView) view.findViewById(R.id.tvNameJobDetail);
        tvDetailDescription = (TextView) view.findViewById(R.id.tvDetailDescriptionJob);

        tvDetailName.setText(item.getName());
        if(item.getWage().equals("0")) {
            tvDetailWage.setText("Не указана");
        }else {
            tvDetailWage.setText(item.getWage());
        }
        tvDetailDescription.setText(Html.fromHtml(item.getDescription()));

        return view;
    }

    public static DetailFragmentJob newInstance(JobItem item) {
        Bundle args = new Bundle();
        ARG_ITEM = "item";
        args.putSerializable(ARG_ITEM, item);
        DetailFragmentJob fragment = new DetailFragmentJob();
        fragment.setArguments(args);
        return fragment;
    }

}