package pt.c02classes.s01knowledge.s02app.actors;

import java.util.HashMap;
import java.util.Map;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerAnimals implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
        IBaseConhecimento bc = new BaseConhecimento();
        IObjetoConhecimento obj;
		
		bc.setScenario("animals");
		
		// perguntas ja respondidas sao tratadas por hashing
		Map<String, String> jaRespondidas = new HashMap<String, String>();
				
		// vetor que guarda todos os animais que estao no jogo
        String[] listaAnimais = bc.listaNomes(); 
        int i = 0;
        boolean animalEsperado = false;
				
        while(!animalEsperado){
        	obj = bc.recuperaObjeto(listaAnimais[i]);
        	IDeclaracao decl = obj.primeira();

        	animalEsperado = true;
        
        	while (decl != null && animalEsperado) {
		    
        		String pergunta = decl.getPropriedade();        		
        		// se a pergunta a ser feita nao foi respondida anteriormente, ela eh feita 
        		if(!(jaRespondidas.containsKey(pergunta))){
        			String respostaEsperada = decl.getValor();
        			String resposta = responder.ask(pergunta);
		        			
        			if (resposta.equalsIgnoreCase(respostaEsperada))
        				decl = obj.proxima();
        			else
        				animalEsperado = false;
		        	
        			jaRespondidas.put(pergunta, resposta);
        		}   // ignora a pergunta, caso seja repetida, passando para a proxima disponivel 
        		else						
        			decl = obj.proxima();
        	}
		        
        	i++;
        }
		        	
		boolean acertei = responder.finalAnswer(listaAnimais[i-1]);
				
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");	
	
		return acertei;
	}	
}
