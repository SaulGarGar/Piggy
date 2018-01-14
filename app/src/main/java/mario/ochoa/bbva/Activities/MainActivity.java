package mario.ochoa.bbva.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import mario.ochoa.bbva.Fragments.InicioFragment;
import mario.ochoa.bbva.Login;
import mario.ochoa.bbva.R;
import mario.ochoa.bbva.Registro;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void btnRegistrar_click(View v){
        Intent i = new Intent(MainActivity.this, Registro.class);
        startActivity(i);

    }
    public void btnLogin_click(View v){
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }



}
