import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;


class InsertBooking extends StatefulWidget{
  InsertBooking();

  @override
  _InsertBooking createState() => _InsertBooking();
}

class _InsertBooking extends State<InsertBooking> {
  String _myCourse="";
  bool c1 = true;
  String _myTeacher="";
  bool vBookings = false;
  List courseList =[];
  List teacherList =[];
  List _bookinkList=[];
  List daPrenotareList=[];

  Future<void> _getCoueseList() async {
    print("entro in getCourses");

    var url = 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations?action=getC';

    var response = await http.get( Uri.parse(url));
    print("   "+response.body);
    var data = json.decode(response.body);
    setState(() {
      courseList = data;
    });
    if(c1){
      _myCourse =courseList.first['titolo'].toString();
      c1 = false;
    }
    print("   "+courseList.toString());
  }

  Future<void> _getTeacherList() async {
    print("entro in getCourses");

    var url = 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations?action=getTFromC&titleC='+_myCourse.toString();
    print("   URL : "+url);

    var response = await http.get( Uri.parse(url));

    print("   "+response.body);
    var data ;
    setState(() {
      data=json.decode(response.body);
      teacherList = data;
    });
    if(teacherList.isEmpty){
      _myTeacher="No theacher";
    }else{
      _myTeacher=teacherList.first['name'].toString()+","
          +teacherList.first['surname'].toString();
    }

    print("   data da prof : "+data.toString());
  }

  Future<void> getBfromTC() async {
    print("entro in getBfromTC");

    var url = 'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking-operations?action=getBfromTC&courseTitle='+_myCourse+'&nameTeacher='+_myTeacher.split(',')[0]+'&surnameTeacher='+_myTeacher.split(',')[1];

    var response = await http.get( Uri.parse(url));
    print("   B:"+response.body);
    var data = json.decode(response.body);
    setState(() {
      _bookinkList = data;
    });
    vBookings=true;
    print("   B:"+_bookinkList.toString());
  }
  Future<void> insertBooking(String day , String hour) async {
    print("entro in insertBooking");

    var url =
        'http://localhost:8081/RepeatWithMe_war_exploded/servlet-insert-booking?teacherName='+_myTeacher.split(',')[0]+
            '&teacherSurname='+_myTeacher.split(',')[1]+
            '&courseTitle='+_myCourse+
            '&day='+day+
            '&hour='+hour;

    await http.get( Uri.parse(url));

  }

  @override
  void initState() {
    _getCoueseList();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Prenota una ripetizione'),
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
          child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(left: 15, right: 15, top: 5),
                  color: Colors.white,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Expanded(
                        child: DropdownButtonHideUnderline(
                          child: ButtonTheme(
                            alignedDropdown: true,
                            child: DropdownButton<String>(
                              value: _myCourse,
                              iconSize: 30,
                              icon: (null),
                              style: TextStyle(
                                color: Colors.black54,
                                fontSize: 16,
                              ),
                              hint: Text('Seleziona corso'),
                              onChanged: (newValue) {
                                setState(() {
                                  print("onChanged setState");
                                  _myCourse = newValue!;
                                  vBookings=false;
                                  _getTeacherList();
                                  print("   _myCourse " + _myCourse);
                                });
                              },
                              items: courseList.map((item){
                                return DropdownMenuItem(
                                    value: item['titolo'].toString(),
                                    child: Text(item['titolo'].toString())
                                );
                              }).toList(),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                SizedBox(
                  height: 30,
                ),
                Container(
                  padding: EdgeInsets.only(left: 15, right: 15, top: 5),
                  color: Colors.white,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Expanded(
                        child: DropdownButtonHideUnderline(
                          child: ButtonTheme(
                            alignedDropdown: true,
                            child: DropdownButton<String>(
                              value: _myTeacher,
                              iconSize: 30,
                              icon: (null),
                              style: TextStyle(
                                color: Colors.black54,
                                fontSize: 16,
                              ),
                              hint: Text('Seleziona insegnante'),
                              onChanged:(val){setState(() {
                                _myTeacher = val!;
                                print(_myTeacher);
                                getBfromTC();
                              });},
                              items: teacherList.map((item){
                                print("Entro items");
                                print("   "+item.toString());
                                return DropdownMenuItem(
                                    value: item['name'].toString()+","+item['surname'].toString(),
                                    child: Text(item['name'].toString()
                                        +" "+
                                        item['surname'].toString()
                                    )
                                );
                              }).toList(),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                SizedBox(
                  height: 30,
                ),

                Container(
                  child: Column(
                    children: <Widget>[
                      if(vBookings)
                        Text("Seleziona Le lezioni che desideri prenotare "),
                      if(vBookings)
                        DataTable(
                          columnSpacing: (MediaQuery.of(context).size.width / 10) * 0.5,
                          showCheckboxColumn: false,
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
                          ],

                          rows:_bookinkList.map(
                                (e) => DataRow(
                              /*onSelectChanged: (bool? selected) {
                                if (selected is bool && selected) {
                                  insertBooking(e['day'], e['hour']);
                                  setState(() {
                                    _bookinkList.remove(e);
                                  });
                                }
                                print(_bookinkList);
                              },*/
                                  onSelectChanged: (bool? selected) => showAlertDialog(context, e, selected),
                              cells: [
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
                              ],
                            ),
                          ).toList(),
                        ),
                    ],
                  ),
                )
              ]
          ),
        )
    );
  }

  showAlertDialog(BuildContext context, dynamic e, bool? selected) {

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
        if (selected is bool && selected) {
          insertBooking(e['day'], e['hour']);
          setState(() {
            _bookinkList.remove(e);
            Navigator.of(context, rootNavigator: true).pop('dialog');
          });
        }
      },
    );

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      //title: Text("AlertDialog"),
      content: Text("Vuoi prenotare la ripetizione selezionata?"),
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