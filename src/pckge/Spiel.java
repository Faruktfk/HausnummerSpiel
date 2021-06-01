package pckge;

import java.util.ArrayList;
import java.util.Random;

public class Spiel {
	public final int GAME_NOT_ENDED = -1;
	public final int P1_WINNS = 1;
	public final int P2_WINNS = 2;
	public final int NO_ONE_WINNS = 0;

	private ArrayList<Integer> zList;
	private int p1Score, p2Score;
	private String spielModus, spielArt, range;
	private int zufallsZahl = -1;
	private int turn, firstNum, secondNum;
	private int[] fplayer, splayer;
	private Random random = new Random();
	private boolean gameOver;

	public Spiel() {
		p1Score = 0;
		p2Score = 0;

		turn = random.nextInt(2) + 1;
		gameRestart();

	}

	public void setRange(String range) {
		this.range = range;
	}

	public void neuZufall() {
		int indx = zList.size();
		int zz;
		if (range.equals("Würfel")) {
			zz = random.nextInt(6) + 1;
		} else {
			zz = random.nextInt(10);
		}

		if (zufallsZahl == zz || indx > 1 && zList.get(indx - 2) == zz) {
			neuZufall();
		} else {
			zufallsZahl = zz;
			zList.add(zufallsZahl);
		}
	}

	public int[] getplayerBrett(int player) {
		if (player == 1) {
			return fplayer;
		} else {
			return splayer;
		}
	}

	public int getZufallszahl() {
		return zufallsZahl;
	}

	public int getTurn() {
		return turn;
	}

	public void setSpielArt(String sArt) {
		spielArt = sArt;
	}

	public void setSpielModus(String sModus) {
		spielModus = sModus;

	}

	public int getScore(int player) {
		if (player == 1) {
			return p1Score;
		} else {
			return p2Score;
		}
	}

	// ----------------------------------------------------------------------------------------

	public void makeMove(int index) {

		if (spielModus.equals("Player 1 vs. Player 2")) {

			if (index / 3 + 1 == turn && getplayerBrett(turn)[index % 3] == -1) {
				getplayerBrett(turn)[index % 3] = zufallsZahl;
				turn = turn == 1 ? 2 : 1;
				neuZufall();
			}

		} else if (spielModus.split("-")[0].equals("Player 1 vs. CPU")) {
			if (turn == 1) {
				if (turn == 1 && index < 3 && fplayer[index] == -1) {
					fplayer[index] = zufallsZahl;
					turn = 2;
					neuZufall();
					kiAmWerk(index);
				}
			} else {
				kiAmWerk(index);
			}
		}

	}

	public int gameEnd() {
		String frst = "";
		String scnd = "";
		for (int i = 0; i < 3; i++) {
			if (fplayer[i] == -1 || splayer[i] == -1) {
				return GAME_NOT_ENDED;
			}
			frst += fplayer[i];
			scnd += splayer[i];

		}
		firstNum = Integer.parseInt(frst);
		secondNum = Integer.parseInt(scnd);
		int winner;

		if (spielArt.equals("Große Hausnummer")) {
			winner = firstNum == secondNum ? NO_ONE_WINNS : firstNum > secondNum ? P1_WINNS : P2_WINNS;
		} else {
			winner = firstNum == secondNum ? NO_ONE_WINNS : firstNum < secondNum ? P1_WINNS : P2_WINNS;
		}
		
		if(!gameOver){
			if (winner != 0) {
				if (winner == 1) {
					p1Score++;
					gameOver = true;
				} else {
					p2Score++;
					gameOver = true;
				}
			}
		}
		return winner;

	}

	public void gameRestart() {
		gameOver = false;
		zList = new ArrayList<>();
		if (zufallsZahl != -1) {
			zList.add(zufallsZahl);
		}
		fplayer = new int[3];
		splayer = new int[3];
		for (int i = 0; i < 3; i++) {
			fplayer[i] = -1;
			splayer[i] = -1;
		}

	}

	public void printbrett() {
		System.out.println("\nturn: " + turn + "\tzZahl: " + zufallsZahl);
		for (int i = 0; i < 3; i++) {
			System.out.print(fplayer[i] + " ");
		}
		System.out.print("\t||\t");
		for (int i = 0; i < 3; i++) {
			System.out.print(splayer[i] + " ");
		}
		System.out.println();
	}

	private void kiAmWerk(int index) {

		if (spielModus.split("-")[1].equals("einfach")) {

			int r = random.nextInt(1000) % 3;
			boolean nochPlatz = false;
			for (int p : splayer) {
				if (p == -1) {
					nochPlatz = true;
					break;
				}
			}
			while (splayer[r] != -1 && nochPlatz) {
				r = random.nextInt(1000) % 3;
			}
			if (nochPlatz) {
				splayer[r] = zufallsZahl;
				turn = 1;
				neuZufall();
			}

		} else if (spielModus.split("-")[1].equals("schwer")) {
			int best = spielArt.equals("Große Hausnummer") ? (range.equals("Kegel") ? 9 : 6)
					: (range.equals("Kegel") ? 0 : 1);
			int worst = spielArt.equals("Große Hausnummer") ? (range.equals("Kegel") ? 0 : 1)
					: (range.equals("Kegel") ? 9 : 6);
			int mitte = (best + worst) / 2;


			int ga = spielArt.equals("Große Hausnummer") ? -1 : 1;
			for (int i = 0; i < 3; i++) {

				if ((zufallsZahl == best || zufallsZahl == best + (1 * ga)) && splayer[i] == -1) {
					splayer[i] = zufallsZahl;
					turn = 1;
					neuZufall();
					break;
				} else if ((zufallsZahl == worst || zufallsZahl == worst + (1 * ga)) && splayer[2 - i] == -1) {
					splayer[2 - i] = zufallsZahl;
					turn = 1;
					neuZufall();
					break;
				} else {
					if (ga == -1) {
						if (zufallsZahl > mitte && splayer[Math.abs((int) Math.pow(i, i) - i)] == -1) {
							splayer[Math.abs((int) Math.pow(i, i) - i)] = zufallsZahl;
							turn = 1;
							neuZufall();
							break;

						} else if (zufallsZahl <= mitte && splayer[(i + 1) % 3] == -1) {
							splayer[(i + 1) % 3] = zufallsZahl;
							turn = 1;
							neuZufall();
							break;
						}
					} else {
						if (zufallsZahl <= mitte && splayer[Math.abs((int) Math.pow(i, i) - i)] == -1) {
							splayer[Math.abs((int) Math.pow(i, i) - i)] = zufallsZahl;
							turn = 1;
							neuZufall();
							break;
							
						} else if (zufallsZahl > mitte && splayer[(i + 1) % 3] == -1) {
							splayer[(i + 1) % 3] = zufallsZahl;
							turn = 1;
							neuZufall();
							break;
						}
						
					}
				}

			}
		}

	}

}
