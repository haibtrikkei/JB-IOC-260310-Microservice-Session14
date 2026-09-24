package ra.orderservice.config;

import org.springframework.stereotype.Service;
import ra.orderservice.dto.DataSend;

@Service
public class Receiver {
    public void receiveMessage(String message) {
        System.out.println("----------- receive data send ----------->>>");
        System.out.println("Message: "+message);
    }
}
