const TeachersManagementComponent = {
    template:`
        <div class = "teachersManagementDiv">
            <h1>Gestione insegnanti</h1>
            <div class="flex">
                <div class="insertTeacherDiv">   
                    <h2>Inserisci insegnante</h2>    
                    <div class="input-text">
                        <input  type="text" v-model="teacherNameInsert">
                        <span></span>
                        <label>Nome</label>
                    </div>

                    <div class="input-text">
                        <input  type="text" v-model="teacherSurnameInsert">
                        <span></span>
                        <label>Cognome</label> 
                    </div>
                    <input id="button" type="button" @click="insertTeacher" value="Inserisci">
                </div>
                
                <div class="deleteTeacherDiv">
                <h2>Rimuovi insegnante</h2>
                     <div class="input-slect">
                        <select v-model="teacherDelete">
                            <option :value="none" selected>none</option>
                            <option v-for="c in teachers" :value="c.name.concat(','.concat(c.surname))" >{{c.name}} {{c.surname}}</option>
                        </select>
                    </div>  
                    <input id="button" type="button" @click="removeTeacher" value="Elimina">
                </div>
            </div>
        </div>
    `,

    data(){
        return{
            teacherNameInsert:'',
            teacherSurnameInsert:'',
            teacherDelete:'',
            teachers:[],
            info:'',
            linkOperations:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations',
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-admin-management',
        }
    },

    methods:{
        insertTeacher: function () {
            var self = this ;
            $.post(this.link,
                    {
                        action:'insertTeacher',
                        teacherName:this.teacherNameInsert,
                        teacherSurname:this.teacherSurnameInsert,
                    },
                    function (data) {
                        alert(data);
                    }
                )


        },
        removeTeacher: function () {
            if(this.teacherDelete != 'none'){
                var self = this ;
                $.post(this.link,
                    {
                        action:'removeTeacher',
                        teacherName:this.teacherDelete.split(',')[0],
                        teacherSurname:this.teacherDelete.split(',')[1],
                    },
                    function (data) {
                        alert(data);
                    }
                );
                this.getTeachers();

            }

        },
        getTeachers: function (){
            let self = this;
            $.get(this.linkOperations,{action : "getT" },function (data) {
                self.teachers=data;
            })
        },
    },
    mounted() {
       this.getTeachers();
    }
}