package files.vinnsla;

public class SpilV {
    private final Tegund tegund;
    private final Gildi gildi;

    public SpilV(Tegund t, Gildi g) {
        tegund = t;
        gildi = g;
    }

    /**
     * skilar gildi spilsins
     * @return ef alvöru spil skilar Hjarta,Spaði,Tígull eða Lauf, ef óþekkt skilar Óþekkt sort
     */
    public Gildi getGildi() {
        return gildi;
    }

    public int getVirdi() {
        return gildi.getGildi();
    }

    public Tegund getTegund() {
        return tegund;
    }

    public String toString() {
        return gildi+" "+tegund.name();
    }
}