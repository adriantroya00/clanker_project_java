package irrgarten;


public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private static final int NO_POS = -1;
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    //todo definido
    public Monster( String _name , float _intelligence , float _strength){
        this.name = _name;
        this.intelligence = _intelligence;
        this.strength = _strength;
        this.health = INITIAL_HEALTH;
        this.row = NO_POS;
        this.col = NO_POS;  
    }

    public boolean dead() {
        return(this.health <= 0.0f);
    }

    public float attack() {
    return Dice.intensity(this.strength);
    }

    public boolean defend(float receivedAttack){
        boolean isDead = this.dead();

        if (!isDead) {
            float defensiveEnergy = Dice.intensity(intelligence);

            if (defensiveEnergy < receivedAttack) {
                gotWounded();
                isDead = dead();
            }
        }

        return isDead;
    }

    public void setPos(int row, int col) {
    this.row = row;
    this.col = col;
    }

    public String toString() {
    return "Monstruo {\n" + 
            "Nombre = " + this.name +
            "Inteligencia = " + this.intelligence + 
            "\n Fuerza = " + this.strength + 
            "\n Vida = " + this.health + 
            "\n Fila = " + this.row + 
            "\n Columna = " + this.col + "\n}\n";
    }

    private void gotWounded() {
    this.health = this.health - 1;
    }

    

}
