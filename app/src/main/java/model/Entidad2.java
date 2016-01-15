package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alvaro on 12/30/15.
 */
@ParseClassName("Entidad2")
public class Entidad2 extends ParseObject {

    public Boolean booleano() {
        return getBoolean("booleano");
    }

    public Date getFecha() {
        return getDate("fecha");
    }
    public Integer getNumber() {
        return getInt("numero");
    }

    public String getTexto() {
        return getString("texto");
    }

    public Entidad1 getEntidad1() {
        return (Entidad1)get("relacionE1");
    }
    public Entidad2 getEntidad2() {
        return (Entidad2)get("relacionE2");
    }

    public Entidad3 getEntidad3() {
        return (Entidad3)get("relacionE3");
    }

    public Imagen getImagen() {
        return (Imagen)get("imagen");
    }

    public List<Entidad1> getArrayE1() {
        return getList("relacionE1Array");
    }

    public List<Entidad2> getArrayE2() {
        return getList("relacionE2Array");
    }

    public List<Entidad3> getArrayE3() {
        return getList("relacionE3Array");
    }

    public HashMap getKeyValue() {
        return (HashMap)get("keyValue");
    }

    public static ParseQuery<Entidad2> getQuery() {
        return ParseQuery.getQuery(Entidad2.class);
    }
}
