package irrgarten;

public class TestP1 {
    public static void main(String[] args) {
    
        Weapon espada = new Weapon(5,5);

        

        for(int i = 0; i<5 ; i++){
            if(espada.discard()){
                espada.reduceUses();
                System.out.println("NO se ha descartado el objeto");
                System.out.println(espada.toString());

            } else {
                System.out.println("SI se ha descartado el objeto");
            }
        }
    }
}
