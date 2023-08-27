package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemEntity {
    @Id
    @Column(name = "ItemCode", length = 6, nullable = false)
    private String itemCode;

    @Column(name = "Description", length = 50, nullable = false)
    private String description;

    @Column(name = "PackSize", length = 20)
    private String packSize;

    @Column(name = "UnitPrice", nullable = false)
    private Double unitPrice;

    @Column(name = "QtyOnHand", nullable = false)
    private Integer qtyOnHand;

}
