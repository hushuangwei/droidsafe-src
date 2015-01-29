package org.apache.http.params;

// Droidsafe Imports
import droidsafe.runtime.*;
import droidsafe.helpers.*;
import droidsafe.annotations.*;

public interface CoreConnectionPNames {

    
    public static final String SO_TIMEOUT = "http.socket.timeout"; 

    
    public static final String TCP_NODELAY = "http.tcp.nodelay"; 

    
    public static final String SOCKET_BUFFER_SIZE = "http.socket.buffer-size"; 

    
    public static final String SO_LINGER = "http.socket.linger"; 

    
    public static final String CONNECTION_TIMEOUT = "http.connection.timeout"; 

    
    public static final String STALE_CONNECTION_CHECK = "http.connection.stalecheck"; 

    
    public static final String MAX_LINE_LENGTH = "http.connection.max-line-length";
    
    
    public static final String MAX_HEADER_COUNT = "http.connection.max-header-count";

}
