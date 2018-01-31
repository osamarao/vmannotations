package osama.me.viewmodelannotations;


public class AKindOfObject{
    String name;
    Integer amount;

    public AKindOfObject(final String name, final Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }
}