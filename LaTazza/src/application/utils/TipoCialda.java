package application.utils;

public enum TipoCialda {
	caffË,
	caffËArabica,
	thË,
	thËLimone,
	cioccolata,
	camomilla;
	
	
	
	public static TipoCialda fromString(String str) {
	    switch(str) {
	    case "caff√®Arabica":
	        return TipoCialda.caffËArabica;
	    case "caff√®":
	        return TipoCialda.caffË;
	    case "camomilla":
	        return TipoCialda.camomilla;
	    case "th√®":
	        return TipoCialda.th√®;
	    case "th√®Limone":
	        return TipoCialda.th√®Limone;
	    case "cioccolata":
	        return TipoCialda.cioccolata;
	    default:	
	    	return TipoCialda.th√®;
	    }
	}
}