package mario.ochoa.bbva.Activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import mario.ochoa.bbva.Fragments.InicioFragment;
import mario.ochoa.bbva.R;

public class DrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        if (savedInstanceState == null){
            setDrawerLayout();
            setFragmentByDefault();
        }
    }


    @Override public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Funcionalidad del DrawerLayout
    private void setDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.inicio:
                        fragment = new InicioFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.opcion_2:
                        fragment = new InicioFragment();
                        fragmentTransaction = true;
                        break;

                }

                if (fragmentTransaction){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,fragment)
                            .commit();
                    item.setChecked(true);
                    //getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawers();

                }
                return true;
            }
        });
    }

    //éste método sirve para definir el primer fragment a mostrar
    private void setFragmentByDefault(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new InicioFragment())

                .commit();
        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
    }



    //Éste método le da funcionalidad al ícono de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                //Abrir menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
