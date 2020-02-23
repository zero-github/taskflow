package com.zero.taskflow.raft;

/**
 * ITaskflowServer
 */
public interface ITaskflowServer {
    boolean init();
    boolean start();
}