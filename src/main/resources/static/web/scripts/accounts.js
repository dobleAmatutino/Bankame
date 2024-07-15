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
            axios.post("/api/logout").then(response =>{
                Swal.fire({icon:'warning',
                    title:response.data,
                    confirmButtonColor:" #dc143c",
                confirmButtonText:"accept",})
            .then(()=> window.location.href="http://localhost:8080/web/index.html")})
            .catch(error =>{Swal.fire({icon:'warning',
                title:error.data,
                confirmButtonColor:" #dc143c",
            confirmButtonText:"ok",})} )
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