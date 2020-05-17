package by.vladyka.discoveryserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Man {
    private int a;

    public Man(int a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Man man = (Man) o;
        return a == man.a;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public static void main(String[] args) {
        Man m1= new Man(1);
        Man m2= new Man(2);

        Set<Man> set = new HashSet<>();
        set.add(m1);
        set.add(m2);

        for (Man m :set) {
            System.out.println(m);
        }

        HashMap<Key, Man> map = new HashMap<>();
        Key k1 = new Key(1);
        Key k2 = new Key(2);

        Man man1 = new Man(1);
        Man man2 = new Man(2);

        map.put(k1, m1);
        map.put(k2, m2);


    }

    @Override
    public String toString() {
        return "Man{" +
                "a=" + a +
                '}';
    }
}
