const app = Vue.
createApp({
    data() {
        return {
            clients:[],
            loans:[],
            accounts:[],
          
            payments:[],           
            interests:[],
            interest:"",
            personal:[],
            mortgage:[],
            automotive:[],
            mortgageInt:[],
            personalInt:[],
            automotiveInt:[],
            /*  newLoan */
            loanId:"",
            payment:"",
            amount:"",
            accountDestination:"",
           /*  newLoanAdmin */
           name:"",
           payments:"",
           maxAmount:"",
           interest:"",
        }
    },
 
    created(){
        this.clientData()
        this.loansApplication()
    },
    methods: {
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
        clientData(){
            axios.get('/api/clients/current')
            .then(response => {
                this.clients = response.data;                
                this.accounts = this.clients.accounts.sort((a,b) =>{
                    return a.id - b.id
               });
               this.email= this.clients.email       
            })           
            .catch(function (error) {
                console.log(error);
            })
        },
        newNumber(amount){ 
            amount = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(amount);
            return amount
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "/index.html")
        },
        newLoan(){
            axios.post("/api/clients/current/loan",
                {
                "id":`${this.loanId}` ,
                "amount":`${this.amount}`,
                "payments":`${this.payments}`,
                "accountDestiny":`${this.accountDestination}`
                })
            .then(response => {
                Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Loan Create',
                showConfirmButton: false,
                timer: 1500
              })
              location.href = "/web/accounts.html"
            })
            .catch(error =>  Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.response.data
              }))

        },
        newLoanAdmin(){
            axios.post("/api/clients/current/loan/create",`name=${this.name}&payments=${this.payments}&maxAmount=${this.maxAmount}&interest=${this.interest}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response=>
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Loan Create',
                        showConfirmButton: false,
                        timer: 2000
                      }))
                .then(response => window.location.reload())
                .catch(error => 
                        Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: error.response.data
                      }))    
        },  
    }
  

}).mount('#app')
