import java.util.Arrays;

public class Edge {
    private int sommetTerminal;
    private double[] valeurs;

    public Edge(int sommetTerminal, int nb_param_arc) {
        this.sommetTerminal = sommetTerminal;
        this.valeurs = new double[nb_param_arc];
    }

    public Edge(int sommetTerminal, double[] param) {
        this.sommetTerminal = sommetTerminal;
        this.valeurs = param;
    }

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

    public void setValeurs(int index, int valeur) {
        this.valeurs[index] = valeur;
    }

    @Override
    public String toString() {
        return sommetTerminal + Arrays.toString(valeurs);
    }

    public String affichage() {
        return sommetTerminal + Arrays.toString(valeurs);
    }
}
