package order;

import lombok.Getter;
import lombok.Setter;
import model.Product;
import order.model.Order;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_products")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;



}