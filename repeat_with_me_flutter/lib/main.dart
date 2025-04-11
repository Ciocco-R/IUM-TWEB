import 'package:flutter/material.dart';
import 'package:repeat_with_me_flutter/AvailableRepetitions.dart';
import 'package:repeat_with_me_flutter/BookingStateDone.dart';
import 'package:repeat_with_me_flutter/User.dart';
import 'package:repeat_with_me_flutter/homeAdmin.dart';
import 'InsertBooking.dart';
import 'login_page.dart';
import 'homeClient.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: LoginPage(),
      //home: HomePageAdmin(new User("c1@gmail.com", "c1", "admin")),
      //home:AvailableRepetitions(),
      //home: InsertBooking(),
      //home: BookingStateDone(),
    );
  }
}