const app = Vue.
createApp({
    data() {
        return {
            firstName: "",
            lastName:"",
            email:"",
            pwd:"",
            pwdRegister:"",
            emailRegister:"",
        }
    },

    created(){
    },
    methods: {
        login(){
            axios.post("/api/login",`email=${this.email}&pwd=${this.pwd}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => location.href = "/web/accounts.html")
                .catch(response =>   
                    Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'User not found!',
                  }))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "/index.html")
        },

        register(){
            axios.post('/api/clients',
            `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.emailRegister}&password=${this.pwdRegister}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
             .then(response =>
                axios.post("/api/login",`email=${this.emailRegister}&pwd=${this.pwdRegister}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}}),
                 Swal.fire({
                    title: "Register Successfully!",
                    icon: "success",
                    button:"clouse!",
                   
                }))
                  .catch(error =>  Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data
                  }))
    
          
        },
        
    },
}).mount('#app');

