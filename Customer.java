public class Customer {
    private String cnic;
    private String name;
    private String phoneNo;
    private String email;

    public Customer(String cnic, String name, String phoneNo, String email) {
        this.cnic = cnic;
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
