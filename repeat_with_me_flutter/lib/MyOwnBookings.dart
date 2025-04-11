import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;


class MyOwnBookings extends StatefulWidget{
  MyOwnBookings();

  @override
  _MyOwnBookings createState() => _MyOwnBookings();
}

class _MyOwnBookings extends State<MyOwnBookings>{
  late List data;
  //late List ripes;
  List bookings = [];

  void getMyOwnBookings() async{
    print("Entro in getMyOwnBookings");
    var url = "http://localhost:8081/RepeatWithMe_war_exploded/servlet-my-own-bookings";
    var response = await http.get(
        Uri.parse(url)
    );
    //debugPrint(response.body);
    data = json.decode(response.body);
    setState(() {
      bookings = data;
    });
    debugPrint(bookings.toString());
  }

  @override
  void initState(){
    super.initState();
    getMyOwnBookings();
  }

  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(
        title: Text("Le mie ripetizioni"),
        actions: <Widget>[
          /*IconButton(
            onPressed: (){
              showAlertDialog(context);
            },
            icon: const Icon(
              Icons.logout,
              color: Colors.white,
            ),
          ),*/
        ],
      ),
      body: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child:
        DataTable(
          columns: const <DataColumn>[
            DataColumn(
              label: Expanded(
                child: Text('Docente'),
              ),
            ),
            DataColumn(
              label: Expanded(
                child: Text('Corso'),
              ),
            ),
            DataColumn(
              label: Expanded(
                child: Text('Giorno'),
              ),
            ),
            DataColumn(
              label: Expanded(
                child: Text('Orario'),
              ),
            ),
            DataColumn(
              label: Expanded(
                child: Text('Stato'),
              ),
            ),
          ],

          rows: bookings
              .map(
                (e) => DataRow(
              cells: [
                DataCell(
                  Text(
                    e['teacherName'] + ' ' + e['teacherSurname'],
                    style: TextStyle(
                      fontStyle: FontStyle.italic,
                    ),
                  ),
                ),
                DataCell(
                  Text(
                    e['courseTitle'],
                    style: TextStyle(
                      fontStyle: FontStyle.italic,
                    ),
                  ),
                ),
                DataCell(
                  Text(
                    e['day'],
                    style: TextStyle(
                      fontStyle: FontStyle.italic,
                    ),
                  ),
                ),
                DataCell(
                  Text(
                    e['hour'],
                    style: TextStyle(
                      fontStyle: FontStyle.italic,
                    ),
                  ),
                ),
                DataCell(
                  Text(
                    e['state'],
                    style: TextStyle(
                      fontStyle: FontStyle.italic,
                    ),
                  ),
                ),
              ],
            ),
          )
              .toList(),
        ),
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

  void logOut () async{
    var url = 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-logout';
    await http.get( Uri.parse(url));
  }
}