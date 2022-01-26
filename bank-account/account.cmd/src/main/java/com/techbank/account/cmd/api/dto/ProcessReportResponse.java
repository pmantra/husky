package com.techbank.account.cmd.api.dto;

import com.techbank.account.common.dto.BaseResponse;
import lombok.Data;

@Data
public class ProcessReportResponse extends BaseResponse {
    private String id;
    private String accountName;

    public ProcessReportResponse(String message, String id, String accountName) {
        super(message);
        this.id = id;
        this.accountName = accountName;
    }
}
