import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:repeat_with_me_flutter/AvailableRepetitions.dart';
import 'package:repeat_with_me_flutter/BookingStateDeletedAdmin.dart';
import './SubPage.dart';
import 'User.dart';
import 'package:http/http.dart' as http;

class HomePageAdmin extends StatefulWidget{
  late User u;
  HomePageAdmin(User u){
    this.u = u;
  }

  @override
  _HomePageState createState()=> _HomePageState();
}

class _HomePageState extends State<HomePageAdmin>{
  void logOut () async{
    var url = 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-logout';
    await http.get( Uri.parse(url));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Text("Bentornato"),
        actions: <Widget>[
          IconButton(
            onPressed: (){
              showAlertDialog(context);
            },
            icon: const Icon(
              Icons.logout,
              color: Colors.white,
            ),
          )
        ],
      ),
      body: Container(
        padding: EdgeInsets.symmetric(vertical: 10.0,horizontal: 10.0),
        height: MediaQuery.of(context).size.height,
        color: Colors.grey.shade300,
        child:Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Expanded(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.stretch ,
                children: <Widget>[
                  getExpamded("Ripetizioni disponibili","AR"),
                ],
              ),
            ),
            Expanded(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.stretch ,
                children: <Widget>[
                  getExpamded("Disdici una prenotazione","BSX"),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }




  Flexible getExpamded(String title,String PageOperzione){
    return Flexible(
      child: TextButton(
        style: TextButton.styleFrom(
            padding: EdgeInsets.all(0)
        ),
        child: Container(
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                if(PageOperzione == "AR")...[
                  Icon(
                    Icons.calendar_month,
                    size: 80,
                  )
                ] else if(PageOperzione == "BSX")...[
                  Icon(
                    Icons.indeterminate_check_box_outlined,
                    size:80,
                  )
                ],
                //SizedBox(child: const ColoredBox(color: Colors.amber),width: 90,height: 90,),
                Text(title,style: TextStyle( fontWeight : FontWeight.bold ,fontSize: 15.0)),
              ],
            ),
          ),
          height: 180,
          width: 180,
          margin: EdgeInsets.only(left: 10.0 ,top: 0,right: 10.0,bottom: 10.0),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.only(
                topLeft: Radius.circular(6),
                topRight: Radius.circular(6),
                bottomLeft:Radius.circular(6),
                bottomRight: Radius.circular(6)
            ),
            boxShadow: [
              BoxShadow()
            ],
          ),
        ),
        onPressed: (){
          if(PageOperzione == "AR"){
            Navigator.push(context, MaterialPageRoute(builder: (context)=>AvailableRepetitions()));
          } else if(PageOperzione == "BSX"){
            Navigator.push(context, MaterialPageRoute(builder: (context) => BookingStateDeletedAdmin()));
          }
        },
      ),
    );
  }

  showAlertDialog(BuildContext context) {

    // set up the buttons
    Widget cancelButton = TextButton(
      child: Text("Chiudi"),
      onPressed:  () {
        Navigator.of(context, rootNavigator: true).pop('dialog');
      },
    );
    Widget continueButton = TextButton(
      child: Text("Sì"),
      onPressed:  () {
        logOut();
        Navigator.of(context, rootNavigator: true).pop('dialog');
        Navigator.pop(context);
      },
    );

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      //title: Text("AlertDialog"),
      content: Text("Vuoi effettuare il logout?"),
      actions: [
        cancelButton,
        continueButton,
      ],
    );

    // show the dialog
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }
}
