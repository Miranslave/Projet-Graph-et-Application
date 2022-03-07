import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.io.*;

public class Graphe {
    private boolean oriente;
    private int nbSommet;
    private int nbArcs;
    private int nbValeursParArc;
    private ArrayList<Vertex> listeSommets;
    private ArrayList<LinkedList<Edge>> listeAdjacence;
    private int[] degree;

    public Graphe() {
        this.oriente = true;
        this.nbSommet = 0;
        this.nbArcs = 0;
        this.nbValeursParArc = 0;
        this.listeSommets = new ArrayList<Vertex>();
        this.listeAdjacence = new ArrayList<LinkedList<Edge>>();
        this.degree = new int[0];
    }

    public static Graphe ImporterGraphe(String nomfichier) throws IOException {
        Graphe curt_graph = new Graphe();

        File doc = new File("C:\\Users\\Farès Zaroui\\Desktop\\Graph et application\\" + nomfichier + ".tgoGraph");
        try (BufferedReader obj = new BufferedReader(new FileReader(doc))) {
            String strng;
            int compt = 1;
            // Boucle de lecture du fichier
            while ((strng = obj.readLine()) != null) {
                if (compt == 2) {
                    String[] tab_crt = strng.split(" ");
                    System.out.println(tab_crt[1]);
                    if (tab_crt[1].equalsIgnoreCase("true")) {
                        System.out.println("Vrai");
                        curt_graph.setOriente(true);
                    } else {
                        System.out.println("Faux");
                        curt_graph.setOriente(false);
                    }
                }
                if (compt == 4) {
                    String[] param_int = strng.split(" ");
                    curt_graph.setNbSommet(Integer.parseInt(param_int[0]));
                    curt_graph.setNbArcs(Integer.parseInt(param_int[2]));
                    curt_graph.setNbValeursParArc(Integer.parseInt(param_int[3]));
                    curt_graph.setDegree(new int[curt_graph.getNbSommet()]);
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        curt_graph.degree[i] = 0;
                    }
                }

                if (5 <= compt && compt <= 5 + curt_graph.getNbSommet()) {
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        strng = obj.readLine();
                        compt += 1;
                        String[] sommet_nom = strng.split(" ");
                        curt_graph.getListeSommets().add(new Vertex(sommet_nom[1]));
                    }
                }
                if (6 + curt_graph.getNbSommet() <= compt
                        && compt <= 6 + curt_graph.getNbSommet() + curt_graph.getNbArcs()) {

                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        curt_graph.getListeAdjacence().add(new LinkedList<Edge>());
                    }
                    for (int i = 0; i < curt_graph.getNbArcs(); i++) {
                        strng = obj.readLine();
                        compt += 1;
                        String[] edge_nom = strng.split(" ");
                        Edge crt_edg = new Edge(Integer.parseInt(edge_nom[1]), curt_graph.getNbValeursParArc());
                        if (edge_nom.length > 0) {
                            for (int m = 2; m < edge_nom.length; m++) {
                                crt_edg.setValeurs(m % curt_graph.getNbValeursParArc(),
                                        Integer.parseInt(edge_nom[m]));
                            }
                        }
                        curt_graph.degree[Integer.parseInt(edge_nom[1])] += 1;
                        curt_graph.degree[Integer.parseInt(edge_nom[0])] += 1;
                        curt_graph.getListeAdjacence().get(Integer.parseInt(edge_nom[0])).add(crt_edg);

                    }
                    if (!curt_graph.oriente) {
                        for (int h = 0; h < curt_graph.getListeAdjacence().size(); h++) {
                            for (int u = 0; u < curt_graph.getListeAdjacence().get(h).size(); u++) {
                                boolean already_in = false;
                                int nouvelle_indice = curt_graph.getListeAdjacence().get(h).get(u).getSommetTerminal();
                                Edge reversed_edge = new Edge(h, curt_graph.getListeAdjacence().get(h).get(u).getTab());
                                for (int o = 0; o < curt_graph.getListeAdjacence().get(nouvelle_indice).size(); o++) {
                                    if (curt_graph.getListeAdjacence().get(nouvelle_indice).get(o)
                                            .getSommetTerminal() == reversed_edge.getSommetTerminal()) {
                                        already_in = true;
                                        System.out.println("test");
                                    }
                                }
                                if (!already_in) {
                                    curt_graph.getListeAdjacence().get(nouvelle_indice).add(reversed_edge);
                                }
                            }
                        }
                    }

                }
                compt += 1;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return curt_graph;

    }

    public void parcoursLargeur(int indexsommetDepart) {
        int[] dist = new int[this.listeSommets.size()];
        Vertex[] pere = new Vertex[this.listeSommets.size()];
        int[] couleur = new int[this.listeSommets.size()];// 0=Blanc / 1=Gris / 2=Noir
        ArrayList<Integer> file = new ArrayList<Integer>();
        for (int i = 0; i < this.listeSommets.size(); i++) {
            couleur[i] = 0;
            pere[i] = null;
            dist[i] = 0;
        }
        file.add(indexsommetDepart);
        couleur[indexsommetDepart] = 1;
        while (!file.isEmpty()) {
            int u = file.get(0);
            for (int v = 0; v < this.getListeAdjacence().get(u).size(); v++) {
                if (couleur[v] == 0) {
                    couleur[v] = 1;
                    dist[v] = dist[u] + 1;
                    pere[v] = this.listeSommets.get(u);
                    file.add(v);
                }
                file.remove(0);
                couleur[u]=2;
            }
        }

    }

    public void affichage() {
        System.out.println("Graphe [" + "\n oriente=" + oriente
                + ", \n nbSommet=" + nbSommet +
                ",\n nbArcs=" + nbArcs
                + ",\n nbValeursParArc=" + nbValeursParArc
                + ",\n\n Degree");
        for (int x = 0; x < degree.length; x++) {
            System.out.println(x + ":" + degree[x]);
        }
        System.out.println("\n Liste d'adjacence");
        for (int i = 0; i < listeAdjacence.size(); i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < listeAdjacence.get(i).size(); j++) {
                System.out.print(listeAdjacence.get(i).get(j).affichage() + " ");
            }
            System.out.print("\n");
        }
    }

    public boolean isOriente() {
        return oriente;
    }

    public void setOriente(boolean oriente) {
        this.oriente = oriente;
    }

    public int getNbSommet() {
        return nbSommet;
    }

    public void setNbSommet(int nbSommet) {
        this.nbSommet = nbSommet;
    }

    public int getNbArcs() {
        return nbArcs;
    }

    public void setNbArcs(int nbArcs) {
        this.nbArcs = nbArcs;
    }

    public int getNbValeursParArc() {
        return nbValeursParArc;
    }

    public void setNbValeursParArc(int nbValeursParArc) {
        this.nbValeursParArc = nbValeursParArc;
    }

    public ArrayList<Vertex> getListeSommets() {
        return listeSommets;
    }

    public ArrayList<LinkedList<Edge>> getListeAdjacence() {
        return listeAdjacence;
    }

    public void setDegree(int[] degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Graphe [listeAdjacence=" + listeAdjacence + "]";
    }

}