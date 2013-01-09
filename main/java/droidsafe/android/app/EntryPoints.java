package droidsafe.android.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import droidsafe.android.system.API;
import droidsafe.utils.SootUtils;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;

/**
 * Calculate the entry points of the android application we are analyzing.
 * Must be called after soot has been configured.
 * 
 * @author mgordon
 *
 */
public class EntryPoints {
	private final static Logger logger = LoggerFactory.getLogger(EntryPoints.class);
	
	/** if true then search for entry points in libraries */
	private final boolean ENTRY_POINTS_IN_LIBRARIES = true;
	
	private LinkedHashSet<SootMethod> systemSupers;
	private LinkedHashSet<SootMethod> appEntryPoints;
	
	private static EntryPoints V;
	
	public EntryPoints() {
		systemSupers = new LinkedHashSet<SootMethod>();
		appEntryPoints = new LinkedHashSet<SootMethod>();
	}
	
	public Set<SootMethod> getAppEntryPoints() {
		return appEntryPoints;
	}
	
	public Set<SootMethod> getAPIInheritedEntryPoints() {
		return systemSupers;
	}

	/**
	 * Return the singleton entry point object for the app
	 */
	public static EntryPoints v() {
		if (V == null) {
			V = new EntryPoints();
		}
		
		return V;
	}
	
	 /**
     * Find all entry points into the application code.
     * 
     * Entry points are defined as methods that override API methods (methods defined in the 
     * android api). 
     *    
     */
    public void calculate() {
    	
    	for (SootClass clazz : Scene.v().getApplicationClasses()) {
    		if (clazz.isInterface() || clazz.getName().equals(Harness.HARNESS_CLASS_NAME))
    			continue;
    		    		
    		//don't add modeling for library classes if we want to ignore them
    		if (!ENTRY_POINTS_IN_LIBRARIES && Project.v().isLibClass(clazz.getName()))
    			continue;
    		
    		//Messages.log("Checking class for missing modeling: " + clazz.getName());
    			    	
    		//now check which methods are overrides
    		for (SootMethod method : clazz.getMethods()) {
    			//Messages.log("    Checking for method: " + method.getSignature());
    			if (!clazz.declaresMethod(method.getSubSignature()))
    				continue;
 
    			
    			if (Hierarchy.v().isImplementedSystemMethod(method)) {
    				appEntryPoints.add(method);
    				logger.info("Found entry point: {}", method.toString());
    			}
    		}
    	}
    }
    
}
