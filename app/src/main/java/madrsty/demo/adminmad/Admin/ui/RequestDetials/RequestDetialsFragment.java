package madrsty.demo.adminmad.Admin.ui.RequestDetials;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import madrsty.demo.adminmad.Common.common;
import madrsty.demo.adminmad.EventBus.PassMassageClick;
import madrsty.demo.adminmad.Model.RequestModel;
import madrsty.demo.adminmad.Model.StudentModel;
import madrsty.demo.adminmad.R;


public class RequestDetialsFragment extends Fragment {

    private RequestDetialsViewModel requestDetialsViewModel;

    private Unbinder unbinder;
    private android.app.AlertDialog waitingDialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;
    private StorageReference StorageRef;

    @BindView(R.id.student_outstanding_request_image)
    CircleImageView student_outstanding_request_image;

    @BindView(R.id.accept_request_fab)
    FloatingActionButton accept_request_fab;

    @BindView(R.id.refuse_request_fab)
    FloatingActionButton refuse_request_fab;

    @BindView(R.id.student_request_first_name)
    TextView student_request_first_name;

    @BindView(R.id.student_request_last_name)
    TextView student_request_last_name;


    @BindView(R.id.student_request_year)
    TextView student_request_year;


    @BindView(R.id.student_request_mail)
    TextView student_request_mail;

    @BindView(R.id.student_request_phone)
    TextView student_request_phone;


    @OnClick(R.id.accept_request_fab)
    void onAccaptButtonClick() {

        //  AcceptRequest(common.selectedStudent);
        // common.selectedStudent = null;
        //  RequestModel requestModel = new RequestModel();

        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("قبول الطلب")
                .setContentText("هل تود بالفعل قبول الطلب ؟")
                .setConfirmText("قبول")
                .setConfirmClickListener(sDialog -> {
                    createUser(common.selectedRequest.getMail(),
                            common.selectedRequest.getPassword(),
                            common.selectedRequest.getF_name(),
                            common.selectedRequest.getL_name(),
                            common.selectedRequest.getS_year(),
                            common.selectedRequest.getPhone(),
                            common.selectedRequest.getS_image());

                    RefuseRequest(common.selectedRequest);

                    EventBus.getDefault().postSticky(new PassMassageClick("QuitRequestDetial"));
                    sDialog.dismiss();
                }).setCancelText("اغلاق")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                })
                .show();



    }

    @OnClick(R.id.refuse_request_fab)
    void onRefuseButtonClick() {

        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("رفض الطلب")
                .setContentText("هل تود بالفعل رفض الطلب ؟")
                .setConfirmText("رفض")
                .setConfirmClickListener(sDialog -> {
                    RefuseRequest(common.selectedRequest);


                    EventBus.getDefault().postSticky(new PassMassageClick("QuitRequestDetial"));
                    sDialog.dismiss();
                }).setCancelText("اغلاق")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                })
                .show();

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestDetialsViewModel =
                ViewModelProviders.of(this).get(RequestDetialsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_requestdetials, container, false);

        unbinder = ButterKnife.bind(this, root);

        initViews();
        initFirebase();
        requestDetialsViewModel.getRequestModelMutableLiveData().observe(getViewLifecycleOwner(), requestModel -> {

            displayInfo(requestModel);
        });

        return root;
    }

    private void initViews() {
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();


    }

    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();
        StorageRef = FirebaseStorage.getInstance().getReference();

    }

    private void displayInfo(RequestModel requestModel) {

        Glide.with(getContext()).load(requestModel.getS_image()).into(student_outstanding_request_image);

        student_request_first_name.setText(new StringBuilder(requestModel.getF_name()));

        student_request_last_name.setText(new StringBuilder(requestModel.getL_name()));

        student_request_mail.setText(new StringBuilder(requestModel.getMail()));

        student_request_phone.setText(new StringBuilder(requestModel.getPhone()));

        student_request_year.setText(new StringBuilder(requestModel.getS_year()));


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" طلب " + common.selectedRequest.getF_name());


    }

    private void createUser(final String Email, final String Password, String Fname, String Lname, String Year, String Phone, String image) {
        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        saveTodb(Email, Password, Fname, Lname, Year, Phone, id, image);
                    } else {
                        waitingDialog.dismiss();
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveTodb(String email, String password, String fname, String lname, String year, String phone, String id, String image) {

        StudentModel studentModel = new StudentModel(fname, lname, year, email, password, phone, image, id,0);
        request.child(common.STUDENT_KEY).child(id).setValue(studentModel).addOnCompleteListener(task -> {

            Toast.makeText(getContext(), "تم قبول الطلب بنجاح :)", Toast.LENGTH_SHORT).show();



        }).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());

        waitingDialog.dismiss();

    }

    private void RefuseRequest(RequestModel requestModel) {
        waitingDialog.show();
        FirebaseDatabase.getInstance()
                .getReference(common.STUDENT_REQUESTS_KEY)
                .child(common.selectedRequest.getR_id())
                .removeValue();
        waitingDialog.dismiss();

        // EventBus.getDefault().postSticky(new ButtonClick("Refuse Clicked"));

    }
}