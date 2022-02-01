package files.vinnsla;
import java.util.Arrays;

public class Leikmadur implements LeikmadurInterface{
    private String name;
    private int total;
    private SpilV[] hand = new SpilV[5];
    private int nHand;
    private int wins;

    public Leikmadur(String n) {
        name = n;
    }

    public int getSamtals() {
        return total;
    }

    public String getNafn() {
        return name;
    }

    public void setNafn(String n) {
        name = n;
    }

    public void gefaSpil(SpilV s) {
        hand[nHand++] = s;
        total += s.getVirdi();
    }

    public boolean vinnurDealer(Leikmadur d) {
        if (total < d.getSamtals()) return true;
        return false;
    }

    public void won() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public Leikmadur hvorVann(Leikmadur d) {
        if (this.getSamtals() == d.getSamtals()) return null;
        else if (this.getSamtals() == 21 || d.getSamtals() > 21) return this;
        else if (d.getSamtals() == 21 || this.getSamtals() > 21) return d;
        else if (vinnurDealer(d)) return d;
        else return this;
    }

    public void nyrLeikur() {
        nHand = 0;
        for (int i = 0; i < hand.length; i++) {
            hand[i] = null;
        }
        total = 0;
    }

    public String toString() {
        return  "Leikmaður {"+
        "Nafn: " + name + 
        ", Hendi: " + Arrays.deepToString(hand) +
        ", Stig: " + total +
        "}";
    }
}
