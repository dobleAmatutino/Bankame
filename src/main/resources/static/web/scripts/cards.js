const app = Vue.createApp({

    data() {
        return {
            client: {},
            cards: [],
            debitCards:[],
            creditCards:[]
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
                    this.cards = this.client.cardDTO;
                    this.debitCards =this.cards.filter(card=>card.type==="DEBIT")
                    this.creditCards =this.cards.filter(card=>card.type==="CREDIT")
                }).catch((error) => {
                    console.log(error);
                })

        },

        formatDateCard(string) {

            var info = string.split('-');

            const a=parseInt(info[0])
            b=a-a%100
            mes=info[1]
            año=a-b
             return mes+'/'+año ;
        },


        formatBalance(saldo) {
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
        },

        capitalLetter(string) {
            return string.toUpperCase();

        },

        logout(){
            axios.post('/api/logout').then(response => window.location.href = "http://localhost:8080/web/index.html")
        }

        
    }
})
    .mount('#app')