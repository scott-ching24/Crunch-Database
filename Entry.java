import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * Entry deals with storing the key and value associated with entries in the
 * database. 
 * As well as storing the data the entry class should manage operations
 * associated with any Entry.
 */

public class Entry {
	private String key;
	private List<Integer> values;

	public Entry(String key, List<Integer> values) {
		this.key = key;
		this.values = new ArrayList<Integer>();
		
		for (int i=0;i<values.size();i++) {
			this.values.add(values.get(i));
		}
	}
	
	/**
	 * Creates a getter method for the key of the current Entry.
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * A getter method that sends back a copy of the values.
	 * Ensures that the original list cannot be changed.
	 */
	public List<Integer> getValues() {
		return new ArrayList<Integer>(values);
	}


	/**
	 * Formats the Entry for display
	 *
	 * @return  the String of values
	 */
	public String get() {
		StringBuilder newString = new StringBuilder();
		newString.append("[");

		for (int i=0;i<values.size();i++) {
			newString.append(values.get(i));
			
			if (i == values.size()-1) {
				break;
			} else {
				newString.append(" ");
			}
		}

		newString.append("]");
		return newString.toString();
	}

	
	/**
	 * Sets the values of this Entry.
	 *
	 * @param values the values to set
	 */
	public void set(List<Integer> values) {
		this.values = new ArrayList<>();
		
		for (int i=0;i<values.size();i++) {
			this.values.add(values.get(i));
		}
	}

	/**
	 * Adds the values to the start.
	 *
	 * @param values the values to add
	 */
	public void push(List<Integer> values) {
		for (int i=0;i<values.size();i++) {
			this.values.add(0, values.get(i));
		}
	}

	/**
	 * Adds the values to the end.
	 *
	 * @param values the values to add
	 */
	public void append(List<Integer> values) {
		this.values.addAll(values);
	}

	/**
	 * Finds the value at the given index.
	 *
	 * @param  index the index
	 * @return       the value found 
	 */
	public Integer pick(int index) {
		int actualIndex = index -1;
		
		if (actualIndex > values.size()-1 || actualIndex < 0) {
			return null;
		}
		
		return values.get(actualIndex);
	}

	/**
	 * Finds and removes the value at the given index.
	 *
	 * @param  index the index
	 * @return       the value found
	 */
	public Integer pluck(int index) {
		int actualIndex = index - 1;
		
		if (actualIndex > values.size()-1 || actualIndex < 0) {
			return null;
		}
		
		int pluckValue = values.get(actualIndex);
		values.remove(actualIndex);
		return pluckValue;
	}

	/**
	 * Finds and removes the first value.
	 *
	 * @return the first value
	 */
	public Integer pop() {
		if (values.size() == 0) {
			return null;
		}

		int popValue = values.get(0);
		values.remove(0);
		return popValue;	
	}

	/**
	 * Finds the minimum value.
	 *
	 * @return the minimum value
	 */
	public Integer min() {
		
		if (values.size() == 0) {
			return 0;
		} else {
			int minimumNumber = values.get(0);
			
			for (int i=1;i<values.size();i++) {
				if (values.get(i) < minimumNumber) {
					minimumNumber = values.get(i);
				}
			}
			return minimumNumber;
		}
	}

	/**
	 * Finds the maximum value.
	 *
	 * @return the maximum value
	 */
	public Integer max() {
		
		if (values.size() == 0) {
			return 0;
		} else {
			int maximumNumber = values.get(0);

			for (int i=0;i<values.size();i++) {
				if (values.get(i) > maximumNumber) {
					maximumNumber = values.get(i);
				}
			}
			return maximumNumber;
		}
	}

	/**
	 * Computes the sum of all values.
	 *
	 * @return the sum
	 */
	public Integer sum() {
		/* Computes the sum of all values and returns the sum */
		
		int sum = 0;
		
		for (int s : values) {
			sum += s;
		}
		return sum;
	}

	/**
	 * Finds the number of values.
	 *
	 * @return the number of values.
	 */
	public Integer len() {
		return values.size();
	}

	/**
	 * Reverses the order of values.
	 */
	public void rev() {
		for (int i=0;i<values.size();i++) {
			values.add(i, values.get(values.size()-1));
			values.remove(values.size()-1);
		}
	}
	
	/**
	 * Removes adjacent duplicate values.
	 */
	public void uniq() {		
		
		for (int i=0;i<values.size()-1;i++) {
			if (values.get(i) == values.get(i+1)) {
				values.remove(i);
				i--;
			}
		}
	}

	/**
	 * Sorts the list in ascending order.
	 */
	public void sort() {
		values.sort(null);
		// TODO: implement this
	}

	/**
	 * Computes the set difference of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> diff(List<Entry> entries) {
		ArrayList<Integer> diffList = new ArrayList<>();
		ArrayList<Integer> duplicateList = new ArrayList<>();
		
		//For loop over all the entries.
		for (int i=0;i<entries.size();i++) {
			ArrayList<Integer> newValues = new ArrayList<>(entries.get(i).getValues());
			ArrayList<Integer> newLoopValues = new ArrayList<>();
			
			//For each value in the entry, find if it unique.
			for (Integer nextValue : newValues) {
				if (diffList.contains(nextValue)) {
					diffList.removeAll(Collections.singletonList(nextValue));
					duplicateList.add(nextValue);
				} else if (duplicateList.contains(nextValue)) {
					continue;
				} else {
					newLoopValues.add(nextValue);
				}
			}
			
			//Create a new list to remove repeats in the list.
			for (int k=0;k<newLoopValues.size();k++) {
				if (diffList.contains(newLoopValues.get(k))) {
					continue;
				} else {
					diffList.add(newLoopValues.get(k));
				}
			}

			duplicateList.addAll(newValues);
		}
		Collections.sort(diffList);
		return diffList;
	}
	
	/**
	 * Computes the set intersection of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> inter(List<Entry> entries) {
		ArrayList<Integer> intersectionList = new ArrayList<>();
		Set<Integer> intersectionSet = new HashSet<Integer>();
		intersectionSet.addAll(entries.get(0).values);
		
		//Run a loop over all the entries to find the intersection of their value lists.
		for (int i=1;i<entries.size();i++) {
			Set<Integer> entrySet = new HashSet<>();
			entrySet.addAll(entries.get(i).values);
			intersectionSet.retainAll(entrySet);
		}
		
		List<Integer> intersectList = new ArrayList<Integer>(intersectionSet);
		Collections.sort(intersectList);
		return intersectList;
	}

	/**
	 * Computes the set union of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> union(List<Entry> entries) {
		List<Integer> unionList = new ArrayList<Integer>();
	
		//Run a for loop over the entries to find the list of union entries.
		for (int i=0;i<entries.size();i++) {
			for (int k=0;k<entries.get(i).getValues().size();k++) {
				if (unionList.contains(entries.get(i).getValues().get(k))) {
					continue;
				} else {
					unionList.add(entries.get(i).getValues().get(k));
				}
			}
		}
		
		Collections.sort(unionList);
		return unionList;
	}

	/**
	 * Computes the Cartesian product of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<List<Integer>> cartprod(List<Entry> entries) {		

		if (entries.size()<=1) {
			List<List<Integer>> returnList = new ArrayList<List<Integer>>();
			returnList.add(entries.get(0).values);
			return returnList;	
		} else {
			//Multiply the first entry by the second entry in the list of entries then iterate until we have completed the list of lists.
			List<List<Integer>> firstList = multiplyFirst(entries.get(0).values, entries.get(1).values);
		
			for (int i=1;i<entries.size();i++) {
				if (i==entries.size()-1) {
					break;
				} else {
					//Each fucntion call will assign firstList a list of lists of integers. This will multiply as the for loop is run.
					firstList = multiplySecond(firstList, entries.get(i+1).values);
				}
			}
			
			return firstList;
		}	
	}

	
	/**
	 * Second method for the Cartesian Product function that multiplies the first version.
	 */
	public static List<List<Integer>> multiplyFirst(List<Integer> firstList, List<Integer> secondList) {
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();

		for (int i=0;i<firstList.size();i++) {
			for (int k=0;k<secondList.size();k++) {
				List<Integer> newList = new ArrayList<Integer>();
				newList.add(firstList.get(i));
				newList.add(secondList.get(k));
				returnList.add(newList);
			}
		}
		
		return returnList;
	}
	
	/**
	 * Third method for the Cartesian Product function that multiplies all versions other than the first.
	 */
	public static List<List<Integer>> multiplySecond(List<List<Integer>> firstList, List<Integer> secondList) {		
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();
		
		for (int i=0;i<firstList.size();i++) {
			
			for (int k=0;k<secondList.size();k++) {
				List<Integer> newElement = new ArrayList<Integer>(firstList.get(i));
				newElement.add(secondList.get(k));
				returnList.add(newElement);
			}
		}
		
		return returnList;
	}
	
	
	/**
	 * Formats all the entries for display.
	 *
	 * @param  entries the entries to display
	 * @return         the entries with their values
	 */
	public static String listAllEntries(List<Entry> entries) { 
		/* Formats all entries for display and returns the entries with their values. */
		StringBuilder newString = new StringBuilder();
	
		for (int i=0;i<entries.size();i++) {
			newString.append(entries.get(i).getKey());
			newString.append(" ");
			newString.append(entries.get(i).get());
			
			if (i == entries.size()-1) {
				break;
			}
			
			newString.append("\n");
		}

		return newString.toString();
	}

}
