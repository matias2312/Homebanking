const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            cards:[],
            accounts:[],
            accountOrigin:"",
            cardsDebits:[],
            cardsCredits:[],
            cardType:[],
            cardColor:[],
        }
    },

    created(){      
        this.cardClient()
    },
    methods: {
             cardClient(){
            axios.get('/api/clients/current')
            .then(response => {
                this.clients = response.data
                console.log(this.clients)
                this.cards = this.clients.cards 
                this.cardsDebits = this.cards.filter((a)=>{
                    return a.cardType == "DEBIT"
                }) 
                this.cardsCredits = this.cards.filter((a)=>{
                    return a.cardType == "CREDIT"
                })
                this.accounts = this.clients.accounts       
            })
            .catch(function (error) {            
                console.log(error);
            })
           
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "/web/index.html")
        }, 
        newCards(){
            axios.post("/api/clients/current/cards",`cardType=${this.cardType}&cardColor=${this.cardColor}&accountOrigin=${this.accountOrigin}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response=>
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Card Create',
                        showConfirmButton: false,
                        timer: 2000
                      }))
                .then(response =>  location.href = "/web/cards.html")
                .catch(error => 
                        Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: error.response.data
                      }))    
        },
          
    }
  

}).mount('#app')