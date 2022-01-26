package hi.verkefni.vinnsla;

import java.io.Console;

public class BingoSpjald implements BingospjaldInterface{

    boolean[] gildi = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
    int[] B = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    int[] I = {16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    int[] N = {31,32,33,34,35,36,37,38,39,40,41,42,43,44,45};
    int[] G = {46,47,48,49,50,51,52,53,54,55,56,57,58,59,60};
    int[] O = {61,62,63,64,65,66,67,68,69,70,71,72,73,74,75};
    int[][] bingo = {B,I,N,G,O};

    boolean[][] spjald = new boolean[5][5];

    boolean skaupp = false;
    boolean skanid = false;


    /**
     * hjálparfall til þess að ákvarða gildi hvers takka á spjaldinu
     * tekur inn heiltölufylki með 15 tölum sem eiga við hvern staf eins og skilgreint er í reglum um bingó
     * þ.e. B hefur 1-15, I hefur 16-30 osfr.
     * tekur líka inn boolean fylki sem heldur utan um hvort talan hafi verið valin fyrir annan reit og
     * lætur fallið velja aðra tölu ef svo er
     * @param arr   heiltölufylki með 15 tölum sem tilheyra staf á bingóspjaldinu
     * @param gildi boolean fylki sem heldur utan um hvort tala hafi verið valin, núllstillt eftir að skipað hefur verið í heila röð
     * @return      heiltala sem er svo skrifuð á takka
     */
    private int getRandom(int[] arr, boolean[] gildi) {
        int n = arr.length;
        int i = (int)(Math.random()*((n)));

        if (!gildi[i]) {
            while (!gildi[i]) {
                i = (int)(Math.random()*((n)));
            }
        }
        gildi[i] = false;

        // System.out.println(arr[i]);

        return arr[i];
    }

    /**
     * fer yfir alla reiti sem hafa sama x gildi og tekið er inn og lætur vita ef sú röð hefur bingó
     * @param x x hnit takka sem var verið að smella á
     */
    private boolean checkLodrettBingo(int x) {
        for (int i = 0; i < 5; i++) {
            // System.out.println("lod: " + x + " " + i);
            // System.out.println(spjald[x][i]);
            if (!spjald[x][i]) break;
            if (i == 4) {
                System.out.println("Lóðrétt bingó!");
                return true;
            }
        }
        return false;
    }

    /**
     * fer yfir alla reiti sem hafa sama y gildi og tekið er inn og lætur vita ef sú röð hefur bingó
     * @param y y hnit takka sem var verið að smella á
     */
    private boolean checkLarettBingo(int y) {
        for (int j = 0; j < 5; j++) {
            // System.out.println("la" + j + " " + y);
            // System.out.println(spjald[j][y]);
            if (!spjald[j][y]) break;
            if (j==4) {
                System.out.println("Lárétt bingó!");
                return true;
            }
        }
        return false;
    }

    /**
     * athugar í hvert skipti sem smellt er á takka hvort ská röðin frá efsta vinstra horninu niður í neðsta
     * hægra hornið hafi bingó og lætur vita ef það er svo
     */
    private boolean checkSkaNidurBingo() {
        for (int i = 0; i < 5; i++) {
            // System.out.println(i+" "+i);
            // System.out.println(spjald[i][i]);
            if (!spjald[i][i]) return false;
            if (i == 4) {
                System.out.println("Ská niður bingó!");
                skanid = true;
                return true;
            }
        }
        return false;
    }

    /**
     * athugar í hvert skipti sem smellt er á takka hvort ská röðin frá neðsta vinstra horninu upp í efsta
     * hægra hornið hafi bingó og lætur vita ef það er svo
     */
    private boolean checkSkaUppBingo() {
        for (int i = 0; i < 5; i++) {
            // System.out.println((i)+" "+(4-i));
            // System.out.println(spjald[(4-i)][(4-i)]);
            if (!spjald[(i)][(4-i)]) return false;
            if (i == 4) {
                System.out.println("Ská upp bingó!");
                skaupp = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void aReit(int i, int j) {
        spjald[i][j] = true;
    }

    @Override
    public int[][] nyttSpjald() {
        int[][] spjald = new int[5][5];
        for (int i = 0; i < 5; i++) {
            // System.out.println(i);
            for (int j = 0; j < 5; j++) {
                // System.out.print(j);
                spjald[i][j] = getRandom(bingo[i],gildi);
            }
            for (int u = 0; u<15; u++) {
                gildi[u] = true;
            }
        }
        // System.out.println(Arrays.deepToString(spjald));
        return spjald;
    }

    @Override
    public boolean erBingo(int x, int y) {
        if (checkLodrettBingo(x) || checkLarettBingo(y)) {return true;}
        if (!skanid && checkSkaNidurBingo()) {return true;}
        if (!skaupp && checkSkaUppBingo()) {return true;}
        return false;
    }
}
