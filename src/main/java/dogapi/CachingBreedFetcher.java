package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private HashMap<String, List> cacheBreed = new HashMap<>();
    private BreedFetcher fetch;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetch = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        List<String> breedList;
        if (cacheBreed.containsKey(breed)){
            breedList = cacheBreed.get(breed);
        }
        else {
            callsMade++;
            breedList = fetch.getSubBreeds(breed);
            cacheBreed.put(breed, breedList);
        }
        return breedList;
    }

    public int getCallsMade() {
        return callsMade;
    }
}