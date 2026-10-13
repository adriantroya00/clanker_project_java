package irrgarten;

/**
 * @authors Alvaro Perez Sanchez y Adrian Hernando Leal 
 */
public class Shield {
    private float protection;
    private int uses;

        public Shield(float _protection, int _uses) {
            this.protection = _protection;
            this.uses = _uses;
        }

        public float protect(){
            if(uses>0){
                uses--;
                return protection;
            }
            return 0;
        }

        public String toString(){
            return "S[" + protection + ", " + uses + "]";
        }

        public boolean discard() {
            return Dice.discardElement(uses);
    }
}
