const app = Vue.createApp({


    data() {
        return {
            name: "",
            lastName: "",
            email: "",
            data:"",
            client:[],
            loanName:"",
            loanPayments:[],
            loanMaxAmount:""
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
            axios.post('/clients',{
                'firstName':this.name,'lastName':this.lastName,'email':this.email
            })
            .then(()=>{
                window.location.reload()
                console.log('esta funcionando vas bien')
            })
              .catch(function (error) {
                console.log(error);
              });

        },
        logout(){
            axios.post("/api/logout").then(response => window.location.href = "http://localhost:8080/web/index.html")
        },
    },


})
app.mount('#app')