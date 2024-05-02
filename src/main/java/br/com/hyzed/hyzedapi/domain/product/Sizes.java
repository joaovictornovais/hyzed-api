package br.com.hyzed.hyzedapi.domain.product;

public enum Sizes {

    P("p"),
    M("m"),
    G("G"),
    GG("GG");

    private String size;

    private Sizes(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

}
