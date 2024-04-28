/**
 * This Java program is for Alphabetizer to sort characters in specific way
 *
 * The program defines several methods to handle different tasks:
 * - `getCharacterCounts` counts occurrences of each alphabetic character.
 * - `sort` sorts characters without considering original positions.
 * - `getCharacterCountsWithOrder` tracks indices of each character's occurrence.
 * - `sortWithOrder` sorts characters while preserving the order of appearance.
 * - `mergeSmallCaps` merges characters based on their appearance order.
 *
 * Author:
 * Parth Bapat
 * bapatparth@vt.edu
 *
 * Date Created:
 * 4/25/2024
 * */

import java.util.ArrayList;
import java.util.List;

public class Alphabetizer {

	/**
	 * Alphabetize method that can optionally sort characters with their order
	 * preserved.
	 * 
	 * @param input     The string to be alphabetized.
	 * @param withOrder If true, sorts characters maintaining the order of their
	 *                  appearance.
	 * @return The alphabetized string.
	 */
	public String alphabetize(String input, boolean withOrder) {
		if (withOrder) {
			return sortWithOrder(input);
		} else {
			return sort(input);
		}
	}

	/**
	 * Overloaded alphabetize method that assumes 'withOrder' is True by default.
	 * 
	 * @param input The string to be alphabetized.
	 * @return The alphabetized string without considering order.
	 */
	public String alphabetize(String input) {
		return alphabetize(input, true);
	}

	/**
	 * Counts occurrences of each alphabetic character in the input string.
	 * @param input the string to alphabetize
	 * @param smalls array to store counts of lowercase alphabetic characters
	 * @param capitals array to store counts of uppercase alphabetic characters
	 */
	private void getCharacterCounts(String input, int[] smalls, int[] capitals) {
		for (int i = 0; i < input.length(); i++) {
			char currentLetter = input.charAt(i);
			if (Character.isAlphabetic(currentLetter)) {
				if (Character.isLowerCase(currentLetter)) {
					smalls[currentLetter - 'a']++;
				} else {
					capitals[currentLetter - 'A']++;
				}
			}
		}
	}

	/**
	 * Arranges the input string in a custom order where all uppercase letters precede the corresponding lowercase letters for each alphabet.
	 * @param input the string to sort
	 * @return a string sorted in alphabetical order
	 */
	private String sort(String input) {
		int[] smalls = new int[26];
		int[] capitals = new int[26];

		getCharacterCounts(input, smalls, capitals);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 26; i++) {
			while (capitals[i] > 0) {
				char currentLetter = (char) (i + 'A');
				builder.append(currentLetter);
				capitals[i]--;
			}
			while (smalls[i] > 0) {
				char currentLetter = (char) (i + 'a');
				builder.append(currentLetter);
				smalls[i]--;
			}
		}

		return builder.toString();
	}

    /**
     * Records the indices of each occurrence of alphabetic characters in the input string.
     * @param input the string to alphabetize
     * @param smalls list of lists where each list holds indices of occurrences for a particular lowercase letter
     * @param capitals list of lists where each list holds indices of occurrences for a particular uppercase letter
     */
    private void getCharacterCountsWithOrder(String input, List<List<Integer>> smalls, List<List<Integer>> capitals) {
        for (int i = 0; i < input.length(); i++) {
            char currentLetter = input.charAt(i);
            if (Character.isAlphabetic(currentLetter)) {
                if (Character.isLowerCase(currentLetter)) {
                    if (smalls.get(currentLetter - 'a') == null) {
                        smalls.set(currentLetter - 'a', new ArrayList<>());
                    }
                    smalls.get(currentLetter - 'a').add(i);
                } else {
                    if (capitals.get(currentLetter - 'A') == null) {
                        capitals.set(currentLetter - 'A', new ArrayList<>());
                    }
                    capitals.get(currentLetter - 'A').add(i);
                }
            }
        }
    }

    /**
     * Sorts the characters of the input string by maintaining the order of appearance and grouping capitals before their corresponding small letters.
     * @param input the string to sort
     * @return the sorted string preserving original order
     */
    private String sortWithOrder(String input) {
        List<List<Integer>> smalls = new ArrayList<>(26);
        List<List<Integer>> capitals = new ArrayList<>(26);
        
        for (int i = 0; i < 26; i++) {
            smalls.add(null);
            capitals.add(null);
        }

        getCharacterCountsWithOrder(input, smalls, capitals);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            mergeSmallCaps(smalls, capitals, builder, i);
        }

        return builder.toString();
    }

    /**
     * Merges characters from lowercase and uppercase lists into the StringBuilder
     * @param smalls list of lists containing indices of lowercase letters
     * @param capitals list of lists containing indices of uppercase letters
     * @param builder the StringBuilder to append sorted characters to
     * @param i the index representing the current character (0 for 'A'/'a', 1 for 'B'/'b'...)
     */
    private void mergeSmallCaps(List<List<Integer>> smalls, List<List<Integer>> capitals, StringBuilder builder, int i) {
        int smallPointer = 0, capPointer = 0;
        while (smalls.get(i) != null && capitals.get(i) != null && smallPointer < smalls.get(i).size() && capPointer < capitals.get(i).size()) {
            if (smalls.get(i).get(smallPointer) < capitals.get(i).get(capPointer)) {
                char currentLetter = (char) (i + 'a');
                builder.append(currentLetter);
                smallPointer++;
            } else {
                char currentLetter = (char) (i + 'A');
                builder.append(currentLetter);
                capPointer++;
            }
        }
        while (smalls.get(i) != null && smallPointer < smalls.get(i).size()) {
            char currentLetter = (char) (i + 'a');
            builder.append(currentLetter);
            smallPointer++;
        }
        while (capitals.get(i) != null && capPointer < capitals.get(i).size()) {
            char currentLetter = (char) (i + 'A');
            builder.append(currentLetter);
            capPointer++;
        }
    }

	public static void main(String[] args) {

		Alphabetizer alpha = new Alphabetizer();
		for (String str : args) {
			str = str.trim();
			if(str.length() > 0){
				System.out.println(str);
				System.out.println(alpha.alphabetize(str));
			}
		}
	}

}
