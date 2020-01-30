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

	
	private void itemOpenActionPerformed(ActionEvent e, JTextArea tekst) {
		FileDialog oknodialogowe = new FileDialog(
		Notatnik.this, "Wbierz plik", FileDialog.LOAD
		);
		oknodialogowe.setVisible(true);
		
		if(oknodialogowe.getFile() != null) {
			plik = oknodialogowe.getDirectory() +
			oknodialogowe.getFile();
			setTitle(plik);
		}
		
		try {
			BufferedReader czytnik = new BufferedReader(new FileReader(plik));
			StringBuilder sb = new StringBuilder();
			String linia = null;
			
			while((linia = czytnik.readLine()) != null) {
				sb.append(linia + "\n");
				tekst.setText(sb.toString());
			}
			czytnik.close();
		} catch(IOException ioe) {
			System.out.println("Nie odnaleziono pliku");
		}
		
	}

	private void itemSaveActionPerformed(ActionEvent e, JTextArea tekst) {
		FileDialog oknodialogowe = new FileDialog(
		Notatnik.this, "Zapisz plik", FileDialog.SAVE
		);
		oknodialogowe.setVisible(true);
		
		if(oknodialogowe.getFile() != null) {
			plik = oknodialogowe.getDirectory() +
			oknodialogowe.getFile();
			setTitle(plik);
		}
		
		try {
			FileWriter zapis = new FileWriter(plik);
			zapis.write(tekst.getText());
			setTitle(plik);
			zapis.close();
			
		} catch(IOException ioe) {
			System.out.println("Nie odnaleziono pliku");
		}
	}
	
	private void itemCutActionPerformed(ActionEvent e, JTextArea tekst) {
		String wytnijTekst = tekst.getSelectedText();
		StringSelection wytnijZaznaczenie = new StringSelection(wytnijTekst);
		schowek.setContents(wytnijZaznaczenie, wytnijZaznaczenie);
		tekst.replaceRange("", tekst.getSelectionStart(), tekst.getSelectionEnd());
		
	}
	
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
		
		// MENU PLIK

		JMenu menuPlik = new JMenu("Plik");
		menu.add(menuPlik);

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
				itemOpenActionPerformed(e, tekst);
			}
		});

		JMenuItem itemSave = new JMenuItem("Zapisz");
		menuPlik.add(itemSave);
		itemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemSaveActionPerformed(e, tekst);
			}
		});

		// MENU EDYCJA
		
		JMenu menuEdycja = new JMenu("Edycja");
		menu.add(menuEdycja);

		JMenuItem itemExit = new JMenuItem("Wyjdz");
		menuEdycja.add(itemExit);
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem itemCut = new JMenuItem("Wytnij");
		menuEdycja.add(itemCut);
		itemCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemCutActionPerformed(e, tekst);
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