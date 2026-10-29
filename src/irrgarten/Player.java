package irrgarten;

import java.util.ArrayList;

public class Player {

    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits = 0;

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    //el nombre se debe inicializar como Player#numero
    public Player( char _number){
        this.number = _number;
        this.name = "Player#"+this.number;
    }

    public void resurrect() {
        this.weapons.clear();
        this.shields.clear();
        this.health = INITIAL_HEALTH;
        this.consecutiveHits = 0;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean dead() {
        return(this.health <= 0);
    }

    //public Directions move(Directions direction, Directions[] validMoves){}

    public float attack() {
        return this.strength + this.sumWeapons();
    }

    public boolean defend( float receivedAttack ) {
        return this.manageHit(receivedAttack);
    }

    public void receiveReward(){}

    public String toString() {
        return "Jugador {" + 
                "\n Nombre = " + this.name +
                "\n Numero = " + this.number +
                "\n Inteligencia = " + this.intelligence + 
                "\n Fuerza = " + this.strength + 
                "\n Vida = " + this.health + 
                "\n Fila = " + this.row + 
                "\n Columna = " + this.col +
                "\n Hits Consecutivos = " + this.consecutiveHits +
                "\n}\n";
    }

    private void receiveWeapon(Weapon w){}

    private void receiveShield(Shield s){}

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private float defensiveEnergy() {
        return this.intelligence + this.sumShields();
    }


    //private boolean manageHit( float receivedAttack ) {}
    
    private void resetHits() {
        this.consecutiveHits = 0;
    }
    
    private void gotWounded() {
        this.health = this.health - 1;
    }

    private void incConsecutiveHits() {
       this.consecutiveHits = this.consecutiveHits + 1; 
    }

    private float sumWeapons() {
        float sumTotal = 0.0f;
        for (Weapon weapon : this.weapons) {
            if (weapon != null) {
                sumTotal += weapon.attack();
            }
        }
        return sumTotal;
    }

    private float sumShields() {
        float sumTotal = 0.0f;
        for (Shield shield : this.shields) {
            if (shield != null) {
                sumTotal += shield.protect();
            }
        }
        return sumTotal;
    }



}


