/*
 
   Derby - Class org.apache.derbyTesting.functionTests.tests.jdbc4.Wrapper41Conn
 
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 
 */

package org.apache.derbyTesting.functionTests.tests.jdbc4;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

import org.apache.derby.impl.jdbc.EmbedConnection40;
import org.apache.derby.iapi.jdbc.BrokeredConnection40;
import org.apache.derby.client.net.NetConnection40;
import org.apache.derby.client.am.LogicalConnection40;

/**
 * A wrapper around the abort(Executor) method added by JDBC 4.1.
 * We can eliminate this class after Java 7 goes GA and we are allowed
 * to use the Java 7 compiler to build our released versions of derbyTesting.jar.
 */
public  class   Wrapper41Conn
{
    ///////////////////////////////////////////////////////////////////////
    //
    // STATE
    //
    ///////////////////////////////////////////////////////////////////////

    private EmbedConnection40    _embedded;
    private NetConnection40      _netclient;
    private BrokeredConnection40 _brokeredConnection;
    private LogicalConnection40 _logicalConnection;
    
    ///////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTORS
    //
    ///////////////////////////////////////////////////////////////////////

    public Wrapper41Conn( Object wrapped ) throws Exception
    {
        if ( wrapped instanceof EmbedConnection40 ) { _embedded = (EmbedConnection40) wrapped; }
        else if ( wrapped instanceof NetConnection40 ) { _netclient = (NetConnection40) wrapped; }
        else if ( wrapped instanceof BrokeredConnection40 ) { _brokeredConnection = (BrokeredConnection40) wrapped; }
        else if ( wrapped instanceof LogicalConnection40 ) { _logicalConnection = (LogicalConnection40) wrapped; }
        else { throw nothingWrapped(); }
    }
    
    ///////////////////////////////////////////////////////////////////////
    //
    // JDBC 4.1 BEHAVIOR
    //
    ///////////////////////////////////////////////////////////////////////

    public  void    abort( Executor executor ) throws SQLException
    {
        if ( _embedded != null ) { _embedded.abort( executor ); }
        else if ( _netclient != null ) { _netclient.abort( executor ); }
        else if ( _brokeredConnection != null ) { _brokeredConnection.abort( executor ); }
        else if ( _logicalConnection != null ) { _logicalConnection.abort( executor ); }
        else { throw nothingWrapped(); }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // OTHER PUBLIC BEHAVIOR
    //
    ///////////////////////////////////////////////////////////////////////

    public Connection   getWrappedObject() throws SQLException
    {
        if ( _embedded != null ) { return _embedded; }
        else if ( _netclient != null ) { return _netclient; }
        else if ( _brokeredConnection != null ) { return _brokeredConnection; }
        else if ( _logicalConnection != null ) { return _logicalConnection; }
        else { throw nothingWrapped(); }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // MINIONS
    //
    ///////////////////////////////////////////////////////////////////////

    private SQLException nothingWrapped() { return new SQLException( "Nothing wrapped!" ); }

}
