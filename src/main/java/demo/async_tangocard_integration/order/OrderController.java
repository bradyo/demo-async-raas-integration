package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.User;
import demo.async_tangocard_integration.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    
    private final UserService userService;
    private final OrderService orderService;
    private final OrderPostValidator orderPostValidator;
    private final OrderViewService orderViewService;
    
    @PostMapping("/orders")
    public OrderView post(@RequestBody OrderPost orderPost) {
        User user = userService.getCurrentUser();
        
        ValidatedOrderPost validatedOrderPost = orderPostValidator.validate(orderPost);
        
        Order order = orderService.placeOrder(user, validatedOrderPost.getAmount());
        
        orderService.queueOrder(order.getId());
        
        return orderViewService.createView(order);
    }
    
}
