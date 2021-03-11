package application.utils;

public enum TipoCialda {
	caffè,
	caffèArabica,
	thè,
	thèLimone,
	cioccolata,
	camomilla;
	
	
	
	public static TipoCialda fromString(String str) {
	    switch(str) {
	    case "caffèArabica":
	        return TipoCialda.caffèArabica;
	    case "caffè":
	        return TipoCialda.caffè;
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