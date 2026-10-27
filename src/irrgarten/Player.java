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

    public float attack() {
        return this.getStrength() + this.sumWeapons();
    }

    public boolean defend() {
        return this.manageHit();
    }

    public String toString() {
         return "Jugador{" + 
          "'"+ super.toString() + 
          ", Impactos consecutivos=" + this.consecutiveHits + 
          ", Armas=" + this.weapons + 
          ", Escudos=" + this.shields + "}\n";
    }

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private float defensiveEnergy() {
        return this.getIntelligence() + this.sumShields();
    }

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
                sumTotal += shield.defend();
            }
        }
        return sumTotal;
    }



}


