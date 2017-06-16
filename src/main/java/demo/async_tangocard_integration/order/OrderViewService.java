package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.UserView;
import org.springframework.stereotype.Service;

@Service
public class OrderViewService {
    
    public OrderView createView(Order order) {
        return OrderView.builder()
            .referenceNumber(order.getReferenceNumber())
            .build();
    }
    
    public FullOrderView createFullView(Order order) {
        return FullOrderView.builder()
            .id(order.getId())
            .referenceNumber(order.getReferenceNumber())
            .amount(order.getAmount())
            .status(order.getStatus())
            .raasExternalId(order.getRaasExternalId())
            .raasOrderRefId(order.getRaasOrderRefId())
            .tries(order.getTries())
            .user(
                UserView.builder()
                    .id(order.getUser().getId())
                    .name(order.getUser().getName())
                    .emailAddress(order.getUser().getEmailAddress())
                    .build()
            )
            .build();
    }
    
}
