package com.block.utils;

import lombok.Getter;

@Getter
public enum Args {
    ACC_THRESHOLD("accThreshold", "准确度阈值"),
    MODEL_NUM_THRESHOLD("modelNumThreshold", "模型数阈值");
    private final String name;
    private final String explain;

    Args(String name, String explain) {
        this.name = name;
        this.explain = explain;
    }

}
