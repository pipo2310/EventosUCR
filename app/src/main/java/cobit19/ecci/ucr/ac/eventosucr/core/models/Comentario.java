package cobit19.ecci.ucr.ac.eventosucr.core.models;

public class Comentario {
    private String comentario;
    private String nombre;
    private String hora;
    private String comentante;
    private int likes;
    public Comentario(){}

    public Comentario(String comentario, String nombre, String hora,String commentante,int likes) {
        this.comentario = comentario;
        this.nombre = nombre;
        this.hora = hora;
        this.comentante=commentante;
        this.likes=likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getComentante() {
        return comentante;
    }

    public void setComentante(String comentante) {
        this.comentante = comentante;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
