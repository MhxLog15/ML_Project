package Guest;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuestView extends JFrame {
	List<JButton> buttonList = new ArrayList<JButton>();
	List<spot> spotList = new ArrayList<spot>();
	int counter = -1;
	static database database = new database();
	InfoView infoView;
	spot spot;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GuestView guestView = new GuestView();
				guestView.setVisible(true);
			}
		});
	}

	public GuestView() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if (infoView != null) {
					infoView.kill();
				}
				database.CloseConnection();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(0, 0, width, height);
		getContentPane().setLayout(null);

		database.NewConnection();
		database.GetSpots(spotList);
		Iterator<spot> s = spotList.iterator();
		while (s.hasNext()) {
			spotview(s.next());
		}
		setResizable(false);
	}

	void spotview(spot tsp) {
		counter++;
		JButton button = new JButton("") {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Image img = new ImageIcon(this.getClass().getResource("/point.png")).getImage();
				g.drawImage(img, 0, 0, width / 50, height / 25, this);
			}
		};
		button.setBounds(tsp.GetX() - 10, tsp.GetY() - 10, width / 50, height / 25);
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i;
				for (i = 0; i < buttonList.size(); i++)
					if (buttonList.get(i).hashCode() == e.getSource().hashCode())
						break;
				infoView = new InfoView();
				infoView.main(tsp.GetX() * width / getWidth(), tsp.GetY() * height / getHeight(), spotList.get(i));

			}

		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if (!(infoView == null))
					infoView.kill();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		button.setName(Integer.toString(tsp.GetId()));
		buttonList.add(button);
		add(buttonList.get(counter));
		repaint();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
