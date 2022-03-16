public class Vertex {
    private String nom;
    private Double latitude;
    private Double longitude;
    private int pop;

    // Constructeur
    public Vertex(String nom, String latitude_s, String longitude_s) {
        this.nom = nom;
        this.latitude = Double.parseDouble(latitude_s);
        this.longitude = Double.parseDouble(longitude_s);
        this.pop = 0;
    }

    // region Get/Set
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public Double getLatitude() {
        return latitude;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // endregion
    public String affichage() {
        return nom;
    }

    // Affichage d'un vertex
    public String toString() {
        return  nom +"[ x="+ latitude +"| y="+ longitude +"]";
    }

}
