package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.generators.dimensions.disposition.DispositionGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DISPOSITION_DIM")
@Generators(generators = {
        DispositionGenerator.class
})
public class DispositionDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "disposition_key")
    private long dispositionKey;

    @Column(name = "sip_code")
    private int sipCode;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "description")
    private String description;

    @Column(name = "failure_due_to_client")
    private boolean failureDueToClient;

    @Column(name = "failure_due_to_server")
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
}
