const app=Vue.createApp({

    data(){
        return{
            roll:"",
            email:"",
            password:""
        }
    },
    created(){
       
    },
    methods:{

        logIn(){

            
            axios.post("/api/login" ,`email=${this.email}&password=${this.password}`)
                .then(response => window.location.href = "http://localhost:8080/web/accounts.html")
                .catch(function(error){
                        Swal.fire({icon:'error',
                            title:"Credentials error",
                            confirmButtonColor:" #dc143c",
                        confirmButtonText:"Correct"})}
                        )
        },
        
        logInAdmin(){

            
            axios.post("/api/login" ,`email=${this.email}&password=${this.password}`)
                .then(response => window.location.href = "http://localhost:8080/manager.html")  
                .catch(function(error){
                    Swal.fire({icon:'error',
                        title:"Credentials error",
                        confirmButtonColor:" #dc143c",
                    confirmButtonText:"Correct"})}
                    )
        }

    }

})
app.mount("#app")