package com.minhub.homebanking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransactionRequestDTO {
   private Double amount;
   private String description;
   private String accountOrigin;
   private String accountDestiny;
}
