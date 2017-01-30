package ua.com.codefire.fxbariga;

/**
 * Created by codefire on 23.01.17.
 */
public class Currency {

    private String name;
    private double buy;
    private double sale;

    public Currency(String name, double buy, double sale) {
        this.name = name;
        this.buy = buy;
        this.sale = sale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }
}

