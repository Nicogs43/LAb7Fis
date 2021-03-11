package application.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;

import application.model.Cassa;
import application.model.rifornimenti.Magazzino;
import application.model.rifornimenti.Rifornimento;
import application.model.utenti.Cliente;
import application.model.utenti.PagamentoDebito;
import application.model.utenti.Persona;
import application.model.utenti.Personale;
import application.model.utenti.Visitatore;
import application.model.vendite.Vendita;
import application.model.vendite.Vendite;
import application.utils.Euro;
import application.utils.TipoCialda;
import application.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Controller {
	//save path for test in eclipse
	private String path="res/laTazza.txt";
	//save path for executable jar
	//private String path=System.getProperty("user.dir")+"/laTazza.txt";

	private Vendite vendite = new Vendite();
	private Personale personale=new Personale();
	
	private HashMap<TipoCialda,Label> cialdeMagTF=new HashMap<TipoCialda,Label>();

	private Cassa cassa=new Cassa();
	private Magazzino magazzino=new Magazzino();

	
	@FXML
	private Button registraVenditaB,cancellaVenditaB,registraPagamentoB,cancellaPagamentoB,registraRifornimentoB,cancellaRifornimentoB;
	
	@FXML
	private ComboBox<Persona> venditaPersonaleCB,rimuoviPersonaCB,pagamentoCB;
	@FXML
	private ComboBox<TipoCialda> rifTipoCialdaCB,venditaTipoCialdaCB;
	
	@FXML
	private Label cialdeCaffeTF,cialdeArabicaTf,cialdeTheTF,cialdeTheLimoneTF,cialdeCioccolatoTF,cialdeCamomillaTF;
	@FXML 
	private Label messRifornimento,messVendita,messPagamento,messAggiungiPersona,messRimuoviPersona,disponibilitaLabel;
	
	@FXML
	private RadioButton contanti;
	
	@FXML
	private Tab cassaTab,debitiTab,magazzinoTab;
	@FXML
	private TabPane tabs;
	
	@FXML
	private TextField quantitaScatoleTF,quantitaCialdeTF,nomeVisitatoreTF,ammontareTF,aggiungiPersonaleTF;
	
	@FXML 
	private VBox debitiLeft,debitiRight,rifornimentiTab,venditeTab,pagDebitiTab;
	
	
/******************************************************************************************************************/

	
	public void init() {
		ObservableList<TipoCialda> obsList=FXCollections.observableArrayList();
		obsList.addAll(TipoCialda.values());
		
		cassa.load(path);
		magazzino.load(path);
		vendite.load(path);
		personale.load(path);
		
		venditaTipoCialdaCB.setItems(obsList);
		rifTipoCialdaCB.setItems(obsList);
		disponibilitaLabel.setText(cassa.toString());
	
		cialdeMagTF.put(TipoCialda.caffè, cialdeCaffeTF);
		cialdeMagTF.put(TipoCialda.caffèArabica, cialdeArabicaTf);
		cialdeMagTF.put(TipoCialda.thè, cialdeTheTF);
		cialdeMagTF.put(TipoCialda.thèLimone, cialdeTheLimoneTF);
		cialdeMagTF.put(TipoCialda.cioccolata, cialdeCioccolatoTF);
		cialdeMagTF.put(TipoCialda.camomilla, cialdeCamomillaTF);
		
		for(TipoCialda tipoCialda : TipoCialda.values())
			updateMagazzinoTab(tipoCialda);
		updatePagamentiTab();
		updateVenditeTab();
		updateRifornimentiTab();
	    updatePagamentoCB();
		updateDebitiTab();
		updatePersonaleCB();
	}
	
	public void hideAllMessages() {
		messRifornimento.setVisible(false);
		messVendita.setVisible(false);
		messPagamento.setVisible(false);
		messAggiungiPersona.setVisible(false);
		messRimuoviPersona.setVisible(false);
	}
	
	
/******************************************************************************************************************/	
	
	
	public void aggiungiPersona() {
		
		hideAllMessages();
		String nome=aggiungiPersonaleTF.getText();
	    //check input per aggiunta persona
		if(nome.isEmpty()) {
			Utils.showMessage(messAggiungiPersona, "red", "Nessun nome inserito");
			return;
		}
		//rimozione persona, se non gia presente
		if(!personale.addPersona(nome)) {
			Utils.showMessage(messAggiungiPersona, "red", "Impossibile aggiungere " + nome + " in quanto già presente");
			aggiungiPersonaleTF.clear();
			return;
		}
		Utils.showMessage(messAggiungiPersona, "green", "Aggiunto " + nome + " al registro clienti");
		//update views
		aggiungiPersonaleTF.clear();
		aggiungiPersonaleTF.setPromptText("Inserisci nome persona");
		updatePersonaleCB();
	}
	
	
	
	public void rimuoviPersona() {
		
		hideAllMessages();
		Persona pers=rimuoviPersonaCB.getValue();
	    //check input per rimozione persona
		if(pers==null) {
			Utils.showMessage(messRimuoviPersona, "red", "Nessuna persona selezionata");
			return;
		}
		//rimozione persona, se non indebitata
		if(!personale.removePersona(pers)) {
			Utils.resetPersonaCB(rimuoviPersonaCB, "Nome Persona");
			Utils.showMessage(messRimuoviPersona, "red", "Impossibile rimuovere " + pers.getNome() + " in quanto indebitato");
			return;
		}
		Utils.showMessage(messRimuoviPersona, "green", "Rimosso " + pers + " dal registro clienti");
		//update views
		updatePersonaleCB();
	}
	
	
	
	public void registraVendita() {

	    hideAllMessages();
	    //get input vendita
	    boolean cont = contanti.isSelected();
	    TipoCialda tipoCialda = venditaTipoCialdaCB.getValue();
	    String nomePers = nomeVisitatoreTF.getText();
	    Cliente cliente = venditaPersonaleCB.getValue();
	    int quantita;
	    
	    //check input per vendita
	    try {
	      quantita = Integer.parseInt(quantitaCialdeTF.getText());
	    } catch (NumberFormatException e) {
	      Utils.resetTextField(quantitaCialdeTF, "Quantità cialde");
	      Utils.showMessage(messVendita, "red", "Inserire un valore numerico di cialde");
	      return;
	    }

	    if (!Utils.checkPersonToSell(nomePers,cliente,messVendita,nomeVisitatoreTF,venditaPersonaleCB) || !Utils.checkNullCialda(tipoCialda,messVendita) || !Utils.checkDisponibilitaCialde(quantita, tipoCialda, magazzino, messVendita, quantitaCialdeTF))
	    	return;

	    Euro euro = new Euro(0,quantita*50);

	    if (cliente==null) {
	      if(!cont) {
	        Utils.showMessage(messVendita, "red", "Ai Visitatori vendita a contanti");
	        return;
	      }
	      cliente = new Visitatore(nomePers);
	    }
	    else	nomePers = ((Persona)cliente).getNome();
	    
	    //effettua pagamento
	    if(cont) {
	      cassa.riceviPagamento(euro);
	      disponibilitaLabel.setText(cassa.toString());
	      tabs.getSelectionModel().select(cassaTab);
	    }
	    //aumenta debito
	    else if(((Persona)cliente).aumentaDebito(euro)) {
	      updateDebitiTab();
	      updatePagamentoCB();
	      tabs.getSelectionModel().select(debitiTab);
	    }
	    //registrazione vendita
	    vendite.addVendita(cliente, quantita, tipoCialda, cont);
	    magazzino.rimuoviCialde(quantita, tipoCialda);
	    Utils.showMessage(messVendita, "green", "Vendita di " + quantita + " " + tipoCialda + " a " + nomePers + " avvenuta");
	    
	    //update views
	    updateVenditeTab();
	    Utils.resetCialdeCB(venditaTipoCialdaCB);
	    updateMagazzinoTab(tipoCialda);
	    resetVendita();
	     
	}
	
	
	
	public void registraPagamento() {
		
		hideAllMessages();
		
		Persona pers=pagamentoCB.getValue();
		int ammontare;
		
		//check input per sanamento debito
		if(pers==null) {
			Utils.showMessage(messPagamento, "red", "Seleziona una Persona");
			return;
		}
		try {
			ammontare=(int)(Double.parseDouble(ammontareTF.getText())*100);
		}catch (Exception e) {
			Utils.showMessage(messPagamento, "red", "Inserisci una cifra in ammontare");
			Utils.resetTextField(ammontareTF, "Ammontare");
			return;
		}
		
		//sana debito
		Euro euro=new Euro(0,ammontare);
		if(!personale.diminuisciDebito(pers,euro)) {
			Utils.showMessage(messPagamento, "red", "Inserisci una cifra positiva non superiore al debito");
			tabs.getSelectionModel().select(debitiTab);
			Utils.resetTextField(ammontareTF, "Ammontare");
			return;
		}
		cassa.riceviPagamento(euro);
		Utils.showMessage(messPagamento, "green", "Sanamento del debito di " + pers.getNome() + " di " + euro + " avvenuto");	
		
		//update views
		updatePagamentiTab();
		updatePagamentoCB();
		updateDebitiTab();
		disponibilitaLabel.setText(cassa.toString());
		tabs.getSelectionModel().select(cassaTab);
		resetPagamento();
		}

	
	
	public void registraRifornimento() {
		
		hideAllMessages();

		TipoCialda tipoCialda=rifTipoCialdaCB.getValue();
		//check input per rifornimento
		if(tipoCialda==null) {
			Utils.showMessage(messRifornimento, "red", "Seleziona un tipo di cialda");
			return;
		}

		int numeroScatole;
		try {
			numeroScatole=Integer.parseInt(quantitaScatoleTF.getText());
		}catch (NumberFormatException e) {
			Utils.showMessage(messRifornimento, "red", "Inserisci un valore numerico di scatole");
			Utils.resetTextField(quantitaScatoleTF, "Numero scatole");
			return;
		}
		
		if(!cassa.effettuaPagamento(new Euro(0,50*numeroScatole*40))) {
			Utils.showMessage(messRifornimento, "red", "Inserisci una cifra positiva non superiore alla disponibilità in cassa");
			return;
		}
		//effettua rifornimento
		magazzino.aggiungiRifornimento(numeroScatole,tipoCialda);
		Utils.showMessage(messRifornimento, "green", "Rifornimento di " + numeroScatole + " scatole di " + tipoCialda + " avvenuto");
		
		//update views
		updateMagazzinoTab(tipoCialda);
		disponibilitaLabel.setText(cassa.toString());
		tabs.getSelectionModel().select(magazzinoTab);
		updateRifornimentiTab();
		resetRifornimento();
		}
	
	
/******************************************************************************************************************/	

	
	private void updatePersonaleCB() {
		ObservableList<Persona> obsList=FXCollections.observableArrayList();
		obsList.addAll(personale.getPersonale());
		Collections.sort(obsList, Persona.SortListPersona);
		rimuoviPersonaCB.setItems(obsList);
		venditaPersonaleCB.setItems(obsList);
		rimuoviPersonaCB.setValue(null);
		rimuoviPersonaCB.setPromptText("Nome Persona");
		venditaPersonaleCB.setValue(null);
		venditaPersonaleCB.setPromptText("Nome Persona");	
	}
	
	private void updatePagamentoCB() {
		ObservableList<Persona> obsList=FXCollections.observableArrayList();
		obsList.addAll(personale.getIndebitati());
		Collections.sort(obsList, Persona.SortListPersona);
		pagamentoCB.setItems(obsList);
		pagamentoCB.setValue(null);
		pagamentoCB.setPromptText("Nome Persona");
	}
	
	
	private void updateVenditeTab() {
		venditeTab.getChildren().clear();
		for(Vendita vendita: vendite.getVendite()) {
			venditeTab.getChildren().add(0, Utils.createVenditeHBox(vendita));
		}
	}
	
	private void updateRifornimentiTab() {
		rifornimentiTab.getChildren().clear();
		for(Rifornimento rif: magazzino.getRifornimenti()) {
			rifornimentiTab.getChildren().add(0, Utils.createRifornimentiHBox(rif));
		}
	}
	
	private void updatePagamentiTab() {
		pagDebitiTab.getChildren().clear();
		for(PagamentoDebito pagDebito: personale.getPagamentiDebito()) {
			pagDebitiTab.getChildren().add(0, Utils.createPagamentoDebitoHBox(pagDebito));
		}
	}
	
	private void updateDebitiTab() {
		boolean value = true;
		debitiLeft.getChildren().clear();
		debitiRight.getChildren().clear();
		for(Persona pers : personale.getIndebitati()) {
			if(value)debitiLeft.getChildren().add(Utils.createDebitiHBox(pers));
			else debitiRight.getChildren().add(Utils.createDebitiHBox(pers));
			value ^= true;
		}
	}
	
	private void updateMagazzinoTab(TipoCialda tipoCialda) {
		int cialdeMag=magazzino.numeroCialdeDisponibili(tipoCialda);
		cialdeMagTF.get(tipoCialda).setText(String.valueOf(cialdeMag));
	}
	

	public void resetVendita() {
		Utils.resetTextField(quantitaCialdeTF, "Quantità cialde");
		Utils.resetPersonaCB(venditaPersonaleCB, "Nome Persona");
		Utils.resetTextField(nomeVisitatoreTF, "Nome Visitatore");
		Utils.resetCialdeCB(venditaTipoCialdaCB);
		messVendita.setVisible(false);
	}

	public void resetPagamento() {
		Utils.resetPersonaCB(pagamentoCB, "Nome Persona");
		Utils.resetTextField(ammontareTF, "Ammontare");
		messPagamento.setVisible(false);
	}
	
	public void resetRifornimento() {
		Utils.resetCialdeCB(rifTipoCialdaCB);
		Utils.resetTextField(quantitaScatoleTF, "Numero scatole");
		messRifornimento.setVisible(false);
	}

	
	public void saveAllInFile() {
		try (OutputStreamWriter outputStreamWriter =
		    new OutputStreamWriter(new FileOutputStream(path),"UTF-8")){
			outputStreamWriter.write(cassa.print());
			outputStreamWriter.write(magazzino.print());
			outputStreamWriter.write(personale.print());
			outputStreamWriter.write(vendite.print());
		   
		} catch (IOException e) {
		}
	}
	
}