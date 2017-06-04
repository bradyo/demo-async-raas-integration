package demo.async_tangocard_integration.order;

import org.springframework.stereotype.Service;

@Service
public class OrderViewService {
    
    public OrderView createView(Order order) {
        OrderView orderView = new OrderView();
        orderView.setReferenceNumber(order.getReferenceNumber());
        return orderView;
    }
    
}
