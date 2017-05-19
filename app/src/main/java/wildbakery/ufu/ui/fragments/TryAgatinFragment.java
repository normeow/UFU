package wildbakery.ufu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wildbakery.ufu.R;

/**
 * Created by Tatiana on 19.05.2017.
 */

public class TryAgatinFragment extends Fragment{

    public interface TryAgainListener{
        void onTryAgainClicked();
    }

    private TryAgainListener listener;

    private Button tryAgainBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.failed_msg_fragment, container, false);
        tryAgainBtn = (Button)view.findViewById(R.id.btn_try_again);
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgainClicked();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        listener = (TryAgainListener)getParentFragment();
        super.onAttach(context);
    }
}
