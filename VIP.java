public class VIP implements Amount {
    private int amount = 1500;

    public VIP() {
    }

    @Override
    public double calculateAmount(double passengers) {
        return amount * passengers;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
