package com.nimble.dcfs.admin.topic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateTopicParameters {

    @JsonProperty("name")
    private final String topicName;

    @JsonProperty("partitions")
    private final int partitionCount;
    
    @JsonProperty("configs")
    private final CreateTopicConfig config;

    @JsonCreator
    public CreateTopicParameters(String topicName, int partitionCount, CreateTopicConfig config) {
        this.topicName = topicName;
        this.partitionCount = partitionCount;
        this.config = config;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getPartitionCount() {
        return partitionCount;
    }

    public CreateTopicConfig getConfig() {
		return config;
	}

	/**
     * Convert an instance of this class to its JSON
     * string representation.
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            return "";
        }
    }

}
