package com.example.tp6_grupo_18;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FormListaContacto extends AppCompatActivity {

    private ListView lvContactos;
    private ContactoDBHelper dbHelper;
    private List<Contacto> listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lista_contacto);

        lvContactos = findViewById(R.id.lvContactos);
        dbHelper = new ContactoDBHelper(this);

        cargarLista();

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto c = listaContactos.get(position);
                mostrarDetalle(c);
            }
        });
    }

    private void cargarLista() {
        listaContactos = dbHelper.obtenerTodos();
        List<String> nombres = new ArrayList<>();
        for (Contacto c : listaContactos) {
            nombres.add(c.getNombre() + " " + c.getApellido() + " - " + c.getEmail());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, nombres);
        lvContactos.setAdapter(adapter);
    }

    private void mostrarDetalle(Contacto c) {
        String detalle = "Nombre: " + c.getNombre() + "\n" +
                "Apellido: " + c.getApellido() + "\n" +
                "Teléfono: " + c.getTelefono() + " (" + c.getTipoTelefono() + ")\n" +
                "Email: " + c.getEmail() + " (" + c.getTipoEmail() + ")\n" +
                "Fecha de Nacimiento: " + c.getFechaNacimiento() + "\n" +
                "Dirección: " + c.getDireccion() + "\n" +
                "Nivel de Estudios: " + c.getNivelEstudios() + "\n" +
                "Intereses: " + c.getIntereses() + "\n" +
                "Recibe información: " + (c.isRecibeInformacion() ? "Sí" : "No");

        new AlertDialog.Builder(this)
                .setTitle("Detalle del Contacto")
                .setMessage(detalle)
                .setPositiveButton("OK", null)
                .show();
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
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_listado) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}