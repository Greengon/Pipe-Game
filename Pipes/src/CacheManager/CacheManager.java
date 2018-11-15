/*
 * Responsible on loading and saving solution
 * if it exists 
 * */

package CacheManager;

public interface CacheManager {
	// There must be an Exception throwing so that the file class IOException would work
	void save(String problem,String solution) throws Exception;
	String load(String problem) throws Exception;; 
}
