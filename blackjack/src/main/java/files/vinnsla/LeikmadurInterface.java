package files.vinnsla;

public interface LeikmadurInterface {
    int getSamtals();

    String getNafn();

    void setNafn(String nafn);

    void gefaSpil(SpilV s);

    boolean vinnurDealer(Leikmadur d);

    Leikmadur hvorVann(Leikmadur d);

    void nyrLeikur();
}
