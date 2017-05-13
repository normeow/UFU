package wildbakery.ufu.ui.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import wildbakery.ufu.R;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class ProgressBarViewHolder extends RecyclerView.ViewHolder{
    ProgressBar progressBar;
    public ProgressBarViewHolder(View view){
        super(view);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
    }
}
