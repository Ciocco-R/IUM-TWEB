const HomeComponent = {
    props: ['title'],
    data: function(){
        return{
            showAbout:false,
            username:null,
        }
    },
    template:`
                <div class="home">
                    <div class="text-box">
                        <h1>Repeat With Me</h1>
                        <p>Il sito numero uno in Italia per imparare tutto ciò che vuoi, quando vuoi e dove vuoi.
                        <br><br>Prenota una lezione con il docente che preferisci e iniziamo!</p>
                        <!--<button @click="getUsername">Logout</button>-->
                    </div>
                </div>
                `,
    methods:{
        toggleAboutVue(){
            this.showAbout=!this.showAbout;
        },
        getUsername(){
            console.log(localStorage.getItem('username'),
                localStorage.getItem('role'),
                localStorage.getItem("email"),
                localStorage.getItem("sessionId"));
                localStorage.clear();
        }
    }
};