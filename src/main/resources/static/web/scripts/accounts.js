const app = Vue.createApp({

    data() {
        return {
            client:[],
            accounts:[],
            loans:[]
        }
    },
    created() {
        this.loadData()
    },
    methods: {

        loadData() {
            axios.get("/clients/current")
                .then(response=>{
                    this.client=response.data;
                    this.accounts=this.client.accountsDTO.sort((a,b)=>a.id-b.id)
                    this.loans=this.client.loansDTO.sort((a,b)=>a.id-b.id)
                }).catch((error) => {
                    console.log(error);
                });

        },
        
        formatBalance(saldo) {
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
          },

        logout(){
            axios.post("/api/logout").then(response => window.location.href = "http://localhost:8080/web/index.html")
        },

        capitalLetter(string){
            return string.toUpperCase();

        },

        createAccount() {
            axios.post("/api/clients/current/accounts").then(()=>{window.location.reload()})

        }
    }
})
    .mount('#app')