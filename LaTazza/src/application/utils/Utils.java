package application.utils;

import application.model.rifornimenti.Magazzino;
import application.model.rifornimenti.Rifornimento;
import application.model.utenti.Cliente;
import application.model.utenti.PagamentoDebito;
import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Utils {
		    
	    public static void resetCialdeCB(ComboBox<TipoCialda> comboTipoCialda) {
			comboTipoCialda.setValue(null);
			comboTipoCialda.setPromptText("Tipo Cialda");
		}
	    
	    public static void resetPersonaCB(ComboBox<Persona> comboPersona, String string) {
			comboPersona.setValue(null);
			comboPersona.setPromptText(string);
		}
	    
	    public static void showMessage(Label label, String colour, String message) {
			label.setText(message);
			label.setStyle("-fx-text-fill: " + colour + ";");
			label.setVisible(true);
		}
	    
	    public static void resetTextField(TextField tf,String prompt) {
			tf.clear();
			tf.setPromptText(prompt);
		}
	    
	    public static boolean checkNullCialda(TipoCialda tc, Label labelMessaggio) {
			if(tc==null) {
				Utils.showMessage(labelMessaggio, "red", "Seleziona il tipo di cialda");
				return false;
			}
			return true;
		}
	    
		public static boolean checkDisponibilitaCialde(int quantita, TipoCialda tc, Magazzino magazzino, Label message, TextField textField) {
						
			if(quantita<=0 || quantita>magazzino.numeroCialdeDisponibili(tc)) {
				showMessage(message, "red", "Inserisci una cifra positiva non superiore alle cialde disponibili");
				return false;
			}
			return true;
		}
		
	    
	    public static HBox createVenditeHBox(Vendita vendita) {
			HBox hbox=new HBox();
			hbox.setSpacing(20);
			Label data=new Label(vendita.getData()+"\t");
			Label quantita=new Label(String.valueOf(vendita.getQuantita()));
			Label tc=new Label(vendita.getTipoCialda().toString());

			Label cliente=new Label(vendita.getCliente().getNome());
			
			String tipoPagamento = vendita.isContanti() ? "in contanti" : "a credito";

			Label inContanti=new Label(tipoPagamento);

			data.setPrefWidth(225);
			cliente.setPrefWidth(150);
			quantita.setPrefWidth(60);
			tc.setPrefWidth(90);
			
			hbox.getChildren().addAll(data,cliente,quantita,tc,inContanti);
			return hbox;
		}
	      
	    public static HBox createDebitiHBox(Persona pers) {
			HBox hbox=new HBox();
			Label nome=new Label(pers.getNome()+"\t");
			Label debiti=new Label(pers.getDebito().toString());
			debiti.setStyle("-fx-text-fill: red;");
			nome.setPrefWidth(100);
			debiti.setPrefWidth(70);
			debiti.setAlignment(Pos.CENTER_RIGHT);
			hbox.getChildren().add(nome);
			hbox.getChildren().add(debiti);
			return hbox;
		}
	    
		public static HBox createRifornimentiHBox(Rifornimento rif) {
			HBox hbox=new HBox();
			hbox.setSpacing(20);
			Label data=new Label(rif.getData()+"\t");
			Label numScatole=new Label(String.valueOf(rif.getNumeroScatole()));
			Label tc=new Label(rif.getTipoCialda().toString());
			data.setPrefWidth(225);
			numScatole.setPrefWidth(60);
			
			hbox.getChildren().add(data);
			hbox.getChildren().add(numScatole);
			hbox.getChildren().add(tc);
			return hbox;
		}
		
		public static HBox createPagamentoDebitoHBox(PagamentoDebito pagDebito) {
			HBox hbox=new HBox();
			hbox.setSpacing(20);
			Label data=new Label(pagDebito.getDate()+"\t");
			Label cliente=new Label(String.valueOf(pagDebito.getPersona()));
			Label importo=new Label(pagDebito.getAmmontare().toString());
			data.setPrefWidth(225);
			cliente.setPrefWidth(150);
			
			hbox.getChildren().addAll(data,cliente,importo);
			return hbox;
		}
		
		public static boolean checkPersonToSell(String nomeVis, Cliente cl, Label message, TextField textField, ComboBox<Persona> comboPersona) {
			if(!(cl==null^ nomeVis.equals(""))) {
				Utils.showMessage(message, "red", "Seleziona tra Cliente registrato e Visitatore");
				Utils.resetTextField(textField, "Nome Visitatore");
				Utils.resetPersonaCB(comboPersona,"Nome Persona");
				return false;
			}
			return true;
		}	
		
}