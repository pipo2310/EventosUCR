package cobit19.ecci.ucr.ac.eventosucr.shared;

import cobit19.ecci.ucr.ac.eventosucr.R;

public class Util {

    public static int idCategoria(String categoria) {
        int id = 0;

        switch (categoria) {
            case "gastronomia":
                id = R.drawable.ic_categoria_grastronomia;
                break;

            case "teatro":
                id = R.drawable.ic_categoria_teatro;
                break;

            case "danza":
                id = R.drawable.ic_categoria_danza;
                break;

            case "expo":
                id = R.drawable.ic_categoria_expo;
                break;

            case "musica":
                id = R.drawable.ic_categoria_musica;
                break;

            case "cine":
                id = R.drawable.ic_categoria_cine;
                break;

            case "literatura":
                id = R.drawable.ic_categoria_literatura;
                break;

            case "taller":
                id = R.drawable.ic_categoria_taller;
                break;

            case "feria":
                id = R.drawable.ic_categoria_feria;
                break;

            case "conversatorio":
                id = R.drawable.ic_categoria_conversatorio;
                break;

            case "convocatoria":
                id = R.drawable.ic_categoria_convocatoria;
                break;

            case "otras":
                id = R.drawable.ic_categoria_otras;
                break;

            default:
                id = R.drawable.ic_add_a_photo_black_24dp;
                break;
        }

        return id;
    }
}
