const BookingStateComponent = {
    data: function (){
        return{
            ripes:[ ],
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-booking-state',
            linkMyOwnBooking:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-my-own-bookings'
        }
    },

    template:`
        <div class="bookingstatediv">
            <h1>Cambia lo stato delle tue prenotazioni</h1>
            <div class="flex">
                <div id="BSScroll">
                    <table class="table-fill" >
                    <thead>
                        <tr>
                            <th>Docente</th>
                            <th>TitoloCorso</th>
                            <th>Giorno</th>
                            <th>Ora</th>
                            <th>Stato</th>
                            <th style="background-color: white"></th>
                            <th style="background-color: white"></th>
                        </tr>
                    </thead>
                        <tbody>
                            <tr v-for="ripe in ripes" v-if="ripe.state === 'active'">
                                <td>{{ripe.teacherName}} {{ripe.teacherSurname}}</td>
                                <td>{{ripe.courseTitle}}</td>
                                <td>{{ripe.day}}</td>
                                <td>{{ripe.hour}}</td>
                                <td>{{ripe.state}}
                                    <img v-if="ripe.state === 'active'" src="./assets/verde.png">
                                    <img v-else-if="ripe.state === 'deleted'" src="./assets/rosso.png">
                                        <img v-else-if="ripe.state === 'done'" src="./assets/giallo.png">
                                </td>
                                <td class="cambiamenti_stato" @click="changeStateDone(ripe)">Effettua</td>
                                <td class="cambiamenti_stato" @click="changeStateDisdici(ripe)">Disdici</td>                             
                            </tr>
                        </tbody>
                    </table>
                    
                </div>
            </div>    
        </div>
    `,

    methods:{
        getRepetitions: function(){
            var self = this;
            $.get(this.linkMyOwnBooking, function(data){
                console.log(data);
                self.ripes=data;
            })
        },

        changeStateDone: function(value){
            console.log('Entro in changeState');
            console.log('suca');
            console.log(value.teacherName);
            var self = this;
            $.post(this.link,{teacherName: value.teacherName, teacherSurname: value.teacherSurname, courseTitle: value.courseTitle, day: value.day, hour: value.hour, state: 'done'}, function (){
                console.log('Cambio lo stato della prenotazione');
                self.getRepetitions();
            })
        },
        changeStateDisdici: function(value){
            console.log('Entro in changeState');
            console.log(value.teacherName);
            var self = this;
            $.post(this.link,{teacherName: value.teacherName, teacherSurname: value.teacherSurname, courseTitle: value.courseTitle, day: value.day, hour: value.hour, state: 'deleted'}, function (){
                console.log('Cambio lo stato della prenotazione');
                self.getRepetitions();
            })
        }
    },

    mounted(){
        this.getRepetitions();
    }
}


