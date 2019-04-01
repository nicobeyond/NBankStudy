package com.nbank.study;

import ico.ico.util.log;

/**
 * @author DOVE
 * @since 2014.10.11
 */
public class MDragGridView {

    private int mNumColumns;
    private boolean mNumColumnsSet;


    public MDragGridView() {
        log.w("===" + mNumColumnsSet);
        if (!mNumColumnsSet) {
            mNumColumns = 1;
        }

    }
}
