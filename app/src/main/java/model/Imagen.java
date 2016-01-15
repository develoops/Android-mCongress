package model;

import android.media.Image;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Alvaro on 12/30/15.
 */
@ParseClassName("Imagen")
public class Imagen extends ParseObject {


    public ParseFile getImagen() {
        return (ParseFile)get("imagen");
    }

    public static ParseQuery<Imagen> getQuery() {
        return ParseQuery.getQuery(Imagen.class);
    }
}


