public class Vertex {
    private String nom;

    //Constructeur
    public Vertex(String nom){
        this.nom = nom;
    }

    // Get/Set
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String affichage() {
        return nom;
    }
    //Affichage d'un vertex
    public String toString() {
        return "Vertex [nom=" + nom + "]";
    }

}
