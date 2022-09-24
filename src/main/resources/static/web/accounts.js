const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            accounts:[],
            accountsOrder: [],
            loans:[],
            email:[],
            accountFilter:[], 
            accountType:"",
            totalAccountsBalance:0,
            totalLoansBalance:0,
            spinner: false,
            
        }
    },
    
    created(){
      this.mostrarCliente() 
     
    },
    methods: {
        mostrarCliente(){
            axios.get('/api/clients/current')
            .then(response => {
                this.clients = response.data;
                this.accounts = this.clients.accounts.sort((a,b) =>{
                    return a.id - b.id
               });
               this.accountFilter = this.accounts.filter(account => account.active == true);
                this.loans = this.clients.loans; 
                this.accounts.forEach(account => {
                    this.totalAccountsBalance += account.balance
                })
                this.loans.forEach(loan =>{
                    this.totalLoansBalance += loan.amount
                })
                this.email= this.clients.email  

            })           
            .catch(function (error) {
                console.log(error);
            })
           
        },
        loansApplication(){
            axios.get('/api/loans')
            .then(response => {
                this.loans = response.data;
                this.interests = this.loans.map(loans => loans.interest);
                this.mortgageInt = this.loans.filter(j => j.name == "Mortgage")[0].interest;
                this.personalInt = this.loans.filter(j => j.name == "Personal")[0].interest;
                this.automotiveInt = this.loans.filter(j => j.name == "Automotive")[0].interest;
                this.mortgage = this.loans.filter(j => j.name == "Mortgage")[0].interest/100+1;
                this.personal = this.loans.filter(j => j.name == "Personal")[0].interest/100+1;
                this.automotive = this.loans.filter(j => j.name == "Automotive")[0].interest/100+1;                             
               })           
            .catch(function (error) {
                console.log(error);
            })
           
        },
        newDate(creationDate){
            creationDate = new Date(creationDate).toLocaleDateString();
            return creationDate; 
        },
        newNumber(balance){ 
            balance = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(balance);
            return balance
        },
  
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "/index.html")
        },
        newAccount(){

            axios.post("/api/clients/current/accounts",`accountType=${this.accountType}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response=>
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Account Create',
                        showConfirmButton: false,
                        timer: 1500
                      })) 
                .then(response => window.location.reload())
                .catch(error => 
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: error.response.data,
                      }))    
        },
        functionDelete(account){
            this.number = account.number
            axios.patch(`/api/clients/current/accounts/delete`,`number=${this.number}`)
            .then(() => {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Account Delete',
                    showConfirmButton: false,
                    timer: 1500
                  })
            })
            .then(response => window.location.reload())
            .catch(error => 
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                  }))              
        },
  

        
    }
  

}).mount('#app')