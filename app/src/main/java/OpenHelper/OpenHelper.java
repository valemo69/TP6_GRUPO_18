package OpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import Entidad.Contacto;
public class OpenHelper extends SQLiteOpenHelper {

    public static String ContactosCreaciontable = "CREATE TABLE IF NOT EXISTS contactos(\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    nombre TEXT NOT NULL,\n" +
            "    apellido TEXT NOT NULL,\n" +
            "    telefono TEXT NOT NULL,\n" +
            "    tipoTelefono TEXT NOT NULL,\n" +
            "    email TEXT NOT NULL,\n" +
            "    tipoEmail TEXT NOT NULL,\n" +
            "    direccion TEXT NOT NULL,\n" +
            "    fechaNacimiento TEXT NOT NULL,\n" +
            "    nivelEstudios TEXT NOT NULL,\n" +
            "    intereses TEXT,\n" +
            "    recibeInformacion INTEGER NOT NULL\n" +
            ");";
    public static String ContactosTable = "contactos";

    public static String ContactosColumnaID = "_ID";

    public static String ContactosColumnaNombre = "Nombre";

    public static String ContactosColumnaApellido = "Apellido";

    public static String ContactosColumnaTelefono = "Telefono";

    public static String ContactosColumnaTipoTelefono = "TipoTelefono";

    public static String ContactosColumnaEmail = "EMail";

    public static String ContactosColumnaTipoEmail = "Email";

    public static String ContactosColumnaDireccion = "Direccion";

    public static String ContactosColumnaFechaNacimiento = "FechaNacimiento";

    public static String ContactosColumnaEstudios = "Estudios";

    public static String ContactosColumnaIntereses="Intereses";
    public static String ContactosColumnaInformacion = "Recibe informacion";

    public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea las tablas
        String query= ContactosCreaciontable;
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertarContacto(Contacto c)
    {
        ContentValues valores = new ContentValues();
        valores.put(ContactosColumnaNombre,c.getNombre());
        valores.put(ContactosColumnaApellido,c.getApellido());
        valores.put(ContactosColumnaTelefono,c.getTelefono());
        valores.put(ContactosColumnaTipoTelefono,c.getTipoTelefono());
        valores.put(ContactosColumnaEmail,c.getEmail());
        valores.put(ContactosColumnaTipoEmail,c.getTipoEmail());
        valores.put(ContactosColumnaDireccion,c.getDireccion());
        valores.put(ContactosColumnaFechaNacimiento,c.getFechaNacimiento());
        valores.put(ContactosColumnaEstudios,c.getNivelEstudios());
        String interesesTexto = "";
        if (c.getIntereses() != null && !c.getIntereses().isEmpty()) {
            interesesTexto = String.join(", ", c.getIntereses());
        }
        valores.put(ContactosColumnaIntereses, interesesTexto);

        int recibeInfoInt = c.getRecibeInformacion() ? 1 : 0;
        valores.put(ContactosColumnaInformacion, recibeInfoInt);
    }
    public void eliminarContacto(Contacto c)
    {
        String id = String.valueOf(c.getId());
        this.getWritableDatabase().delete(ContactosTable,"_ID=?",new String[]{id});
    }

    public ArrayList<Contacto> getListadoMascotas()
    {
        ArrayList<Contacto> listaContacto = new ArrayList<Contacto>();
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query(ContactosTable,
                new String[]{ContactosColumnaID,ContactosColumnaNombre,ContactosColumnaApellido,ContactosColumnaTipoEmail},
                null,null,null,null,null);

        if(mcursor.moveToFirst())
        {
            do{
                Contacto c = new Contacto();
                c.setId(mcursor.getInt(0));
                c.setNombre(mcursor.getString(1));
                c.setApellido(mcursor.getString(2));
                c.setTipoEmail(mcursor.getString(3));
                listaContacto.add(c);
            }
            while(mcursor.moveToNext());
        }
        return listaContacto;
    }





}
