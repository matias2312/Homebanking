package com.minhub.homebanking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class PdfDTO {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String clientAccount;
    public PdfDTO(LocalDateTime fromDate, LocalDateTime toDate, String clientAccount) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.clientAccount = clientAccount;
    }
}
