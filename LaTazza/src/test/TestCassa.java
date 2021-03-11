package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import application.model.Cassa;
import application.utils.Euro;



public class TestCassa {

	Cassa cassa;
	
	@BeforeEach
	void setUp() {
		cassa=new Cassa();
	}
	
	@Test
	void testVuotaOnCreate() {
		assertTrue(cassa.getDisponibilita().ugualeA(new Euro(0)));
	}
	
	@Test
	void testEffettuaPagamentoSuperiore() {
		assertFalse(cassa.effettuaPagamento(new Euro(42)));
	}

	
	@Test
	void testEffettuaPagamento() {
		cassa.riceviPagamento(new Euro(100));
		assertTrue(cassa.effettuaPagamento(new Euro(42)));
	}
	
	
	@ParameterizedTest
	@CsvSource({ "42","10"})
	void testRiceviPagamento(int ammontareInt) {
		Euro disponibilitaIniziale = cassa.getDisponibilita();
		Euro ammontareEuro = new Euro(ammontareInt); 
		cassa.riceviPagamento(ammontareEuro);
		assertTrue(cassa.getDisponibilita().ugualeA(disponibilitaIniziale.somma(ammontareEuro)));
	}
	
	@ParameterizedTest
	@CsvSource({ "-42","-10"})
	void testRiceviPagamentoNeg(int ammontareInt) {
		assertFalse(cassa.riceviPagamento(new Euro(ammontareInt)));
	}
	
	
	@Test
	void testToString() {
		assertEquals(cassa.toString(),"0.00");
	}
	
	
	@Test
	void testPrint() {
		assertEquals(cassa.print(),"CASSA\n0\n\n");
	}
	
	
	@Test 
	void testLoad() {
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/pippo.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=cassa.print());
			printWriter.close();
		} catch (IOException e) {
		}
		cassa=new Cassa();
		cassa.load("res/pippo.txt");
		new File("res/pippo.txt").delete();
		assertEquals(cassa.print(),str);
	}
	
	@Test
	void testLoadFileNotExists() {
		cassa.load("res/pippo.txt");
		assertTrue(cassa.getDisponibilita().ugualeA(new Euro(2000)));
	}
	
	@Test
	void testLoadFileWithNoCassa() {
		File file = new File("res/test.txt");
		cassa.load("res/test.txt");
        file.delete();
		assertTrue(cassa.getDisponibilita().ugualeA(new Euro(2000)));
	}
	
}
