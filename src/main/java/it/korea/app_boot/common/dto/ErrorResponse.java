package it.korea.app_boot.common.dto;

import it.korea.app_boot.common.utils.TimeFormatUtils;
import lombok.Data;

@Data
public class ErrorResponse {

    private String message;
    private int status;
    private String nowTime;

    public ErrorResponse(String message, int status) {
        this.setMessage(message);
        this.setStatus(status);
        this.setNowTime(message);
        this.setNowTime(TimeFormatUtils.getDateTime());
    }
}
