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

    public void newRound() {
        round++;
    }

    public boolean isLastRound() {
        if (round > 3) return true;
        else return false;
    }

    public void isDealerDone(Leikmadur d) {
        while (d.getSamtals() < 17) d.gefaSpil(stokkur.dragaSpil());;
    }
}
