package pt.c02classes.s01knowledge.s02app.actors;

import java.util.*;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;


class posicao{
	public int X,Y;
	
	public posicao(int X, int Y){
		this.X = X;
		this.Y = Y;
	}

	public posicao(posicao ponto, int X, int Y){
		this.X = ponto.X + X;
		this.Y = ponto.Y + Y;
	}
	
	public boolean posicoesIguais(posicao ponto)
	{	  
		if((ponto.X == this.X) && (ponto.Y == this.Y))		
			return true;
		
		return false;
		
	}
	
}

public class EnquirerMaze implements IEnquirer {

	IResponder responder;
	
	// pilha com posicoes ja passadas
	Stack<posicao> jaPassou;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		
		jaPassou = new Stack<posicao>();
		jaPassou.push(new posicao(0,0));
		if(resolveLab())
		{	
		
			System.out.println("A saida foi encontrada!");
			return true;
		}
		else
			return false;
	}
	
	public boolean resolveLab(){
		
		Enumeration<posicao> elements = jaPassou.elements();
		posicao mp = elements.nextElement();
		
		while(elements.hasMoreElements())
		{	
		
			if(jaPassou.peek().posicoesIguais(mp)){
				return false;
				
			}
			mp = elements.nextElement();
		}
		
	
		if (responder.ask("aqui").equals("saida")){
			return true;
		}
		
		
		if(responder.ask("norte").equals("passagem") || responder.ask("norte").equals("saida")){
		
			responder.move("norte");
			
			//Colocar a nova posicao na pilha
			jaPassou.push(new posicao(jaPassou.peek(),0,1));

			// recursividade
			if (resolveLab() == true){
				return true;
			}else{
				// caso entre em um caminho "sem saida", vai voltando ate encontrar outro caminho viavel
				responder.move("sul");
				jaPassou.pop();
			}
			
		}
		if(responder.ask("leste").equals("passagem") || responder.ask("leste").equals("saida")){		
			responder.move("leste");		
			jaPassou.push(new posicao(jaPassou.peek(),1,0));
		
			if (resolveLab() == true){
				return true;
			}
			else{
				responder.move("oeste");
				jaPassou.pop();
			}
		}
		if(responder.ask("sul").equals("passagem") || responder.ask("sul").equals("saida")){
			responder.move("sul");
			jaPassou.push(new posicao(jaPassou.peek(),0,-1));
	
			if (resolveLab() == true){
				return true;
			}else{
				responder.move("norte");
				jaPassou.pop();
			}
		}
		
		if (responder.ask("oeste").equals("passagem") || responder.ask("oeste").equals("saida")){
			responder.move("oeste");
			jaPassou.push(new posicao(jaPassou.peek(),-1,0));

			if (resolveLab() == true){
				return true;
			}
			else{
				responder.move("leste");
				jaPassou.pop();
			}	
		}
		
		return false;
	}
}