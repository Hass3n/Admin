package madrsty.demo.adminmad.Admin.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import madrsty.demo.adminmad.Common.common;
import madrsty.demo.adminmad.EventBus.PassMassageClick;
import madrsty.demo.adminmad.Model.StudentModel;
import madrsty.demo.adminmad.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel sendViewModel;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;
    private Unbinder unbinder;
    @BindView(R.id.profileimage_admin)
    CircleImageView profileImg;

    @BindView(R.id.profile_year_admin)
    TextView s_year;

    @BindView(R.id.profile_name_admin)
    TextView s_name;

    @BindView(R.id.profile_phone_admin)
    TextView s_point;

    @BindView(R.id.profile_btn_log_out_admin)
    Button btn_log_out;

    View root;

    @OnClick(R.id.profile_btn_log_out_admin)
    void logOutSelected() {
        signOut();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        initFirebase();
       initView();

        return root;
    }

    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();

    }

    private void initView() {

        unbinder = ButterKnife.bind(this, root);

        request.child(common.ADMIN_KEY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(getuID()).exists()) {
                    StudentModel studentModel = dataSnapshot.child(getuID()).getValue(StudentModel.class);
                    common.currentStudent = studentModel;

                    Glide.with(getContext()).load(common.currentStudent.getS_image()).into(profileImg);

                    s_name.setText(new StringBuilder(common.currentStudent.getF_name()));
                    s_year.setText(new StringBuilder(common.currentStudent.getS_year()));
                    s_point.setText(new StringBuilder(String.valueOf(common.currentStudent.getPhone())));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }

    private void signOut() {


        new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("تسجيل الخروج")
                .setContentText("هل تود تسجيل الخروج؟")
                .setCustomImage(R.drawable.ic_exit_to_app_black_24dp)
                .setConfirmText("نعم")
                .setConfirmClickListener(sDialog -> {

                    EventBus.getDefault().postSticky(new PassMassageClick("SignOut"));
                    sDialog.dismiss();
                }).setCancelText("لا")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                })
                .show();


    }
}