public class Vertex {
    private String nom;

    public Vertex(String nom){
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    @Override
    public String toString() {
        return "Vertex [nom=" + nom + "]";
    }

}
