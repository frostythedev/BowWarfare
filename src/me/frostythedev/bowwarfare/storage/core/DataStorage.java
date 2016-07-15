package me.frostythedev.bowwarfare.storage.core;

import me.frostythedev.bowwarfare.enums.StorageType;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public abstract class DataStorage<T> {

    private StorageType storageType;

    public DataStorage(StorageType storageType) {
        this.storageType = storageType;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public abstract void initialize();

    public abstract T load(String name);

    public abstract void save(T t);
}
