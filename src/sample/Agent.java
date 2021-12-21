package sample;

import java.util.ArrayDeque;
import java.util.Random;

public class Agent extends Thread {
    private final int[] position = new int[2];
    private final int[] positionFinale = new int[2];
    private int iteration = 1;
    private Environnement environnement;
    private String nom;
    private ArrayDeque<String> messageReceived = new ArrayDeque<>();
    private boolean change = false;

    public Agent(String nom) {
        this.nom = nom;
    }

    public void run() {
        while (!this.environnement.isFinished()) {
            if (!this.messageReceived.isEmpty()) {
                String coordDep = messageReceived.pollFirst();
                this.deplacementForce(coordDep);
            } else if (!change) {
                this.deplacement();
            } else this.deplacement2();

            if (this.iteration % 15 == 0) {
                System.out.println(iteration);
                this.change = !change;
                System.out.println(this);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String toString() {
        return "version1.Agent{" +

                ", nom='" + nom + '\'' +
                ",Position'" + position[0] + " , " + position[1] +
                ",Position Finale'" + positionFinale[0] + " , " + positionFinale[1] +

                '}';
    }

    public synchronized void deplacer(String dep, int nextPosition) {
        switch (dep) {
            case "x":
                if (this.environnement.getGrille()[nextPosition][this.position[1]] == null) {
                    this.environnement.getGrille()[nextPosition][this.position[1]] = this;
                    this.environnement.getGrille()[this.position[0]][this.position[1]] = null;
                    this.position[0] = nextPosition;
                    break;
                }
            case "y":
                if (this.environnement.getGrille()[this.position[0]][nextPosition] == null) {

                    this.environnement.getGrille()[this.position[0]][nextPosition] = this;
                    this.environnement.getGrille()[this.position[0]][this.position[1]] = null;
                    this.position[1] = nextPosition;
                    break;
                }
        }
        this.iteration++;
    }

    public void deplacement() {
        int nextPositionX = this.position[0];
        int nextPositionY = this.position[1];

        // POSITION X
        if (this.position[0] != this.positionFinale[0]) {
            // recupere s'il doit monter ou descendre
            if (this.position[0] < positionFinale[0]) {
                nextPositionX = this.position[0] + 1;
            } else if (this.position[0] > positionFinale[0]) {
                nextPositionX = this.position[0] - 1;
            }
            // regarde s'il peut se déplacer, ou s'il doit pousser
            if (this.environnement.getGrille()[nextPositionX][this.position[1]] == null) { // se deplace
                this.deplacer("x", nextPositionX);
            } else { // pousse
                Agent agent = (Agent) this.environnement.getGrille()[nextPositionX][this.position[1]];
                agent.getMessageReceived().addLast("x");
            }
        } else if (this.position[1] != this.positionFinale[1]) { // POSITION Y
            // recupere s'il doit monter ou descendre
            if (this.position[1] < positionFinale[1]) {
                nextPositionY = this.position[1] + 1;
            } else if (this.position[1] > positionFinale[1]) {
                nextPositionY = this.position[1] - 1;
            }
            // regarde s'il peut se déplacer, ou s'il doit pousser
            if (this.environnement.getGrille()[this.position[0]][nextPositionY] == null) {
                //Se deplace
                this.deplacer("y", nextPositionY);
            } else {
                Agent agent = (Agent) this.environnement.getGrille()[this.position[0]][nextPositionY];
                agent.getMessageReceived().addLast("y");
            }
        }
    }

    public void deplacement2() {
        int nextPositionX = this.position[0];
        int nextPositionY = this.position[1];

        if (this.position[1] != this.positionFinale[1]) { // POSITION Y
            // recupere s'il doit monter ou descendre
            if (this.position[1] < positionFinale[1]) {
                nextPositionY = this.position[1] + 1;
            } else if (this.position[1] > positionFinale[1]) {
                nextPositionY = this.position[1] - 1;
            }
            // regarde s'il peut se déplacer, ou s'il doit pousser
            if (this.environnement.getGrille()[this.position[0]][nextPositionY] == null) {
                //Se deplace
                this.deplacer("y", nextPositionY);
            } else {
                Agent agent = (Agent) this.environnement.getGrille()[this.position[0]][nextPositionY];
                agent.getMessageReceived().addLast("y");
            }
        } else if (this.position[0] != this.positionFinale[0]) { // POSITION Y
            // recupere s'il doit monter ou descendre
            if (this.position[0] < positionFinale[0]) {
                nextPositionX = this.position[0] + 1;
            } else if (this.position[0] > positionFinale[0]) {
                nextPositionX = this.position[0] - 1;
            }
            // regarde s'il peut se déplacer, ou s'il doit pousser
            if (this.environnement.getGrille()[nextPositionX][this.position[1]] == null) { // se deplace
                this.deplacer("x", nextPositionX);
            } else { // pousse
                Agent agent = (Agent) this.environnement.getGrille()[nextPositionX][this.position[1]];
                agent.getMessageReceived().addLast("x");
            }
        }
    }

    public synchronized void deplacementForce(String dep) {
        int nextPositionX = this.position[0];
        int nextPositionY = this.position[1];
        Random rand = new Random();
        switch (dep) {
            case "y":
                int aleatoireX = rand.nextInt(2);
                switch (aleatoireX) {
                    case 0:
                        if (this.position[0] - 1 >= 0)
                            nextPositionX = this.position[0] - 1;
                        break;
                    case 1:
                        if (this.position[0] + 1 <= this.environnement.getGrille().length - 1)
                            nextPositionX = this.position[0] + 1;
                        break;
                }

                if (nextPositionX != this.position[0] && this.environnement.getGrille()[nextPositionX][this.position[1]] == null) {
                    this.deplacer("x", nextPositionX);
                } else {
                    Agent agent = (Agent) this.environnement.getGrille()[nextPositionX][this.position[1]];
                    agent.getMessageReceived().addLast("x");
                }
                break;
            case "x":
                int aleatoireY = rand.nextInt(2);
                switch (aleatoireY) {
                    case 0:
                        if (this.position[1] - 1 >= 0)
                            nextPositionY = this.position[1] - 1;
                        break;
                    case 1:
                        if (this.position[1] + 1 < this.environnement.getGrille()[0].length)
                            nextPositionY = this.position[1] + 1;
                        break;
                }
                if (nextPositionY != this.position[1] && this.environnement.getGrille()[this.position[0]][nextPositionY] == null) {
                    //Se deplace
                    this.deplacer("y", nextPositionY);
                } else {
                    Agent agent = (Agent) this.environnement.getGrille()[this.position[0]][nextPositionY];
                    agent.getMessageReceived().addLast("y");
                }
                break;
        }
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public int[] getPositionFinale() {
        return positionFinale;
    }

    public void setPositionFinale(int x, int y) {
        this.positionFinale[0] = x;
        this.positionFinale[1] = y;
    }

    public ArrayDeque<String> getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(ArrayDeque<String> messageReceived) {
        this.messageReceived = messageReceived;
    }
}