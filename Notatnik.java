import javax.swing.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.*;
import java.io.*;

public class Notatnik extends JFrame {
	
	String plik;
	Clipboard schowek = getToolkit().getSystemClipboard();
	
	public Notatnik() {
		addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			}
		);
	
		JTextArea tekst = new JTextArea();
		tekst.setBounds(0, 0, 960, 600);
		add(tekst);
	
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		
		JMenu menuPlik = new JMenu("Plik");
		menu.add(menuPlik);
		
		JMenu menuEdycja = new JMenu("Edycja");
		menu.add(menuEdycja);

		JMenuItem itemNew = new JMenuItem("Nowy");
		menuPlik.add(itemNew);
		itemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tekst.setText("");
				setTitle("");
			}
		});

		JMenuItem itemOpen = new JMenuItem("Otworz");
		menuPlik.add(itemOpen);
		itemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemOpenActonPerformed(e, tekst);
			}
		});

	
		setTitle("Notatnik");
		setSize(960, 600);
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		new Notatnik();
	}
}