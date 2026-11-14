package com.example.tp6_grupo_18;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormAgregarContacto extends AppCompatActivity {

    EditText nombre;
    EditText apellido;
    EditText telefono;
    EditText email;
    EditText direccion;
    EditText fechaNacimiento;
    Button btnContinuar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_agregar_contacto);

        nombre = findViewById(R.id.etNombre);



        apellido = findViewById(R.id.etApellido);
        telefono = findViewById(R.id.etTelefono);
        email = findViewById(R.id.etEmail);
        direccion = findViewById(R.id.etDireccion);
        fechaNacimiento = findViewById(R.id.etFechaNacimiento);
        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(v -> {
            validarFormulario();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_agregar) {
            return true;
        } else if (id == R.id.menu_listado) {
            Intent intent = new Intent(FormAgregarContacto.this, FormListaContacto.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void validarFormulario() {

        // Recuperamos los valores ingresados
        String nombreIngresado = nombre.getText().toString().trim();
        String apellidoIngresado = apellido.getText().toString().trim();
        String telefonoIngresado = telefono.getText().toString().trim();
        String emailIngresado = email.getText().toString().trim();
        String direccionIngresada = direccion.getText().toString().trim();
        String fechaIngresada = fechaNacimiento.getText().toString().trim();

        nombre.setError(null);
        apellido.setError(null);
        telefono.setError(null);
        email.setError(null);
        direccion.setError(null);
        fechaNacimiento.setError(null);


        if (nombreIngresado.isEmpty()) {
            nombre.setError("El nombre es obligatorio");
            nombre.requestFocus();
            return;
        }

        if (!nombreIngresado.matches("[\\p{L}]+(?:[ '\\-][\\p{L}]+)*")) {
            nombre.setError("El nombre no puede contener números");
            nombre.requestFocus();
            return;
        }


        if (apellidoIngresado.isEmpty()) {
            apellido.setError("El apellido es obligatorio");
            apellido.requestFocus();
            return;
        }

        if (!apellidoIngresado.matches("[\\p{L}]+(?:[ '\\-][\\p{L}]+)*")) {
            apellido.setError("El apellido no puede contener números");
            apellido.requestFocus();
            return;
        }


        if (telefonoIngresado.isEmpty()) {
            telefono.setError("El teléfono es obligatorio");
            telefono.requestFocus();
            return;
        }

        if (!telefonoIngresado.matches("[0-9]+(?:-[0-9]+)*")) {
            telefono.setError("Ingrese solamente números o guiones");
            telefono.requestFocus();
            return;
        }


        if (emailIngresado.isEmpty()) {
            email.setError("El email es obligatorio");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailIngresado).matches()) {
            email.setError("Ingrese un email válido");
            email.requestFocus();
            return;
        }


        if (direccionIngresada.isEmpty()) {
            direccion.setError("La dirección es obligatoria");
            direccion.requestFocus();
            return;
        }


        if (fechaIngresada.isEmpty()) {
            fechaNacimiento.setError("La fecha es obligatoria");
            fechaNacimiento.requestFocus();
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        formato.setLenient(false);

        ParsePosition posicion = new ParsePosition(0);
        Date fecha = formato.parse(fechaIngresada, posicion);

        if (fecha == null || posicion.getIndex() != fechaIngresada.length()) {
            fechaNacimiento.setError("Ingrese una fecha válida (dd/MM/yyyy)");
            fechaNacimiento.requestFocus();
            return;
        }

       
        Toast.makeText(this, "Formulario válido", Toast.LENGTH_SHORT).show();
    }
}