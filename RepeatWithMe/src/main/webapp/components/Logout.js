const LogoutComponent = {
    data: function(){
        return{
            sessionID : localStorage.getItem('user-info'),
            link: 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-logout'
        }
    },
    mounted(){
        console.log("suca logout");
        $.get(this.link);
        localStorage.clear();
        this.logoutsetStateAppLog();
        this.transitInner('home');
    },
    methods: {
        logoutsetStateAppLog: function (){
            this.$emit('logout-set-app-log');
            console.log("ENTROPRIMS");
        },
        transitInner: function (value) {
            this.$emit('transit-inner', value);
        },
    }
}