package madrsty.demo.adminmad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import madrsty.demo.adminmad.Admin.Admin2;
import madrsty.demo.adminmad.Common.common;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;

    @BindView(R.id.login_email)
    EditText login_email;

    @BindView(R.id.login_passwod)
    EditText login_passwod;


    @BindView(R.id.Login)
    Button Login;


    private SweetAlertDialog pDialog;
    private DatabaseReference databaseReference;


    @OnClick(R.id.Login)
    void clickLoginBtn() {
        LoginUser();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initView();

        initFirebase();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
          
            startActivity(new Intent(getApplicationContext(), Admin2.class));
            finish();


        }

    }

    private void initView() {


        getSupportActionBar().hide();

    }

    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();

    }

    private void LoginUser() {

        String email = login_email.getText().toString();

        String password = login_passwod.getText().toString();

        if (email.isEmpty()) {
            login_email.setError("من فضلك , اكتب الا يميل ..!");

        } else if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            login_passwod.setError("الرقم السري من 6 الي 10 حروف");

        } else if (common.isConnectedToInternet(getBaseContext())) {

            // this method to check  if the user is admin or student by check the value in spinner

            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("جار تسجيل الدخول");
            pDialog.setCancelable(false);
            pDialog.show();


            SignInAdmin(email, password);

            pDialog.dismiss();


        } else {
            Toast.makeText(LoginActivity.this, "من فضلك تفقد الاتصال بالانترنت !", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
    private void SignInAdmin(String email, String password) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        pDialog.dismiss();

                        Intent intent = new Intent(LoginActivity.this, Admin2.class);
                        startActivity(intent);


                    } else {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }

}
