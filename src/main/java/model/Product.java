package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import order.model.Order;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Value> values;


    @Override
    public String toString() {
        return "Product: " + "\n" +
                "id: " + id + "\n" +
                "name: " + name + "\n" +
                "price: " + price + "\n";
    }

    @Getter
    @OneToMany(targetEntity = Option.class)
    @JoinColumn(name = "category_id")
    private List<Option> options;

    @ManyToMany(mappedBy = "productList")
    private List<Order> orderList;

}
