import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppserviceService } from '../Services/appservice.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(private service: AppserviceService, private route: Router) {}

  ngOnInit(): void {
    this.checkLogin();
  }

  userInfo = {
    email: '',
    password: '',
  };
  checkLogin() {
    const token = localStorage.getItem('token')
    if(token){
      this.route.navigateByUrl('/home')
    }
  }
  logIn() {
    this.service.loginCustomer(this.userInfo).subscribe((res) => {
      if (res.Result == 'Success') {
        localStorage.setItem('token', res.token);
        localStorage.setItem('id', res.userDetails.id);
        localStorage.setItem('role', res.userDetails.role);
        this.route.navigateByUrl('home');
      }
    });
  }
}
