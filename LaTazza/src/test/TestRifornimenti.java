package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.rifornimenti.Rifornimenti;
import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

class TestRifornimenti {
	
	Rifornimenti rifornimenti, rifornimentiEmpty;
	int size = 10, numScatola = 1;
	File file = new File("res/test.txt");

	
	@BeforeEach
	void setUp() throws Exception {
		rifornimentiEmpty= new Rifornimenti();
		
		rifornimenti= new Rifornimenti();
		for(int i=0; i<size; i++) {
			rifornimenti.addRifornimento(numScatola,TipoCialda.caffè);
		}
	}
	
	
	@Test
	void testGetRifornimentiByTipoCialda() {
		for(Rifornimento rif : rifornimenti.getRifornimenti()) {
			assertEquals(rif.getTipoCialda(),TipoCialda.caffè);
			assertEquals(rif.getNumeroScatole(),numScatola);
		}
	}
	
	@Test
	void testGetRifornimentiSize() {
		assertEquals(rifornimenti.getRifornimenti().size(),size);
	}
	
	@Test
	void testLoad() {

		rifornimenti.addRifornimento(numScatola, TipoCialda.caffè);
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/test.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=rifornimenti.print());
			printWriter.close();
		} catch (IOException e) {
		}
		rifornimentiEmpty.load("res/test.txt");
        file.delete();
		assertEquals(rifornimentiEmpty.print(),str);
	}
	
	
	@Test
	void testLoadFileNotExists() {
		rifornimentiEmpty.load("res/test5.txt");
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),0);
	}
	
	@Test
	void testLoadFileWithNoRifornimenti() {
		rifornimentiEmpty.load("res/test.txt");
        file.delete();
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),0);
	}
	
}