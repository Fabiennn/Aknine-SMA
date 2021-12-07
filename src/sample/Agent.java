package sample;

public class Agent extends Thread {
    private Environnement environnement;
    private String nom;
    private int[] position = new int[2];
    private int[] positionFinale = new int[2];

    public Agent(String nom) {
        this.nom = nom;
    }

    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!this.environnement.isFinished()) {
            if (position[0] != positionFinale[0] || position[1] != positionFinale[1]) {
                this.deplacement();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    public synchronized void deplacement() {
        int nextPositionX = this.position[0];
        int nextPositionY = this.position[1];
        boolean isDeplaced = false;
        if (this.position[0] < positionFinale[0]) {
            nextPositionX = this.position[0] + 1;
        } else if (this.position[0] > positionFinale[0]) {
            nextPositionX = this.position[0] - 1;
        }
        if (nextPositionX != this.position[0] && this.environnement.getGrille()[nextPositionX][this.position[1]] == null) {
            this.environnement.getGrille()[nextPositionX][this.position[1]] = this;
            this.environnement.getGrille()[this.position[0]][this.position[1]] = null;
            this.position[0] = nextPositionX;
            isDeplaced = true;
        }

        if (this.position[1] < positionFinale[1]) {
            nextPositionY = this.position[1] + 1;
        } else if (this.position[1] > positionFinale[1]) {
            nextPositionY = this.position[1] - 1;
        }

        if (nextPositionY != this.position[1] && this.environnement.getGrille()[this.position[0]][nextPositionY] == null && !isDeplaced) {
            //Se deplace
            this.environnement.getGrille()[this.position[0]][nextPositionY] = this;
            this.environnement.getGrille()[this.position[0]][this.position[1]] = null;
            this.position[1] = nextPositionY;
        }

        System.out.println(this.environnement);
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
}