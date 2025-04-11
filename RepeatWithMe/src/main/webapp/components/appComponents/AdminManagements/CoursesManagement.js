const CoursesManagementComponent = {
    template:`
        <div class = "coursesManagementDiv">
            <h1>Gestione corsi</h1>
            <div class="flex">
                <div class="insertCourseDiv">    
                    <h2>Inserisci un corso</h2> 
                    <div class="input-text">
                        <input type="text" v-model="courseTitleInsert">
                        <span></span>
                        <label>Titolo</label>
                    </div>           
                    <input id="button" type="button" @click="insertCourse" value="Inserisci">
                </div>
                <div class="deleteCourseDiv">     
                    <h2>Rimuovi corso</h2> 
                    <div class="input-slect">
                        <select v-model="courseTitleRemove">
                            <option :value="none" selected>Titolo</option>
                            <option v-for="c in courses" :value="c.titolo"  >{{c.titolo}}</option>
                        </select>
                    </div>  
                    <input id="button" type="button" @click="removeCourse" value="Elimina">
                </div>
            </div>
        </div>
    `,

    data(){
        return{
            courseTitleInsert:'',
            courseTitleRemove:'',
            courses:[],

            info:'',
            linkOperations:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations',
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-admin-management',
        }
    },

    methods:{
        getCourses: function (){
            let self = this;
            $.get(this.linkOperations,{action : "getC" },function (data) {
                console.log(data);
                self.courses=data;
            })
        },
        insertCourse: function () {
            var self = this ;
            $.post(this.link,
                {
                    action:'insertCourse',
                    courseTitle:this.courseTitleInsert,
                },
                function (data) {
                    alert(data);
                }
            )
            this.getCourses();
        },
        removeCourse: function () {
            if(this.courseTitleRemove != 'none'){
                var self = this ;
                $.post(this.link,
                    {
                        action:'removeCourse',
                        courseTitle:this.courseTitleRemove,
                    },
                    function (data) {
                        alert(data);
                    }
                )
                this.getCourses();
            }

        }

    },
    mounted() {
        this.getCourses();
    }
}