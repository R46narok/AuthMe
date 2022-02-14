package com.authme.authme.data.view;

import java.util.ArrayList;
import java.util.List;

public class DataMonitorViewModel {
    private List<DataAccessRequestRecordViewModel> requests = new ArrayList<>();

    public List<DataAccessRequestRecordViewModel> getRequests() {
        return requests;
    }

    public DataMonitorViewModel setRequests(List<DataAccessRequestRecordViewModel> requests) {
        this.requests = requests;
        return this;
    }
}
