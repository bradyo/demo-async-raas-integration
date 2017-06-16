package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orders")
@Builder
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String referenceNumber;
    private BigDecimal amount;
    private String raasExternalId;
    private String raasOrderRefId;
    private Integer tries;
    private String status;
    
    @OneToOne
    private User user;
    
}
