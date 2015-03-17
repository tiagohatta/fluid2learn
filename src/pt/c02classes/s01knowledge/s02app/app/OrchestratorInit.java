package pt.c02classes.s01knowledge.s02app.app;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;
import java.util.Scanner;


public class OrchestratorInit
{
	public static void main(String[] args)
	{
		Scanner entrada = new Scanner(System.in);
		
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
				
		IBaseConhecimento base = new BaseConhecimento();
		
		System.out.println("Qual desafio você deseja? Animals ou Maze?");
		String desafio = entrada.nextLine();
		
		if(desafio.equalsIgnoreCase("Animals")){
			base.setScenario("animals");
			String listaAnimais[] = base.listaNomes();
			
			System.out.println("Animais disponíveis:");
			
			for(int i = 0; i < listaAnimais.length; i++){
				System.out.println(listaAnimais[i]);				
			}
			
			System.out.println();
			System.out.println("Escolha o animal a ser descoberto:");
			String animal = entrada.nextLine();
			
        	System.out.println("Enquirer com " + animal + "...");
			stat = new Statistics();
			resp = new ResponderAnimals(stat, animal.toLowerCase());
			enq = new EnquirerAnimals();
			enq.connect(resp);
			enq.discover();
			System.out.println("----------------------------------------------------------------------------------------\n");
		
		}
		else{
			if(desafio.equalsIgnoreCase("Maze")){
				
				base.setScenario("maze");
				String listaLab[] = base.listaNomes();
				
				System.out.println("Labirintos disponíveis:");
				
				for(int j = 0; j < listaLab.length; j++){
					System.out.println(listaLab[j]);				
				}
				
				System.out.println();
				System.out.println("Escolha o labirinto a ser descoberto:");
				String labirinto = entrada.nextLine();
							
				System.out.println("Enquirer com " + labirinto + "...");
				stat = new Statistics();
				resp = new ResponderMaze(stat, labirinto.toLowerCase());
				enq = new EnquirerMaze();
				enq.connect(resp);
				enq.discover();
				System.out.println("----------------------------------------------------------------------------------------\n");
			}
			else
				System.out.println("Você não escolheu nenhum dos dois desafios.");
		}
		
		entrada.close();
	}
	
			
}
