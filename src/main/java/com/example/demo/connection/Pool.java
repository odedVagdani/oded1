package com.example.demo.connection;

import java.util.ArrayList;

/**
 * Connection pool holding 5 connections
 * @author oded
 *
 */
public class Pool {

	private static Pool _INSTANCE;

	// define maximum 5 connections
	private static final int NUMBER_OF_CONNECTIONS = 5;

	// list of connections
	private ArrayList<SyncObject> connections = null;

	/*
	 * Initializing connectionPool with 5 connections
	 */
	private Pool() {
		connections = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
			SyncObject syncObject = new SyncObject();
			connections.add(syncObject);
		}
	}

	/**
	 * Returning INSTANCE connection
	 * this is singleton since only one instance of Connection pool is created 
	 * @return ConnectionPool instance
	 */
	public static synchronized Pool getInstance() {
		if (_INSTANCE == null) 
		{
			_INSTANCE = new Pool();
		}
		return _INSTANCE;
	}
	
	
	/**
	 * get a connection from the list
	 * if there is no connection available- wait
	 * @return SyncObject
	 * @throws InterruptedException
	 */
    public synchronized SyncObject getConnection() throws InterruptedException {
        while (this.connections.size() == 0) {          
            wait();
        }
        SyncObject connection = this.connections.get(0);
        this.connections.remove(0);        
        return connection;
    }

    /**
     *  return connection into the list- notify (the waiters)
     * @param connection
     */
    public synchronized void returnConenction(SyncObject connection)
    {
        this.connections.add(connection);        
        notify();
    }

	

	
}