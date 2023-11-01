public class Economy implements Amount {
    private int amount = 1000;

    public Economy() {
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
