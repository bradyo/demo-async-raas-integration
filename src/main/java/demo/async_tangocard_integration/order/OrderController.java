package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.User;
import demo.async_tangocard_integration.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    
    private final UserService userService;
    private final OrderService orderService;
    private final OrderQueueService orderQueueService;
    private final OrderPostValidator orderPostValidator;
    private final OrderViewService orderViewService;
    
    @GetMapping("/internal/orders")
    public List<FullOrderView> list() {
        return orderService.getOrders().stream()
            .map(orderViewService::createFullView)
            .collect(Collectors.toList());
    }
    
    @PostMapping("/orders")
    public OrderView post(@RequestBody OrderPost orderPost) {
        User user = userService.getCurrentUser();
        
        ValidatedOrderPost validatedOrderPost = orderPostValidator.validate(orderPost);
        
        Order order = orderService.placeOrder(user, validatedOrderPost.getAmount());

        orderQueueService.queueOrder(order.getId());
        
        return orderViewService.createView(order);
    }
    
}
