import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * Snapshot deals with storing the id and current state of the database. 
 * As well as storing this data, the Snapshot class should manage operations
 * related to snapshots.
 */

public class Snapshot {
	private int id;
	private List<Entry> entries;

	public Snapshot(int id, List<Entry> entries) {
		/* Initialises the id and ArrayList of entries. We make a deep copy of the list so that it is not aliased. */
		this.id = id;
		List<Entry> allEntries = new ArrayList<Entry>();
		
		//Make a deep copy of the list of entries to be saved as a snapshot
		for (int i=0;i<entries.size();i++) {
			Entry newEntry = new Entry(entries.get(i).getKey(), entries.get(i).getValues());
			allEntries.add(newEntry);
		}
		
		this.entries = allEntries;
	}
	
	/**
	 * Method to check whether the snapshot contains a given key.
	 */
	public boolean hasKey(String key) {
		
		//Run a for loop over the list of entries to determine if the snapshot has the key.
		for (int i=0;i<entries.size();i++) {
			if (entries.get(i).getKey().equals(key)) {
				return true;
			}
		}

		return false;
	}
	
	
	/** 
	 * Getter method used to retrieve the Snapshot's id because it is a private variable.
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Getter method for returning entires in the current snapshot.
	 */
	public List<Entry> getEntries() {
		List<Entry> returnEntries = new ArrayList<Entry>();
		
		//Run a loop to make a deep copy of the Entry objects to retrurn to ensure that lists are not aliased.
		for (int i=0;i<entries.size();i++) {
			Entry newEntry = new Entry(entries.get(i).getKey(), entries.get(i).getValues());
			returnEntries.add(newEntry);
		}
		
		return returnEntries;
	}
	
	
	/**
	 * Finds and removes the key.
	 *
	 * @param key the key to remove
	 */
	public void removeKey(String key) {
		//Run a for loop to find the Entry objects that match the given key and remove them.
		for (int i=0;i<entries.size();i++) {
			if (entries.get(i).getKey().equals(key)) {
				entries.remove(i);
				i--;
			}
		}
	}

	/**
	 * Finds the list of entries to restore.
	 *
	 * @return the list of entries in the restored state
	 */
	public List<Entry> rollback() {
		List<Entry> copyList = new ArrayList<Entry>();
		
		//Run a for loop over the entries to make and return a deep copy of the list.
		for (int i=0;i<entries.size();i++) {
			Entry newEntry = new Entry(entries.get(i).getKey(), entries.get(i).getValues());
			copyList.add(newEntry);
		}
		
		return copyList;
	}


	/**
	 * Saves the snapshot to file.
	 *
	 * @param filename the name of the file
	 */
	public void archive(String filename) {
		try {
			FileWriter outfileWriter = new FileWriter(filename);

			//Iterate over each entry stored in the current list of entries to format and store each key and list of values.
			for (int i=0;i<entries.size();i++) {
				outfileWriter.write(entries.get(i).getKey() + "|");
				List<Integer> entryValues = entries.get(i).getValues();

				//For each list of values, format the integers.
				for (int k=0;k<entryValues.size();k++) {
					outfileWriter.write(entryValues.get(k) + "");
					
					if (k == entryValues.size()-1) {
						break;
					} else {
						outfileWriter.write(",");
					}
				}
				
				outfileWriter.write("\n");
			}
			
			outfileWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads and restores a snapshot from file.
	 *
	 * @param  filename the name of the file
	 * @return          the list of entries in the restored state
	 */
	public static List<Entry> restore(String filename) {
		List<Entry> allEntries = new ArrayList<Entry>();

		try {
			File restoreFile = new File(filename);
			Scanner infile = new Scanner(restoreFile);

			//Use the file to get the key convert the values to integers, and create a list of Entry objects.
			while (infile.hasNextLine()) {
				String[] newLine = infile.nextLine().split("\\|");
				String key = newLine[0];
				String[] newValues = newLine[1].split(",");
				ArrayList<Integer> allValues = new ArrayList<>();

				//Iterate over the values in each line of the file, convert them to integers and then add then to a list.
				for (int i=0;i<newValues.length;i++) {
					allValues.add(Integer.parseInt(newValues[i]));
				}

				if (allValues.size()==0) {
					continue;
				} else if (key == null) {
					continue;
				} else {
					//Create entry objects and store these objects in the list of entry objects.
					Entry newObject = new Entry(key, allValues);
					allEntries.add(newObject);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allEntries;
	}

	/**
	 * Formats all snapshots for display.
	 *
	 * @param  snapshots the snapshots to display
	 * @return           the snapshots ready to display
	 */
	public static String listAllSnapshots(List<Snapshot> snapshots) {
		
		if (snapshots.size() == 0) {
			return "no snapshots";
		} else {
			StringBuilder returnString = new StringBuilder();

			//Loop over the snapshots to format their ids and, if we reach the second last element, do not add a new line character.
			for (int i=0;i<snapshots.size();i++) {
				returnString.append(snapshots.get(i).getId());
				if (i==snapshots.size()-1) {
					break;
				} 
				returnString.append("\n");
			}

			return returnString.toString();
		}
	}
}
