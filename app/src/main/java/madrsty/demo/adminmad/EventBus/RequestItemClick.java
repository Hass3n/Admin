package madrsty.demo.adminmad.EventBus;


import madrsty.demo.adminmad.Model.RequestModel;

public class RequestItemClick {

    private boolean success;
    private RequestModel requestModel;

    public RequestItemClick(boolean success, RequestModel requestModel) {
        this.success = success;
        this.requestModel = requestModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RequestModel getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
    }
}
