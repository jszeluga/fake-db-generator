package com.github.jszeluga.generators.disposition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jszeluga.entity.dimension.DispositionDimension;

import java.io.IOException;

@JsonPropertyOrder(value = {
        "code",
        "name",
        "outcome",
        "failure_due_to_client",
        "failure_due_to_server",
        "description"
})
public class DispositionMixIn extends DispositionDimension {

    @Override
    @JsonIgnore
    public void setDispositionKey(long dispositionKey) {
        super.setDispositionKey(dispositionKey);
    }

    @Override
    @JsonProperty(value = "name")
    public void setCodeName(String codeName) {
        super.setCodeName(codeName);
    }

    @Override
    @JsonProperty(value = "outcome")
    public void setOutcome(String outcome) {
        super.setOutcome(outcome);
    }

    @Override
    @JsonProperty(value = "failure_due_to_client")
    @JsonDeserialize(using = BooleanDeserializer.class)
    public void setFailureDueToClient(boolean failureDueToClient) {
        super.setFailureDueToClient(failureDueToClient);
    }

    @Override
    @JsonProperty(value = "failure_due_to_server")
    @JsonDeserialize(using = BooleanDeserializer.class)
    public void setFailureDueToServer(boolean failureDueToServer) {
        super.setFailureDueToServer(failureDueToServer);
    }

    @Override
    @JsonProperty(value = "code")
    public void setSipCode(int sipCode) {
        super.setSipCode(sipCode);
    }

    @Override
    @JsonProperty(value = "description")
    public void setDescription(String description) {
        super.setDescription(description);
    }

    private static class BooleanDeserializer extends StdDeserializer<Boolean>{

        public BooleanDeserializer(){
            this(null);
        }

        public BooleanDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String value = p.getText();
            return Boolean.parseBoolean(value);
        }
    }
}
