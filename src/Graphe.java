import java.util.*;
import java.io.*;

public class Graphe {
    private boolean oriente;
    private int nbSommet;
    private int nbArcs;
    private int nbValeursParArc;
    private ArrayList<Vertex> listeSommets;
    private ArrayList<LinkedList<Edge>> listeAdjacence;
    private int[] degree;
    public Object getListeAdjacence;

    // Constructeur
    public Graphe() {
        this.oriente = true;
        this.nbSommet = 0;
        this.nbArcs = 0;
        this.nbValeursParArc = 0;
        this.listeSommets = new ArrayList<Vertex>();
        this.listeAdjacence = new ArrayList<LinkedList<Edge>>();
        this.degree = new int[0];
    }

    // Import d'un graphique à partir d'un fichier
    public static Graphe ImporterGraphe(String nomfichier) throws IOException {
        Graphe curt_graph = new Graphe();

        // chemin relatif : "../graphs/" +nomfichier+".tgoGraph"
        File doc = new File("./graphs/" + nomfichier + ".tgoGraph");
        try (BufferedReader obj = new BufferedReader(new FileReader(doc))) {
            String strng;
            int compt = 1;
            // Boucle de lecture du fichier, on le lit tant que la ligne n'est pas null (fin
            // de fichier)
            while ((strng = obj.readLine()) != null) {
                // Determination de l'orientation du graph
                if (compt == 2) {
                    String[] tab_crt = strng.split(" ");
                    if (tab_crt[1].equalsIgnoreCase("true")) {
                        curt_graph.setOriente(true);
                    } else {
                        curt_graph.setOriente(false);
                    }
                }
                // Definition du nombre de sommet, du nombre d'arcs, du nombre de valeurs par
                // arcs.
                // On initialise pour chaque sommet son degré à 0
                if (compt == 4) {
                    String[] param_int = strng.split(" ");
                    curt_graph.setNbSommet(Integer.parseInt(param_int[0]));
                    curt_graph.setNbArcs(Integer.parseInt(param_int[2]));
                    curt_graph.setNbValeursParArc(Integer.parseInt(param_int[3]));
                    curt_graph.setDegree(new int[curt_graph.getNbSommet()]);
                    // Initialisation du degré i à 0
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        curt_graph.degree[i] = 0;
                    }
                }

                // Boucle de lecteur de saisie de chaque sommet
                if (5 <= compt && compt <= 5 + curt_graph.getNbSommet()) {
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        strng = obj.readLine();
                        compt += 1;
                        String[] sommet = strng.split(" ");
                        // Ajout du sommet dans ListeSommets
                        curt_graph.getListeSommets().add(new Vertex(sommet[1],sommet[2],sommet[3]));
                    }
                }

                // Boucle de lecture des arcs
                if (6 + curt_graph.getNbSommet() <= compt
                        && compt <= 6 + curt_graph.getNbSommet() + curt_graph.getNbArcs()) {
                    // Initialisation de la listeAdjacence
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        curt_graph.getListeAdjacence().add(new LinkedList<Edge>());
                    }
                    // Boucle remplissage de listeAdjacence
                    for (int i = 0; i < curt_graph.getNbArcs(); i++) {
                        strng = obj.readLine();
                        compt += 1;

                        String[] edge_nom = strng.split(" ");
                        // On créer l'arc qu'on ajoutera dans listeAdjacence
                        Edge crt_edg = new Edge(Integer.parseInt(edge_nom[1]), curt_graph.getNbValeursParArc());
                        // Si l'arc contient des valeurs, alors on les ajoutes
                        if (edge_nom.length > 0) {
                            for (int m = 2; m < edge_nom.length; m++) {
                                crt_edg.setValeurs(m % curt_graph.getNbValeursParArc(),
                                        Double.parseDouble(edge_nom[m]));
                            }
                        }
                        // On ajoute a chaque sommet de l'edge 1 à son degré
                        curt_graph.degree[Integer.parseInt(edge_nom[1])] += 1;
                        curt_graph.degree[Integer.parseInt(edge_nom[0])] += 1;
                        // On ajoute au sommet concerné l'edge qu'o nvient de créer
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
                                    }
                                }
                                if (!already_in) {
                                    curt_graph.getListeAdjacence().get(nouvelle_indice).add(reversed_edge);
                                }
                            }
                        }
                    }

                }
                // A la fin de chaque traitement de ligne on incrémente le compteur de ligne
                // d'un
                compt += 1;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return curt_graph;

    }


    // region Parcours 

    public void djikstra(int depart, int arrive){
        ArrayList<Vertex> sommet;


    }



    // Methode de parcours en Largeur du graphique, à partir d'un sommet placé en
    // paramètre
    // DE FARES NE PAS TOUCHER JE SUIS EN TRAIN DE TAFFER DESSU EN ATTENDANT ON
    // LAISSE EN STAND BY FAUT FAIRE LE DJIKSTRA 16/03
    public void parcoursLargeur(int depart, int arrive) {
        int[] dist = new int[this.listeSommets.size()];
        Vertex[] pere = new Vertex[this.listeSommets.size()];
        int[] couleur = new int[this.listeSommets.size()];// 0=Blanc / 1=Gris / 2=Noir
        ArrayList<Integer> file = new ArrayList<Integer>();
        for (int i = 0; i < this.listeSommets.size(); i++) {
            couleur[i] = 0;
            pere[i] = null;
            dist[i] = 0;
        }
        file.add(depart);
        couleur[depart] = 1;
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
                couleur[u] = 2;
            }
        }

    }
    // endregion

    public void lecture_csv(){
        File doc = new File("./graphs/CommunesFrance.csv");
        try (BufferedReader obj = new BufferedReader(new FileReader(doc))) {
            String strng;
            obj.readLine();
            while ((strng = obj.readLine()) != null){
                String[] param_int = strng.split(";");
                //On extrait la valeur de la population de la ville
                int pop_temp = Integer.parseInt(param_int[2]);

                //On fait une boucle de parcour de chaque sommet pour lui attribuer sa pop
                for(int i = 0; i< listeSommets.size(); i++){
                    //Si nous observons une une correspondance entre le sommet et le
                    if(listeSommets.get(i).getNom().equalsIgnoreCase(param_int[0])){
                        listeSommets.get(i).setPop(pop_temp);
                        System.out.println(listeSommets.get(i).affichage());
                    }
                }


            };
        } catch (NumberFormatException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // region Affichage

    // Affichage des caratériqtique du graph créer
    public void affichage_resume() {
        System.out.println("\n oriente=" + oriente
                + ", \n nbSommet=" + nbSommet +
                ",\n nbArcs=" + nbArcs
                + ",\n nbValeursParArc=" + nbValeursParArc);
    }

    // Affichage de la liste d'adjacence du graph
    public void affichage_adjacence() {

        System.out.println("\n Liste d'adjacence");
        for (int i = 0; i < listeAdjacence.size(); i++) {
            System.out.print(i + " " + listeSommets.get(i).affichage() + " : ");
            if (listeAdjacence.get(i).size() == 0) {
                System.out.print("Vide");
            } else {
                for (int j = 0; j < listeAdjacence.get(i).size(); j++) {
                    System.out.print(listeAdjacence.get(i).get(j).affichage() + " ");
                }
            }
            System.out.print("\n");
        }

    }

    // Affiche la liste des sommet avec le nom de la communes
    public void affiche_sommet() {
        System.out.println("\n");
        System.out.println("------------------------------------------");
        System.out.println("\n");
        System.out.println("\n Liste des Sommets");
        for (int i = 0; i < listeSommets.size(); i++) {
            System.out.print(i + ":" + listeSommets.get(i).affichage() + "\n");
        }
    }

    // Affichage de la liste des degrees par communes
    public void affichage_degree() {
        // Séparateur
        for (int x = 0; x < listeSommets.size(); x++) {
            System.out.println(x + " " + listeSommets.get(x).affichage() + ":" + degree[x]);
        }
    }
    // endregion

    public ArrayList<Vertex> parent(int v) {
        ArrayList<Vertex> parent = new ArrayList<Vertex>();
        int temp;
            for (int i = 0; i < listeAdjacence.get(v).size();i++){
                temp = listeAdjacence.get(v).get(i).getSommetTerminal();
                parent.add(listeSommets.get(temp));
            }
        return parent;
    }

    // region Get / Set
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
    // endregion

    @Override
    // region toString
    // toString liste adjacence
    public String toString() {
        return "Graphe [listeAdjacence=" + listeAdjacence + "]";
    }
    // endregion
}