const app = Vue.createApp({

    data() {
        return {
            client: [],
            accounts:[],
            destinyAccount:"",
            amount:"",            
            loans:[],
            id:1,
            amount:"",
            payments:"",
            clientLoans:[],
            clientLoanGot:[],
        }
    },
    created() {
        this.loadData()
        this.getloan()
    },
    methods: {
        logout(){
            axios.post("/api/logout").then(response => window.location.href = "http://localhost:8080/web/index.html")
        },

        loadData() {
            axios.get("/clients/current")
                .then(response=>{
                    this.client=response.data;
                    this.accounts=this.client.accountsDTO.sort((a,b)=>a.id-b.id)
                    this.clientLoans=this.client.loansDTO.sort((a,b)=>a.id-b.id)
                    this.clientLoanGot = this.clientLoans.map(loan => loan.id)
                    this.cards = this.client.cardDTO.sort((a,b)=>a.fromDate-b.fromDate)
                    this.debitCards = this.cards.filter(card=>card.type=="DEBIT")
                    this.creditCards = this.cards.filter(card=>card.type=="CREDIT")
                }).catch((error) => {
                    console.log(error);
                });

        },

        makeAtransaction(){

            let originAccount=this.originAccount
            let destinyAccount=this.destinyAccount
            let amount= this.amount
            let description= this.description
            let id = this.originAccount.id

            axios.post('/api/transactions', `amount=${amount}&description=${description}&originAccount=${originAccount.number}&destinyAccount=${destinyAccount}`)
            .then(response => window.location.href = `http://localhost:8080/web/account.html?id=${id}`)
        },

        getloan(){
            axios.get('/api/loans')
            .then(response=>
                this.loans=response.data
                ).catch((error) => {
                    console.log(error);
                });
        },

        getClientLoan(){
            Swal.fire({
                title: 'Are you sure to get this loan',
                showDenyButton: true,
                icon:'question',
                confirmButtonText: 'Get it',
                confirmButtonColor:'rgb(0, 255, 98)',
                denyButtonColor:'#dc143c',
                denyButtonText: `Cancel`,
              }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                  this.clientLoan()
                } else if (result.isDenied) {
                  Swal.fire("Sure, don't hesitate to ask again")
                }
              })
        },

        clientLoan(){
            var ids=this.id;
            var amount=this.amount;
            var payments = this.payments;
            var destinyAccount = this.destinyAccount
            var loans = this.loans
            var clientLoanGot = this.clientLoanGot
             
            axios.post('/api/loans', {"id":ids,"amount":amount,"payments":payments,"destinyAccount":destinyAccount})
            .then(response => window.location.href = `http://localhost:8080/web/accounts.html`)
            .catch(function(err){
                
                

                if(amount > loans[ids-1].maxAmount){
                    Swal.fire('There was a problem with the amount') 
                }

                if(clientLoanGot.includes(ids)){
                Swal.fire({
                    title:'You already have this loan',
                    icon:'warning',
                    confirmButtonText:'Ok',
                }) 
                
                }
            }
            )
        }

    }
})
.mount('#app')