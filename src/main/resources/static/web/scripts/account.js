const app=Vue.createApp({

    data(){
        return{
            id:new URLSearchParams(location.search).get('id'),
            transactions:[],
            account:[],
            finalBalance:"",
            balance:""
        }
    },
    created(){
        this.loadData()
    },
    methods:{

        loadData(){
            axios.get("/api/accounts/"+this.id)
                .then(response => {
                    this.account=response.data
                    this.transactions=this.account.transactionsDTO
                    this.finalBalance=this.transactions.amount+this.balance
                    this.balance=this.account.balance

                })

        },
        logout(){
            axios.post('/api/logout').then(response => window.location.href = "http://localhost:8080/web/index.html")
        }

    }

})
app.mount("#app")