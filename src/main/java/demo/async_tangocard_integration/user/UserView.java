package demo.async_tangocard_integration.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {
    
    private Long id;
    private String name;
    private String emailAddress;
}
