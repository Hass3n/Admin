package madrsty.demo.adminmad.Admin;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import madrsty.demo.adminmad.Common.common;
import madrsty.demo.adminmad.EventBus.PassMassageClick;
import madrsty.demo.adminmad.EventBus.RequestItemClick;
import madrsty.demo.adminmad.LoginActivity;
import madrsty.demo.adminmad.R;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Admin2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private SmoothBottomBar smoothBottomBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        smoothBottomBar = findViewById(R.id.bottomBar_admin);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile_admin,
                R.id.nav_outstanding_request,
                R.id.nav_request_detial)
                .build();

        this.navController = Navigation.findNavController(this, R.id.main_admin_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        getSupportActionBar().hide();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bottom_admin, menu);

        smoothBottomBar.setupWithNavController(menu, navController);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_admin_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);
        //  drawer.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.nav_outstanding_request:

                navController.navigate(R.id.nav_outstanding_request);

                break;
            case R.id.nav_profile_admin:

                navController.navigate(R.id.nav_profile_admin);

                break;



        }
        return true;
    }

    @Override
    protected void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);


    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN_ORDERED)
    public void onRequestSelected(RequestItemClick event) {

        if (event.isSuccess()) {
            // Toast.makeText(this, "click to", Toast.LENGTH_SHORT).show();
            getSupportActionBar().hide();

            navController.navigate(R.id.nav_request_detial);

        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPassMassegeSelected(PassMassageClick event) {

        if (event.getMassage().equals("SignOut")) {

            common.currentStudent = null;
            common.selectedRequest = null;

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(Admin2.this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            EventBus.clearCaches();

        }
        else if (event.getMassage().equals("QuitRequestDetial")){
            common.selectedRequest = null;

            navController.navigate(R.id.nav_outstanding_request);

        }
    }

    private String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }

}
