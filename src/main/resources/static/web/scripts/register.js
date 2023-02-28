const app=Vue.createApp({

    data(){
        return{
            firstName:"",
            lastName:"",
            email:"",
            password:""
        }
    },
    created(){
       
    },
    methods:{

        fixEmail(){
            if(!this.email.includes("@")){
                Swal.fire('Problems with the email')
                return this.email=""
            }
            if(this.email.includes("@")){
                a = this.email.split("@")
                if(a.length>2){
                    Swal.fire('Problems with the email')
                    return this.email=""
                }
                if(!a[1].includes(".")){
                    Swal.fire('Problems with the email')
                    return this.email=""
                }
                if(a[0].includes(" ")||a[1].includes(" ")){
                    Swal.fire('Problems with the email')
                    return this.email = ""
                }
            
            }
            else{
                b = this.email.toLowerCase()
            }
            return b
            
        },

        registerAclient(){
            let firstName=this.firstName
            let lastName=this.lastName
            let email=this.fixEmail()
            let password=this.password

            axios.post("/api/clients" ,`firstName=${firstName}&lastName=${lastName}&email=${email}&password=${password}`)
                .then(response =>axios.post('/api/login' , `email=${email}&password=${password}`).then(response=> window.location.href = "http://localhost:8080/web/accounts.html"))
                .catch(function(error){
                    console.log(error.response.data)
                    if(firstName==""){
                        Swal.fire({
                            icon:"danger",
                            title:"FirstName is empty"
                      })
                    }
                    if(LastName==""){
                        Swal.fire({
                            icon:"danger",
                            title:"LastName is empty"
                        })
                    }
                    if(password.length<10){
                        Swal.fire({
                            icon:"danger",
                            title:"The password its too short"
                        })
                    }
                    if(email==""){
                        Swal.fire({
                            icon:"danger",
                            title:"the email does not comply with the format"
                        })
                    }
                })
        }

    }

})
app.mount("#app")