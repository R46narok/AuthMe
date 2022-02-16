package com.authme.authme.web;

import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.view.DataMonitorViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;

@Controller
public class DataMonitorController {
    private final DataValidationRecordService validationService;

    public DataMonitorController(DataValidationRecordService validationService) {
        this.validationService = validationService;
    }

    @Transactional
    @ModelAttribute("dataMonitorViewModel")
    public DataMonitorViewModel getViewModel() {
        return validationService.getDataMonitorViewModel();
    }

    @GetMapping("/data")
    public String getPage() {
        return "monitor";
    }
}
