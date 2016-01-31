package Guest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InfoView {
	public JTextArea Title, Descr;
	JFrame frame;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	int imageW = 20 * width / 100;
	int imageH = 40 * height / 100;

	public void main(int X, int Y, spot spot) {
		frame = new JFrame();
		frame.setUndecorated(true);
		JPanel guestView = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Image image = new ImageIcon(this.getClass().getResource("/papiros.png")).getImage();
				g.drawImage(image, 0, 0, imageW, imageH, this);
			}
		};
		guestView.setOpaque(false);
		Title = new JTextArea();
		if (spot.GetTitle().equals(""))
			Title.setText("Replace this with your Title.");
		else
			Title.setText(spot.GetTitle());
		Title.setBounds(imageW / 3, imageH / 6, imageW - imageW / 2, imageH / 12);
		Title.setLineWrap(true);
		Title.setOpaque(false);
		guestView.add(Title);
		guestView.setLayout(null);
		Descr = new JTextArea();
		if (spot.GetDescr().equals(""))
			Descr.setText("Replace this with your Description.");
		else
			Descr.setText(spot.GetDescr());
		Descr.setBounds(imageW / 4, imageH / 4, imageW - imageW / 3, imageH - imageH / 2);
		Descr.setLineWrap(true);
		Descr.setOpaque(false);
		guestView.add(Descr);
		frame.getContentPane().add(guestView);
		frame.setSize(imageW, imageH);
		if (X > width / 2)
			frame.setLocation( 2, 2);
		else
			frame.setLocation(width  - imageW - 2,  2);
		frame.setAlwaysOnTop(true);
		frame.show();
	}

	public void kill() {
		frame.dispose();
	}
}
