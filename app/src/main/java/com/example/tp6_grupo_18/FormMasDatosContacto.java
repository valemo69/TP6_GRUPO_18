package com.example.tp6_grupo_18;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FormMasDatosContacto extends AppCompatActivity {

    private RadioGroup rgNivelEstudios;
    private CheckBox cbDeporte, cbMusica, cbArte, cbTecnologia;
    private Switch swRecibeInfo;
    private Button btnGuardar;

    private String nombre, apellido, telefono, tipoTelefono, email, tipoEmail, fechaNac, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mas_datos);

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.scrollTo(0, 0));

        rgNivelEstudios = findViewById(R.id.rgNivelEstudios);
        cbDeporte = findViewById(R.id.cbDeporte);
        cbMusica = findViewById(R.id.cbMusica);
        cbArte = findViewById(R.id.cbArte);
        cbTecnologia = findViewById(R.id.cbTecnologia);
        swRecibeInfo = findViewById(R.id.swRecibeInfo);
        btnGuardar = findViewById(R.id.btnGuardar);

        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        apellido = intent.getStringExtra("apellido");
        telefono = intent.getStringExtra("telefono");
        tipoTelefono = intent.getStringExtra("tipoTelefono");
        email = intent.getStringExtra("email");
        tipoEmail = intent.getStringExtra("tipoEmail");
        fechaNac = intent.getStringExtra("fechaNac");
        direccion = intent.getStringExtra("direccion");

        btnGuardar.setOnClickListener(v -> guardarContacto());
    }

    private void guardarContacto() {
        int selectedEstudioId = rgNivelEstudios.getCheckedRadioButtonId();
        if (selectedEstudioId == -1) {
            Toast.makeText(this, "Debe seleccionar un nivel de estudios", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rbEstudio = findViewById(selectedEstudioId);
        String nivelEstudios = rbEstudio.getText().toString();

        List<String> listaIntereses = new ArrayList<>();
        if (cbDeporte.isChecked()) listaIntereses.add("Deporte");
        if (cbMusica.isChecked()) listaIntereses.add("Música");
        if (cbArte.isChecked()) listaIntereses.add("Arte");
        if (cbTecnologia.isChecked()) listaIntereses.add("Tecnología");

        if (listaIntereses.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar al menos un interés", Toast.LENGTH_SHORT).show();
            return;
        }

        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setTelefono(telefono);
        contacto.setTipoTelefono(tipoTelefono);
        contacto.setEmail(email);
        contacto.setTipoEmail(tipoEmail);
        contacto.setFechaNacimiento(fechaNac);
        contacto.setDireccion(direccion);
        contacto.setNivelEstudios(nivelEstudios);
        contacto.setIntereses(TextUtils.join(", ", listaIntereses));
        contacto.setRecibeInformacion(swRecibeInfo.isChecked());

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