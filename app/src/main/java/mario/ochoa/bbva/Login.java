package mario.ochoa.bbva;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText txtEmailLogin;
    private EditText txtContraLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmailLogin = (EditText)findViewById(R.id.txtEmailLogin);
        txtContraLogin = (EditText)findViewById(R.id.txtContrasLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void btnUserLogin_click(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "Espere....", "Progresando informacion", true);
        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtContraLogin.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Login.this, MenuP.class);
                    i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                    startActivity(i);
                }else {
                    Log.e("Error", task.getException().toString());
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
