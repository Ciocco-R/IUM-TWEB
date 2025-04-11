const InsertBookingComponent = {
    template: `
        <div class="insertbooking">
            <h1>Prenota</h1>
            <div id="InsertBookingOpDiv">
                <div class="input-slect"> 
                    <select v-model="corsoSelected" @change="getTfromC()">
                        <option class="select-items" v-if="cVetIni.length === 0 " :value="'none'" selected>Nessun corso diponibile</option>
                        <option class="select-items" v-if="cVetIni.length > 0 " :value="'none'" selected>Corso</option>
                        <option class="select-items" v-for="c in cVetIni" :value="c.titolo"  >{{c.titolo}}</option>
                    </select>
                </div>
                
                <div class="input-slect"> 
                    <select v-model="teacherSelected" v-if="this.corsoSelected !== 'none'"   @change="getBookings()" >
                        <option class="select-items" v-if="tVet.length > 0" :value="'none'" selected>Docente</option>
                        <option class="select-items" v-for="t in tVet" :value="t.name.concat(','.concat(t.surname))" selected>{{t.name}} {{t.surname}}</option>
                        <option class="select-items" v-if="tVet.length === 0" :value="'none'"  selected>Nessun docente diponibile</option>
                    </select>
                </div>
              
            </div>
            <div  class="flex-insertBooking">
                <div  class="t-scroll">
                    <table v-if="showTable && corsoSelected !== 'none' && teacherSelected !== 'none'" >
                        <thead>
                            <tr>
                                <th class="text-left">Docente</th>
                                <th class="text-left">TitoloCorso</th>
                                <th class="text-left">Giorno</th>
                                <th class="text-left">Ora</th>
                            </tr>
                        </thead>
                        <tbody class="table-hover">
                            <tr v-for="b in bookingsFromTCvet" @click="switchContext(b,0)">
                                <td class="text-left">{{b.teacherName}} {{b.teacherSurname}}</td>
                                <td class="text-left">{{b.courseTitle}}</td>
                                <td class="text-left">{{b.day}}</td>
                                <td class="text-left">{{b.hour}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>  
                <div class="t-scroll">
                    <table v-if="selectedBookings.length > 0">
                        <thead>
                            <tr>
                                <th class="text-left">Docente</th>
                                <th class="text-left">TitoloCorso</th>
                                <th class="text-left">Giorno</th>
                                <th class="text-left">Ora</th>
                            </tr>
                        </thead>
                        <tbody >
                            <tr v-for="b in selectedBookings" @click="switchContext(b,1)">
                                <td class="text-left">{{b.teacherName}} {{b.teacherSurname}}</td>
                                <td class="text-left">{{b.courseTitle}}</td>
                                <td class="text-left">{{b.day}}</td>
                                <td class="text-left">{{b.hour}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <button id="button" v-if="selectedBookings.length > 0" @click="insertBooking">Prenota</button>
            </div>
        </div>
    `,

    data(){
        return{
            showTable : false,
            teacherSelected:'none',
            corsoSelected:'none',
            daySelected:'none',

            selectedBookingsDay:[],
            selectedBookings: [],
            tVet: [],
            cVetIni:[],
            days:["lunedì","martedì","mercoledì","giovedì","venerdì"],
            bookingsFromTCvet:[],
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations',
            linkInsert:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking'

        }
    },
    methods:{
        selectDay:function(){
            console.log("selectDay");
            if(this.daySelected != 'none'){
                this.getBookings();
                console.log("g!= null :: " + this.daySelected);
                this.bookingsFromTCvet=this.bookingsFromTCvet.filter(e => e.day == this.daySelected);
                console.log(this.bookingsFromTCvet.filter(e => e.day == this.daySelected));
            }else{
                this.getBookings();
            }
        },
        getBookings:function () {
            if(this.corsoSelected !== 'none'){
                this.showTable=true;
                var self=this;
                $.get(this.link,{action : "getBfromTC", courseTitle : this.corsoSelected, nameTeacher:this.teacherSelected.split(',')[0], surnameTeacher:this.teacherSelected.split(',')[1] },function (data) {

                    self.bookingsFromTCvet=data ;

                })
            }
        },

        insertBooking: function(){
            console.log('insertBooking');
            let prob;
            let bul = 0;
            this.selectedBookings.forEach((b) =>{
                let self = this;
                $.get(this.linkInsert,
                    {teacherName: b.teacherName,
                        teacherSurname: b.teacherSurname, courseTitle: b.courseTitle,
                        day: b.day, hour: b.hour},
                    function(data){
                        prob=data;
                        if(prob != '200'){
                            alert(prob);
                        }else{
                            bul=1;
                            self.selectedBookings= self.selectedBookings.filter((el) => el !== b );
                        }
                })
            });
            if(bul == 1){
                alert('Prenotato');
            }
        },
        getTfromC(){
            this.showTable=false;
            var self=this;
            $.get(this.link,{action : "getTFromC", titleC : this.corsoSelected },function (data) {
                console.log(data);
                self.tVet=data ;
                self.teacherSelected='none';
            })
        },
        switchContext(elem,type){
            if(type === 0){
                if(this.selectedBookings.find(el => (el.hour === elem.hour && el.day === elem.day))){
                    alert('Hai già una ripetizione da prenotare per questa data e questo orario');
                } else{
                    this.bookingsFromTCvet= this.bookingsFromTCvet.filter((el) => el !== elem );
                    this.selectedBookings.push(elem);
                }
            }else{
                this.selectedBookings= this.selectedBookings.filter((el) => el !== elem );
                if(this.teacherSelected.split(',')[0] === elem.teacherName  && this.teacherSelected.split(',')[1] === elem.teacherSurname  )
                    this.bookingsFromTCvet.push(elem);
            }
            this.bookingsFromTCvet.filter((elem) => ((this.selectedBookings.find((el) => el === elem )) ===  undefined) );
        }
    },

    mounted() {
        let self=this;
        $.get(this.link,{action : "getC" },function (data) {
            console.log(data);
            self.cVetIni=data ;
        })
    }

}