package app.univs.cn.myapplication;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zxr on 2016/6/15.
 */
public class Goods implements Serializable{

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }
}
