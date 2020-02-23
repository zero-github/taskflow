
package com.zero.taskflow.raft;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.remoting.rpc.RpcServer;
import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import com.zero.taskflow.config.TaskflowConfig;

/**
 * TaskflowServer
 */
@Component
public class TaskflowServer implements ITaskflowServer {
    private NodeOptions raftNodeOptions;
    private RaftGroupService raftGroupService;
    private Node raftNode;
    private RpcServer rpcServer;
    private PeerId serverId;

    private TaskflowStateMachine raftStateMachine;

    @Autowired
    private TaskflowConfig config;

    private void registerProcessor(RpcServer rpcServer) {
        RaftRpcServerFactory.addRaftRequestProcessors(rpcServer);
    }

    public TaskflowServer() {
    }

    public boolean init() {
        this.serverId = new PeerId();
        if (!this.serverId.parse(this.config.getServerId())) {
            return false;
        }

        Configuration raftConfig = new Configuration();
        if (!raftConfig.parse(this.config.getGroupMembers())) {
            return false;
        }

        this.raftStateMachine = new TaskflowStateMachine();
        this.raftNodeOptions = new NodeOptions();
        this.raftNodeOptions.setFsm(this.raftStateMachine);

        String dataPath = this.config.getDataPath();
        this.raftNodeOptions.setLogUri(dataPath + File.separator + "log");
        this.raftNodeOptions.setRaftMetaUri(dataPath + File.separator + "raft_meta");
        this.raftNodeOptions.setSnapshotUri(dataPath + File.separator + "snapshot");

        this.raftNodeOptions.setInitialConf(raftConfig);
        return true;
    }

    public boolean start() {
        String groupId = this.config.getGroupId();
        this.rpcServer = new RpcServer(serverId.getPort());

        registerProcessor(this.rpcServer);

        this.raftGroupService = new RaftGroupService(groupId, serverId, this.raftNodeOptions, this.rpcServer);

        this.raftNode = this.raftGroupService.start();
        if(this.raftNode == null){
            return false;
        }

        try {
            this.raftNode.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
    
}