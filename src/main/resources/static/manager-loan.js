const app = Vue.createApp({


    data() {
        return {
            loans:[],
            data:"",
            loanName:"",
            payment:"",
            loanInterests:"",
            loanPayments:[],
            loanMaxAmount:""
        }
    },
    created() {
        this.loadData()
    },
    methods: {

        logout(){
            axios.post("/api/logout").then(response => window.location.href = "http://localhost:8080/web/index.html")
        },

        loadData() {
            axios.get('/api/loans')
                .then(response=> {
                    this.data=response
                    this.loans=this.data.data

                })
                .catch((error) => {
                    console.log(error);
                });
        },

        createNewLoan(){
            axios.post('/api/newLoan', `loanName=${this.loanName}&loanMaxAmount=${this.loanMaxAmount}&loanInterests=${this.loanInterests}&loanPayments=${this.loanPayments}`).then(()=>{
                window.location.reload()
                console.log('loan created')
            })
        },

        pushPayment(){
            this.payment=parseInt(this.payment)

            this.loanPayments.push(this.payment)
            
            console.log(this.loanPayments)
            return this.loanPayments
           
           
            
        },

        logout(){
            axios.post("/api/logout").then(response => window.location.href = "http://localhost:8080/web/index.html")
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

        formatBalance(saldo) {
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
          },

        capitalLetter(string){
            return string.toUpperCase();
    
        },

    },
    computed:{
   
    }

})
app.mount('#app')