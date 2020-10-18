package madrsty.demo.adminmad.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import madrsty.demo.adminmad.Callback.IRecyclerClickListener;
import madrsty.demo.adminmad.Common.common;
import madrsty.demo.adminmad.EventBus.RequestItemClick;
import madrsty.demo.adminmad.Model.RequestModel;
import madrsty.demo.adminmad.R;

public class OutStanding_request_adapter extends RecyclerView.Adapter<OutStanding_request_adapter.MyViewHolder> {

    private Context context;
    private List<RequestModel> outstanding_reuquest_models;


    public OutStanding_request_adapter(Context context, List<RequestModel> outstanding_reuquest_models) {
        this.context = context;
        this.outstanding_reuquest_models = outstanding_reuquest_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.outstanding_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(outstanding_reuquest_models.get(position).getS_image())
                .into(holder.outstanding_student_img);

        holder.outstanding_student_name.setText(new StringBuilder(outstanding_reuquest_models.get(position).getF_name()));

        holder.outstanding_student_year.setText(new StringBuilder(outstanding_reuquest_models.get(position).getS_year()));

        holder.view_profile_detial_btn.setOnClickListener(view -> {
            common.selectedRequest = outstanding_reuquest_models.get(position);

            EventBus.getDefault().postSticky(new RequestItemClick(true, outstanding_reuquest_models.get(position)));

        });
        holder.setListener((view, pos) -> {
            common.selectedRequest = outstanding_reuquest_models.get(pos);

            EventBus.getDefault().postSticky(new RequestItemClick(true, outstanding_reuquest_models.get(pos)));

        });

    }

    @Override
    public int getItemCount() {
        return outstanding_reuquest_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Unbinder unbinder;


        @BindView(R.id.outstanding_student_name)
        TextView outstanding_student_name;

        @BindView(R.id.outstanding_student_year)
        TextView outstanding_student_year;


        @BindView(R.id.outstanding_student_img)
        CircleImageView outstanding_student_img;

        @BindView(R.id.view_profile_detial_btn)
        Button view_profile_detial_btn;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v, getAdapterPosition());

        }
    }

}
