package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	
	protected static Menjacnica sistem = new Menjacnica();
	private static JTable table;

	public static JScrollPane getScrollPane(JScrollPane scrollPane){
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private static JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new MenjacnicaTableModel());
		}
		return table;
	}
	
	public static void prikaziDodajKursGUI(JPanel contentPane, MenjacnicaGUI menjacnicaGUI) {
		DodajKursGUI prozor = new DodajKursGUI(menjacnicaGUI);
		prozor.setLocationRelativeTo(contentPane);
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(JPanel contentPane, MenjacnicaGUI menjacnicaGUI) {
		if (getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(getTable().getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(menjacnicaGUI,
					model.vratiValutu(getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(JPanel contentPane, MenjacnicaGUI menjacnicaGUI) {
		if (getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(getTable().getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(menjacnicaGUI,
					model.vratiValutu(getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}
	
	public static void sacuvajUFajl(JPanel contentPane) {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla(JPanel contentPane) {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}		
		
	}
	
	protected static void prikaziSveValute() {
		MenjacnicaTableModel model = (MenjacnicaTableModel)(getTable().getModel());
		model.staviSveValuteUModel(sistem.vratiKursnuListu());

	}
	
	public static void prikaziAboutProzor(JPanel contentPane){
		JOptionPane.showMessageDialog(contentPane,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void ugasiAplikaciju(JPanel contentPane) {
		int opcija = JOptionPane.showConfirmDialog(contentPane,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void unesiKurs(DodajKursGUI dodajKursGUI, JPanel contentPane, MenjacnicaGUI glavniProzor, String naziv, String skraceniNaziv, Object sifra, String prodajni, String kupovni, String srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra((Integer)(sifra));
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			// Dodavanje valute u kursnu listu
			dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			GUIKontroler.prikaziSveValute();
			
			//Zatvaranje DodajValutuGUI prozora
			dodajKursGUI.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void dodajValutu(Valuta valuta){
		sistem.dodajValutu(valuta);
	}
	
	public static void obrisiValutu(ObrisiKursGUI obrisiKursGUI, JPanel contentPane, MenjacnicaGUI glavniProzor, Valuta valuta) {
		try{
			obrisiValutu(valuta);
			prikaziSveValute();
			obrisiKursGUI.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void obrisiValutu(Valuta valuta){
		sistem.obrisiValutu(valuta);
	}
	
	public static void izvrsiZamenu(Valuta valuta, MenjacnicaGUI glavniProzor, JPanel contentPane, boolean rdbtnProdaja, JTextField iznos){
		try{
			double konacniIznos = 
					izvrsiTransakciju(valuta,
							rdbtnProdaja, 
							Double.parseDouble(iznos.getText()));
		
			iznos.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	private static double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos){
		return sistem.izvrsiTransakciju(valuta, prodaja, iznos);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenjacnicaGUI frame = new MenjacnicaGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
