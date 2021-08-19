package test.mqtt.server.dummyServer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreateImage {
	public static BufferedImage makeOneColorImage(int width, int height) {
		// Create a BufferedImage
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create random rgb values
		int r = (int) (Math.random() * 256);
		int g = (int) (Math.random() * 256);
		int b = (int) (Math.random() * 256);

		// Fill the whole image with these rgb values
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = (r << 16) | (g << 8) | b; // pixel. r, g and b have to be shifted to the correct bit positions
				img.setRGB(x, y, p);
			}
		}
		return img;
	}

	public static void makeFaceImage() {
		JFrame frame = new JFrame("TheFrame");
		frame.setBounds(10, 10, 300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Random random = new Random();

		JPanel p = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				g.fillRect(50, 50, 200, 200);
			}

		};
		frame.add(p);
		frame.setVisible(true);

	}
}
