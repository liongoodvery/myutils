package org.lion.beans;

import org.lion.utils.Nationality;

import java.util.Comparator;
import java.util.Random;

public class Person implements Comparator<Person> {
    private String name;
    private int age;
    private Nationality nation;

    public Person() {
    }

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
        Nationality[] values = Nationality.values();
        Random random = new Random();
        this.nation = values[random.nextInt(values.length)];
    }

    public Person(String name, int age, Nationality nation) {
        this.name = name;
        this.age = age;
        this.nation = nation;
    }

    public Nationality getNation() {
        return nation;
    }

    public void setNation(Nationality nation) {
        this.nation = nation;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        Nationality.valueOf("CHINA");
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", nation=" + nation
                + "]";
    }


    @Override
    public int compare(Person p1, Person p2) {
        int i1 = p1.getName().compareTo(p2.getName());
        if (i1 != 0)
            return i1;
        return p1.getAge() - p2.getAge();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((nation == null) ? 0 : nation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (age != other.age)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (nation != other.nation)
            return false;
        return true;
    }
}
