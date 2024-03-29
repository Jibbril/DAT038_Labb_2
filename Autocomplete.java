import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Autocomplete {
    private Term[] dictionary;

    // Initializes the dictionary from the given array of terms.
    public Autocomplete(Term[] dictionary) {
        this.dictionary = dictionary;
        sortDictionary();
    }

    // Sorts the dictionary in *case-insensitive* lexicographic order.
    // Complexity: O(N log N), where N is the number of terms
    private void sortDictionary() {
        Arrays.sort(this.dictionary, Term.byLexicographicOrder());
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Complexity: O(log N + M log M), where M is the number of matching terms
    public Term[] allMatches(String prefix) {
        int firstIndex = RangeBinarySearch.firstIndexOf(this.dictionary, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int lastIndex = -1;
        if (firstIndex >= 0) {
            lastIndex = RangeBinarySearch.lastIndexOf(this.dictionary, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        }

        if (firstIndex >= 0 && lastIndex >= 0) {
            Term[] results = Arrays.copyOfRange(this.dictionary, firstIndex, lastIndex+1);
            Arrays.sort(results, Term.byReverseWeightOrder());
            return results;
        }
        return new Term[0];

    }

    // Returns the number of terms that start with the given prefix.
    // Complexity: O(log N)
    public int numberOfMatches(String prefix) {
        int firstIndex = RangeBinarySearch.firstIndexOf(this.dictionary, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int lastIndex = -1;
        if (firstIndex >= 0) {
            lastIndex = RangeBinarySearch.lastIndexOf(this.dictionary, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        }

        if (firstIndex < 0 || lastIndex < 0) {
            return 0;
        }
        return lastIndex - firstIndex + 1;
    }
}
