package cobit19.ecci.ucr.ac.eventosucr.core.models;

public class Comentario {
    private String comentario;
    private String nombre;
    private String hora;
    private String commentante;
    public Comentario(){}

    public Comentario(String comentario, String nombre, String hora,String commentante) {
        this.comentario = comentario;
        this.nombre = nombre;
        this.hora = hora;
        this.commentante=commentante;
    }

    public String getCommentante() {
        return commentante;
    }

    public void setCommentante(String commentante) {
        this.commentante = commentante;
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
