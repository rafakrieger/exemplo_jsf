package br.com.correntista.webservice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

import br.com.correntista.entidade.Endereco;

public class WebServiceEndereco {
	
	private Client client;
	private WebResource webResource;
	
	public WebServiceEndereco() {
		ClientConfig clientConfig = new DefaultClientConfig(GensonProvider.class);
		client = Client.create(clientConfig);
		
		//imprimir operações no console
		client.addFilter(new LoggingFilter(System.out));
		
		webResource = client.resource("https://viacep.com.br/ws/");
	}
	
	public Endereco pesquisarCep(String cep) {		
		return webResource.path(cep).path("/json").get(Endereco.class);
	}

}
