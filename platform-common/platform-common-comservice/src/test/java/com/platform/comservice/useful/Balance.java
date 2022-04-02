package com.platform.comservice.useful;

import lombok.Data;

/**
 * @author Advance
 * @date 2022年01月17日 14:56
 * @since V1.0.0
 */
@Data
public class Balance {
    private long amount;

    public Balance(long amount) {
        this.amount = amount;
    }
}
