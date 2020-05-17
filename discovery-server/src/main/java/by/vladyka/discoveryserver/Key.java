package by.vladyka.discoveryserver;

import java.util.Objects;

public class Key{
    private int field;

    public Key(int field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return field == key.field;
    }

    @Override
    public int hashCode() {

        return Objects.hash(field);
    }

}
