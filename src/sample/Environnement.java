package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Environnement {
    private ArrayList<Agent> agents;
    private Object[][] grille;
    private HashMap<Agent, int[]> tabAgents = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> tabFinal = new HashMap<>();
    private Object[][] grilleFinal;

    public Environnement(int nbLignes, int nbColonnes, int nbAgent) {
        this.grille = new Object[nbLignes][nbColonnes];
        this.grilleFinal = new Object[nbLignes][nbColonnes];
        Random rand = new Random();
        int nAleatoire = rand.nextInt(nbLignes);
        int mAleatoire = rand.nextInt(nbColonnes);
        this.agents = new ArrayList<>();

        for (int i = 0; i< nbAgent;i++) {
            this.agents.add(new Agent(String.valueOf(i)));
        }

        for (Agent agent : agents) {
            int nAleatoireFinal = rand.nextInt(nbLignes);
            int mAleatoireFinal = rand.nextInt(nbColonnes);
            while (grille[nAleatoire][mAleatoire] != null) {
                nAleatoire = rand.nextInt(nbLignes);
                mAleatoire = rand.nextInt(nbColonnes);
            }
            grille[nAleatoire][mAleatoire] = agent;
            int[] tab = new int[2];
            tab[0] = nAleatoire;
            tab[1] = mAleatoire;
            this.tabAgents.put(agent,tab);
            agent.setPosition(nAleatoire, mAleatoire);
            boolean isDone = false;
            while (!isDone) {
                nAleatoireFinal = rand.nextInt(nbLignes);
                mAleatoireFinal = rand.nextInt(nbColonnes);
                if (tabFinal.containsKey(nAleatoireFinal)) {
                    if (!tabFinal.get(nAleatoireFinal).contains(mAleatoireFinal)) {
                        if (nAleatoireFinal != nAleatoire || mAleatoireFinal != mAleatoire) {
                            isDone = true;
                        }
                    }
                } else {
                    if (nAleatoireFinal != nAleatoire  || mAleatoireFinal != mAleatoire) {
                        isDone = true;
                    }
                }
            }
            if (tabFinal.containsKey(nAleatoireFinal)) {
                tabFinal.get(nAleatoireFinal).add(mAleatoireFinal);
            } else {
                ArrayList<Integer> m = new ArrayList<>();
                m.add(mAleatoireFinal);
                tabFinal.put(nAleatoireFinal, m);
            }
            agent.setPositionFinale(nAleatoireFinal, mAleatoireFinal);
        }

        for (Agent agent : this.agents) {
            this.grilleFinal[agent.getPositionFinale()[0]][agent.getPositionFinale()[1]] = agent;
        }
    }

    @Override
    public String toString() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                System.out.print(grille[i][j]);
                System.out.print(" || ");
            }
            System.out.println("\n");
        }
        return "";
    }

    public boolean isFinished() {
        for (Agent agent : agents) {
            int[] position = agent.getPosition();
            int[] positionFinale = agent.getPositionFinale();
            if (position[0] != positionFinale [0] || position[1] != positionFinale [1]) return false;
        }
        return true;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public Object[][] getGrille() {
        return grille;
    }

    public void setGrille(Object[][] grille) {
        this.grille = grille;
    }

    public HashMap<Agent, int[]> getTabAgents() {
        return tabAgents;
    }

    public void setTabAgents(HashMap<Agent, int[]> tabAgents) {
        this.tabAgents = tabAgents;
    }

    public HashMap<Integer, ArrayList<Integer>> getTabFinal() {
        return tabFinal;
    }

    public void setTabFinal(HashMap<Integer, ArrayList<Integer>> tabFinal) {
        this.tabFinal = tabFinal;
    }

    public Object[][] getGrilleFinal() {
        return this.grilleFinal;
    }
}
