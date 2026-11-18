
package com.example.tp6_grupo_18;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactoDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "contactos.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE = "contactos";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_APELLIDO = "apellido";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_TIPO_TEL = "tipo_telefono";
    private static final String COL_EMAIL = "email";
    private static final String COL_TIPO_EMAIL = "tipo_email";
    private static final String COL_FECHA_NAC = "fecha_nacimiento";
    private static final String COL_DIRECCION = "direccion";
    private static final String COL_SEXO = "sexo";
    private static final String COL_INTERESES = "intereses";
    private static final String COL_NEWSLETTER = "newsletter";
    private static final String COL_NOTIFICACIONES = "notificaciones";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOMBRE + " TEXT NOT NULL, " +
                    COL_APELLIDO + " TEXT NOT NULL, " +
                    COL_TELEFONO + " TEXT, " +
                    COL_TIPO_TEL + " TEXT, " +
                    COL_EMAIL + " TEXT, " +
                    COL_TIPO_EMAIL + " TEXT, " +
                    COL_FECHA_NAC + " TEXT, " +
                    COL_DIRECCION + " TEXT, " +
                    COL_SEXO + " TEXT, " +
                    COL_INTERESES + " TEXT, " +
                    COL_NEWSLETTER + " INTEGER, " +
                    COL_NOTIFICACIONES + " INTEGER" +
                    ")";

    public ContactoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE +
                    " ADD COLUMN " + COL_TIPO_EMAIL + " TEXT");
        }
    }

    // Insertar un contacto
    public long insertar(Contacto c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, c.getNombre());
        values.put(COL_APELLIDO, c.getApellido());
        values.put(COL_TELEFONO, c.getTelefono());
        values.put(COL_TIPO_TEL, c.getTipoTelefono());
        values.put(COL_EMAIL, c.getEmail());
        values.put(COL_TIPO_EMAIL, c.getTipoEmail());
        values.put(COL_FECHA_NAC, c.getFechaNacimiento());
        values.put(COL_DIRECCION, c.getDireccion());
        values.put(COL_SEXO, c.getSexo());
        values.put(COL_INTERESES, c.getIntereses());
        values.put(COL_NEWSLETTER, c.isNewsletter() ? 1 : 0);
        values.put(COL_NOTIFICACIONES, c.isNotificaciones() ? 1 : 0);

        long id = db.insert(TABLE, null, values);
        db.close();

        return id;
    }

    // Obtener todos los contactos
    public List<Contacto> obtenerTodos() {
        List<Contacto> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE + " ORDER BY " + COL_NOMBRE,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Contacto c = new Contacto();

                c.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                c.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                c.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(COL_APELLIDO)));
                c.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
                c.setTipoTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO_TEL)));
                c.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                c.setTipoEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO_EMAIL)));
                c.setFechaNacimiento(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_NAC)));
                c.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(COL_DIRECCION)));
                c.setSexo(cursor.getString(cursor.getColumnIndexOrThrow(COL_SEXO)));
                c.setIntereses(cursor.getString(cursor.getColumnIndexOrThrow(COL_INTERESES)));
                c.setNewsletter(cursor.getInt(cursor.getColumnIndexOrThrow(COL_NEWSLETTER)) == 1);
                c.setNotificaciones(cursor.getInt(cursor.getColumnIndexOrThrow(COL_NOTIFICACIONES)) == 1);

                lista.add(c);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }
}
