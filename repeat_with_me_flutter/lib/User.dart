class User{
  String email = '';
  String username = '';
  String role = '';

  User(String email, String username, String role){
    this.email = email;
    this.username = username;
    this.role = role;
  }

  String getRole(){
    return this.role;
  }

  String getEmail(){
    return this.email;
  }

  String getUsername(){
    return this.username;
  }
}

