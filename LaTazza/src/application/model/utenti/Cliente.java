package application.model.utenti;

public abstract class Cliente {
	
	private String nome;

	public Cliente() {}
	public Cliente(String nome){
		this.nome=nome;
	}
	
	
	public String getNome() {
		return nome;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
	    if (!(obj instanceof Cliente))
	    	return false;
	    Cliente cliente = (Cliente)obj;
	    return this.getNome().equals(cliente.getNome());
	}


	@Override
	public int hashCode() {
		return nome.hashCode();
	}
	

}
