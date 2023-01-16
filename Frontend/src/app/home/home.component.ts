import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppserviceService } from '../Services/appservice.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  products: any = [];
 

  constructor(private service: AppserviceService,private route:Router) {}

  ngOnInit(): void {
    this.getAllProd();
   
  }

  getAllProd() {
    this.service.getAllProduct().subscribe((res) => {
      if (res.Result == 'Success') {
        this.products = res.products;
      }
      console.log('response is', this.products);
    });
  }

  addProdToCart(data: any) {
    const cartItem = {
      title: data.title,
      price: data.price,
      description: data.description,
      image: data.image,
      userId: localStorage.getItem('id'),
    };
    this.service.addToCart(cartItem).subscribe((res) => {
      if (res.Result == 'Success') {
        this.route.navigateByUrl('/cart');
      }
      console.log('response is', this.products);
    });
  }
}
