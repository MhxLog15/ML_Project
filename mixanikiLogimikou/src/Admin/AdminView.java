package Admin;

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

public class AdminView extends JFrame {
	boolean spotNew = false;
	boolean spotEdit = false;
	boolean spotDelete = false;
	boolean gotInfo = false;
	List<JButton> buttonList = new ArrayList<JButton>();
	List<spot> spotList = new ArrayList<spot>();
	int counter = -1;
	static database database = new database();
	JPanel guestView;
	InfoView infoView;
	spot spot;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	private JButton buttonNew, buttonEdit, buttonDelete, buttonConf, buttonCanc, buttonQuit;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AdminView adminView = new AdminView();
				adminView.setVisible(true);
			}
		});
	}

	public AdminView() {
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
		guestView = new JPanel();
		guestView.setBorder(new LineBorder(new Color(0, 0, 0)));
		guestView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (spotNew)
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (spotNew)
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (spotNew) {
					spot = new spot();
					spot.SetX(width * e.getX() / width + width * 1 / 100);
					spot.SetY(height * e.getY() / height + width * 1 / 100);
					infoView = new InfoView();
					Point location = MouseInfo.getPointerInfo().getLocation();
					infoView.main(location.x, location.y, spot);
					spotNew = false;
					gotInfo = true;
				}
			}
		});
		guestView.setBounds(width * 1 / 100, height * 1 / 100, width - width * 10 / 100 - width * 1 / 100,
				height - height * 2 / 100 - height * 1 / 100);
		getContentPane().add(guestView);
		guestView.setLayout(null);

		buttonNew = new JButton("New");
		buttonNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				spotNew = true;
				buttonEdit.setEnabled(false);
				buttonDelete.setEnabled(false);
				buttonConf.setVisible(true);
				buttonCanc.setVisible(true);
			}
		});
		buttonNew.setBounds(width - width * 9 / 100, height * 1 / 100, width * 8 / 100, height * 6 / 100);
		getContentPane().add(buttonNew);

		buttonEdit = new JButton("Edit");
		buttonEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!(spotList.isEmpty()))
					spotEdit = true;
				buttonNew.setEnabled(false);
				buttonDelete.setEnabled(false);
				buttonConf.setVisible(true);
				buttonCanc.setVisible(true);

			}
		});
		buttonEdit.setBounds(width - width * 9 / 100, height * 1 / 100 * 2 + height * 6 / 100, width * 8 / 100,
				height * 6 / 100);
		getContentPane().add(buttonEdit);
		buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (counter > -1) {
					buttonCanc.setVisible(true);
					buttonConf.setVisible(true);
					buttonConf.setEnabled(false);
					buttonEdit.setEnabled(false);
					buttonNew.setEnabled(false);
					spotDelete = true;
				}
			}
		});
		buttonDelete.setBounds(width - width * 9 / 100, height * 1 / 100 * 3 + height * 6 / 100 * 2, width * 8 / 100,
				height * 6 / 100);
		getContentPane().add(buttonDelete);
		buttonConf = new JButton("Confirm");
		buttonConf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (infoView != null) {
					if ((infoView.Title.equals("") || infoView.Descr.equals("Type your Name here:")))
						JOptionPane.showMessageDialog(null, "Invailid Name", "Alert", JOptionPane.ERROR_MESSAGE);
					else
						spot.SetTitle(infoView.Title.getText());
					if ((infoView.Title.equals("") || infoView.Descr.equals("Type your Description here...")))
						JOptionPane.showMessageDialog(null, "Invailid Description", "Alert", JOptionPane.ERROR_MESSAGE);
					else
						spot.SetDescr(infoView.Descr.getText());
					if (gotInfo) {
						spot.SetId(spotList.size() + 1);
						spotList.add(spot);
						database.Add(spot);
						spotview(spot);
					} else // Edit
					{
						for (spot s : spotList) {
							if (s.GetId() == spot.GetId()) {
								s = spot;
							}
						}
						database.Edit(spot);
						spotEdit = false;
					}
					infoView.kill();
					repaint();
					buttonCanc.setVisible(false);
					buttonConf.setVisible(false);
					buttonNew.setEnabled(true);
					buttonEdit.setEnabled(true);
					buttonDelete.setEnabled(true);
				}
			}
		});
		buttonConf.setBounds(width - width * 9 / 100, height * 1 / 100 * 4 + height * 6 / 100 * 3, width * 8 / 100,
				height * 6 / 100);
		getContentPane().add(buttonConf);

		buttonCanc = new JButton("Cancel");
		buttonCanc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(infoView == null))
					infoView.kill();
				spotNew = false;
				buttonCanc.setVisible(false);
				buttonConf.setVisible(false);
				buttonNew.setEnabled(true);
				buttonEdit.setEnabled(true);
				buttonDelete.setEnabled(true);
				spotDelete = false;
			}
		});
		buttonCanc.setBounds(width - width * 9 / 100, height * 1 / 100 * 5 + height * 6 / 100 * 4, width * 8 / 100,
				height * 6 / 100);
		getContentPane().add(buttonCanc);
		database.NewConnection();
		database.GetSpots(spotList);
		Iterator<spot> s = spotList.iterator();
		while (s.hasNext()) {
			spotview(s.next());
		}
		buttonConf.setVisible(false);
		buttonCanc.setVisible(false);
		setResizable(false);

		buttonQuit = new JButton("Quit");
		buttonQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		buttonQuit.setBounds(width - width * 9 / 100, height - height * 1 / 100 - height * 7 / 100, width * 8 / 100,
				height * 6 / 100);
		getContentPane().add(buttonQuit);

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
				if (spotDelete) {
					database.Delete(spotList.get(i));
					spotList.remove(i);
					guestView.remove(buttonList.get(i));
					buttonList.remove(i);
					counter--;
					guestView.repaint();
					repaint();
					spotDelete = false;
					buttonCanc.setVisible(false);
					buttonConf.setVisible(false);
					buttonNew.setEnabled(true);
					buttonEdit.setEnabled(true);
					buttonDelete.setEnabled(true);
				} else if (!spotNew) {
					if (infoView == null) {
						infoView = new InfoView();
						infoView.main(tsp.GetX() * width / guestView.getWidth(),
								tsp.GetY() * height / guestView.getHeight(), spotList.get(i));
					}
					if (spotEdit) {
						spot = spotList.get(i);
					}
				}
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (!(spotNew || spotEdit) && !gotInfo)
					if (!(infoView == null)) {
						infoView.kill();
						infoView = null;
					}
				if (spotNew)
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (spotNew)
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				else
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		button.setName(Integer.toString(tsp.GetId()));
		buttonList.add(button);
		guestView.add(buttonList.get(counter));
		repaint();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
