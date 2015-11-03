package com.test;

import com.test.model.Response;

public interface DataFetchListener {
    void doAfterFetchData(Response response);
}
