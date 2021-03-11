package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

class TestRifornimento {

	Rifornimento rifornimento;
	Date actualDate = new Date();
	int numeroScatole = new Random().nextInt(10);
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testCostruttoreConData(TipoCialda tc) {
		rifornimento = new Rifornimento(1, tc, actualDate);
		assertEquals(rifornimento.getData(),actualDate);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testGetNumeroScatole(TipoCialda tc) {
		rifornimento = new Rifornimento(numeroScatole, tc);
		assertEquals(rifornimento.getNumeroScatole(),numeroScatole);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testGetData(TipoCialda tc) {
		rifornimento = new Rifornimento(1, tc, actualDate);
		assertEquals(rifornimento.getData(),actualDate);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testGetTipoCialda(TipoCialda tc) {
		rifornimento = new Rifornimento(1, tc);
		assertEquals(rifornimento.getTipoCialda(),tc);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testGetEpoch(TipoCialda tc) {
		rifornimento = new Rifornimento(1, tc, actualDate);
		assertEquals(rifornimento.getEpoch(),actualDate.getTime());
	}

}