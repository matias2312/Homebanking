package com.minhub.homebanking.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.minhub.homebanking.DTO.PaymentsDTO;
import com.minhub.homebanking.DTO.PdfDTO;
import com.minhub.homebanking.DTO.TransactionRequestDTO;
import com.minhub.homebanking.Services.*;

import com.minhub.homebanking.models.*;

import com.minhub.homebanking.repository.BankDAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final CardService cardService;
    private final BankDAO bankDAO;
    @Transactional
    @PostMapping("/clients/current/transactions")
    public ResponseEntity<Object> newTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO, Authentication authentication) {
        return transactionService.newTransaction(transactionRequestDTO,authentication);
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @Transactional
    @PostMapping("/clients/current/transactions/payments")
    public ResponseEntity<Object> newPayments(@RequestBody PaymentsDTO paymentsDTO){
        //Client client = clientService.findByClientEmail(authentication.getName());
        Card cardNumber = cardService.getCardByNumber(paymentsDTO.getNumber());
        int cvv = cardNumber.getCvv();

        Account accountOrigin = cardNumber.getAccount();

        if(cardNumber.getActive() == false){
            return new ResponseEntity<>("disabled card", HttpStatus.FORBIDDEN);
        }
        if(paymentsDTO.getCvv() != cvv){
            return new ResponseEntity<>("cvv invalid", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getAmount() <= 0 ){
           return new ResponseEntity<>("invalid amount", HttpStatus.FORBIDDEN);
        }
        if(accountOrigin == null){
            return new ResponseEntity<>("account does not exist", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getAmount() > accountOrigin.getBalance()){
            return new ResponseEntity<>("Amount is invalid", HttpStatus.FORBIDDEN);
        }
        Double balance1 = accountOrigin.getBalance() - paymentsDTO.getAmount();
        transactionService.saveTransaction(new Transaction(accountOrigin,TransactionType.DEBIT,- paymentsDTO.getAmount(), paymentsDTO.getDescription(),LocalDateTime.now(),balance1));
        accountOrigin.setBalance(accountOrigin.getBalance() - paymentsDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //PDF
    @PostMapping("/transactions/filtered")
    public ResponseEntity<Object> getFilteredTransaction(
            @RequestBody PdfDTO pdfDTO, Authentication authentication) throws DocumentException, FileNotFoundException {
       // Client client = clientService.findByClientEmail(authentication.getName());
        Client client = bankDAO.findByClientEmail(authentication.getName());
        Account account = bankDAO.getAccountFindByNumber(pdfDTO.getClientAccount());
        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("You cannot request data from an account that isn't yours.", HttpStatus.FORBIDDEN);
        }
        if (account.getTransactions().isEmpty()){
            return new ResponseEntity<>("You don't have any transactions in this account.", HttpStatus.FORBIDDEN);
        }
        if(pdfDTO.getFromDate() == null){
            return new ResponseEntity<>("enter the from date", HttpStatus.FORBIDDEN);
        }
        if(pdfDTO.getToDate() == null){
            return new ResponseEntity<>("enter the to date", HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactions = transactionService.filterTransactionsWithDate(pdfDTO.getFromDate(),pdfDTO.getToDate(),account);
        createPdf( transactions);
        return new ResponseEntity<>("transactions",HttpStatus.CREATED);
    }

    public static void createPdf(Set<Transaction> transactions) throws DocumentException, FileNotFoundException {
        var doc = new Document();
        String route = System.getProperty("user.home");
        PdfWriter.getInstance(doc, new FileOutputStream(route + "/Downloads/TransactionInfo.pdf"));
        doc.open();

        var bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        var paragraph = new Paragraph("MindHub Brother Bank");
        var table = new PdfPTable(4);
        Stream.of("Amount", "Description","Date","Type").forEach(table::addCell);

        transactions.forEach(transaction -> {
            table.addCell("$" +transaction.getAmount());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getType().toString());

        });

        paragraph.add(table);
        doc.add(paragraph);
        doc.close();
    }


}
