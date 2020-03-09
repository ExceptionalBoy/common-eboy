package com.eboy.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Context<E> implements Serializable {

    private Header header;

    private E content;

    public Context() {
    }

    public Context(E content) {
        this.content = content;
    }

    public Context(Header header) {
        this.header = header;
    }

    public Context(Header header, E content) {
        this.header = header;
        this.content = content;
    }
}
