package irrgarten;

int main() {
    
    Weapon espada = new Weapon(5,5);

    if(espada.discard()){
        usesLeft--;
        System.out.println("NO se ha descartado el objeto");
        espada.toString();

    } else {
        System.out.println("SI se ha descartado el objeto");
    }
}