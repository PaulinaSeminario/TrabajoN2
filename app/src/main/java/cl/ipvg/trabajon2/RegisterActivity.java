package cl.ipvg.trabajon2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ImageView atras;
    EditText nombre;
    EditText correoRegister;
    EditText contraseñaRegister;
    EditText confirmarContraseña;
    FirebaseAuth mAuth;

    FirebaseFirestore mFirestore;

    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        atras = findViewById(R.id.atras);
        nombre= findViewById(R.id.nombre);
        correoRegister= findViewById(R.id.correoRegister);
        contraseñaRegister= findViewById(R.id.contraseñaRegister);
        confirmarContraseña= findViewById(R.id.confirmarContraseña);
        btnRegister= findViewById(R.id.btnRegister);
        mAuth= FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });




    }

    private void register() {
        String name = nombre.getText().toString();
        String email = correoRegister.getText().toString();
        String password = contraseñaRegister.getText().toString();
        String confirmPassword = confirmarContraseña.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()&& !confirmPassword.isEmpty()){
            if(isEmailValid(email)){
                if(password.equals(confirmPassword)) {
                    if (password.length() >= 6){
                        createUser(name,email,password);
                }
                    else{
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(RegisterActivity.this, "El correo no es valido", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(RegisterActivity.this, "Para continuar inserte todos los campos", Toast.LENGTH_SHORT).show();
        }


    }

    private void createUser(String name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);
                    mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(RegisterActivity.this, "El usuario ha sido registrado", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public boolean isEmailValid (String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}