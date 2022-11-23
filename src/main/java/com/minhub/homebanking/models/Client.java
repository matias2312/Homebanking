package com.minhub.homebanking.models;

import com.minhub.homebanking.DTO.CardDTO;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        @Setter(AccessLevel.NONE)
        private long id;
        @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
        private Set<Account> accounts = new HashSet<>();
        @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
        private Set<ClientLoan> clientLoans = new HashSet<>();

        @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
        private Set<Card> cards = new HashSet<>();
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        public Client(String first, String last, String mail) {
        this.firstName = first;
        this.lastName = last;
        this.email = mail;
        }
        public Client(String firstName, String lastName, String email, String password) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
        }
        public String toString() {
            return firstName + " " + lastName;
        }
        public Set<Account> getAccounts() {
            return accounts;
        }
        //metodo para asociar cuenta/cliente
        public void addAccount(Account account) {// llamamos un metodo y pasamos un argumento
        account.setClient(this);//agregamos el cliente que asignemos
        accounts.add(account);// agregamos la cuneta al set de cuentas llamado account
        }
        public List<Loan> getLoan (){
                return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
        }
}


