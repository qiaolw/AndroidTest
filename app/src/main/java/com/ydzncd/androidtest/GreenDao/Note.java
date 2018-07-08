package com.ydzncd.androidtest.GreenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qiaoliwei on 2018/7/8.
 */

@Entity
public class Note {
    @Id
    private Long id;

    @NotNull
    private String text;
    private String comment;
    @Generated(hash = 1038952471)
    public Note(Long id, @NotNull String text, String comment) {
        this.id = id;
        this.text = text;
        this.comment = comment;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
