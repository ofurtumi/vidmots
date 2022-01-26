package hi.verkefni.vinnsla;

public interface BingospjaldInterface {
    /**
     * Talan á reit (i,j) hefur verið lesin upp. Reiturinn er merktur sem -1 í
     fylkinu.
     * Forskilyrði - (i,j) er innan marka bingóspjaldsins - óþarfi að tékka
     sérstaklega
     *
     * @param i
     * @param j
     */
    void aReit(int i, int j);
    /**
     * Frumstillir bingóspjald með tölum af handahófi
     *
     * @return skilar fylkinu með gögnum bingóspjaldsins
     */
    int[][] nyttSpjald();
    /**
     * Athugar hvort það er bingó í hornalínu spjaldsins
     *
     * @return true ef það er bingo annars false
     */
    boolean erBingo(int x, int y);
}