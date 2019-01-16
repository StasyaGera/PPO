package ru.akirakozov.sd.refactoring;

public class Product {
    private String name;
    private long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Product other = (Product) o;
        return (this.name == null ? other.name == null : this.name.equals(other.name))
                && this.price == other.price;
    }
}
