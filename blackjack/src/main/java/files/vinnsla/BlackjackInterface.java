package files.vinnsla;

public interface BlackjackInterface {
    /**
     * nær í spil af handahófi úr stokk af 52 spilum
     * @return skilar spili
     */
    SpilV getCard();

    /**
     * bætir random spili í hendi leikmanns
     * @param l Leikmadur sem á að fá spil á hendi
     */
    void addCard(Leikmadur l);

    /**
     * athugar hvort player eða dealer hafi unnið
     * @param p Leikmaður player
     * @param d Leikmaður dealer
     * @param sidasta boolean, segir til um hvort sé síðasta umferð
     * @return skilar þeim leikmanni sem hefur unnið,
     * ef enginn hefur unnið, skilar null
     */
    Leikmadur win(Leikmadur p, Leikmadur d, boolean sidasta);

    int whatRound();
}
