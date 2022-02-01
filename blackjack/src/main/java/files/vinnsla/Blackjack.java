package files.vinnsla;

public class Blackjack {
    private int round = 0;
    private Stokkur stokkur;

    public void nyrLeikur(Leikmadur p, Leikmadur d) {
        round = 0;
        stokkur = new Stokkur();
        p.nyrLeikur();
        d.nyrLeikur();
    }

    public void dragaSpil(Leikmadur p, Leikmadur d) {
        p.gefaSpil(stokkur.dragaSpil());
        if(d.getSamtals() < 17) d.gefaSpil(stokkur.dragaSpil());
    }

    public boolean ehvBuinn(Leikmadur p, Leikmadur d) {
        if (p.getSamtals() >= 21 || d.getSamtals() > 21) return true;
        return false;
    }

    public void newRound() {
        round++;
    }

    public boolean isLastRound() {
        if (round > 3) return true;
        else return false;
    }

    public void isDealerDone(Leikmadur d, Leikmadur p) {
        if (p.getSamtals() <= 21) {
            while (d.getSamtals() < 17) d.gefaSpil(stokkur.dragaSpil());;
        } 
    }
}
