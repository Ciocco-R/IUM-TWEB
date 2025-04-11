const MyOwnBookingComponent = {
    template: `
        <div class="MOB">
            <h1>Le mie prenotazioni</h1>
            <div class="flex">
                <div id="MOBdiv">
                    <table class="table-fill">
                        <thead>
                        <tr>
                            <th class="text-left">Docente</th>
                            <th class="text-center">TitoloCorso</th>
                            <th class="text-center">Giorno</th>
                            <th class="text-center">Ora</th>
                            <th class="text-right">Stato</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="ripe in ripes" >
                            <td>{{ripe.teacherName}} {{ripe.teacherSurname}}</td>
                            <td>{{ripe.courseTitle}}</td>
                            <td>{{ripe.day}}</td>
                            <td>{{ripe.hour}}</td>
                            <td>{{ripe.state}}
                                <img v-if="ripe.state === 'active'" src="./assets/verde.png">
                                    <img v-else-if="ripe.state === 'deleted'" src="./assets/rosso.png">
                                        <img v-else-if="ripe.state === 'done'" src="./assets/giallo.png">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    `,
    data(){
        return{
            ripes:[],
            data:'',
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-my-own-bookings'
        }
    },
    methods:{
        getRepetitions: function(){
            var self = this;
            $.get(this.link, function(data){
                console.log(data);
                self.ripes=data;
            })
        }
    },
    mounted() {
        this.getRepetitions();
    }
}
