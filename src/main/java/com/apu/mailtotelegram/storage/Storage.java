package com.apu.mailtotelegram.storage;

public interface Storage<T> {

    public void setFileName(String fileName);
    void add(T data) throws StorageException;
    boolean find(T data) throws StorageException;;
    
}
