const app = Vue.
createApp({
    data() {
        return {
            clients:[],
            accounts:[],
            cards:[],

            cardId:"",
            cardHolder:"",
            number:"",
            amount:"",
            cvv:"",
            thruDate:"",
            description:"",

         
        }
    },
 
    created(){
        this.clientData()
    },
    methods: {
        clientData(){
            axios.get('/api/clients/current')
            .then(response => {
                this.clients = response.data;                
                this.accounts = this.clients.accounts.sort((a,b) =>{
                    return a.id - b.id}); 
                this.cards = this.clients.cards          
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
        newPayment(){
            axios.post("/api/clients/current/transactions/payments",
                {
                    "id": `${this.cardId}`,
                    "cardHolder": `${this.cardHolder}`,
                    "number":`${this.number}`, 
                    "amount":`${this.amount}`,
                    "cvv": `${this.cvv}`, 
                    "thruDate": `${this.thruDate}`, 
                    "description": `${this.description}`
                })
            .then(response => {
                Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Payment Accepted',
                showConfirmButton: false,
                timer: 1500
              })
            })
            .catch(error =>  Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.response.data
              }))

        }
      
        
    }
  

}).mount('#app')