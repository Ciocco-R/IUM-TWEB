import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class BookingStateDeleted extends StatefulWidget{
  BookingStateDeleted();

  @override
  _BookingStateDeleted createState() => _BookingStateDeleted();
}

class _BookingStateDeleted extends State<BookingStateDeleted>{
  List bookings = [];
  late List data;

  void getMyOwnBookings() async{
    print("Entro in getMyOwnBookings");
    var url = "http://localhost:8081/RepeatWithMe_war_exploded/servlet-my-own-bookings";
    var response = await http.get(
        Uri.parse(url)
    );
    debugPrint(response.body);
    data = json.decode(response.body);
    setState(() {
      for(var e in data){
        if(e['state'].toString() == "active"){
          bookings.add(e);
        }
      }

    });
    debugPrint(bookings.toString());
  }

  void changeBookingStateToDeleted(String teacherName, teacherSurname, courseTitle, day, hour, state) async{
    print("Entro in changeBookingStateToDone");
    print(teacherName + teacherSurname + courseTitle + day + hour + state);
    var url = "http://localhost:8081/RepeatWithMe_war_exploded/servlet-booking-state";
    var response = await http.post(
        Uri.parse(url),
        body:{
          "teacherName":teacherName,
          "teacherSurname":teacherSurname,
          "courseTitle":courseTitle,
          "day":day,
          "hour":hour,
          "state": "deleted"
        }
    );
    //getMyOwnBookings();
  }

  void initState(){
    super.initState();
    getMyOwnBookings();
  }

  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(
        title: Text("Disdici una ripetizione"),
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
          columnSpacing: (MediaQuery.of(context).size.width / 10) * 0.5,
          showCheckboxColumn: false,
          columns: <DataColumn>[
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
                cells : [
                  DataCell(
                    Text(
                      e['teacherName']+' '+e['teacherSurname'],
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
                /*onSelectChanged: (selected){
                  print('selected row');
                  changeBookingStateToDeleted(e['teacherName'], e['teacherSurname'],e['courseTitle'], e['day'], e['hour'], e['state']);
                  setState((){
                    bookings.remove(e);
                  });
                }*/
                    onSelectChanged: (selected) => showAlertDialog(context, e),

            ),
          )
              .toList(),
        ),
      ),
    );
  }

  showAlertDialog(BuildContext context, dynamic e) {

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
        changeBookingStateToDeleted(e['teacherName'], e['teacherSurname'],e['courseTitle'], e['day'], e['hour'], e['state']);
        setState((){
          bookings.remove(e);
          Navigator.of(context, rootNavigator: true).pop('dialog');
        });
      },
    );

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      //title: Text("AlertDialog"),
      content: Text("Vuoi disdire la ripetizione selezionata?"),
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

  showAlertDialogLogout(BuildContext context) {

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
