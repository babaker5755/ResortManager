package sample;

public abstract class DetailedResortSummaryReport {
    public final static double TAX_RATE = 0.0665;

    public double calculateTax(double charge){
        return charge * TAX_RATE;
    }
    public double calculateCostWithTax(double charge){
        return calculateTax(charge) + charge;
    }

}