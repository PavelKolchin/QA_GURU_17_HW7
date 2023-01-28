package quru_qa.data;

public enum Model {
    MODEL_3("Model 3"),
    MODEL_S("Model S"),
    MODEL_X("Model X");

    private final String value;

    Model(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
