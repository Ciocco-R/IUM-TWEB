const AllOperationsComponent = {
    template: `  
      <div class="wrapper" id="mainApp">
        <div class="sidebar">
          <h3>Operazioni</h3>
          <ul>
            <li :class="(mainVue ==='A')? 'li1': 'li'" @click="toggleMain('A')" >  <a><i class="fas"></i>Ripetizioni Disponibili</a></li>
            <li :class="(mainVue ==='B')? 'li1': 'li'" v-if="role === 'client'" @click="toggleMain('B')" >  <a><i class="fas"></i>Le mie prenotazioni</a></li>
            <li :class="(mainVue ==='C')? 'li1': 'li'" v-if="role === 'client'"  @click="toggleMain('C')" >  <a><i class="fas"></i>Prenota</a></li>
            <li :class="(mainVue ==='D')? 'li1': 'li'" v-if="role === 'client'" @click="toggleMain('D')" > <a><i class="fas"></i>Cambia lo stato di una prenotazione</a> </li>
            <li :class="(mainVue ==='E')? 'li1': 'li'" v-if="role === 'admin'"  @click="toggleMain('E')"  > <a><i class="fas"></i>Visualizza tutte le ripetizioni</a></li>
            <li class="li" v-if="role === 'admin'" @click="subMenge = true"  > <a><i class="bx bx-wrench"></i>Operazioni admin</a></li>   
                <ul  v-if="subMenge === true">
                    <li :class="(mainVue ==='FA')? 'li1': 'li'" @click="toggleMain('FA')"><a><i class="fas"></i>Gestione insegnanti</a></li>
                    <li :class="(mainVue ==='FB')? 'li1': 'li'" @click="toggleMain('FB')"><a><i class="fas"></i>Gestioni corsi</a></li>
                    <li :class="(mainVue ==='FC')? 'li1': 'li'" @click="toggleMain('FC')"><a><i class="fas"></i>Gestione insegnamenti</a></li>
                </ul>
          </ul>
        </div>
        <div class="main_content">  
            <available-repetitions  v-if="mainVue === 'A'"></available-repetitions>
            <my-own-booking         v-if="mainVue === 'B'"></my-own-booking>
            <insert-booking         v-if="mainVue === 'C'"></insert-booking>
            <booking-state          v-if="mainVue === 'D'"></booking-state>
            <all-bookings           v-if="mainVue === 'E'"></all-bookings>
            <teachers-management    v-if="mainVue === 'FA'"></teachers-management>
            <courses-management     v-if="mainVue === 'FB'"></courses-management>
            <teachings-management   v-if="mainVue === 'FC'"></teachings-management>
        </div>
      </div>
    `,
    data:function (){
        return{
            mainVue:'',
            username:'',
            role:'',
            email:'',
            sessionId:'',
            subMenge:false
        }
    },
    methods:{
        toggleMain(value){
            console.log(value)
            if(!this.mainVue.includes('F')){
                this.subMenge=false;
            }
            this.mainVue=value;
        },
        getUser(){
            this.username=localStorage.getItem("username");
            this.role=localStorage.getItem("role");
            this.email=localStorage.getItem("email");
            this.sessionId=localStorage.getItem("sessionId");
            console.log(this.role);
        }
    },
    mounted() {
        console.log("Entro mount del Operazioni ")
        let user =localStorage.getItem('username')
        if(user === null ){
            alert('Devi Loggarti');
            router.push({name : 'login'});
        }else{
            this.getUser()
        }
    }
}
