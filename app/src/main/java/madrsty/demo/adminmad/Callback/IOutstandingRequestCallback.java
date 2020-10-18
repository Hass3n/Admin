package madrsty.demo.adminmad.Callback;




import java.util.List;

import madrsty.demo.adminmad.Model.RequestModel;

public interface  IOutstandingRequestCallback {

    void onOutStandingRequestLoadSuccess(List<RequestModel> outstanding_reuquest_models);

    void onOutStandingRequestLoadFailed(String massege);
}
