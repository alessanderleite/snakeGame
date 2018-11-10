package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	// Render
	private Graphics2D g2d;
	private BufferedImage image;
	// Game Loop
	private Thread thread;
	private boolean running;
	private long targetTime;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}
	
	private void setFPS(int fps) {
		targetTime = 1000 / fps;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void run() {
		if(running) return;
		init();
		long startTime;
		long elapsed;
		long wait;
		while(running) {
			startTime = System.nanoTime();
			
			update();
			requestRender();
			
			elapsed = System.nanoTime() - startTime;
			wait  = targetTime - elapsed / 1000000;
			if(wait > 0) {
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
		running = true;
		setFPS(10);
	}
	private void requestRender() {
		render(g2d);
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
	private void update() {
		
	}
	public void render(Graphics2D g) {
		g2d.clearRect(0, 0, WIDTH, HEIGHT);
	}
}
