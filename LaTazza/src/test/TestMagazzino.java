package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import application.model.rifornimenti.Magazzino;
import application.utils.TipoCialda;

class TestMagazzino {
	
	Magazzino magazzino;
	
	@BeforeEach
	void setUp() throws Exception {
		magazzino=new Magazzino();
	}
	
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testVuotoOnCreate(TipoCialda tipoCialda) {
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testNumeroCialdeDisponibili(TipoCialda tipoCialda) {
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testAggiungiRifornimento(TipoCialda tipoCialda) {
		magazzino.aggiungiRifornimento(1,tipoCialda);
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),50);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testAggiungiRifornimentoNeg(TipoCialda tipoCialda) {
		assertFalse(magazzino.aggiungiRifornimento(-1,tipoCialda));
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testRimuoviCialde(TipoCialda tipoCialda) {
		magazzino.aggiungiRifornimento(1,tipoCialda);
		magazzino.rimuoviCialde(34, tipoCialda);
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),16);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testRimuoviCialdeNeg(TipoCialda tipoCialda) {
		assertFalse(magazzino.rimuoviCialde(-34, tipoCialda));
	}
	

	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testGetRifornimentiSize(TipoCialda tipoCialda) {
		magazzino.aggiungiRifornimento(1,tipoCialda);
		magazzino.aggiungiRifornimento(1,tipoCialda);
		magazzino.aggiungiRifornimento(1,tipoCialda);
		assertEquals(magazzino.getRifornimenti().size(),3);
	}
	
	@Test
	void testLoad() {

		magazzino.aggiungiRifornimento(4, TipoCialda.caffè);
		
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/test.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=magazzino.print());
			printWriter.close();
		} catch (IOException e) {
		}
		magazzino=new Magazzino();
		magazzino.load("res/test.txt");
		File file = new File("res/test.txt");
        file.delete();
		assertEquals(magazzino.print(),str);
	}
	
	@Test
	void testLoadFileNotExists() {
		magazzino.load("res/test.txt");
        for(TipoCialda tipoCialda: TipoCialda.values()) {
        	assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
        }
	}
	
	@Test
	void testLoadFileWithNoRifornimenti() {
		File file = new File("res/test.txt");
		magazzino.load("res/test.txt");
        file.delete();
        for(TipoCialda tipoCialda: TipoCialda.values()) {
        	assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
        }
	}

}
