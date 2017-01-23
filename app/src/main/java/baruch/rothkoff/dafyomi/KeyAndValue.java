package baruch.rothkoff.dafyomi;


public class KeyAndValue<T,H> {
    private T key;
    private H value;

    public KeyAndValue(T key, H value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public H getValue() {
        return value;
    }

    public void setValue(H value) {
        this.value = value;
    }
}
