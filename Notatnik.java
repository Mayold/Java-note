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

	
	// OTWORZ
	
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

	//  ZAPISZ

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
	
	// WYTNIJ
	
	private void itemCutActionPerformed(ActionEvent e, JTextArea tekst) {
		String wytnijTekst = tekst.getSelectedText();
		StringSelection wytnijZaznaczenie = new StringSelection(wytnijTekst);
		schowek.setContents(wytnijZaznaczenie, wytnijZaznaczenie);
		tekst.replaceRange("", tekst.getSelectionStart(), tekst.getSelectionEnd());
	}
	
	// KOPIUJ
	
	private void itemCopyActionPerformed(ActionEvent e, JTextArea tekst) {
		String kopiujTekst = tekst.getSelectedText();
		StringSelection kopiujZaznaczenie = new StringSelection(kopiujTekst);
		schowek.setContents(kopiujZaznaczenie, kopiujZaznaczenie);
	}		
	
	// WKLEJ
	
	private void itemPasteActionPerformed(ActionEvent e, JTextArea tekst) {
		try {
			Transferable wklejTekst = schowek.getContents(Notatnik.this);
			String zaznaczenie = (String) wklejTekst.getTransferData(DataFlavor.stringFlavor);
			tekst.replaceRange(zaznaczenie, tekst.getSelectionStart(), tekst.getSelectionEnd());
		
		} catch(Exception ioe) {
			System.out.println("Błąd");
		}
	}		
	
	
	// NOTATNIK //
	
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
		menuPlik.add(itemExit);
		
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// WYTNIJ
		
		JMenuItem itemCut = new JMenuItem("Wytnij");
		menuEdycja.add(itemCut);
		
		itemCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemCutActionPerformed(e, tekst);
			}
		});

		// KOPIUJ

		JMenuItem itemCopy = new JMenuItem("Kopiuj");
		menuEdycja.add(itemCopy);
		
		itemCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemCopyActionPerformed(e, tekst);
			}
		});
		
		// WKLEJ
		
		JMenuItem itemPaste = new JMenuItem("Wklej");
		menuEdycja.add(itemPaste);
		
		itemPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemPasteActionPerformed(e, tekst);
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