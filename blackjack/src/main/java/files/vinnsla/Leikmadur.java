package files.vinnsla;
import java.util.Arrays;

import files.vidmot.Spil;

public class Leikmadur implements LeikmadurInterface{
    private String name;
    private int total;
    private SpilV[] hand = new SpilV[5];
    private int nHand;
    private int wins;
    private int index;

    public Leikmadur(String n, int i) {
        name = n;
        index = i;
    }

    public int getSamtals() {
        return total;
    }

    public int getIndex() {
        return index;
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

    public String handToString() {
        String hendi = "";
        for (SpilV s : hand) {
            if (s != null) hendi += "\n"+s.toString();
        }
        return hendi;
    }

    public SpilV[] getHand() {
        return hand;
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
