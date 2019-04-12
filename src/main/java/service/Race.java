package service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Domain.Turtle;

public class Race {
	
	private Turtle [] contenders;
	private Racecontender [] raceContenders;
	private final int SLOTS = 8;
	private final int distance = 100;
	private int [] finishingPositions = new int [3];
	private Random random;
	
	public Race (Turtle [] contenders) {
		setContenders(contenders);
		raceContenders = new Racecontender[SLOTS];
		
		for (int i = 0 ; i < SLOTS ; i++ ) {
			raceContenders[i] = new Racecontender(this.contenders[i]);
		}
		
	}

	public Turtle[] getContenders() {
		return contenders;
	}
	
	public int [] getFinishingPosition() {
		return finishingPositions;
	}

	public void setContenders(Turtle[] contenders) {

		
		if (contenders.length == SLOTS) {
			this.contenders = contenders;
		} else if (contenders.length > 3) {
			this.contenders = Arrays.copyOfRange(contenders, 0, 2);
		} else {
			this.contenders = Arrays.copyOf(contenders, 3);
			for (int i = contenders.length ; i < SLOTS ; i++ ) {
				System.out.println("called");
				this.contenders[i] = new Turtle("DefaulTurtle" + i, 7, 7, 7);
			}
		}
	}
	
	
	public void startRace () {

		
		boolean raceCompleted = false;
		int lap = 0;
		
		while ( !raceCompleted ) {

			for (int i = 0 ; i < this.raceContenders.length ; i++ ) {
				
				raceContenders[i].goDistance();
				
				if (raceContenders[i].getDistance() >= distance ) {
					raceCompleted = true;
				}

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Lap: " + ++lap);
			List<Racecontender>currentStandings = getCurrentPositions();
			System.out.printf("%s in the lead. %s in second and %s in third\n", 
					currentStandings.get(0).getTurtle().getName(),
					currentStandings.get(1).getTurtle().getName(),
					currentStandings.get(2).getTurtle().getName());
			
	
		}
		
		List<Racecontender> finalStandings = getCurrentPositions();
		for (int i = 0 ; i < SLOTS ; i++ ) {
			System.out.printf("%d: %s with a distance of %d\n", i + 1, 
					finalStandings.get(i).getTurtle().getName(), finalStandings.get(i).getDistance());
		}
	
		
	}
	
	public List<Racecontender> getCurrentPositions () {
		List<Racecontender> contendersAsList = Arrays.asList(raceContenders);
		return contendersAsList.stream().sorted((a, b) -> b.getDistance() - a.getDistance()).collect(Collectors.toList());
	}
	
	
	

}

