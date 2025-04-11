const LoginComponent = {
    data: function(){
        return{
            email:'',
            password:'',
            sessionId:'',
            role:'',
            username:'',
            data:'',
            link:'http://localhost:8081/RepeatWithMe_war_exploded/servlet-login'
        }
    },
    template:`
          <div class="login-box" id="login">
          <h1>Login</h1>
          <form>
            <div class="txt_field">
                <input type="email" v-model="email" required>
                <span></span>
                <label>Email</label>
            </div>
            <div class="txt_field">
                <input type="password" v-model="password" required>
                <span></span>
                <label>Password</label>
            </div>
            <input id="loginButton" type="button" @click="userInfo" value="Invia">
            <div class="signup_link">
                <p>Non hai un account? Registrati!</p>
            </div>
            <!--<p>email : {{email}}</p>
            <p>pass : {{ password }}</p>
            <p>seess : {{ sessionId }}</p>
            <p>username : {{ username }}</p>
            <p>role : {{ role }}</p>
            <p>data : {{ data }}</p>-->
          </form>
          </div>
          `,

    methods:{
        loginsetStateAppLog: function (){
            this.$emit('login-set-app-log');
            console.log("ENTROPRIMS");
        },
        transitInner: function (value){
            this.$emit('transit-inner', value);
        },
        userInfo: function () {
            var self = this;
            if (self.sessionId === '') {
                console.log("session null ");
                $.post(this.link, {email: this.email, password: this.password},
                    function (data) {
                        console.log('torna questo', data.username);
                        if (data.username === undefined) {
                            console.log('user non esiste')
                            alert(data);
                            self.data = data;
                        } else {
                            console.log('user esiste')
                            self.role = data.role;
                            self.data = data;
                            self.sessionID = data.sessionId;
                            self.username = data.username;
                            localStorage.setItem("username", data.username);
                            localStorage.setItem("role", data.role);
                            localStorage.setItem("email", data.email);
                            localStorage.setItem("sessionId", data.sessionId);
                            self.loginsetStateAppLog();
                            self.transitInner('allOperations');

                        }
                    }
                )
            } else {
                console.log('');
                $.post(this.link, {email: this.email, password: this.password, sessionId: this.sessionId},
                    function (data) {
                        if (data.username === undefined) {
                            alert(data);
                            console.log('user non esiste')
                            self.data = data;
                            self.sessionID = data.sessionId;
                        } else {
                            console.log('user esiste')
                            self.role = data.role;
                            self.data = data;
                            self.sessionID = data.sessionId;
                            self.username = data.username;
                            localStorage.setItem("username", JSON.stringify(data.username));
                            localStorage.setItem("role", JSON.stringify(data.role));
                            localStorage.setItem("email", JSON.stringify(data.email));
                            localStorage.setItem("sessionId", JSON.stringify(data.sessionId));
                            self.loginsetStateAppLog();
                            self.transitInner('allOperations');
                        }
                    }
                )
            }
        },
    },
    mounted() {
        console.log("entro mount login");
        let user = localStorage.getItem("username");
        if(user != null){
            alert("Sei già loggato");
            this.transitInner('home');
        }
    }
}