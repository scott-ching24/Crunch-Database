import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.NumberFormatException;
import java.util.Collections;

/** 
 * This is responsible for the overall management of the database.
 * CrunchDB should deal with taking input from the user and displaying the correct
 * output while passing off the more complicated work to the corresponding
 * classes.
 */

public class CrunchDB {

	private List<Entry> entries;
	private List<Snapshot> snapshots;

	public CrunchDB() {
		entries = new ArrayList<Entry>();
		snapshots = new ArrayList<Snapshot>();
	}
	
	
	/** 
	 * Custom function used to find the Entry object of a key.
	 */
	private Entry findKey(String key) {
		for (int i=0;i<entries.size();i++) {
			if (entries.get(i).getKey().equals(key)) {
				return entries.get(i);
			}
		}
		return null;
	}

	/**
	 * Custom function used to create a String for output.
	 */
	private <T> String getOutput(List<T> unformatted) {
		StringBuilder outputString = new StringBuilder();
		outputString.append("[");

		for (int i=0;i<unformatted.size();i++) {
			outputString.append(unformatted.get(i));

			if (i == unformatted.size()-1) {
				break;
			} else {
				outputString.append(" ");
			}

		}

		outputString.append("]");
		return outputString.toString();
	}
	
	
	/** 
	 * Displays all keys in current state.
	 */
	private void listKeys() {
		if (entries.size() == 0) {
			System.out.println("no keys");
		} else {
			//Run a for loop over the entries to get their keys in reverse order.
			for (int i=0;i<entries.size();i++) {
				System.out.println(entries.get(i).getKey());
			}		
		}
	}

	/**
	 * Displays all entries in the current state.
	 */
	private void listEntries() {
		if (entries.size()==0) {
			System.out.println("no entries");
		} else {
			System.out.println(Entry.listAllEntries(entries));
		}	
	}
	
	/**
	 * Displays all snapshots in the current state.
	 */
	private void listSnapshot() {
		if (snapshots.size()==0) {
			System.out.println("no snapshots");
		} else {
			System.out.println(Snapshot.listAllSnapshots(snapshots));
		}
	}

	/**
	 * Displays entry values.
	 *
	 * @param key the key of the entry
	 */
	private void get(String key) {
		boolean found = false;
		int index = 0;

		//Run a for loop over the entries to find the values of the key.
		Entry keyObject = findKey(key);
		if (keyObject == null) {
			System.out.println("no such key");
		} else {
			System.out.println(keyObject.get());
		}
	}
	
	/**
	 * Deletes entry from current state.
	 *
	 * @param key the key of the entry
	 */
	private void del(String key) {
		boolean found = false;
		
		//Run a loop over the entries to find the Entry with the key and remove it.
		Entry keyObject = findKey(key);
		
		if (keyObject == null) {
			System.out.println("no such key");
		}  else {
			entries.remove(keyObject);
			System.out.println("ok");
		}
	}

	/**
	 * Deletes entry from current state and snapshots.
	 *
	 * @param key the key of the entry
	 */
	private void purge(String key) {		
		//Run a loop over the entries to find the corresponding Entry object to the key and remove it.
		Entry keyEntry = findKey(key);
		
		if (keyEntry != null) {
			entries.remove(keyEntry);
		}
		
		//Run a loop over the snapshots in CrunchDB to remove all Entry objects that have the key.
		for (int k=0;k<snapshots.size();k++) {
			if (snapshots.get(k).hasKey(key)) {
				snapshots.get(k).removeKey(key);
			}
		}
		
		System.out.println("ok");
	}

	/**
	 * Sets entry values.
	 *
	 * @param key    the key of the entry
	 * @param values the values to set
	 */
	private void set(String key, List<Integer> values) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			entries.add(0, new Entry(key, values));
		} else {
			keyEntry.set(values);
		}
		
		System.out.println("ok");
	}

	/**
	 * Pushes values to the front.
	 *
	 * @param key    the key of the entry
	 * @param values the values to push
	 */
	private void push(String key, List<Integer> values) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			keyEntry.push(values);
			System.out.println("ok");
		}
	}

	/**
	 * Appends values to the back.
	 *
	 * @param key    the key of the entry
	 * @param values the values to append
	 */
	private void append(String key, List<Integer> values) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			keyEntry.append(values);
			System.out.println("ok");
		}
		boolean found = false;
	}

	/**
	 * Displays value at index.
	 *
	 * @param key   the key of the entry
	 * @param index the index to display
	 */
	private void pick(String key, int index) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			Integer returnValue = keyEntry.pick(index);
			
			if (returnValue == null) {
				System.out.println("index out of range");
			} else {
				System.out.println(returnValue);
			}
		}
	}
	
	/**
	 * Displays and removes the value at index.
	 *
	 * @param key   the key of the entry
	 * @param index the index to pluck
	 */
	private void pluck(String key, int index) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			Integer returnValue = keyEntry.pluck(index);
			
			if (returnValue == null) {
				System.out.println("index out of range");
			} else {
				System.out.println(returnValue);
			}
		}
	}

	/**
	 * Displays and removes the front value.
	 *
	 * @param key the key of the entry
	 */
	private void pop(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			Integer returnValue = keyEntry.pop();
			
			if (returnValue == null) {
				System.out.println("nil");
			} else {
				System.out.println(returnValue);
			}
		}
		Integer returnValue = null;
		boolean found = false;	
	}

	/** 
	 * Deletes snapshot.
	 *
	 * @param id the id of the snapshot
	 */
	private void drop(int id) {
		for (int i=0;i<snapshots.size();i++) {
			if (snapshots.get(i).getId() == id) {
				snapshots.remove(i);
				System.out.println("ok");
				return;
			}
		}
		
		System.out.println("no such snapshot");
	}

	/**
	 * Restores to snapshot and deletes newer snapshots.
	 *
	 * @param id the id of the snapshot
	 */
	private void rollback(int id) {
		int index = 0;
		boolean found = false;
		
		for (int i=0;i<snapshots.size();i++) {
			if (snapshots.get(i).getId() == id) {
				entries = new ArrayList<Entry>(snapshots.get(i).rollback());
				index = i;
				found = true;
				break;
			}
		}
		
		if (found == false) {
			System.out.println("no such snapshot");
		} else {
			List<Snapshot> newSnapshots = new ArrayList<Snapshot>();

			for (int k=index;k<snapshots.size();k++) {
				newSnapshots.add(snapshots.get(k));
			}

			snapshots = newSnapshots;
			System.out.println("ok");
		}
	}

	/** 
	 * Replaces current state with a copy of snapshot.
	 *
	 * @param id the id of the snapshot
	 */
	private void checkout(int id) {
		for (int i=0;i<snapshots.size();i++) {
			if (snapshots.get(i).getId() == id) {
				ArrayList<Entry> copyEntries = new ArrayList<>();

				for (int k=0;k<snapshots.get(i).rollback().size();k++) {
					Entry newObject = new Entry(snapshots.get(i).rollback().get(k).getKey(), new ArrayList<Integer>(snapshots.get(i).rollback().get(k).getValues()));
					copyEntries.add(newObject);
				}
				
				this.entries = copyEntries;
				System.out.println("ok");
				return;
			}
		}
		
		System.out.println("no such snapshot");
	}

	/** 
	 * Saves the current state as a snapshot.
	 */
	private void snapshot() {
		ArrayList<Entry> copyEntries = new ArrayList<>();

		for (int i=0;i<entries.size();i++) {
			copyEntries.add(new Entry(entries.get(i).getKey(), entries.get(i).getValues()));
		}

		Snapshot newSnapshot = new Snapshot(snapshots.size()+1, copyEntries);
		snapshots.add(0, newSnapshot);

		System.out.printf("saved as snapshot %d\n", snapshots.size());
	}

	/**
	 * Saves snapshot to file.
	 *
	 * @param id       the id of the snapshot 
	 * @param filename the name of the file
	 */
	private void archive(int id, String filename) {
		boolean found = false;
		
		for (int i=0;i<snapshots.size();i++) {
			if (snapshots.get(i).getId() == id) {
				snapshots.get(i).archive(filename);	
				found = true;
				break;
			}
		}
		
		if (found) {
			System.out.println("ok");
		} else {
			System.out.println("no such snapshot");
		}
	}

	/**
	 * Loads and restores snapshot from file.
	 *
	 * @param filename the name of the file
	 */
	private void restore(String filename) {
		List<Entry> newEntries = Snapshot.restore(filename);
		entries = newEntries;
		
		for (int i=0;i<snapshots.size();i++) {
			snapshots.remove(i);
			i--;
		}
		
		System.out.println("ok");
	}

	/**
	 * Displays minimum value.
	 *
	 * @param key the key of the entry
	 */
	private void min(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else if (keyEntry.getValues().size() == 0) {
			System.out.println("nil");
		} else {
			System.out.println(keyEntry.min());
		}
	}

	/**
	 * Displays maximum value.
	 *
	 * @param key the of the entry
	 */
	private void max(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else if (keyEntry.getValues().size() == 0) {
			System.out.println("nil");
		} else {
			System.out.println(keyEntry.max());
		}
	}

	/**
	 * Displays the sum of values.
	 *
	 * @param key the key of the entry
	 */
	private void sum(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else if (keyEntry.getValues().size() == 0) {
			System.out.println("nil");
		} else {
			System.out.println(keyEntry.sum());
		}
	}


	/**
	 * Displays the number of values.
	 *
	 * @param key the key of the entry
	 */
	private void len(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			System.out.println(keyEntry.len());
		}
	}


	/**
	 * Reverses the order of values.
	 *
	 * @param key the key of the entry
	 */
	private void rev(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			keyEntry.rev();
			System.out.println("ok");
		}
	}

	/**
	 * Removes repeated adjacent values.
	 *
	 * @param key the key of the entry
	 */
	private void uniq(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			keyEntry.uniq();
			System.out.println("ok");
		}
	}

	/** 
	 * Sorts values in ascending order.
	 *
	 * @param key the key of the entry
	 */
	private void sort(String key) {
		Entry keyEntry = findKey(key);
		
		if (keyEntry == null) {
			System.out.println("no such key");
		} else {
			keyEntry.sort();
			System.out.println("ok");
		}
	}
	
	
	
	/**
	 * Displays set difference of values in keys. 
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void diff(List<String> keys) {
		List<Entry> diffEntries = new ArrayList<Entry>();
		int counter = 0;
		
		//Loop through keys and entries to create a list of entries that is used by the diff method.
		for (int i=0;i<keys.size();i++) {
			for (int k=0;k<entries.size();k++) {
				if (entries.get(k).getKey().equals(keys.get(i))) {
					diffEntries.add(entries.get(k));
					counter++;
					break;
				}
			}
		}
		
		if (counter != keys.size()) {
			System.out.println("not enough arguments");
		} else {
			System.out.println(getOutput(Entry.diff(diffEntries)));	
		}
	}

	/**
	 * Displays set intersection of values in keys.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void inter(List<String> keys) {
		List<Entry> intersectEntries = new ArrayList<Entry>();
		int counter = 0;
		
		//Run a loop over all the keys to find the matching Entry object.
		for (int i=0;i<keys.size();i++) {
			
			//Run a loop over the list of entries to find the matching Entry object. If it is found, increment the match counter.
			for (int k=0;k<entries.size();k++) {
				if (entries.get(k).getKey().equals(keys.get(i))) {
					intersectEntries.add(entries.get(k));
					counter++;
					break;
				}
			}
			
		}
		
		if (counter == keys.size()) {
			
			System.out.println(getOutput(Entry.inter(intersectEntries)));
		} else {
			System.out.println("not enough arguments");
		}
		
	}

	/**
	 * Displays set union of values in keys.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void union(List<String> keys) {
		List<Entry> unionEntries = new ArrayList<Entry>();
		int counter = 0;
		
		//Run a loop over all the keys to find the matching Entry object.
		for (int i=0;i<keys.size();i++) {
			
			//Run a loop over the list of entries to find the matching Entry object. If it is found, increment the match counter.
			for (int k=0;k<entries.size();k++) {
				if (entries.get(k).getKey().equals(keys.get(i))) {
					unionEntries.add(entries.get(k));
					counter++;
					break;
				}
			}
			
		}
		
		if (counter == keys.size() && keys.size() >= 2) {
			System.out.println(getOutput(Entry.union(unionEntries)));
		} else {
			System.out.println("not enough arguments");
		}
		
	}

	/** 
	 * Displays cartesian product of the sets.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void cartprod(List<String> keys) {
		List<Entry> cartprodEntries = new ArrayList<Entry>();
		int counter = 0;
		
		//Run a loop over all the keys to find the matching Entry object.
		for (int i=0;i<keys.size();i++) {
			
			//Run a loop over the list of entries to find the matching Entry object. If it is found, increment the match counter.
			for (int k=0;k<entries.size();k++) {
				if (entries.get(k).getKey().equals(keys.get(i))) {
					cartprodEntries.add(entries.get(k));
					counter++;
					break;
				}
			}
			
		}
		
		if (counter == keys.size()) {
			List<List<Integer>> cartprodList = Entry.cartprod(cartprodEntries);
			StringBuilder outputString= new StringBuilder();
			outputString.append("[ ");

			for(int i=0;i<cartprodList.size();i++) {
				outputString.append("[");
				for (int k=0;k<cartprodList.get(i).size();k++) {
					outputString.append(cartprodList.get(i).get(k));
					if (k == cartprodList.get(i).size()-1) {
						outputString.append("]");
						break;
					}
					outputString.append(" ");
				}

				if (i == cartprodList.size()-1) {
					outputString.append(" ]");
					break;
				}

				outputString.append(" ");
			}
			System.out.println(outputString);
		} else {
			System.out.println("no such key");
		}
		
	}

	private static final String HELP =
		"BYE   clear database and exit\n"+
		"HELP  display this help message\n"+
		"\n"+
		"LIST KEYS       displays all keys in current state\n"+
		"LIST ENTRIES    displays all entries in current state\n"+
		"LIST SNAPSHOTS  displays all snapshots in the database\n"+
		"\n"+
		"GET <key>    displays entry values\n"+
		"DEL <key>    deletes entry from current state\n"+
		"PURGE <key>  deletes entry from current state and snapshots\n"+
		"\n"+
		"SET <key> <value ...>     sets entry values\n"+
		"PUSH <key> <value ...>    pushes values to the front\n"+
		"APPEND <key> <value ...>  appends values to the back\n"+
		"\n"+
		"PICK <key> <index>   displays value at index\n"+
		"PLUCK <key> <index>  displays and removes value at index\n"+
		"POP <key>            displays and removes the front value\n"+
		"\n"+
		"DROP <id>      deletes snapshot\n"+
		"ROLLBACK <id>  restores to snapshot and deletes newer snapshots\n"+
		"CHECKOUT <id>  replaces current state with a copy of snapshot\n"+
		"SNAPSHOT       saves the current state as a snapshot\n"+
		"\n"+
		"ARCHIVE <id> <filename> saves snapshot to file\n"+
		"RESTORE <filename> loads snapshot from file\n"+
		"\n"+
		"MIN <key>  displays minimum value\n"+
		"MAX <key>  displays maximum value\n"+
		"SUM <key>  displays sum of values\n"+
		"LEN <key>  displays number of values\n"+
		"\n"+
		"REV <key>   reverses order of values\n"+
		"UNIQ <key>  removes repeated adjacent values\n"+
		"SORT <key>  sorts values in ascending order\n"+
		"\n"+
		"DIFF <key> <key ...>   displays set difference of values in keys\n"+
		"INTER <key> <key ...>  displays set intersection of values in keys\n"+
		"UNION <key> <key ...>  displays set union of values in keys\n"+
		"CARTPROD <key> <key ...>  displays set union of values in keys";
	
	public static void bye() {
		System.out.println("bye");
	}
	
	public static void help() {
		System.out.println(HELP);
	}
	
	
public static void main(String[] args) {
		/* Main function of the database that runs all the methods. It creates a new database and accepts user input. */
		CrunchDB newDatabase = new CrunchDB();
		Scanner userInput = new Scanner(System.in);
		System.out.print("> ");
		
		while (userInput.hasNextLine()) {
			
			
			
			String[] newUserInput = userInput.nextLine().trim().split(" ");
			boolean hasTwoInputs = false;
			boolean hasAllInputs = false;
			
			if (newUserInput.length == 2) {
				hasTwoInputs = true;
			} else if (newUserInput.length >= 3) {
				hasAllInputs = true;
			}
			
			if (newUserInput[0].toLowerCase().equals("bye")) {
				if (newUserInput.length == 1) {
					bye();
					break;
				} else {
					System.out.println("invalid command");
				}
			}
			
			switch (newUserInput[0].toLowerCase()) {
					
				case "help":
					if (newUserInput.length == 1) {
						help();
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "list":
					if (hasTwoInputs) {
						
						switch (newUserInput[1].toLowerCase()) {
								
							case "keys":
								newDatabase.listKeys();
								break;

							case "entries":
								newDatabase.listEntries();
								break;

							case "snapshots":
								newDatabase.listSnapshot();
								break;
						} 
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "get":
					if (hasTwoInputs) {
						newDatabase.get(newUserInput[1]);	
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "del":
					if (hasTwoInputs) {
						newDatabase.del(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "purge":
					if (hasTwoInputs) {
						newDatabase.purge(newUserInput[1]);	
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "set":
					if (hasAllInputs) {
						String key = newUserInput[1];
						ArrayList<Integer> userValues = new ArrayList<>();
						
						for (int i=2;i<newUserInput.length;i++) {
							try {
								Integer newNumber = Integer.parseInt(newUserInput[i]);
								userValues.add(newNumber);
							} catch (NumberFormatException e) {
								continue;
							}
						}

						newDatabase.set(key, userValues);	
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "push":
					if (hasAllInputs) {
						String key = newUserInput[1];
						ArrayList<Integer> userValues = new ArrayList<>();
						
						for (int i=2;i<newUserInput.length;i++) {
							try {
								Integer newNumber = Integer.parseInt(newUserInput[i]);
								userValues.add(newNumber);
							} catch (NumberFormatException e) {
								continue;
							}
						}
						
						newDatabase.push(key, userValues);	
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "append":
					if (hasAllInputs) {
						String key = newUserInput[1];
						ArrayList<Integer> userValues = new ArrayList<>();
						
						for (int i=2;i<newUserInput.length;i++) {
							try {
								Integer newNumber = Integer.parseInt(newUserInput[i]);
								userValues.add(newNumber);
							} catch (NumberFormatException e) {
								continue;
							}
						}
						
						newDatabase.append(key, userValues);	
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "pick":
					if (hasAllInputs) {
						String key = newUserInput[1];
						Integer index;
						
						try {
							index = Integer.parseInt(newUserInput[2]);
						} catch (NumberFormatException e) {
							break;
						}
						
						newDatabase.pick(key, index);	
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "pluck":
					if (hasAllInputs) {
						String key = newUserInput[1];
						Integer index;
						
						try {
							index = Integer.parseInt(newUserInput[2]);
						} catch (NumberFormatException e) {
							break;
						}
						
						newDatabase.pluck(key, index);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "pop":
					if (hasTwoInputs) {
						newDatabase.pop(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "drop":
					if (hasTwoInputs) {
						try {
							Integer snapshotId = Integer.parseInt(newUserInput[1]);
							newDatabase.drop(snapshotId);
						} catch (NumberFormatException e) {
							System.out.println("id is not a valid number");
							break;
						}
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "rollback":
					if (hasTwoInputs) {
						try {
							Integer snapshotsId = Integer.parseInt(newUserInput[1]);
							newDatabase.rollback(snapshotsId);
						} catch (NumberFormatException e) {
							System.out.println("id is not a valid number");
							break;
						}
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "checkout":
					if (hasTwoInputs) {
						try {
							Integer snapshotsId = Integer.parseInt(newUserInput[1]);
							newDatabase.checkout(snapshotsId);
						} catch (NumberFormatException e) {
							System.out.println("id is not a valid number");
							break;
						}
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "snapshot":
					if (newUserInput.length == 1) {
						newDatabase.snapshot();
					} else {
						System.out.println("invalid command");
					} 
					
					break;
				
				case "archive":
					if (hasAllInputs) {
						try {
							Integer snapshotId = Integer.parseInt(newUserInput[1]);
							newDatabase.archive(snapshotId, newUserInput[2]);
						} catch (NumberFormatException e) {
							System.out.println("id is not a valid number");
							break;
						}
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "restore":
					if (hasTwoInputs) {
						newDatabase.restore(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "min":
					if (hasTwoInputs) {
						newDatabase.min(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "max":
					if (hasTwoInputs) {
						newDatabase.max(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "sum":
					if (hasTwoInputs) {
						newDatabase.sum(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "len":
					if (hasTwoInputs) {
						newDatabase.len(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "rev":
					if (hasTwoInputs) {
						newDatabase.rev(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "uniq":
					if (hasTwoInputs) {
						newDatabase.uniq(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "sort":
					if (hasTwoInputs) {
						newDatabase.sort(newUserInput[1]);
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "diff":
					if (hasAllInputs) {
						ArrayList<String> allKeys = new ArrayList<>();
						
						for (int i=1;i<newUserInput.length;i++) {
							allKeys.add(newUserInput[i]);
						}
						
						newDatabase.diff(allKeys);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "inter":
					if (hasAllInputs) {
						ArrayList<String> allKeys = new ArrayList<>();
						
						for (int i=1;i<newUserInput.length;i++) {
							allKeys.add(newUserInput[i]);
						}
						
						newDatabase.inter(allKeys);
					} else {
						System.out.println("invalid command");
					}
					
					break;
				
				case "union":
					if (hasAllInputs) {
						ArrayList<String> allKeys = new ArrayList<>();
						
						for (int i=1;i<newUserInput.length;i++) {
							allKeys.add(newUserInput[i]);
						}
						
						newDatabase.union(allKeys);
					} else {
						System.out.println("invalid command");
					}
					
					break;
					
				case "cartprod":
					if (hasAllInputs) {
						ArrayList<String> allKeys = new ArrayList<>();
						
						for (int i=1;i<newUserInput.length;i++) {
							allKeys.add(newUserInput[i]);
						}
						
						newDatabase.cartprod(allKeys);
					} else {
						System.out.println("invalid command");
					}
					
					break;
			}
			
			System.out.print("\n> ");
		}

	}
}

