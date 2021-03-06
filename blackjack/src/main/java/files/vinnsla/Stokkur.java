package files.vinnsla;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir spilastokk
 *
 *
 *****************************************************************************/
public class Stokkur {

    private final SpilV[] stokkur = new SpilV[52];
    private final boolean [] notadSpil = new boolean[52];
    private int fjoldi=0;   // fjöldi spila sem búið er að draga úr stokknum

    /**
     * Upphafsstillir spilastokk með 52 spilum
     */
    public Stokkur() {
        int i = 0;
        for (Tegund t : Tegund.values()) {
            for (Gildi g : Gildi.values()) {
                stokkur[i++] = new SpilV(t, g);
            }
        }
    }

    /**
     * Dregur spil af handahófi. Gæti þess að sama spilið sé ekki dregið
     *
     * @return spil af handahófi ef eitthvert spil er eftir í stokknum annars null
     */
    public SpilV dragaSpil() {
        int naesta;
        if (fjoldi == 52)
            return null;
        do {
            naesta = (int) (Math.random() * 52);
        } while (notadSpil[naesta]);
        notadSpil[naesta] = true;
        fjoldi++;
        return stokkur[naesta];
    }
}
