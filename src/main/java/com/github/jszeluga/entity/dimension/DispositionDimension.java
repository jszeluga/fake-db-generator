package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.entity.InsertEntity;
import com.github.jszeluga.generators.dimensions.disposition.DispositionGenerator;

@Generators(generators = {
        DispositionGenerator.class
})
public class DispositionDimension implements InsertEntity {

    private long dispositionKey;

    private int sipCode;

    private String codeName;

    private String outcome;

    private String description;

    private boolean failureDueToClient;

    private boolean failureDueToServer;

    public long getDispositionKey() {
        return dispositionKey;
    }

    public void setDispositionKey(long dispositionKey) {
        this.dispositionKey = dispositionKey;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public boolean isFailureDueToClient() {
        return failureDueToClient;
    }

    public void setFailureDueToClient(boolean failureDueToClient) {
        this.failureDueToClient = failureDueToClient;
    }

    public boolean isFailureDueToServer() {
        return failureDueToServer;
    }

    public void setFailureDueToServer(boolean failureDueToServer) {
        this.failureDueToServer = failureDueToServer;
    }

    public int getSipCode() {
        return sipCode;
    }

    public void setSipCode(int sipCode) {
        this.sipCode = sipCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public Object[] getInsertParams() {
        return new Object[]{sipCode,codeName,outcome,description,failureDueToClient,failureDueToServer};
    }
}
