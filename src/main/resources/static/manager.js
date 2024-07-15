const app = Vue.createApp({


    data() {
        return {
            firstName:"",
            lastName:"",
            email:"",
            data:"",
            client:[],
            loanName:"",
            loanPayments:[],
            loanMaxAmount:"",
            password:""
        }
    },
    created() {
        this.loadData()
    },
    methods: {

        loadData() {
            axios.get('/api/clients')
                .then(response=> {
                    this.data=response
                    this.client=this.data.data

                })
                .catch((error) => {
                    console.log(error);
                });
        },

        createNewLoan(){
            axios.post('/api/newLoan', {'name':this.loanName,'payments':this.loanPayments,'maxAmount':this.loanMaxAmount}).then(()=>{
                window.location.reload()
                console.log('loan created')
            })
        },

        addClient(){
            axios.post("/api/clients" ,`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
                .then(response => window.location.href = "http://localhost:8080/manager.html")  
                .catch((error) => {console.log(error)})
            },
        logout(){
            axios.post("/api/logout").then(response => window.location.href = "http://localhost:8080/web/index.html")
        },
    },


})
app.mount('#app')