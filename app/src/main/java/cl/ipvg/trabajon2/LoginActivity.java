package cl.ipvg.trabajon2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText correoLogin;
    EditText contraseñaLogin;
    Button buttonLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        correoLogin = findViewById(R.id.correoLogin);
        contraseñaLogin = findViewById(R.id.contraseñaLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String emailLogin = correoLogin.getText().toString();
        String password = contraseñaLogin.getText().toString();
        mAuth.signInWithEmailAndPassword(emailLogin, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(LoginActivity.this, InicioActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(LoginActivity.this, "El correo o la contraseña son incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}