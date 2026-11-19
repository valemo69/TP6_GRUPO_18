package com.example.tp6_grupo_18;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormListaContacto extends AppCompatActivity {

    private ListView lvContactos;
    private TextView tvSinContactos;
    private ContactoDBHelper dbHelper;
    private List<Contacto> listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lista_contacto);

        setTitle("Contactos");

        lvContactos = findViewById(R.id.lvContactos);
        tvSinContactos = findViewById(R.id.tvSinContactos);
        lvContactos.setEmptyView(tvSinContactos);

        dbHelper = new ContactoDBHelper(this);

        cargarLista();

        lvContactos.setOnItemClickListener((parent, view, position, id) -> {
            Contacto c = listaContactos.get(position);
            mostrarDetalle(c);
        });
    }

    private void cargarLista() {
        listaContactos = dbHelper.obtenerTodos();
        List<String> items = new ArrayList<>();

        for (Contacto c : listaContactos) {
            String fila = valorSeguro(c.getNombre()) + " " +
                    valorSeguro(c.getApellido()) + " - " +
                    valorSeguro(c.getEmail());
            items.add(fila.toUpperCase(Locale.getDefault()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );
        lvContactos.setAdapter(adapter);
    }

    private void mostrarDetalle(Contacto c) {
        String detalle = "Nombre: " + valorSeguro(c.getNombre()) + "\n" +
                "Apellido: " + valorSeguro(c.getApellido()) + "\n" +
                "Teléfono: " + valorSeguro(c.getTelefono()) + " (" + valorSeguro(c.getTipoTelefono()) + ")\n" +
                "Email: " + valorSeguro(c.getEmail()) + " (" + valorSeguro(c.getTipoEmail()) + ")\n" +
                "Fecha de Nacimiento: " + valorSeguro(c.getFechaNacimiento()) + "\n" +
                "Dirección: " + valorSeguro(c.getDireccion()) + "\n" +
                "Nivel de Estudios: " + valorSeguro(c.getNivelEstudios()) + "\n" +
                "Intereses: " + valorSeguro(c.getIntereses()) + "\n" +
                "Recibe información: " + (c.isRecibeInformacion() ? "Sí" : "No");

        new AlertDialog.Builder(this)
                .setTitle("Detalle del Contacto")
                .setMessage(detalle)
                .setPositiveButton("Cerrar", null)
                .show();
    }

    private String valorSeguro(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? "-" : valor.trim();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
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
            finish();
            return true;
        } else if (id == R.id.menu_listado) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}