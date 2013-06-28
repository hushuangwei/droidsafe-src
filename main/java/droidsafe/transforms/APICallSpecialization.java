package droidsafe.transforms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import droidsafe.android.system.API;
import droidsafe.utils.SootUtils;

import soot.Body;
import soot.BodyTransformer;
import soot.IntType;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.StmtBody;
import soot.tagkit.AnnotationAnnotationElem;
import soot.tagkit.AnnotationArrayElem;
import soot.tagkit.AnnotationIntElem;
import soot.tagkit.AnnotationStringElem;
import soot.tagkit.AnnotationTag;
import soot.tagkit.Tag;
import soot.tagkit.VisibilityAnnotationTag;
import soot.util.Chain;

public class APICallSpecialization extends BodyTransformer {
	private final static Logger logger = LoggerFactory.getLogger(APICallSpecialization.class);
	
	private static APICallSpecialization singleton;
	
	private Map<SootMethod, CallSpecialization<?>> specializations;

	public APICallSpecialization() {
		specializations = new HashMap<SootMethod, CallSpecialization<?>>();
	}
	
	/**
	 * Call this pass on all application classes in the project.
	 */
	public static void run() {
		singleton = new APICallSpecialization();
		singleton.findSpecializedMethods();
	}
	
	protected void internalTransform(Body b, String phaseName, Map options)  {
		StmtBody stmtBody = (StmtBody)b;

		// get body's unit as a chain
		Chain units = stmtBody.getUnits();

		// get a snapshot iterator of the unit since we are going to
		// mutate the chain when iterating over it.
		Iterator stmtIt = units.snapshotIterator();

		while (stmtIt.hasNext()) {
		}
	}

	/**
	 * Based on the annotations on the modeling, find the methods that were specialized 
	 * and remember them in the specialized map.
	 */
	private void findSpecializedMethods() {
		for (SootMethod method : API.v().getAllSystemMethods()) {
			for (Tag tag : method.getTags()) {
				if (tag instanceof VisibilityAnnotationTag) {
					VisibilityAnnotationTag vat = (VisibilityAnnotationTag)tag;
					for (AnnotationTag at : vat.getAnnotations()) {
						if (at.getType().equals("Ldroidsafe/annotations/DSSpecialize;")) {
							logger.info("Found api specialized method: {}\n", method);
							if (!method.hasActiveBody() || at.getNumElems() != 1 ||
									!(at.getElemAt(0) instanceof AnnotationArrayElem)) {
									logger.error("Modeled api method has no active body or other problem: {}", method);
									droidsafe.main.Main.exit(1);
							}
							AnnotationArrayElem templateArray = (AnnotationArrayElem)at.getElemAt(0);
							for (int i = 0; i < templateArray.getNumValues(); i++) {
								AnnotationTag template = ((AnnotationAnnotationElem)templateArray.getValueAt(i)).getValue();
								
								int arg = ((AnnotationIntElem)template.getElemAt(0)).getValue();
								String value = ((AnnotationStringElem)template.getElemAt(1)).getValue();
								String meth = ((AnnotationStringElem)template.getElemAt(2)).getValue();
								
								
								SootMethod specializedMeth = method.getDeclaringClass().getMethodByName(meth);
								
								soot.Type argType = method.getParameterType(arg);
								CallSpecialization<?> cs;
								if (argType instanceof IntType) {
									cs = new CallSpecialization<Integer>(specializedMeth, 
											arg, Integer.decode(value));
								} else if (SootUtils.isStringType(argType)) {
									 cs = new CallSpecialization<String>(specializedMeth, 
												arg, value);
								} else {
									logger.error("Unsupported argument type for specialization {} in method {}.", argType, method);
									droidsafe.main.Main.exit(1);
								}
								
								//TODO: Check annotation on specialized method
								
								//TODO: Check signature of specialized method
								
								 cs = new CallSpecialization<String>(specializedMeth, 
										arg, value);
								
								specializations.put(method, cs);
							}
						}
					}
				}
			}
		}
	}

	static class CallSpecialization<T> {
		SootMethod method;
		int arg;
		T value;
		
		public CallSpecialization(SootMethod method, int arg, T value) {
			this.arg = arg;
			this.value = value;
			this.method = method;
		}
	}
}
