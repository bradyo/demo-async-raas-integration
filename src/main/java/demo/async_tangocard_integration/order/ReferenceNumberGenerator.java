package demo.async_tangocard_integration.order;

import org.springframework.stereotype.Service;

@Service
public interface ReferenceNumberGenerator {
    
    String generate();
    
}
