const TeachingsManagementComponent = {
    template:`
        <div class = "teachingsManagementDiv">
            <h1>Gestione insegnamenti</h1>
            <div class="flex">
                <div class="insertTeachingDiv">       
                    <h2>Inserisci insegnamento</h2>
                    <div class="input-slect">
                        <select v-model="courseInsert">
                            <option value="none">none</option>
                            <option v-for="c in courses" :value="c.titolo"  >{{c.titolo}}</option>
                        </select>
                    </div>
                    <div class="input-slect">
                        <select v-model="teacherInsert">
                            <option value="none">none</option>
                            <option v-for="t in teachers" :value="t.name.concat(','.concat(t.surname))" >{{t.name}} {{t.surname}}</option>
                        </select>
                    </div>
                    <input id="button" type="button" @click="insertTeaching" value="Inserisci">
                </div>
                
                <div class="removeTeachingDiv">  
                    <h2>Rimuovi insegnamento</h2>
                    <div class="input-slect">
                        <select v-model="courseRemove" @change="getTfromC">
                            <option value="none">none</option>
                            <option v-for="c in courses" :value="c.titolo"  >{{c.titolo}}</option>
                        </select>
                    </div>
                    <div class="input-slect">
                        <select class="input-select" v-model="teacherRemove" v-if="this.courseRemove !== 'none'" >
                            <option v-if="teachersR.length === 0" selected>Nessun docente diponibile</option>
                            <option v-for="t in teachersR" :value="t.name.concat(','.concat(t.surname))" >{{t.name}} {{t.surname}}</option>
                        </select>
                    </div>
                    <input id="button" type="button" @click="removeTeaching" value="Elimina">
                </div>
            </div>
        </div>
    `,

    data(){
        return{
            teachers:[],
            courses:[],
            teachersR:[],

            teacherInsert:'',
            courseInsert:'',

            teacherRemove:'',
            courseRemove:'',

            info:'',
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-admin-management',
            linkOperations:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations'
        }
    },

    methods:{
        insertTeaching: function () {
            var self = this ;
            $.post(this.link,
                {
                    action:'insertTeaching',
                    courseTitle:this.courseInsert,
                    teacherName:this.teacherInsert.split(",")[0],
                    teacherSurname:this.teacherInsert.split(",")[1],
                },
                function (data) {
                    alert(data);
                }
            )
        },
        removeTeaching: function () {
            var self = this ;
            $.post(this.link,
                {
                    action:'removeTeaching',
                    courseTitle:this.courseRemove,
                    teacherName:this.teacherRemove.split(",")[0],
                    teacherSurname:this.teacherRemove.split(",")[1],
                },
                function (data) {
                    alert(data);
                }
            )
        },

        getTfromC(){
            console.log('entro in getTfromC');
            var self=this;
            $.get(this.linkOperations,{action : "getTFromC", titleC : this.courseRemove },function (data) {
                console.log(data);
                self.teachersR=data ;
            })
        },

        getTeachers: function (){
            let self = this;
            $.get(this.linkOperations,{action : "getT" },function (data) {
                console.log(data);
                self.teachers=data;
            })
        },

        getCourses: function (){
            let self = this;
            $.get(this.linkOperations,{action : "getC" },function (data) {
                console.log(data);
                self.courses=data;
            })
        }

    },

    mounted(){
        this.getCourses();
        this.getTeachers();
    }
}