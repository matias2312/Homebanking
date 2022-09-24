let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get("id"); 

const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            accounts:[],
            accountsid: [],
            transactions: [],
            clientAccount:[],

            fromDate:"",
            toDate:""
        }
    },

    created(){
       
        this.Cliente()
    },
    methods: {
            Cliente(){
            axios.get('/api/clients/current')
            .then(response => {
                this.clients = response.data;
                this.accounts = this.clients.accounts;   
                this.accountsid = this.accounts.find(account => account.id == urlName);
                console.log(this.accountsid)
                this.transactions = this.accountsid.transaction.sort((b,a) =>{
                    return a.id - b.id});  
                this.clientAccount = this.accountsid.number                     
            })
            .catch(function (error) {
              
                console.log(error);
            })
           
        },
        newPdf(){
            axios.post("/api/transactions/filtered",
            {
                "fromDate": this.fromDate,
                "toDate":this.toDate,
                "clientAccount":this.clientAccount

            })
            .then(response => {
                Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Download Accepted',
                showConfirmButton: false,
                timer: 1500
              })
            })
            .catch(error =>  Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.response.data
              }))
         
        },
        
        newDate(paramDate){
            paramDate = new Date(paramDate).toLocaleString();
            return paramDate; 
        },
        newNumber(balance){ 
            balance = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(balance);
            return balance
        },
  
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "/index.html")
        },         
    }
  

}).mount('#app')