package irrgarten;


public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    public Monster( String _name , float _intelligence , float _strength , float _health , int _row , int _col ){
        this.name = _name;
        this.intelligence = _intelligence;
        this.strength = _strength;
        this.health = _health;
        this.row = _row;
        this.col = _col;
    }

    public Monster(String _name , float _health , int _row , int _col ){
        this.name = _name;
        this.intelligence = Dice.randomIntelligence();
        this.strength = Dice.randomStrength();
        this.health = _health;
        this.row = _row;
        this.col = _col;

    }

    public Monster( int _row , int _col ){
        this.row = _row;
        this.col = _col;
    }

    public boolean dead() {
        return(this.health <= 0.0f);
    }

    public float attack() {
    return Dice.intensity(this.strength);
    }

    //public boolean defend(float receivedAttack){}

    public void setPos(int row, int col) {
    this.row = row;
    this.col = col;
    }

    public String toString() {
    return "Monstruo {\n" + 
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
