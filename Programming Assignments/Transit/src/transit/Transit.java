package transit;

//import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered
 * linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/*
	 * Default constructor used by the driver and Autolab.
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit() {
		trainZero = null;
	}

	/*
	 * Default constructor used by the driver and Autolab.
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) {
		trainZero = tz;
	}

	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero() {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations,
	 * bus
	 * stops, and walking locations. Each layer begins with a location of 0, even
	 * though
	 * the arrays don't contain the value 0. Store the zero node in the train layer
	 * in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops      Int array listing all the bus stops
	 * @param locations     Int array listing all the walking locations (always
	 *                      increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		TNode locT = new TNode();
		TNode busT = new TNode(0, null, locT);
		TNode trainT = new TNode(0, null, busT);
		this.trainZero = trainT;

		TNode currentLocT = locT;
		TNode currentBusT = busT;
		TNode currentTrainT = trainT;

		for (int locCount = 0; locCount < locations.length; locCount++) {
			currentLocT.setNext(new TNode(locations[locCount], null, null));
			currentLocT = currentLocT.getNext();

		}

		for (int busCount = 0; busCount < busStops.length; busCount++) {
			currentBusT.setNext(new TNode(busStops[busCount], null, null));
			currentBusT = currentBusT.getNext();
			currentLocT = locT;

			while(currentLocT != null) {
				if(currentLocT.getLocation() == currentBusT.getLocation()) currentBusT.setDown(currentLocT);
				currentLocT=currentLocT.getNext();
			}

		}

		for (int trainCount = 0; trainCount < trainStations.length; trainCount++) {
			currentTrainT.setNext(new TNode(trainStations[trainCount], null, null));
			currentTrainT = currentTrainT.getNext();
			currentBusT=busT;
			while(currentBusT != null) {
				if(currentBusT.getLocation() == currentTrainT.getLocation()) currentTrainT.setDown(currentBusT);
				currentBusT = currentBusT.getNext();
			}
		}

		
	}

	/**
	 * Modifies the layered list to remove the given train station but NOT its
	 * associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		
		TNode current = getTrainZero();
		TNode prev = current;
		while (current != null) {
			if (current.getLocation() == station) {
				prev.setNext(current.getNext());
				return;
			}
			prev = current;
			current = current.getNext();
		}
		return;

	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do
	 * nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
		TNode busList = getTrainZero().getDown();
		TNode currentBus = busList;
		TNode prev = busList;
		TNode currentLoc = getTrainZero().getDown().getDown();
		TNode currentTrain = getTrainZero();
		TNode newBusStop = null;

		TNode checkCurrent = currentLoc;
		TNode checkPrev = currentLoc;

		while(checkCurrent!=null) {

			checkPrev = checkCurrent;
			checkCurrent = checkCurrent.getNext();
		}

		if(checkPrev.getLocation() < busStop) return;

		while (currentBus != null) {
			if (currentBus.getLocation() == busStop)
				return;
			if (currentBus.getLocation() > busStop && prev.getLocation() < busStop) {
				newBusStop = new TNode(busStop, currentBus, null);
				prev.setNext(newBusStop);
			}
			prev = currentBus;
			currentBus = currentBus.getNext();

		}

		if(currentBus == null && prev.getLocation() < busStop) {
			newBusStop = new TNode(busStop, currentBus, null);
			prev.setNext(newBusStop);
		}

		while (currentLoc != null) {
			if (currentLoc.getLocation() == busStop) {
				newBusStop.setDown(currentLoc);
			}
			currentLoc = currentLoc.getNext();
		}

		while (currentTrain != null) {
			if (currentTrain.getLocation() == busStop) {
				currentTrain.setDown(newBusStop);
			}
			currentTrain = currentTrain.getNext();
		}

		
	}

	/**
	 * Determines the optimal path to get to a given destination in the walking
	 * layer, and
	 * collects all the nodes which are visited in this path into an arraylist.
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> bestPath = new ArrayList<TNode>();
		TNode currentTrain = getTrainZero();
		TNode currentBus = getTrainZero().getDown();
		TNode currentLoc = getTrainZero().getDown().getDown();

		while (currentTrain != null) {
			if (currentTrain.getLocation() <= destination) {
				bestPath.add(currentTrain);
				currentBus = currentTrain.getDown();
			}
			currentTrain = currentTrain.getNext();
		}

		while (currentBus != null) {
			if (currentBus.getLocation() <= destination) {
				bestPath.add(currentBus);
				currentLoc = currentBus.getDown();
			}
			currentBus = currentBus.getNext();
		}

		while (currentLoc != null) {
			if (currentLoc.getLocation() <= destination) {
				bestPath.add(currentLoc);
			}
			currentLoc = currentLoc.getNext();
		}

		
		return bestPath;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the
	 * same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		// UPDATE THIS METHOD

		TNode trainList=getTrainZero();
		TNode busList=getTrainZero().getDown();
		TNode locList=getTrainZero().getDown().getDown();

		TNode currentTrain=trainList;
		TNode currentBus=busList;
		TNode currentLoc=locList;

		TNode newLocList=new TNode();
		TNode newBusList=new TNode(0,null,newLocList);

		TNode newTrainList=new TNode(0,null,newBusList);


		TNode latestTrain=newTrainList;
		TNode latestBus=newBusList;
		TNode latestLoc=newLocList;

		while(currentLoc!=null) {
			if(currentLoc.getLocation()!=0) {
				latestLoc.setNext(new TNode(currentLoc.getLocation(), null, null));
			
			latestLoc = latestLoc.getNext();
			}
			currentLoc = currentLoc.getNext();
		}

		while(currentBus!=null) {
			if(currentBus.getLocation()!=0) {
				latestBus.setNext(new TNode(currentBus.getLocation(), null, null));
			
			latestBus = latestBus.getNext();
			}
			currentBus = currentBus.getNext();
			currentLoc=newLocList;
			while(currentLoc!=null) {
				if(latestBus.getLocation()==currentLoc.getLocation()) {
					latestBus.setDown(currentLoc);
				}
				currentLoc=currentLoc.getNext();
			}

		}

		while(currentTrain!=null) {
			if(currentTrain.getLocation()!=0) {
				latestTrain.setNext(new TNode(currentTrain.getLocation(), null, null));
			
			latestTrain = latestTrain.getNext();
			}
			currentTrain = currentTrain.getNext();
			currentBus=newBusList;
			while(currentBus!=null) {
				if(latestTrain.getLocation()==currentBus.getLocation()) {
					latestTrain.setDown(currentBus);
				}
				currentBus=currentBus.getNext();
			}

		}

		return newTrainList;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are
	 *                     located
	 */
	public void addScooter(int[] scooterStops) {

		TNode trainList=getTrainZero();
		TNode busList=getTrainZero().getDown();
		TNode locList=getTrainZero().getDown().getDown();

		TNode scootList=new TNode();
		TNode currentScoot=scootList;

		busList.setDown(scootList);
		scootList.setDown(locList);

		TNode currentBus=busList;
		TNode currentLoc=locList;

		for (int scootCount = 0; scootCount < scooterStops.length; scootCount++) {
			currentScoot.setNext(new TNode(scooterStops[scootCount], null, null));
			currentScoot = currentScoot.getNext();

			currentBus=busList;
			while(currentBus!=null) {
				if(currentBus.getLocation()==currentScoot.getLocation()) currentBus.setDown(currentScoot);
				currentBus=currentBus.getNext();
			}
			currentLoc=locList;
			while(currentLoc!=null) {
				if(currentLoc.getLocation()==currentScoot.getLocation()) currentScoot.setDown(currentLoc);
				currentLoc=currentLoc.getNext();
			}

		}



		// UPDATE THIS METHOD
	}

	/**
	 * Used by the driver to display the layered linked list.
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null)
					break;

				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation() + 1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++)
						StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null)
				break;
			StdOut.println();

			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation())
					downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr)
					StdOut.print("|");
				else
					StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen - 1; j++)
					StdOut.print(" ");

				if (horizPtr.getNext() == null)
					break;

				for (int i = horizPtr.getLocation() + 1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++)
							StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}

	/**
	 * Used by the driver to display best path.
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr))
					StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++)
						StdOut.print(" ");
				}
				if (horizPtr.getNext() == null)
					break;

				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation() + 1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);

					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++)
						StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}

			if (vertPtr.getDown() == null)
				break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen - 1; j++)
					StdOut.print(" ");

				if (horizPtr.getNext() == null)
					break;

				for (int i = horizPtr.getLocation() + 1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++)
							StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
