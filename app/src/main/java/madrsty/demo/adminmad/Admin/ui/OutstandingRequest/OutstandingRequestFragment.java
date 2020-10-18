package madrsty.demo.adminmad.Admin.ui.OutstandingRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import madrsty.demo.adminmad.Adapter.OutStanding_request_adapter;
import madrsty.demo.adminmad.R;


public class OutstandingRequestFragment extends Fragment {

    private OutstandingRequestViewModel outstandingRequestViewModel;

    private OutStanding_request_adapter adapter;
    LayoutAnimationController layoutAnimationController;


    Unbinder unbinder;

    @BindView(R.id.outstanding_recycler)
    RecyclerView outstanding_recycler;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        outstandingRequestViewModel =
                ViewModelProviders.of(this).get(OutstandingRequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_outstanding_request, container, false);

        unbinder = ButterKnife.bind(this, root);

        initView();
        outstandingRequestViewModel.getMassageErorr().observe(getViewLifecycleOwner(), s -> {

            Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();

        });

        outstandingRequestViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), catagoryModels -> {
            adapter = new OutStanding_request_adapter(getContext(), catagoryModels);
            outstanding_recycler.setAdapter(adapter);
            outstanding_recycler.setLayoutAnimation(layoutAnimationController);

        });


        return root;
    }

    private void initView() {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("الطلبات");

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);

        outstanding_recycler.setHasFixedSize(true);
        outstanding_recycler.setLayoutManager(new LinearLayoutManager(getContext()));


    }
}