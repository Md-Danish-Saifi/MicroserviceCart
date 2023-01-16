import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppserviceService } from '../Services/appservice.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  constructor(private service: AppserviceService, private route: Router) {}

  ngOnInit(): void {}

  userInfo = {
    name: '',
    email: '',
    password: '',
  };

  registerNow() {
    this.service.registerCustomer(this.userInfo).subscribe((res) => {
      console.log('responese is ', res.Msg);
      if (res.Result == 'Success') {
        localStorage.setItem('token', res.data.customerId);
        this.route.navigateByUrl('home')
      }
    });
  }
}
