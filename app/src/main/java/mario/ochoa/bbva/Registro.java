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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtContraseña;
    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtEdad;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtEmail = (EditText)findViewById(R.id.txtEmailregistro);
        txtContraseña = (EditText)findViewById(R.id.txtContraseñaRegistro);
        txtNombre = (EditText)findViewById(R.id.txtNombreRegistro);
        txtTelefono = (EditText)findViewById(R.id.txtTelefonoRegistro);
        firebaseAuth = FirebaseAuth.getInstance();


    }

    public void registraUsiario_click(View v){
        final String nombre = txtNombre.getText().toString().trim();
        final String telefono = txtTelefono.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();

        final ProgressDialog progressDialog = ProgressDialog.show(Registro.this, "Por favor espere...", "Progresando..", true);

        (firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(),txtContraseña.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()){
                    Toast.makeText(Registro.this, "Has sido registrado", Toast.LENGTH_LONG).show();

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Datos");
                    DatabaseReference currentUserDB = mDatabase.child(firebaseAuth.getCurrentUser().getUid());
                    currentUserDB.child("nombre").setValue(nombre);
                    currentUserDB.child("Telefono").setValue(telefono);
                    currentUserDB.child("E-mail").setValue(email);

                    Intent i = new Intent(Registro.this, Login.class);
                    startActivity(i);
                }else{
                    Log.e("Error",task.getException().toString());
                    Toast.makeText(Registro.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
