const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            cards:[],
            cardsDebits:[],
            cardsCredits:[],
            cardType:"",
            cardColor:"",
            cardFilter:[],
            today:[]
    
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
                this.cards = this.clients.cards 
                this.cardFilter = this.cards.filter(card => card.active == true);
                this.cardsDebits = this.cards.filter((a)=>{
                    return a.cardType == "DEBIT"
                }) 
                this.cardsCredits = this.cards.filter((a)=>{
                    return a.cardType == "CREDIT"
                }) 
                this.today = new Date().toISOString()        
            })
            .catch(function (error) {            
                console.log(error);
            })
           
        },
      
        newDate(thruDate){ 
            return  new Date(thruDate).toLocaleDateString('es-AR', {month: '2-digit', year: '2-digit'});
        },
        newNumber(balance){ 
            balance = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(balance);
            return balance
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "/index.html")
        },
        newCards(){
            axios.post("/api/clients/current/cards",`=${this.cardType}&=${this.cardColor}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => window.location.reload())
                .catch(response => 
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'User is Register!',
                      }))    
        },
        functionDelete(card){
            this.cardId = card.id
            axios.patch(`/api/clients/current/cards/delete`,`cardId=${this.cardId}`)
            .then(() => {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Cards was deleted',
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