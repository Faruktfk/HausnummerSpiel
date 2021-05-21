package pckge;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class GUI implements ActionListener {

	private JButton[] btns = new JButton[6];
	private JButton restrt;
	private JLabel lfirst, lsecond, lzufall, turnF, turnS;
	private JLabel[] scores = new JLabel[3];
	private String spielArt, spielModus, range;
	private Spiel hnSpiel;
	private int zufallszahl;

	public static void main(String[] args) {
		new GUI();
	}

	@SuppressWarnings("deprecation")
	public GUI() {
		JFrame frame = new JFrame("Hausnummer Spiel");
		frame.setSize(650, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		frame.setJMenuBar(menuBar);

		JMenu spiel = new JMenu("Spiel");
		spiel.setFont(new Font("Calibri", Font.PLAIN, 15));
		menuBar.add(spiel);

		JMenuItem newGame = new JMenuItem("Neues Spiel");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		newGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		newGame.addActionListener(e -> {
			removeAll(frame);
			startPage(frame);
		});
		spiel.add(newGame);

		JMenuItem quit = new JMenuItem("Spiel verlassen");
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		quit.setFont(new Font("Calibri", Font.PLAIN, 15));
		quit.addActionListener(e -> System.exit(0));
		spiel.add(quit);

		startPage(frame);

		frame.setVisible(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void startPage(JFrame frame) {
		hnSpiel = null;
		hnSpiel = new Spiel();

		JLabel lbl1 = new JLabel("Hausnummer Spiel");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setFont(new Font("MV Boli", Font.BOLD, 28));
		lbl1.setBounds(162, 56, 321, 80);
		frame.getContentPane().add(lbl1);

		JLabel lblSArt = new JLabel("Spielart:");
		lblSArt.setFont(new Font("Cambria Math", Font.PLAIN, 14));
		lblSArt.setBounds(50, 164, 157, 17);
		frame.getContentPane().add(lblSArt);

		JLabel lblSMode = new JLabel("Spielmodus:");
		lblSMode.setFont(new Font("Cambria Math", Font.PLAIN, 14));
		lblSMode.setBounds(368, 164, 115, 17);
		frame.getContentPane().add(lblSMode);

		JLabel lblKi = new JLabel("KI Schwierigkeit:");
		lblKi.setFont(new Font("Cambria Math", Font.PLAIN, 10));
		lblKi.setBounds(474, 260, 80, 13);
		frame.getContentPane().add(lblKi);
		lblKi.setVisible(false);
		lblKi.repaint();

		JComboBox spielArten = new JComboBox();
		spielArten.setBackground(Color.WHITE);
		spielArten.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
		spielArten.setModel(new DefaultComboBoxModel(new String[] { "Große Hausnummer", "Kleine Hausnummer" }));
		spielArten.setSelectedIndex(0);
		spielArten.setBounds(50, 188, 254, 37);
		spielArten.setFocusable(false);
		frame.getContentPane().add(spielArten);
		spielArt = (String) spielArten.getSelectedItem();
		spielArten.addActionListener(e -> spielArt = (String) spielArten.getSelectedItem());
		spielArten.repaint();
		
		JComboBox cpuSchwierigkeit = new JComboBox();
		cpuSchwierigkeit.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
		cpuSchwierigkeit.setBackground(Color.WHITE);
		cpuSchwierigkeit.setModel(new DefaultComboBoxModel(new String[] {"einfach", "schwer"}));
		cpuSchwierigkeit.setBounds(474, 277, 93, 21);
		cpuSchwierigkeit.setSelectedIndex(0);
		cpuSchwierigkeit.addActionListener(e -> spielModus = "Player 1 vs. CPU-" + cpuSchwierigkeit.getSelectedItem());
		frame.getContentPane().add(cpuSchwierigkeit);
		cpuSchwierigkeit.setVisible(false);
		cpuSchwierigkeit.repaint();

		JComboBox spielModes = new JComboBox();
		spielModes.setBackground(Color.WHITE);
		spielModes.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
		spielModes.setModel(new DefaultComboBoxModel(new String[] { "Player 1 vs. Player 2", "Player 1 vs. CPU" }));
		spielModes.setSelectedIndex(0);
		spielModes.setBounds(368, 188, 201, 37);
		spielModes.setFocusable(false);
		frame.getContentPane().add(spielModes);
		spielModus = (String) spielModes.getSelectedItem();
		spielModes.addActionListener(e -> {
			spielModus = (String) spielModes.getSelectedItem();
			if(spielModes.getSelectedItem().equals("Player 1 vs. CPU")) {
				spielModus = "Player 1 vs. CPU-" + cpuSchwierigkeit.getSelectedItem();
				cpuSchwierigkeit.setVisible(true);
				lblKi.setVisible(true);
			}
		});
		spielModes.repaint();

		ButtonGroup group = new ButtonGroup();

		JRadioButton cKegel = new JRadioButton("Kegel");
		cKegel.setFocusable(false);
		cKegel.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		cKegel.setSelected(true);
		cKegel.setBounds(205, 278, 93, 21);
		frame.getContentPane().add(cKegel);
		group.add(cKegel);

		JRadioButton cWürfel = new JRadioButton("W\u00FCrfel");
		cWürfel.setFocusable(false);
		cWürfel.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		cWürfel.setBounds(347, 278, 93, 21);
		frame.getContentPane().add(cWürfel);
		group.add(cWürfel);

		range = "Kegel";
		cKegel.addActionListener(e -> range = "Kegel");
		cWürfel.addActionListener(e -> range = "Würfel");

		JButton strtBtn = new JButton("Starten");
		strtBtn.setFont(new Font("Calibri", Font.PLAIN, 25));
		strtBtn.setBackground(Color.WHITE);
		strtBtn.setBounds(255, 343, 135, 50);
		strtBtn.setFocusable(true);
		strtBtn.requestFocusInWindow();
		frame.getContentPane().add(strtBtn);
		strtBtn.addActionListener(e -> {
			hnSpiel.setRange(range);
			removeAll(frame);
			game(frame);
		});

	}

	private void removeAll(JFrame frame) {

		frame.getContentPane().removeAll();
		frame.repaint();
	}

	private void game(JFrame frame) {
		hnSpiel.setSpielModus(spielModus);
		hnSpiel.setSpielArt(spielArt);
		hnSpiel.neuZufall();
		zufallszahl = hnSpiel.getZufallszahl();


		for (int i = 0; i < btns.length; i++) {
			btns[i] = new JButton();
			btns[i].setFont(new Font("Ink Free", Font.BOLD, 55));
			btns[i].setSize(80, 100);
			btns[i].setBackground(Color.white);
			btns[i].setActionCommand("btn" + i);
			btns[i].addActionListener(this);
			btns[i].setLocation(200 + (90 * (i % 3)), 150 + (110 * (i / 3)));
			btns[i].setFocusable(false);
			btns[i].setVisible(true);
			frame.getContentPane().add(btns[i]);
			btns[i].repaint();
		}

		lfirst = new JLabel("Player 1:");
		lfirst.setFont(new Font("Ink Free", Font.BOLD, 35));
		lfirst.setBounds(26, 156, 200, 100);
		lfirst.setVisible(true);
		frame.getContentPane().add(lfirst);
		lfirst.setVisible(true);
		lfirst.repaint();

		lsecond = new JLabel("Player 2:");
		lsecond.setFont(new Font("Ink Free", Font.BOLD, 35));
		lsecond.setBounds(26, 266, 200, 100);
		lsecond.setVisible(true);
		frame.getContentPane().add(lsecond);
		lsecond.setVisible(true);
		lsecond.repaint();
		
		if(spielModus.split("-")[0].equals("Player 1 vs. CPU")) {
			lsecond.setText("CPU:");
		}

		JLabel lGameSettings = new JLabel();
		lGameSettings.setFont(new Font("Calibri", Font.PLAIN, 12));
		lGameSettings.setBounds(200, 0, 290, 20);
		lGameSettings.setVisible(true);
		frame.getContentPane().add(lGameSettings);
		lGameSettings.repaint();
		lGameSettings.setForeground(Color.red);
		lGameSettings.setText(spielArt + "__" + spielModus + "__" + range);

		JLabel lblZ = new JLabel("Zufallszahl: ");
		lblZ.setFont(new Font("Ink Free", Font.BOLD, 35));
		lblZ.setBounds(225, 50, 200, 35);
		lblZ.setVisible(true);
		frame.getContentPane().add(lblZ);
		lblZ.repaint();

		lzufall = new JLabel(zufallszahl + "");
		lzufall.setFont(new Font("Ink Free", Font.BOLD, 35));
		lzufall.setBackground(Color.white);
		lzufall.setBounds(445, 50, 50, 35);
		lzufall.setVisible(true);
		frame.getContentPane().add(lzufall);
		lzufall.repaint();

		turnF = new JLabel("<--");
		turnF.setFont(new Font("Calibri", Font.BOLD, 60));
		turnF.setForeground(Color.BLUE);
		turnF.setBounds(490, 170, 80, 70);
		turnF.setVisible(false);
		frame.getContentPane().add(turnF);
		turnF.repaint();

		turnS = new JLabel("<--");
		turnS.setFont(new Font("Calibri", Font.BOLD, 60));
		turnS.setForeground(Color.RED);
		turnS.setBounds(490, 290, 80, 70);
		turnS.setVisible(false);
		frame.getContentPane().add(turnS);
		turnS.repaint();

		if (hnSpiel.getTurn() == 1) {
			turnF.setVisible(true);
		} else {
			turnS.setVisible(true);
		}

		restrt = new JButton("Neue Runde");
		restrt.setActionCommand("restrt");
		restrt.setFont(new Font("Calibri", Font.PLAIN, 25));
		restrt.setBackground(Color.WHITE);
		restrt.setBounds(225, 400, 200, 50);
		restrt.setFocusable(true);
		restrt.requestFocusInWindow();
		frame.getContentPane().add(restrt);
		restrt.addActionListener(this);
		restrt.setVisible(false);
		restrt.setEnabled(false);

		for (int i = 0; i < scores.length; i++) {
			scores[i] = new JLabel(":");
			scores[i].setFont(new Font("Calibri", Font.BOLD, 40));
			scores[i].setBackground(Color.white);
			int x = 80;
			if (i == 1) {
				scores[i].setForeground(Color.blue);
				x -= 45;
			} else if (i == 2) {
				scores[i].setForeground(Color.red);
				x += 45;
			}
			scores[i].setBounds(x, 43, 50, 40);
			scores[i].setVisible(false);
			frame.getContentPane().add(scores[i]);
			scores[i].repaint();
		}
		if(hnSpiel.getTurn() == 2 && spielModus.split("-")[0].equals("Player 1 vs. CPU")) {
			turnF.setVisible(false);
			turnS.setVisible(true);
			hnSpiel.makeMove(4);
			afterClick(4);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "btn0":
			afterClick(0);
			break;
		case "btn1":
			afterClick(1);
			break;
		case "btn2":
			afterClick(2);
			break;
		case "btn3":
			afterClick(3);
			break;
		case "btn4":
			afterClick(4);
			break;
		case "btn5":
			afterClick(5);
			break;

		case "restrt":
			neueRunde();
			break;
		}

//		hnSpiel.printbrett();

	}

	public void afterClick(int index) {


		
		hnSpiel.makeMove(index);
		for(int i = 0; i<6; i++) {
			if(btns[i].getText() != (""+hnSpiel.getplayerBrett((i/3)+1)[i%3]) && hnSpiel.getplayerBrett(i/3+1)[i%3] != -1) {
				btns[i].setText(hnSpiel.getplayerBrett(i/3+1)[i%3]+"");
			}
		}
		
		boolean a = hnSpiel.getTurn() == 1 ? true : false;
		turnF.setVisible(a);
		turnS.setVisible(!a);
		
		
		int gameState = hnSpiel.gameEnd();

		zufallszahl = hnSpiel.getZufallszahl();
		lzufall.setText(zufallszahl + "");


		if (gameState > -1) {
			restrt.setVisible(true);
			restrt.setEnabled(true);
			turnF.setVisible(false);
			turnS.setVisible(false);
			for (int i = 0; i < scores.length; i++) {
				scores[i].setVisible(true);
				if (i > 0) {
					scores[i].setText(hnSpiel.getScore(i) + "");
				}
			}
			if (gameState > 0) {
				for (int i = 0; i < btns.length; i++) {
					if ((i / 3) + 1 == gameState) {
						if (gameState == 1) {
							btns[i].setForeground(Color.blue);
						} else {
							btns[i].setForeground(Color.red);
						}
					}
				}
			}
		}

	}

	public void neueRunde() {
		
		hnSpiel.neuZufall();
		zufallszahl = hnSpiel.getZufallszahl();
		lzufall.setText(zufallszahl + "");
		
		restrt.setVisible(false);
		restrt.setEnabled(false);
		hnSpiel.gameRestart();
		for (JButton b : btns) {
			b.setText("");
			b.setForeground(Color.black);

		}

		boolean a = hnSpiel.getTurn() == 1 ? true : false;
		turnF.setVisible(a);
		turnS.setVisible(!a);
		
		
		if(hnSpiel.getTurn() == 2 && spielModus.split("-")[0].equals("Player 1 vs. CPU")) {
			turnF.setVisible(false);
			turnS.setVisible(true);
			hnSpiel.makeMove(4);
			afterClick(4);
		}

	}
}
