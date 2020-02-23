/**
 * TaskflowStateMachine
 */
package com.zero.taskflow.raft;

import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;

public class TaskflowStateMachine extends StateMachineAdapter {
    @Override
    public void onApply(final Iterator it) {
        while (it.hasNext()) {
            it.next();
        }
    }

    @Override
    public void onLeaderStart(final long term) {
        super.onLeaderStart(term);
    }

    @Override
    public void onLeaderStop(final Status status) {
        super.onLeaderStop(status);
    }
}