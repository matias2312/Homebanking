package com.minhub.homebanking.models;

import com.minhub.homebanking.DTO.CardDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity

public class Client {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private long id;
//cramos la anotacion uno a muchos para asociar un cliente con muchas cuentas, EAGER nos mantiene la inforamcion actualizada
        @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
        private Set<Account> accounts = new HashSet<>();// le pasamos lo que vamos asociar (cuentas) y le pedimos que nos reserve un espacio en memoria para que lo podamos guardar

        @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
        private Set<ClientLoan> clientLoans = new HashSet<>();

        @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
        private Set<Card> cards = new HashSet<>();
//propiedades donde guardo..
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        //constructor, para crear el objeto que necesito. le asignamos los parametro entre ()
        public Client() { }

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

        //metodos accesores que nos permite acceder a nuestras propiedades
        public long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName ) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String toString() {
            return firstName + " " + lastName;
        }

        public Set<Account> getAccounts() {
            return accounts;
        }
        public void addAccount(Account account) {// llamamos un metodo y pasamos un argumento
        account.setClient(this);//agregamos el cliente que asignemos
        accounts.add(account);// agregamos la cuneta al set de cuentas llamado account
        }

        public Set<ClientLoan> getClientLoans() {
                return clientLoans;
        }

        public void setClientLoans(Set<ClientLoan> clientLoans) {
                this.clientLoans = clientLoans;
        }

        public List<Loan> getLoan (){
                return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
        }

        public Set<Card> getCards() {
                return cards;}

        public void setCards(Set<Card> cards) {
                this.cards = cards;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}


