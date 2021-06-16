import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

public class Main implements ActionListener {
	private int[][] fieldData;
	private JButton[][] fieldButton;
	private boolean[][] pressed;
	private int row, column, numberOfMines, numberOfPressed;
	private int sec, min, hour, elapsedTime;
	private boolean endgame, flag, firstclick;
	private MainWindow mw;
	private ImageIcon mineImg, flagImg, flagImgsmall, sadImg, smileImg, winImg;
	private Timer t;

	public Main() {
		row = 16;
		column = 38;
		mw = new MainWindow();
		mineImg = new ImageIcon((new ImageIcon("mine.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		flagImgsmall = new ImageIcon(
				(new ImageIcon("flag.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		flagImg = new ImageIcon((new ImageIcon("flag.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		sadImg = new ImageIcon((new ImageIcon("sad.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		smileImg = new ImageIcon((new ImageIcon("smile.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		winImg = new ImageIcon((new ImageIcon("win.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		mw.getFlag().setIcon(flagImg);
		mw.getReset().addActionListener(this);
		mw.getReset().setActionCommand("reset");

		mw.getFlag().addActionListener(this);
		mw.getFlag().setActionCommand("flag");

		reset();
		t = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				elapsedTime += 1000;
				hour = elapsedTime / 3600000;
				min = elapsedTime / 60000 % 60;
				sec = elapsedTime / 1000 % 60;
				mw.getSw().setText(String.format("%02d", hour) + " : " + String.format("%02d", min) + " : "
						+ String.format("%02d", sec));
			}

		});

	}

	public static void main(String[] args) {
		Main m = new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String k = e.getActionCommand();
		if (k.equals("reset")) {
			mw.getMinefield().removeAll();
			t.stop();
			mw.getSw().setText("00 : 00 : 00");
			reset();
		} else if (k.equals("flag")) {
			if (flag) {
				flag = false;
				mw.getFlag().setBackground(new Color(210, 210, 210));
			} else {
				flag = true;
				mw.getFlag().setBackground(new Color(210, 0, 30));
			}
		} else if (!endgame) {
			int i = Integer.parseInt(k.substring(0, 2));
			int j = Integer.parseInt(k.substring(2));
			if (flag) {
				if (!pressed[i][j]) {
					if ((fieldButton[i][j].getIcon() == null)) {
						fieldButton[i][j].setIcon(flagImgsmall);
						numberOfMines--;
						mw.getNm().setText(numberOfMines + "");
					} else {
						fieldButton[i][j].setIcon(null);
						numberOfMines++;
						mw.getNm().setText(numberOfMines + "");
					}
				}
			} else {
				if ((fieldButton[i][j].getIcon() == null)) {
					if (firstclick) {
						if (fieldData[i][j] == -1) {
							endgame = true;
							fieldButton[i][j].setIcon(mineImg);
							showallmines();
							t.stop();
							mw.getReset().setIcon(sadImg);
							mw.getMinefield().revalidate();
							mw.getMinefield().repaint();
						} else {
							if (!pressed[i][j])
								updateField(i, j);
						}
					} else {
						createfield(i, j);
						updateField(i, j);
						firstclick = true;
					}
				}
				if (numberOfPressed == 0) {
					endgame = true;
					t.stop();
					mw.getReset().setIcon(winImg);
					mw.getReset().setBackground(Color.GREEN.brighter());
				}
			}
		}
	}

	private void showallmines() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (fieldData[i][j] == -1) {
					fieldButton[i][j].setIcon(mineImg);
				}
			}
		}

	}

	private void createfield(int x, int y) {
		elapsedTime = min = sec = hour = 0;
		t.start();
		for (int i = 1; i <= numberOfMines;) {
			int I = (int) (Math.random() * row);
			int J = (int) (Math.random() * column);
			if (Math.abs(x - I) > 1 && Math.abs(y - J) > 1 && fieldData[I][J] != -1) {
				fieldData[I][J] = -1;
				i++;
				if (I > 0) {
					if (fieldData[I - 1][J] != -1)
						fieldData[I - 1][J]++;
				}
				if (J > 0) {
					if (fieldData[I][J - 1] != -1)
						fieldData[I][J - 1]++;
				}
				if (I < row - 1) {
					if (fieldData[I + 1][J] != -1)
						fieldData[I + 1][J]++;
				}
				if (J < column - 1) {
					if (fieldData[I][J + 1] != -1)
						fieldData[I][J + 1]++;
				}
				if (I > 0 && J > 0) {
					if (fieldData[I - 1][J - 1] != -1)
						fieldData[I - 1][J - 1]++;
				}
				if (I > 0 && J < column - 1) {
					if (fieldData[I - 1][J + 1] != -1)
						fieldData[I - 1][J + 1]++;
				}
				if (I < row - 1 && J > 0) {
					if (fieldData[I + 1][J - 1] != -1)
						fieldData[I + 1][J - 1]++;
				}
				if (I < row - 1 && J < column - 1) {
					if (fieldData[I + 1][J + 1] != -1)
						fieldData[I + 1][J + 1]++;
				}
			}
		}
		for (int i = 0; i < 13; i++)
			System.out.println(Arrays.toString(fieldData[i]));
	}

	public void updateField(int i, int j) {
		if (!pressed[i][j]) {
			pressed[i][j] = true;
			fieldButton[i][j].setBackground(new Color(240, 240, 240));
			int x = fieldData[i][j];
			numberOfPressed--;
			if (x != 0) {
				fieldButton[i][j].setText("" + x);
				switch (x) {
				case 1:
					fieldButton[i][j].setForeground(Color.BLUE);
					return;
				case 2:
					fieldButton[i][j].setForeground(Color.GREEN.darker());
					return;
				case 3:
					fieldButton[i][j].setForeground(Color.RED);
					return;
				case 4:
					fieldButton[i][j].setForeground(Color.BLUE.darker());
					return;
				case 5:
					fieldButton[i][j].setForeground(Color.ORANGE.darker());
					return;
				case 6:
					fieldButton[i][j].setForeground(Color.CYAN.darker());
					return;
				case 7:
					fieldButton[i][j].setForeground(Color.PINK.darker());
					return;
				case 8:
					fieldButton[i][j].setForeground(Color.DARK_GRAY);
					return;
				}
			} else {
				if (i > 0) {
					updateField(i - 1, j);
				}
				if (j > 0) {
					updateField(i, j - 1);
				}
				if (i < row - 1) {
					updateField(i + 1, j);
				}
				if (j < column - 1) {
					updateField(i, j + 1);
				}
				if (i > 0 && j > 0) {
					updateField(i - 1, j - 1);
				}
				if (i > 0 && j < column - 1) {
					updateField(i - 1, j + 1);
				}
				if (i < row - 1 && j > 0) {
					updateField(i + 1, j - 1);
				}
				if (i < row - 1 && j < column - 1) {
					updateField(i + 1, j + 1);
				}

			}
		}
	}

	public void reset() {
		numberOfMines = 1;
		numberOfPressed = row * column - numberOfMines;
		endgame = false;
		firstclick = false;
		mw.getReset().setIcon(smileImg);
		fieldData = new int[row][column];
		fieldButton = new JButton[row][column];
		pressed = new boolean[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				JButton b = new JButton();
				b.addActionListener(this);
				b.setFocusable(false);
				b.setHorizontalAlignment(JButton.CENTER);
				b.setVerticalAlignment(JButton.CENTER);
				b.setActionCommand(String.format("%02d", i) + String.format("%02d", j));
				b.setFont(new Font("SansSerif", Font.BOLD, 13));
				b.setMargin(new Insets(0, 0, 0, 0));
				fieldButton[i][j] = b;
				mw.getMinefield().add(b);
			}
		}
		mw.getNm().setText(numberOfMines + "");

		mw.revalidate();
		mw.repaint();
	}

}
