import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    "username" : '',
    "password" : '',
  }

  constructor(private snack:MatSnackBar, private loginService:LoginService, private router:Router) { }

  ngOnInit(): void {

    if (this.loginService.isLoggedIn()) {
      this.router.navigate(['warehouses']);
    }

  }

  formSubmit(){
    if(this.loginData.username.trim() == '' || this.loginData.username.trim() == null){
      this.snack.open('Username is required','Accept',{
        duration:3000
      })
      return;
    }

    if(this.loginData.password.trim() == '' || this.loginData.password.trim() == null){
      this.snack.open('Password is required','Aceptar',{
        duration:3000
      })
      return;
    }

    this.loginService.generateToken(this.loginData).subscribe(
      (data:any) => {
        console.log(data);
        this.loginService.loginUser(data.tokenAcess);
        this.loginService.getCurrentUser().subscribe((user:any) => {
          this.loginService.setUser(user);
          console.log(user);
          this.router.navigate(['warehouses']);
          this.loginService.loginStatusSubjec.next(true);
        })
      },
      (error) => {
        console.log(error);
        Swal.fire(error.error.message[0], '', 'error')}    // errorHandler

    )
  }

}
