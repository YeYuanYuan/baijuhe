package com.lin.alllib.data.respone;

/**
 * Created by linhui on 2017/8/8.
 */
public class BaseRespone<T>  implements Cloneable{

    protected String url;

    protected Object parame;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getParame() {
        return parame;
    }

    public void setParame(Object parame) {
        this.parame = parame;
    }

    public T  clone() throws CloneNotSupportedException {
        return (T) super.clone();
    }
}
