package madrsty.demo.adminmad.Admin.ui.OutstandingRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import madrsty.demo.adminmad.Callback.IOutstandingRequestCallback;
import madrsty.demo.adminmad.Common.common;
import madrsty.demo.adminmad.Model.RequestModel;

public class OutstandingRequestViewModel extends ViewModel implements IOutstandingRequestCallback {

    private MutableLiveData<List<RequestModel>> listMutableLiveData;

    private MutableLiveData<String> massageErorr = new MutableLiveData<>();

    private IOutstandingRequestCallback iOutstandingRequestCallback;

    public OutstandingRequestViewModel() {
        iOutstandingRequestCallback = this;
    }

    public MutableLiveData<List<RequestModel>> getListMutableLiveData() {

        if (listMutableLiveData == null) {

            listMutableLiveData = new MutableLiveData<>();
            massageErorr = new MutableLiveData<>();


            loadRequests();
        }
        return listMutableLiveData;
    }

    private void loadRequests() {

        List<RequestModel> tmpList = new ArrayList<>();
        DatabaseReference OutStandingRequestRef = FirebaseDatabase.getInstance().getReference(common.STUDENT_REQUESTS_KEY);
        OutStandingRequestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                    RequestModel studentModel = itemSnapShot.getValue(RequestModel.class);
                    studentModel.setR_id(itemSnapShot.getKey());
                    tmpList.add(studentModel);


                }

                iOutstandingRequestCallback.onOutStandingRequestLoadSuccess(tmpList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iOutstandingRequestCallback.onOutStandingRequestLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMassageErorr() {
        return massageErorr;
    }

    @Override
    public void onOutStandingRequestLoadSuccess(List<RequestModel> outstanding_reuquest_models) {
        listMutableLiveData.setValue(outstanding_reuquest_models);
    }

    @Override
    public void onOutStandingRequestLoadFailed(String massege) {
        massageErorr.setValue(massege);

    }
}