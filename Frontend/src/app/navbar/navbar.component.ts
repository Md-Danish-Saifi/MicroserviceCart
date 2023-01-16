import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  constructor(private route: Router) {}
role:any;
  ngOnInit(): void {
    this.role = localStorage.getItem('role')
  }

  logout() {
    sessionStorage.clear();
    localStorage.clear();
    this.route.navigateByUrl('/login');
  }
}
