package br.edu.utfpr.pwncheck.server.error;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
public class ApiError {
    private long timestamp = new Date().getTime();
    private int status;
    private String message;
    private String url;
    private Map<String, String> errrors;

    public ApiError(int status, String message, String url, Map<String, String> errrors) {
        this.status = status;
        this.message = message;
        this.url = url;
        this.errrors = errrors;
    }

    public ApiError(String message, String url, Integer status) {
        this.message = message;
        this.url = url;
        this.status = status;
    }
}
