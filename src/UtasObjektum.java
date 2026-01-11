import java.util.Random;

public class UtasObjektum implements Utas {

    private String jegy;
    private int penz;

    public UtasObjektum() {
        this.jegy = null;
        this.penz = utasPenzEgyenleg();
    }

    @Override
    public int utasPenzEgyenleg() {
        Random rnd = new Random();
        return rnd.nextInt(1000);
    }

    public void setJegy(String ujJegy) {
        this.jegy = ujJegy;
    }

    public int getPenz() {
        return penz;
    }

    public void fizetJegyert(int osszeg) {
        this.penz -= osszeg;
    }
}
