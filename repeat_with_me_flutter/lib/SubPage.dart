import 'package:flutter/material.dart';

class SubPage extends StatelessWidget{
  late String Title;
  SubPage(String title){
    Title = title ;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.red,
        centerTitle: true,
        title: Text(Title),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: (){
            Navigator.pop(context);
          },
          child: Text("back to HOME"),
        ),
      ),
    );
  }
  
}