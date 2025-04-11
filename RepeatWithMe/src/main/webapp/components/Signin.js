const SigninComponent = {
    data: function(){
        return{
            email:'',
            username:'',
            password:'',
            dataError:'',
            link: 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-signin'
        }
    },

    template: `
        <div class="login-box" id="login">
        <form>
            <h1>Registrazione</h1>
            <div class="txt_field">
                <input type="text" id="name" name="name" required>
                <span></span>
                <label for="name">Name</label>    
            </div>      
            <div class="txt_field">
                <input type="email" id="email" name="email" required>
                <span></span>
                <label for="email">Email</label>
            </div>
            <div class="txt_field">
                <input type="password" id="password" name="password" required>
                <span></span>
                <label for="password">Password</label>  
            </div>
            <div class="txt_field">
                <input type="password" id="password-confirm" name="password-confirm" required>
                <span></span>
                <label for="password-confirm">Conferma Password</label>     
             </div>
            <input id="loginButton" type="button" @click="insertUser" value="Registrati">
        </form> 
        </div>
        `,

    methods:{
        transitInner: function (value){
            this.$emit('transit-inner', value);
        },

        insertUser(){
            var self = this;
            $.post(this.link,{
                    email : this.email,
                    username : this.username,
                    password : this.password
                },
                function (data){
                    console.log(data);
                    if(data === ''){
                        self.transitInner('login');
                    }else{
                        self.dataError = data;
                    }

                }
            )
        }
    },

    mounted() {
        let username = localStorage.getItem('username');
        if(username != undefined){
            this.transitInner('home');
        }
    }
}