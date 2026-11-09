package irrgarten;


public class Monster {
    private static final int INITIAL_HEALTH = 5; // vida inicial
    private static final int NO_POS = -1;
    private String name; // nombre
    private float intelligence; // defensa
    private float strength; // ataque
    private float health; // vida
    private int row; // posición en laberinto
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

    public boolean dead() { // función muerte
        return(this.health <= 0.0f);
    }

    public float attack() {
    return Dice.intensity(this.strength); // delega en dado: el ataque es un valor aletorio entre [0, strength)
    }

    public boolean defend(float receivedAttack){
        boolean isDead = this.dead(); // si ya esta muerto, no se defiende y devuelve true

        if (!isDead) { // si sigue vivo
            float defensiveEnergy = Dice.intensity(intelligence); // se calcula su energía defensiva entre [0, inteligencia)

            if (defensiveEnergy < receivedAttack) { // si esa defensa no alcanza al ataque recibido
                gotWounded(); // recibe una herida
                isDead = dead();
            }
        }

        return isDead;
    }

    public void setPos(int row, int col) { // sirve para que el monstruo sepa donde está
    this.row = row;
    this.col = col;
    }

    public String toString() {
    return "Monstruo {" + 
            "\nNombre = " + this.name +
            "\nInteligencia = " + this.intelligence + 
            "\nFuerza = " + this.strength + 
            "\nVida = " + this.health + 
            "\nFila = " + this.row + 
            "\nColumna = " + this.col + "\n}\n";
    }

    private void gotWounded() { // resta 1 a la salud
    this.health = this.health - 1;
    }

    

}
