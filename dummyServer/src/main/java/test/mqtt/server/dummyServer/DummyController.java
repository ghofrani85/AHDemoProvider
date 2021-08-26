 package test.mqtt.server.dummyServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
	@GetMapping ("/getServerInfo")
	public String getDummyServerInfo() {
		 // Broker-Address and Topic
		return "{\"mqttserver\": \"tcp://127.0.0.1\", \"topic\": \"Image\"}";
	}
}
