package madrsty.demo.adminmad.Admin.ui.RequestDetials;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import madrsty.demo.adminmad.Common.common;
import madrsty.demo.adminmad.Model.RequestModel;


public class RequestDetialsViewModel extends ViewModel {

    private MutableLiveData<RequestModel> requestModelMutableLiveData;

    public RequestDetialsViewModel() {

    }

    public MutableLiveData<RequestModel> getRequestModelMutableLiveData() {

        if (requestModelMutableLiveData == null)

            requestModelMutableLiveData = new MutableLiveData<>();

        requestModelMutableLiveData.setValue(common.selectedRequest);
        return requestModelMutableLiveData;
    }
}