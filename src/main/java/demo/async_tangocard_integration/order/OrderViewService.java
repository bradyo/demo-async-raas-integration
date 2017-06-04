package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.UserView;
import org.springframework.stereotype.Service;

@Service
public class OrderViewService {
    
    public OrderView createView(Order order) {
        OrderView orderView = new OrderView();
        orderView.setReferenceNumber(order.getReferenceNumber());
        return orderView;
    }
    
    public FullOrderView createFullView(Order order) {
        FullOrderView fullOrderView = new FullOrderView();
        fullOrderView.setReferenceNumber(order.getReferenceNumber());
        fullOrderView.setAmount(order.getAmount());
        fullOrderView.setStatus(order.getStatus());
        fullOrderView.setTangoCardExternalId(order.getTangoCardExternalId());
        fullOrderView.setTangoCardOrderRefId(order.getTangoCardOrderRefId());
        fullOrderView.setTries(order.getTries());

        UserView userView = new UserView();
        userView.setId(order.getUser().getId());
        userView.setName(order.getUser().getName());
        userView.setEmailAddress(order.getUser().getEmailAddress());
        fullOrderView.setUser(userView);
        
        return fullOrderView;
    }
    
}
