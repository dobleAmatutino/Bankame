const app = Vue.createApp({

    data() {
        return {
            client:[],
            data:[],
            loans:[]
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
            axios.get("/api/loans")
                .then(response=>{
                   this.data=response.data;
                   this.loans=this.data
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
    }
})
    .mount('#app')