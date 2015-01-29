package org.xmlpull.v1;

// Droidsafe Imports
import droidsafe.runtime.*;
import droidsafe.helpers.*;
import droidsafe.annotations.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface XmlSerializer {
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void setFeature(String name,
                           boolean state)
        throws IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    boolean getFeature(String name);
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void setProperty(String name,
                            Object value)
        throws IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    Object getProperty(String name);
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void setOutput (OutputStream os, String encoding)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void setOutput (Writer writer)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void startDocument (String encoding, Boolean standalone)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void endDocument ()
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void setPrefix (String prefix, String namespace)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    String getPrefix (String namespace, boolean generatePrefix)
        throws IllegalArgumentException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    int getDepth();
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    String getNamespace ();
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    String getName();
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    XmlSerializer startTag (String namespace, String name)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    XmlSerializer attribute (String namespace, String name, String value)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    XmlSerializer endTag (String namespace, String name)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    XmlSerializer text (String text)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    XmlSerializer text (char [] buf, int start, int len)
        throws IOException, IllegalArgumentException, IllegalStateException;

    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void cdsect (String text)
        throws IOException, IllegalArgumentException, IllegalStateException;
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void entityRef (String text)  throws IOException,
        IllegalArgumentException, IllegalStateException;
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void processingInstruction (String text)
        throws IOException, IllegalArgumentException, IllegalStateException;
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void comment (String text)
        throws IOException, IllegalArgumentException, IllegalStateException;
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void docdecl (String text)
        throws IOException, IllegalArgumentException, IllegalStateException;
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void ignorableWhitespace (String text)
        throws IOException, IllegalArgumentException, IllegalStateException;
    
    @DSComment("Abstract Method")
    @DSSpec(DSCat.ABSTRACT_METHOD)
    void flush ()
        throws IOException;

}
