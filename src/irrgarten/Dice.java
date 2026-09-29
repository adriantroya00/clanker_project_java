package irrgarten;
import java.util.Random;



public class Dice {
    private int MAX_USES = 5; // Numero maximo de usos de armas y escudos
    private float MAX_INTELLIGENCE = 10.0f; // Valor maximo para la inteligencia de jugadores y monstruos
    private float MAX_STRENGTH = 10.0f; // Valor maximo para la fuerza de jugadores y monstruos
    private float RESURRECT_PROB = 0.3f; // Probabilidad de que un jugador sea resucitado en cada turno
    private int WEAPONS_REWARD = 2; // Numero maximo de armas recibidas al ganar un combate
    private int SHIELDS_REWARD = 3; // Numero maximo de escudos recibidos al ganar un combate
    private int HEALTH_REWARD = 5; // Numero maximo de unidades de salud recibidas al ganar un combate
    private int MAX_ATTACK = 3; // Maxima potencia de las armas
    private int MAX_SHIELD = 2; // Maxima potencia de los escudos

    private Random generator = new Random();

    public int randomPos(int max){
        return generator.nextInt(max);
    }

    public int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }

    public float randomIntelligence(){
        return generator.nextFloat() * MAX_INTELLIGENCE ;
    }

    public float randomStrength(){
        return generator.nextFloat() * MAX_STRENGTH;
    }


}
