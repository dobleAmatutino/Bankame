const app = Vue.createApp({

    data() {
        return {
            client: [],
            cards: [],
            debitCards:[],
            creditCards:[],
            cardType:["CREDIT","DEBIT"],
            cardColor:["GOLD","SILVER","TITANIUM"],
            type:"",
            color:""
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
            axios.get("/clients/current")
                .then(response=>{
                    this.client=response.data;
                    this.accounts=this.client.accountsDTO.sort((a,b)=>a.id-b.id)
                    this.loans=this.client.loansDTO.sort((a,b)=>a.id-b.id)
                    this.cards = this.client.cardDTO.sort((a,b)=>a.fromDate-b.fromDate)
                    this.debitCards = this.cards.filter(card=>card.type=="DEBIT")
                    this.creditCards = this.cards.filter(card=>card.type=="CREDIT")
                }).catch((error) => {
                    console.log(error);
                });

        },

        newCard(){
            Swal.fire({
                title: 'Are you sure to created a new card',
                showDenyButton: true,
                icon:'question',
                confirmButtonText: 'Get it',
                confirmButtonColor:'rgb(0, 255, 98)',
                denyButtonColor:'#dc143c',
                denyButtonText: `Cancel`,
              }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                  this.createdAcard()
                } else if (result.isDenied) {
                  Swal.fire("Sure, don't hesitate to ask again")
                }
              })
        },



        
        createdAcard(){

            let type=this.type
            let color=this.color
            

            axios.post('/api/clients/current/cards', `type=${type}&color=${color}`)
            .then(response => window.location.href = "http://localhost:8080/web/cards.html")
            .catch(function(err){
                if(type == "" ){
                    Swal.fire({
                        icon:'warning',
                        title:'Please get in the type of card',
                        confirmButtomColor:'#dc143c'
                    }) 
                }
                if(color == ""){
                    Swal.fire({
                        icon:'warning',
                        title:'Please get in the color of your card',
                        confirmButtomColor:'#dc143c'
                    }) 
                }

                if(color=="" && type==""){
                    Swal.fire({
                        icon:'warning',
                        title:'Get in color and type of card ',
                        confirmButtomColor:'#dc143c'
                    }) 
                }
                
            })
        }
    }
})
    .mount('#app')