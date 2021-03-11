package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.utenti.PagamentoDebito;
import application.model.utenti.Persona;
import application.utils.Euro;


public class TestPagamentoDebito {
	
	PagamentoDebito pagamentoDebito1;
	PagamentoDebito pagamentoDebito2;
	Date actualDate = new Date();
	
	@BeforeEach
	void setUp() {
		pagamentoDebito1 = new PagamentoDebito(new Persona("test1"), new Euro(42));
		pagamentoDebito2 = new PagamentoDebito(new Persona("test2"), new Euro(50), actualDate);
	}
	
	
	@Test
	void testPagamentoDebitoFuturo() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		pagamentoDebito2 = new PagamentoDebito(new Persona("test2"), new Euro(50), sdf.parse("04/02/2042"));
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
		assertTrue(pagamentoDebito2.getDate().before(new Date()));
	}
	
	@Test
	void testGetPersona() {
		assertEquals("test1", pagamentoDebito1.getPersona().getNome());
		assertEquals("test2", pagamentoDebito2.getPersona().getNome());
	}
	
	@Test
	void testGetAmmontare() {
		assertTrue(new Euro(42).ugualeA(pagamentoDebito1.getAmmontare()));
		assertTrue(new Euro(50).ugualeA(pagamentoDebito2.getAmmontare()));
	}
	
	@Test
	void testGetDate() {
		assertEquals(actualDate, pagamentoDebito2.getDate());
	}
	
	@Test
	void testGetEpoch() {
		assertEquals(actualDate.getTime(), pagamentoDebito2.getEpoch());
	}
	
}
