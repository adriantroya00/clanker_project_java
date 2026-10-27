package irrgarten;

public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private String name;
    private float intelligence;
    private float health;
    private int row;
    private int col;
    
    public boolean dead() {
        return(this.health <= 0);
    }

    public float attack() {
    return Dice.Intensity(this.getStrength());
    }

    public void setPos(int row, int col) {
    this.row = row;
    this.col = col;
    }

    public String toString() {
    return "Monstruo{" + 
               "', Inteligencia=" + this.getIntelligence() + 
               ", Fuerza=" + this.getStrength() + 
               ", Vida=" + this.getHealth() + 
               ", Fila=" + this.getRow() + 
               ", Columna=" + this.getCol() + "}\n";
    }

    private void gotWounded() {
    this.health = this.health - 1;
    }

}
