package ran.com.swastikgold;

public class ClientData {
   private String name, phone, rate, quantity, total, pending, key;

    public ClientData() {
    }

    public ClientData(String name, String phone, String rate, String quantity, String total, String pending, String key) {
        this.name = name;
        this.phone = phone;
        this.rate = rate;
        this.quantity = quantity;
        this.total = total;
        this.pending = pending;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
