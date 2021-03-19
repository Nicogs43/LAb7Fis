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

	Cassa cassa=new Cassa();
	Cassa cassaVuota=new Cassa();;
	
	@BeforeEach
	void setUp() {
		cassa.load();
	}
	
	@Test
	void testVuotaOnCreate() {
		assertTrue(cassaVuota.getDisponibilita().ugualeA(new Euro(0)));
	}
	
	@Test
	void testEffettuaPagamentoSuperiore() {
		int Disponibilità=(int)cassa.getDisponibilita().getValore();
		assertFalse(cassa.effettuaPagamento(new Euro(Disponibilità+42)));
		assertEquals(Disponibilità, cassa.getDisponibilita().getValore());
	}
	
	
	@Test
	void testEffettuaPagamento() {
		cassa.riceviPagamento(new Euro(0, 100));
		assertTrue(cassa.effettuaPagamento(new Euro(0, 42)));
		assertTrue(cassa.effettuaPagamento(new Euro(0, 58)));
	}
	
	@ParameterizedTest
	@CsvSource({ "42","10"})
	void testRiceviPagamento(int ammontareInt) {
		Euro disponibilitaIniziale = new Euro (0 , cassa.getDisponibilita().getValore());
		cassa.riceviPagamento(new Euro(0, ammontareInt));
		assertTrue(cassa.getDisponibilita().ugualeA(disponibilitaIniziale.somma(new Euro(0, ammontareInt))));
		cassa.effettuaPagamento(new Euro(0, ammontareInt));
	}
	
	@ParameterizedTest
	@CsvSource({ "-42","-10"})
	void testRiceviPagamentoNeg(int ammontareInt) {
		assertFalse(cassa.riceviPagamento(new Euro(ammontareInt)));
	}
	
}
