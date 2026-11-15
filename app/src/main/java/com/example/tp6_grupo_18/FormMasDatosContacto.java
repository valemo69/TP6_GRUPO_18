package com.example.tp6_grupo_18;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormMasDatosContacto extends AppCompatActivity {

    private RadioGroup rgSexo;
    private CheckBox cbDeportes, cbMusica, cbLectura, cbTecnologia;
    private Switch swNewsletter, swNotificaciones;
    private Button btnGuardar;

    private String nombre, apellido, telefono, email, fechaNac, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mas_datos);

        rgSexo = findViewById(R.id.rgSexo);
        cbDeportes = findViewById(R.id.cbDeportes);
        cbMusica = findViewById(R.id.cbMusica);
        cbLectura = findViewById(R.id.cbLectura);
        cbTecnologia = findViewById(R.id.cbTecnologia);
        swNewsletter = findViewById(R.id.swNewsletter);
        swNotificaciones = findViewById(R.id.swNotificaciones);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Recibir datos del formulario anterior
        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        apellido = intent.getStringExtra("apellido");
        telefono = intent.getStringExtra("telefono");
        email = intent.getStringExtra("email");
        fechaNac = intent.getStringExtra("fechaNac");
        direccion = intent.getStringExtra("direccion");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });
    }

    private void guardarContacto() {
        // Validar que se haya seleccionado un sexo
        int selectedSexoId = rgSexo.getCheckedRadioButtonId();
        if (selectedSexoId == -1) {
            Toast.makeText(this, "Debe seleccionar un sexo", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rbSexo = findViewById(selectedSexoId);
        String sexo = rbSexo.getText().toString().substring(0, 1);

        // Validar que se haya seleccionado al menos un interés
        boolean tieneInteres = cbDeportes.isChecked() || cbMusica.isChecked() ||
                cbLectura.isChecked() || cbTecnologia.isChecked();
        if (!tieneInteres) {
            Toast.makeText(this, "Debe seleccionar al menos un interés", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir lista de intereses
        StringBuilder intereses = new StringBuilder();
        if (cbDeportes.isChecked()) intereses.append("Deportes,");
        if (cbMusica.isChecked()) intereses.append("Música,");
        if (cbLectura.isChecked()) intereses.append("Lectura,");
        if (cbTecnologia.isChecked()) intereses.append("Tecnología,");

        // Crear el contacto
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setTelefono(telefono);
        contacto.setEmail(email);
        contacto.setFechaNacimiento(fechaNac);
        contacto.setDireccion(direccion);
        contacto.setSexo(sexo);
        contacto.setIntereses(intereses.toString());
        contacto.setNewsletter(swNewsletter.isChecked());
        contacto.setNotificaciones(swNotificaciones.isChecked());

        // Guardar en SQLite
        ContactoDBHelper dbHelper = new ContactoDBHelper(this);
        long id = dbHelper.insertar(contacto);

        if (id > 0) {
            Toast.makeText(this, "Contacto guardado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FormAgregarContacto.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el contacto", Toast.LENGTH_SHORT).show();
        }
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
            Intent intent = new Intent(this, FormAgregarContacto.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_listado) {
            Intent intent = new Intent(this, FormListaContacto.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}