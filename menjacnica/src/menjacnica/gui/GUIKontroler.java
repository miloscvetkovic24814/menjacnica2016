package menjacnica.gui;

import java.awt.EventQueue;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import menjacnica.Menjacnica;
import menjacnica.Valuta;

public class GUIKontroler {
	
	private static MenjacnicaGUI menjacnicaGUI;
	protected static Menjacnica sistem;	
	
	public static MenjacnicaGUI vratiGlavniProzor(){
		return menjacnicaGUI;
	}
	
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(menjacnicaGUI.getContentPane());
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(Valuta valuta) {
		ObrisiKursGUI prozor = new ObrisiKursGUI(valuta);
		prozor.setLocationRelativeTo(menjacnicaGUI.getContentPane());
		prozor.setVisible(true);
	}
	
	public static void prikaziIzvrsiZamenuGUI(Valuta valuta) {
		IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(menjacnicaGUI, valuta);
		prozor.setLocationRelativeTo(menjacnicaGUI.getContentPane());
		prozor.setVisible(true);
	}
	
	public static void sacuvajUFajl(String putanja) {
		sistem.sacuvajUFajl(putanja);
	}
	
	public static void ucitajIzFajla(String putanja) {
		sistem.ucitajIzFajla(putanja);		
	}
	
	public static LinkedList<Valuta> vratiKursnuListu(){
		return sistem.vratiKursnuListu();
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGUI.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}	
	
	public static void dodajValutu(Valuta valuta){
		sistem.dodajValutu(valuta);
	}	
	
	public static void obrisiValutu(Valuta valuta){
		sistem.obrisiValutu(valuta);
	}	
		
	public static double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos){
		return sistem.izvrsiTransakciju(valuta, prodaja, iznos);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new Menjacnica();
					menjacnicaGUI = new MenjacnicaGUI();
					menjacnicaGUI.setVisible(true);
					menjacnicaGUI.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
