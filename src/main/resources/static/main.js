const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            firstName: " ",
            lastName:" ",
            email:" ",


        }
    },

    created(){
    this.funcionCargardatos()
       
    },
    methods: {
        funcionCargardatos(){
            axios.get('/api/clients')
            .then(response => {
               this.clients = response.data;
                console.log(this.clients);
                
            })
            .catch(function (error) {
              
                console.log(error);
            })
        },
        funcionaddClient () {       
            if(this.firstName != 0 && this.lastName != 0 && this.email != 0)    
            axios.post('/rest/clients', {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
                
              })
              .then(()=>{ this.funcionCargardatos()
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: "Client was saved",
                    showConfirmButton: false,
                    timer: 1000
                  })
            })
              .catch(function (error) {
                console.log(error);
              })
        },
        funcionDelete(client){
            axios.delete(`/rest/clients/`+ client.id)
            .then(() => {
                this.funcionCargardatos()
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Client was deleted',
                    showConfirmButton: false,
                    timer: 1000
                  })
            })

           
        },
        
    }
  

}).mount('#app')
