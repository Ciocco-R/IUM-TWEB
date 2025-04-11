const AvailableRepetitionsComponent = {
    data: function (){
        return{
            ripes:[],
            data:null,
            showPipes:false,
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-available-repetition'
        }
    },

    template: `
          <div id="availableRepetitions">
              <h1>Ripetizioni disponibili</h1>
              <div class="flex">
                <div class="arDiv">
                  <table class="table-fill">
                      <thead>
                      <tr>
                        <th>Docente</th>
                        <th>TitoloCorso</th>
                        <th>Giorno</th>
                        <th>Ora</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr v-for="ripe in ripes" >
                        <td>{{ripe.teacherName}} {{ripe.teacherSurname}}</td>
                        <td>{{ripe.courseTitle}}</td>
                        <td>{{ripe.day}}</td>
                        <td>{{ripe.hour}}</td>
                      </tr>
                      </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        `,

        methods:{

},

    mounted() {
        var self = this;
        $.get(this.link,function (data){
            console.log(data);
            self.ripes=data;
        })
    }
}
