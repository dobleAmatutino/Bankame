const app = Vue.createApp({

    data() {
        return {
            client: [],
            accounts: [],
            originAccount: "",
            destinyAccount: "",
            accountHolder: "",
            amount: "",
            description: "",
            id: "",
        }
    },
    created() {
        this.loadData()
    },
    methods: {

        loadData() {
            axios.get("/clients/current")
                .then(response => {
                    this.client = response.data;
                    this.accounts = this.client.accountsDTO.sort((a, b) => a.id - b.id)
                    this.loans = this.client.loansDTO.sort((a, b) => a.id - b.id)
                    this.cards = this.client.cardDTO.sort((a, b) => a.fromDate - b.fromDate)
                    this.debitCards = this.cards.filter(card => card.type == "DEBIT")
                    this.creditCards = this.cards.filter(card => card.type == "CREDIT")
                }).catch((error) => {
                    console.log(error);
                });

        },
        
        formatBalance(saldo) {
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
        },

        transaction(){
            Swal.fire({
                title: 'Sure to make this transfer?',
                showDenyButton: true,
                icon:'info',
                confirmButtonText: 'Transfer',
                confirmButtonColor:'rgb(0, 255, 98)',
                denyButtonColor:'#dc143c',
                denyButtonText: `Dont Transfer`,
              }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                  this.makeAtransaction()
                } else if (result.isDenied) {
                  Swal.fire('The transfer wont be done', '', 'info')
                }
              })
        },
        makeAtransaction() {

            let originAccount = this.originAccount
            let destinyAccount = this.destinyAccount
            let amount = this.amount
            let description = this.description
            let id = this.originAccount.id

            axios.post('/api/transactions', `amount=${amount}&description=${description}&originAccount=${originAccount.number}&destinyAccount=${destinyAccount}`)
                .then(response => window.location.href = `http://localhost:8080/web/account.html?id=${id}`)
                .catch(function(error){
                    if(amount > originAccount.balance){
                        Swal.fire({
                            icon:'warning',
                            title:"insufficient balance",
                            confirmButtonColor:"#dc143c"
                        })
                    }
                    if(description == ""){
                        Swal.fire({icon:'warning',
                        title:"Description please",
                        confirmButtonColor:"#dc143c"
                    })
                }
                })
        }
    },
    computed:{
        lacksMoney(){
            if (this.amount > this.originAccount.balance){
                Swal.fire({
                    icon:'warning',
                    title:"insufficient balance",
                    confirmButtonColor:"#dc143c"
                })
            }
        }
    }
})
    .mount('#app')