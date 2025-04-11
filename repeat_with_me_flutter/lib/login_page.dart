import 'dart:convert';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:repeat_with_me_flutter/User.dart';
import 'package:repeat_with_me_flutter/homeClient.dart';

import 'User.dart';
import 'homeAdmin.dart';
import 'homeGuest.dart';


class LoginPage extends StatefulWidget{
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}
class _LoginPageState extends State<LoginPage> {

  late TapGestureRecognizer _recognizer;
  @override
  void initState() {
    super.initState();
    _recognizer = TapGestureRecognizer()..onTap = (){
      Navigator.push(context, MaterialPageRoute(builder: (context) => HomePageGuest()));
    };
  }

  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  void login(String email, String password) async{
    var url = "http://localhost:8081/RepeatWithMe_war_exploded/servlet-login";
    print('Entro in login');
    var response = await http.post(
      Uri.parse(url),
      body:{
        "email":email,
        "password":password
      }
    );
    User u = new User(jsonDecode(response.body)['email'], jsonDecode(response.body)['username'], jsonDecode(response.body)['role']);
    if(u.role != null){
      if(u.role == "admin"){
        Navigator.push(context, MaterialPageRoute(builder: (context)=>HomePageAdmin(u)));
      } else if(u.role == "client"){
        Navigator.push(context, MaterialPageRoute(builder: (context)=>HomePageClient(u)));
      } else if(u.role == "guest"){
        Navigator.push(context, MaterialPageRoute(builder: (context)=>HomePageGuest()));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.grey[300],
        body: SafeArea(
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset(
                  'assets/images/logo-rwm.png',
                  scale: 3,
                ),
                /*Icon(
                  Icons.login,
                  size: 150,
                ),*/
                SizedBox(height: 75),

                //Hello again!
                Text(
                  'LOGIN PAGE',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                  ),
                ),
                SizedBox(height: 10),
                Text(
                    'Bentornato, ci sei mancato!',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 20,
                    )
                ),
                SizedBox(height: 50),


                //email textfield
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 25.0),
                  child: Container(
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      border: Border.all(color: Colors.white),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.only(left: 20.0),
                      child: TextFormField(
                        controller: emailController,
                        decoration: InputDecoration(
                          border: InputBorder.none,
                          hintText: 'Email',
                        ),
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 10),


                //password textfield
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 25.0),
                  child: Container(
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      border: Border.all(color: Colors.white),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.only(left: 20.0),
                      child: TextFormField(
                        controller: passwordController,
                        obscureText: true,
                        decoration: InputDecoration(
                          border: InputBorder.none,
                          hintText: 'Password',
                        ),
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 10),


                //sign in button
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 25.0),
                  child: GestureDetector(
                    onTap: (){
                      login(emailController.text.toString(), passwordController.text.toString());
                    },
                    child: Container(
                      padding: EdgeInsets.all(20),
                      decoration: BoxDecoration(
                        color: Colors.blue,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Center(
                        child: Text(
                          'Accedi',
                          style: TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                            fontSize: 18,
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 10),

                /*new InkWell(
                  child: new Text('Non sei registrato? Accedi come ospite!'),
                  onTap: Navigator.push(context, route),
                )*/
                RichText(
                  text: TextSpan(
                    children: <TextSpan>[
                      TextSpan(text: 'Non sei registrato? '),
                      TextSpan(text: 'Accedi come ospite!', style: TextStyle(fontWeight: FontWeight.bold), recognizer: _recognizer),
                    ],
                  ),
                )
              ],),
          ),
        )
    );
  }


}


