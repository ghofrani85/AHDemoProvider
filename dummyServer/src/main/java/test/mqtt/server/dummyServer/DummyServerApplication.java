package test.mqtt.server.dummyServer;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// get mqtt server and topic from this server
@SpringBootApplication
public class DummyServerApplication {

	public static MqttClient client;

	public static void main(String[] args) throws MqttException {

		SpringApplication.run(DummyServerApplication.class, args);

		// Generate a random ID
		Random rnd = new Random();
		int ID = rnd.nextInt(999999);

		// New MQTT Client
		String mqttServer = "tcp://127.0.0.1:1883";
		client = new MqttClient(mqttServer, "" + ID);

		// Connect MQTT Client to Broker
		MqttConnectOptions mqOptions = new MqttConnectOptions();
		mqOptions.setCleanSession(true);
		client.connect(mqOptions);

		// Send Temperature, image and Lighting value every 10 seconds
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					sendTemperatureValue();
					sendLightingValue();
					sendImage();
					System.out.println("Temperature, image and Lighting value successfully transmitted to Broker.");
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, new Date(), 10000);

	}

	public static void sendTemperatureValue() throws MqttException {

		// Generate random Temperature
		Random rnd = new Random();
		int temp = rnd.nextInt(35);
		String tempToSend = "" + temp;

		// Generate new MqttMessage and publish it
		MqttMessage msg = new MqttMessage(tempToSend.getBytes());
		client.publish("Temperature", msg);

	}

	public static void sendLightingValue() throws MqttException {

		// Generate random boolean to state, if light is switched on or off
		Random rnd = new Random();
		boolean light = rnd.nextBoolean();
		int lightInt = light ? 1 : 0;
		String lightToSend = "" + lightInt;

		// Generate new MqttMessage and publish it
		MqttMessage msg = new MqttMessage(lightToSend.getBytes());
		client.publish("Light", msg);

	}

	public static void sendImage() throws MqttException {
		
		// Create a random image
		// Set a path for the image
		String path = "./Output.jpg";
		
		// First set width and height of the image
		int width = 50;
		int height = 50;
		
		// call function for random image
		BufferedImage img = CreateImage.makeOneColorImage(width, height);
		
		File f = null;
		
		// Write the created image into ./Output.jpg
		try {
			f = new File(path);
			ImageIO.write(img, "jpg", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// read the created image from files and encode it to a String
		String imageAsString = EncoderDecoder.encodeToString(img, "jpg");
        System.out.println("Image as String: "+imageAsString);
		
        // publish the image String
        MqttMessage message = new MqttMessage(imageAsString.getBytes());
        client.publish("Image", message);
        System.out.println("Image was published.");
		
	}

}
