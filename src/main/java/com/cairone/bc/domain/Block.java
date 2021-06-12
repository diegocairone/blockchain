package com.cairone.bc.domain;

public interface Block<T> {
    
    public String getId();

    public String getPrevId();

    public long getCreatedAt();

    public int getNonce();

    public Payload<T> getPayload();
    
}
