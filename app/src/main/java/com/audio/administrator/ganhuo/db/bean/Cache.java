package com.audio.administrator.ganhuo.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description
 */
@Entity
public class Cache {
    @Id
    private String key;
    private String jsonEntity;
    private Long lastTime;
    private Long storageTime;
    @Generated(hash = 2000521047)
    public Cache(String key, String jsonEntity, Long lastTime, Long storageTime) {
        this.key = key;
        this.jsonEntity = jsonEntity;
        this.lastTime = lastTime;
        this.storageTime = storageTime;
    }
    @Generated(hash = 1305017356)
    public Cache() {
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getJsonEntity() {
        return this.jsonEntity;
    }
    public void setJsonEntity(String jsonEntity) {
        this.jsonEntity = jsonEntity;
    }
    public Long getLastTime() {
        return this.lastTime;
    }
    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }
    public Long getStorageTime() {
        return this.storageTime;
    }
    public void setStorageTime(Long storageTime) {
        this.storageTime = storageTime;
    }
    
}
