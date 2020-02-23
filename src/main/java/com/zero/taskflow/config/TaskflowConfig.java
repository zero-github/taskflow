package com.zero.taskflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * TaskflowConfig
 */
@Configuration
public class TaskflowConfig {
    @Value("${taskflow.dataPath}")
    private String dataPath;

    @Value("${taskflow.serverId}")
    private String serverId;

    @Value("${taskflow.groupId}")
    private String groupId;

    @Value("${taskflow.groupMembers}")
    private String groupMembers;

    public String getDataPath(){
        return this.dataPath;
    }

    public String getServerId(){
        return this.serverId;
    }

    public String getGroupId(){
        return this.groupId;
    }

    public String getGroupMembers(){
        return this.groupMembers;
    }
}