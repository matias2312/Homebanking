const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            accounts:[],
            amount:0,
            description:"",           
            accountOrigin:"",
            accountDestiny:"",
            accountOrigin2:"",
            own:"own",
            third:"third",            
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
        newTransfer(){
            axios.post("/api/clients/current/transactions",`accountOrigin=${this.accountOrigin}&accountDestiny=${this.accountDestiny}&amount=${this.amount}&description=${this.description}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response =>
                    Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Transfer Create',
                    showConfirmButton: false,
                    timer: 1500
                  }))
                .then(response => window.location.reload())
                .catch(error => 
                    Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data
                  }))
        }
      
        
    }
  

}).mount('#app')