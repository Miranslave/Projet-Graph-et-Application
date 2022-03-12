import java.util.Arrays;

public class Edge {
    private int sommetTerminal;
    private double[] valeurs;

    //Constructeurs
    public Edge(int sommetTerminal, int nb_param_arc) {
        this.sommetTerminal = sommetTerminal;
        this.valeurs = new double[nb_param_arc];
    }

    public Edge(int sommetTerminal, double[] param) {
        this.sommetTerminal = sommetTerminal;
        this.valeurs = param;
    }

    public String affichage() {
        return sommetTerminal + Arrays.toString(valeurs);
    }

    //region Get / Set
    // Get/Set
    public int getSommetTerminal() {
        return sommetTerminal;
    }

    public void setSommetTerminal(int sommetTerminal) {
        this.sommetTerminal = sommetTerminal;
    }

    public double getValeurs(int index) {
        return valeurs[index];
    }

    public double[] getTab(){
        return this.valeurs;
    }

    public void setValeurs(int index, double valeur) {
        this.valeurs[index] = valeur;
    }
    //endregion
    @Override

    //region toString
    public String toString() {
        return sommetTerminal + Arrays.toString(valeurs);
    }
    //endregion

}
