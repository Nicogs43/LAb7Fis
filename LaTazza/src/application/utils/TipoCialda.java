package application.utils;

public enum TipoCialda {
	caff�,
	caff�Arabica,
	th�,
	th�Limone,
	cioccolata,
	camomilla;
	
	
	
	public static TipoCialda fromString(String str) {
	    switch(str) {
	    case "caffèArabica":
	        return TipoCialda.caff�Arabica;
	    case "caffè":
	        return TipoCialda.caff�;
	    case "camomilla":
	        return TipoCialda.camomilla;
	    case "thè":
	        return TipoCialda.thè;
	    case "thèLimone":
	        return TipoCialda.thèLimone;
	    case "cioccolata":
	        return TipoCialda.cioccolata;
	    default:	
	    	return TipoCialda.thè;
	    }
	}
}