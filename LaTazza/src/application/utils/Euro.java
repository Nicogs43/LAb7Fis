package application.utils;

import java.util.Locale;

public class Euro { 
	
	private long valore;
	
	
	public Euro(long euro, long cent) { 
		if (euro >= 0)
			valore = euro*100 + cent;
		else
			valore = euro*100 - cent;
	}
	
	public Euro(double d) { 
		valore = (long)(d*100);
	}
	
	
	public long getValore() {
		return valore;
	}
	
	
	public Euro somma(Euro e) {
		this.valore = this.valore + e.getValore(); 
		return this;
	}
	
	
	public Euro sottrai(Euro e) {
		this.valore = this.valore - e.getValore(); 
		return this;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
		if(!(obj instanceof Euro))
			return false;
		return this.ugualeA((Euro)obj);
	}

	
	@Override
	public String toString() {
		return String.format(Locale.US, "%.2f€", (double)valore/100);
	}

	public boolean ugualeA(Euro e){
		return (valore == e.getValore());
	}
	
	
	public boolean minoreDi(Euro e){ 
		return (valore < e.getValore());
	}
	
}